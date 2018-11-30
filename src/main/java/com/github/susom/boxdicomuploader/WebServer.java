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
import io.vertx.core.Context;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.core.shareddata.SharedData;
import io.vertx.ext.auth.oauth2.AccessToken;
import io.vertx.ext.auth.oauth2.OAuth2Auth;
import io.vertx.ext.auth.oauth2.providers.BoxAuth;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.OAuth2AuthHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.handler.UserSessionHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
import io.vertx.ext.web.templ.HandlebarsTemplateEngine;

public class WebServer extends AbstractVerticle {

  private static final String CLIENT_ID = System.getenv("BOX_CLIENT_ID");
  private static final String CLIENT_SECRET = System.getenv("BOX_CLIENT_SECRET");
  private static Logger LOG = LoggerFactory.getLogger(WebServer.class);
  private final HandlebarsTemplateEngine engine = HandlebarsTemplateEngine.create();

  @Override
  public void init(Vertx vertx, Context context) {
    super.init(vertx, context);
  }

  @Override
  public void start(Future<Void> startFuture) throws Exception {

    final Router router = Router.router(vertx);

    // We need cookies and sessions
    router.route().handler(CookieHandler.create());
    router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));

    // Get Box provider
    OAuth2Auth authProvider = BoxAuth.create(vertx, CLIENT_ID, CLIENT_SECRET);

    // We need a user session handler too to make sure the user is stored in the session
    router.route().handler(UserSessionHandler.create(authProvider));

    // Need to be authenticated before browsing to /app/*
    router
        .route("/app*")
        .handler(OAuth2AuthHandler.create(authProvider).setupCallback(router.route("/callback")));

    // Entry point to the application
    router
        .get("/")
        .handler(
            ctx ->
                engine.render(
                    ctx,
                    "views",
                    "/index.hbs",
                    rendered -> {
                      if (rendered.succeeded()) {
                        ctx.response().putHeader("Content-Type", "text/html").end(rendered.result());
                      } else {
                        ctx.fail(rendered.cause());
                      }
                    }));

    router.get("/app/browser").handler(this::browseFolder);
    router.get("/app/browser/:id").handler(this::browseFolder);
    router.post("/app/upload/:id").handler(this::upload);
    router.get("/app/job").handler(this::job);

    vertx
        .createHttpServer()
        .requestHandler(router::accept)
        .listen(
            8000,
            ar -> {
              if (ar.succeeded()) {
                LOG.info("HTTP server running on port " + 8000);
                startFuture.complete();
              } else {
                LOG.error("Could not start a HTTP server", ar.cause());
                startFuture.fail(ar.cause());
              }
            });
  }

  @Override
  public void stop() {
    System.out.println("HTTP server stopped");
  }

  private void job(RoutingContext ctx) {
    String sessionId = ctx.session().id();

    SharedData sharedData = vertx.sharedData();
    LocalMap<String, JsonObject> jobs = sharedData.getLocalMap("jobs");

    ctx.put("job", jobs.get(sessionId));
    engine.render(
        ctx,
        "views",
        "/job.hbs",
        rendered -> {
          if (rendered.succeeded()) {
            ctx.response().putHeader("Content-Type", "text/html").end(rendered.result());
          } else {
            ctx.fail(rendered.cause());
          }
        });
  }

  private void upload(RoutingContext ctx) {
    final String folderId = ctx.request().getParam("id");

    AccessToken user = (AccessToken) ctx.user();
    String sessionId = ctx.session().id();
    String accessToken = user.principal().getString("access_token");
    String refreshToken = user.principal().getString("refresh_token");

    Job job = new Job().withSource("dicom-large").withDestination(folderId);
    job.setAccessToken(accessToken);
    job.setRefreshToken(refreshToken);
    job.setSessionId(sessionId);
    job.setStatusMessage("Submitted");

    JsonObject jsonJob = JsonObject.mapFrom(job);

    // Put this job in the shared local map called jobs
    vertx.sharedData().getLocalMap("jobs").putIfAbsent(job.getSessionId(), jsonJob);

    // Submit to the background worker sub
    vertx.eventBus().send(Messages.EXECUTE, jsonJob);

    // Send user to job status page
    ctx.response().putHeader("location", "/app/job").setStatusCode(302).end();
  }

  private void browseFolder(RoutingContext ctx) {
    final String id = ctx.request().getParam("id") != null ? ctx.request().getParam("id") : "0";

    AccessToken user = (AccessToken) ctx.user();

    user.fetch(
        String.format("https://api.box.com/2.0/folders/%s/items", id),
        res1 -> {
          if (res1.failed()) {
            ctx.session().destroy();
            ctx.fail(res1.cause());
          } else {

            // Render folders
            JsonArray entries = res1.result().jsonObject().getJsonArray("entries");
            if (entries != null) {
              JsonArray folders = new JsonArray();
              for (Object entry : entries) {
                if (entry instanceof JsonObject) {
                  if (((JsonObject) entry).getString("type").equals("folder")) {
                    folders.add(entry);
                  }
                }
              }
              ctx.put("folders", folders);
            }

            // Current folder
            ctx.put("id", id);
            ctx.put("session_data", ctx.session().data());

            engine.render(
                ctx,
                "views",
                "/browser.hbs",
                rendered -> {
                  if (rendered.succeeded()) {
                    ctx.response().putHeader("Content-Type", "text/html").end(rendered.result());
                  } else {
                    ctx.fail(rendered.cause());
                  }
                });
          }
        });
  }
}
