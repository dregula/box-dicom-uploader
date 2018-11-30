/*
 * Copyright 2018 The Board of Trustees of The Leland Stanford Junior University.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.susom.boxdicomuploader;

import com.box.sdk.BoxAPIConnection;
import com.box.sdk.BoxAPIException;
import com.box.sdk.BoxFile;
import com.box.sdk.BoxFolder;
import com.box.sdk.BoxItem;
import com.github.susom.boxdicomuploader.Box.BoxDicom;
import com.github.susom.boxdicomuploader.Box.Instance;
import com.github.susom.boxdicomuploader.Box.InstanceMeta;
import com.github.susom.boxdicomuploader.Box.Series;
import com.github.susom.boxdicomuploader.Box.SeriesMeta;
import com.github.susom.boxdicomuploader.Box.Study;
import com.github.susom.boxdicomuploader.Box.StudyMeta;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.DicomInputStream;
import org.dcm4che3.util.SafeClose;

public class Worker extends AbstractVerticle {

  private static Logger LOG = LoggerFactory.getLogger(Worker.class);

  /**
   * Convenience main for testing purposes
   * @param args
   */
  public static void main(String[] args) {

    Job job =
        new Job()
            .withAccessToken("(put an oauth access token here)")
            .withDestination("(put a Box folderId here")
            .withSource("dicom");

    long startTime = System.currentTimeMillis();
    upload(job, Vertx.vertx());
    long elapsedTime = System.currentTimeMillis() - startTime;

    LOG.info("Ran for {} ms", elapsedTime);
  }

  // TODO: Switch to the box uploader file naming conventions
  private static void upload(Job job, Vertx vertx) {

    String accessToken = job.getAccessToken();
    Path sourceDir = Paths.get(job.getSource());
    String destinationId = job.getDestination();

    BoxAPIConnection api = new BoxAPIConnection(accessToken);

    LOG.debug(
        "Session {} is uploading {} to {}",
        job.getSessionId(),
        job.getSource(),
        job.getDestination());

    job.setPercentComplete(0);
    job.setStatusMessage("Running");

    vertx.sharedData().getLocalMap("jobs").put(job.getSessionId(), JsonObject.mapFrom(job));

    try {

      // Create the prelim .boxdicom objects (without folderId / fileId)
      List<BoxDicom> boxStudies =
          Files.list(sourceDir)
              .filter(Files::isDirectory)
              .map(
                  study -> {
                    BoxDicom boxDicom = new BoxDicom();
                    try {
                      Files.walk(study)
                          .filter(Files::isRegularFile)
                          .forEach(
                              path -> {
                                try {
                                  Attributes attrs = getDicomTags(path);
                                  boxDicom.addInstance(path, attrs);
                                } catch (IOException e) {
                                  LOG.error("Error parsing tags for {}", path.toString(), e);
                                }
                              });
                    } catch (IOException e) {
                      LOG.error(e);
                    }
                    return boxDicom;
                  })
              .collect(Collectors.toList());

      // Map of SOPInstanceUID to SeriesInstanceUID so we can upload all series at once
      Map<String, String> objectSeriesMap = new HashMap<>();

      for (BoxDicom boxStudy : boxStudies) {

        long startTime = System.currentTimeMillis();

        Study study = boxStudy.getStudy();

        // Create study root folder
        BoxFolder.Info studyInfo =
            getOrCreateBoxFolder(
                api, destinationId, study.getDicomMetadata().getStudyInstanceUID());
        study.withMeta(new StudyMeta().withFolderId(studyInfo.getID()));

        BoxFolder studyFolder = new BoxFolder(api, studyInfo.getID());

        // Create folder for each series, and record the instances
        LOG.debug("Enumerating series and instances for study {}", study.getDicomMetadata().getStudyInstanceUID());
        for (Series series : study.getSeries()) {
          BoxFolder.Info seriesInfo =
              getOrCreateBoxFolder(
                  api, studyInfo.getID(), series.getDicomMetadata().getSeriesInstanceUID());
          series.withMeta(new SeriesMeta().withFolderId(seriesInfo.getID()));

          // populat series/objects map
          for (Instance instance : series.getObjects()) {
            objectSeriesMap.put(
                instance.getDicomMetadata().getSOPInstanceUID(), seriesInfo.getID());
          }
        }

        // Todo: make this use a more explicit executor so we can ensure only 4 uploads per second?
        new ForkJoinPool(5)
            .submit(
                () -> {
                  study
                      .getSeries()
                      .stream()
                      .flatMap(series -> series.getObjects().stream())
                      .collect(Collectors.toList())
                      .parallelStream()
                      .map(
                          instance -> {
                            BoxFolder seriesFolder =
                                new BoxFolder(
                                    api,
                                    objectSeriesMap.get(
                                        instance.getDicomMetadata().getSOPInstanceUID()));

                            try (FileInputStream stream =
                                new FileInputStream(instance.getPath().toFile())) {
                              LOG.debug(
                                  "Uploading {}", instance.getPath().getFileName().toString());

                              try {
                                long fileSize = instance.getPath().toFile().length();

                                BoxFile.Info instanceInfo;
                                if (fileSize > 20000000L) {
                                  instanceInfo =
                                      seriesFolder.uploadLargeFile(
                                          stream,
                                          instance.getPath().getFileName().toString(),
                                          fileSize);
                                } else {
                                  instanceInfo =
                                      seriesFolder.uploadFile(
                                          stream, instance.getPath().getFileName().toString());
                                }

                                instance.withMeta(
                                    new InstanceMeta()
                                        .withFileId(instanceInfo.getID())
                                        .withFileVersionId(instanceInfo.getVersion().getVersionID())
                                        .withDicomUrl(""));
                              } catch (BoxAPIException | InterruptedException ex) {
                                LOG.error(
                                    "Failed to upload {}",
                                    instance.getPath().getFileName().toString(),
                                    ex);
                              }

                            } catch (FileNotFoundException e) {
                              LOG.error("Could not open input file", e);
                            } catch (IOException e) {
                              e.printStackTrace();
                            }
                            return instance;
                          })
                      .count();
                })
            .get();

        // Now upload the completed study file as JSON
        String json = JsonObject.mapFrom(boxStudy).encodePrettily();

        LOG.debug("Uploading study.boxdicom");
        try {
          try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
            studyFolder.uploadFile(is, "study.boxdicom");
          } catch (IOException e) {
            LOG.error("Error uploading study.boxdicom", e);
          }
        } catch (BoxAPIException ex) {
          LOG.error("Failed to upload study.boxdicom", ex);
        }

        // TODO: Catch errors
        job.setStatusMessage("Completed");
        //        job.setPercentComplete((int) ((++count * 100.0f) / boxStudies.size()));
        vertx.sharedData().getLocalMap("jobs").put(job.getSessionId(), JsonObject.mapFrom(job));

        long elapsedTime = System.currentTimeMillis() - startTime;
        LOG.info(
            "Study upload completed in {} seconds", TimeUnit.MILLISECONDS.toSeconds(elapsedTime));
      }

    } catch (InterruptedException | ExecutionException | IOException ex) {
      LOG.error("Failed to upload study.boxdicom", ex);
    }
  }

  private static BoxFolder.Info getOrCreateBoxFolder(
      BoxAPIConnection api, String folderId, String name) {

    BoxFolder folder = new BoxFolder(api, folderId);

    // We look to see if the folder exists first and return that if it does
    for (BoxItem.Info itemInfo : folder) {
      if (itemInfo instanceof BoxFolder.Info) {
        BoxFolder.Info folderInfo = (BoxFolder.Info) itemInfo;
        if (folderInfo.getName().equals(name)) {
          LOG.debug("Using existing folder for {}", name);
          return folderInfo;
        }
      }
    }

    return folder.createFolder(name);
  }

  /**
   * Reads a dicom file and returns the attributes tags
   *
   * @param path
   * @return tags or null if there was an error
   */
  private static Attributes getDicomTags(Path path) throws IOException {
    try (DicomInputStream in = new DicomInputStream(path.toFile())) {
      in.setIncludeBulkData(DicomInputStream.IncludeBulkData.NO);
      try {
        return in.readDataset(-1, Tag.PixelData);
      } finally {
        SafeClose.close(in);
      }
    }
  }

  @Override
  public void start(Future<Void> done) {

    MessageConsumer<JsonObject> consumer = vertx.eventBus().consumer(Messages.EXECUTE);
    consumer.handler(jsonJob -> upload(jsonJob.body().mapTo(Job.class), vertx));

    done.complete();
  }
}
