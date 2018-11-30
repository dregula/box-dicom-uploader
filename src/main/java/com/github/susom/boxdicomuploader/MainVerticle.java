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

import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainVerticle extends AbstractVerticle {

  private static final int WORKER_POOL_SIZE =
      System.getenv("WORKER_POOL_SIZE") != null
          ? Integer.valueOf(System.getenv("WORKER_POOL_SIZE"))
          : 10;

  private static Logger LOG = LoggerFactory.getLogger(MainVerticle.class);

  public static void main(String[] args) {

    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(
        new MainVerticle(),
        result -> {
          if (result.succeeded()) {
            LOG.info("Deployment complete");
          } else {
            LOG.error("Failed to deploy: {} ", result.cause().getMessage(), result.cause());
            vertx.close();
          }
        });
  }

  @Override
  public void start(Future<Void> done) throws Exception {

    LOG.info("Starting {} background workers", WORKER_POOL_SIZE);

    DeploymentOptions workerOpts =
        new DeploymentOptions()
            .setWorker(true)
            .setInstances(WORKER_POOL_SIZE)
            .setWorkerPoolSize(WORKER_POOL_SIZE)
            .setWorkerPoolName("worker-pool")
            .setMaxWorkerExecuteTime(TimeUnit.HOURS.toNanos(8));

    CompositeFuture.all(
            deploy(WebServer.class.getName(), new DeploymentOptions().setInstances(1)),
            deploy(Worker.class.getName(), workerOpts))
        .setHandler(
            r -> {
              if (r.succeeded()) {
                done.complete();
              } else {
                done.fail(r.cause());
              }
            });
  }

  private Future<Void> deploy(String name, DeploymentOptions opts) {
    Future<Void> done = Future.future();

    vertx.deployVerticle(
        name,
        opts,
        res -> {
          if (res.failed()) {
            LOG.error("Failed to deploy verticle " + name);
            done.fail(res.cause());
          } else {
            LOG.debug("Deployed verticle " + name);
            done.complete();
          }
        });

    return done;
  }
}
