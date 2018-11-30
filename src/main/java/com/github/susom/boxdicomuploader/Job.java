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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
  "source",
  "destination",
  "sessionId",
  "accessToken",
  "refreshToken",
  "errorMessage",
  "statusMessage",
  "percentComplete",
  "completed"
})
public class Job implements Serializable {

  private static final long serialVersionUID = 3537076580923255347L;

  @JsonProperty("source")
  private String source;

  @JsonProperty("destination")
  private String destination;

  @JsonProperty("sessionId")
  private String sessionId;

  @JsonProperty("accessToken")
  private String accessToken;

  @JsonProperty("refreshToken")
  private String refreshToken;

  @JsonProperty("errorMessage")
  private String errorMessage;

  @JsonProperty("statusMessage")
  private String statusMessage;

  @JsonProperty("percentComplete")
  private Integer percentComplete;

  @JsonProperty("completed")
  private Boolean completed;

  /** No args constructor for use in serialization */
  public Job() {}

  /**
   * @param errorMessage
   * @param percentComplete
   * @param accessToken
   * @param source
   * @param sessionId
   * @param refreshToken
   * @param completed
   * @param statusMessage
   * @param destination
   */
  public Job(
      String source,
      String destination,
      String sessionId,
      String accessToken,
      String refreshToken,
      String errorMessage,
      String statusMessage,
      Integer percentComplete,
      Boolean completed) {
    super();
    this.source = source;
    this.destination = destination;
    this.sessionId = sessionId;
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
    this.errorMessage = errorMessage;
    this.statusMessage = statusMessage;
    this.percentComplete = percentComplete;
    this.completed = completed;
  }

  @JsonProperty("source")
  public String getSource() {
    return source;
  }

  @JsonProperty("source")
  public void setSource(String source) {
    this.source = source;
  }

  public Job withSource(String source) {
    this.source = source;
    return this;
  }

  @JsonProperty("destination")
  public String getDestination() {
    return destination;
  }

  @JsonProperty("destination")
  public void setDestination(String destination) {
    this.destination = destination;
  }

  public Job withDestination(String destination) {
    this.destination = destination;
    return this;
  }

  @JsonProperty("sessionId")
  public String getSessionId() {
    return sessionId;
  }

  @JsonProperty("sessionId")
  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  public Job withSessionId(String sessionId) {
    this.sessionId = sessionId;
    return this;
  }

  @JsonProperty("accessToken")
  public String getAccessToken() {
    return accessToken;
  }

  @JsonProperty("accessToken")
  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public Job withAccessToken(String accessToken) {
    this.accessToken = accessToken;
    return this;
  }

  @JsonProperty("refreshToken")
  public String getRefreshToken() {
    return refreshToken;
  }

  @JsonProperty("refreshToken")
  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  public Job withRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
    return this;
  }

  @JsonProperty("errorMessage")
  public String getErrorMessage() {
    return errorMessage;
  }

  @JsonProperty("errorMessage")
  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public Job withErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
    return this;
  }

  @JsonProperty("statusMessage")
  public String getStatusMessage() {
    return statusMessage;
  }

  @JsonProperty("statusMessage")
  public void setStatusMessage(String statusMessage) {
    this.statusMessage = statusMessage;
  }

  public Job withStatusMessage(String statusMessage) {
    this.statusMessage = statusMessage;
    return this;
  }

  @JsonProperty("percentComplete")
  public Integer getPercentComplete() {
    return percentComplete;
  }

  @JsonProperty("percentComplete")
  public void setPercentComplete(Integer percentComplete) {
    this.percentComplete = percentComplete;
  }

  public Job withPercentComplete(Integer percentComplete) {
    this.percentComplete = percentComplete;
    return this;
  }

  @JsonProperty("completed")
  public Boolean getCompleted() {
    return completed;
  }

  @JsonProperty("completed")
  public void setCompleted(Boolean completed) {
    this.completed = completed;
  }

  public Job withCompleted(Boolean completed) {
    this.completed = completed;
    return this;
  }
}
