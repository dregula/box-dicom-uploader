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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.io.Serializable;
import java.nio.file.Path;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"dicomMetadata", "meta"})
public class Instance implements Serializable {

  private static final long serialVersionUID = 3400780104438881703L;

  @JsonProperty("dicomMetadata")
  private InstanceMetadata dicomMetadata;

  @JsonProperty("meta")
  private InstanceMeta meta;

  @JsonIgnore private Path path;

  /** No args constructor for use in serialization */
  public Instance() {}

  /**
   * @param meta
   * @param dicomMetadata
   */
  public Instance(InstanceMetadata dicomMetadata, InstanceMeta meta) {
    super();
    this.dicomMetadata = dicomMetadata;
    this.meta = meta;
  }

  public Path getPath() {
    return path;
  }

  public void setPath(Path file) {
    this.path = file;
  }

  @JsonProperty("dicomMetadata")
  public InstanceMetadata getDicomMetadata() {
    return dicomMetadata;
  }

  @JsonProperty("dicomMetadata")
  public void setDicomMetadata(InstanceMetadata dicomMetadata) {
    this.dicomMetadata = dicomMetadata;
  }

  public Instance withDicomMetadata(InstanceMetadata dicomMetadata) {
    this.dicomMetadata = dicomMetadata;
    return this;
  }

  @JsonProperty("meta")
  public InstanceMeta getMeta() {
    return meta;
  }

  @JsonProperty("meta")
  public void setMeta(InstanceMeta meta) {
    this.meta = meta;
  }

  public Instance withMeta(InstanceMeta meta) {
    this.meta = meta;
    return this;
  }

  public Instance withPath(Path path) {
    this.path = path;
    return this;
  }
}
