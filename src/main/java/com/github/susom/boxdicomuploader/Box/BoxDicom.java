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

package com.github.susom.boxdicomuploader.Box;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"study"})
public class BoxDicom implements Serializable {

  private static final long serialVersionUID = 3766819188801721751L;

  @JsonProperty("study")
  private Study study;

  /** No args constructor for use in serialization */
  public BoxDicom() {}

  /** @param study */
  public BoxDicom(Study study) {
    super();
    this.study = study;
  }

  @JsonProperty("study")
  public Study getStudy() {
    return study;
  }

  @JsonProperty("study")
  public void setStudy(Study study) {
    this.study = study;
  }

  public BoxDicom withStudy(Study study) {
    this.study = study;
    return this;
  }

    /**
     * Adds or a replaces an existing DICOM instance to this object
     * @param attr DICOM attributes
     */
  public void addInstance(Path path, Attributes attr) {

    String studyInstanceUID = attr.getString(Tag.StudyInstanceUID);
    String seriesInstanceUID = attr.getString(Tag.SeriesInstanceUID);
    String sopInstanceUID = attr.getString(Tag.SOPInstanceUID);

    if (this.study == null) {
      this.withStudy(
          new Study()
              .withDicomMetadata(
                  new StudyMetadata()
                      .withModality(attr.getString(Tag.Modality))
                      .withPatientBirthDate(attr.getString(Tag.PatientBirthDate))
                      .withPatientID(attr.getString(Tag.PatientID))
                      .withPatientName(attr.getString(Tag.PatientName))
                      .withReferringPhysicianName(attr.getString(Tag.ReferringPhysicianName))
                      .withStudyDate(attr.getString(Tag.StudyDate))
                      .withStudyDescription(attr.getString(Tag.StudyDescription))
                      .withStudyInstanceUID(studyInstanceUID))
              .withSeries(new ArrayList<>()));
    }

    Series series =
        this.study
            .getSeries()
            .stream()
            .filter(s -> s.getDicomMetadata().getSeriesInstanceUID().equals(seriesInstanceUID))
            .findFirst()
            .orElse(null);

    if (series == null) {
      series =
          new Series()
              .withDicomMetadata(
                  new SeriesMetadata()
                      .withSeriesInstanceUID(attr.getString(Tag.SeriesInstanceUID))
                      .withSeriesDescription(attr.getString(Tag.SeriesDescription))
                      .withSeriesNumber(attr.getString(Tag.SeriesNumber)))
              .withObjects(new ArrayList<>());
      this.study.getSeries().add(series);
    }

    Instance instance =
        series
            .getObjects()
            .stream()
            .filter(s -> s.getDicomMetadata().getSOPInstanceUID().equals(sopInstanceUID))
            .findFirst()
            .orElse(null);

    if (instance == null) {

      InstanceMetadata instanceMetadata =
          new InstanceMetadata()
              .withSOPInstanceUID(sopInstanceUID)
              .withInstanceNumber(attr.getInt(Tag.InstanceNumber, 1))
              .withAcquisitionDate(attr.getString(Tag.AcquisitionDate))
              .withAcquisitionTime(attr.getString(Tag.AcquisitionTime))
              .withContentDate(attr.getString(Tag.ContentDate))
              .withContentTime(attr.getString(Tag.ContentTime))
              .withNumberOfFrames(attr.getInt(Tag.NumberOfFrames, 1));

      if (attr.getInt(Tag.Columns, 0) != 0)
        instanceMetadata.withColumns(attr.getInt(Tag.Columns, 0));

      if (attr.getInt(Tag.Rows, 0) != 0) instanceMetadata.withRows(attr.getInt(Tag.Rows, 0));

      if (attr.getInts(Tag.ImageOrientationPatient) != null) {
        instanceMetadata.withImageOrientationPatient(
            Arrays.stream(attr.getInts(Tag.ImageOrientationPatient))
                .boxed()
                .collect(Collectors.toList()));
      } else {
        instanceMetadata.withImageOrientationPatient(Arrays.asList(0, 0, 0, 0, 0, 0));
      }

      if (attr.getInts(Tag.ImagePositionPatient) != null) {
        instanceMetadata.withImagePositionPatient(
            Arrays.stream(attr.getInts(Tag.ImagePositionPatient))
                .boxed()
                .collect(Collectors.toList()));
      } else {
        instanceMetadata.withImagePositionPatient(Arrays.asList(0, 0, 0, 1));
      }

      if (attr.getDoubles(Tag.PixelSpacing) != null) {
        instanceMetadata.withPixelSpacing(
            Arrays.stream(attr.getDoubles(Tag.PixelSpacing)).boxed().collect(Collectors.toList()));
      }

      instance = new Instance().withDicomMetadata(instanceMetadata).withPath(path);
      series.getObjects().add(instance);
    }
  }
}
