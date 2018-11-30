
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

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "dicomUrl",
    "fileId",
    "fileVersionId"
})
public class InstanceMeta implements Serializable
{

    @JsonProperty("dicomUrl")
    private String dicomUrl;
    @JsonProperty("fileId")
    private String fileId;
    @JsonProperty("fileVersionId")
    private String fileVersionId;
    private final static long serialVersionUID = -8612693977236293560L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public InstanceMeta() {
    }

    /**
     * 
     * @param fileId
     * @param fileVersionId
     * @param dicomUrl
     */
    public InstanceMeta(String dicomUrl, String fileId, String fileVersionId) {
        super();
        this.dicomUrl = dicomUrl;
        this.fileId = fileId;
        this.fileVersionId = fileVersionId;
    }

    @JsonProperty("dicomUrl")
    public String getDicomUrl() {
        return dicomUrl;
    }

    @JsonProperty("dicomUrl")
    public void setDicomUrl(String dicomUrl) {
        this.dicomUrl = dicomUrl;
    }

    public InstanceMeta withDicomUrl(String dicomUrl) {
        this.dicomUrl = dicomUrl;
        return this;
    }

    @JsonProperty("fileId")
    public String getFileId() {
        return fileId;
    }

    @JsonProperty("fileId")
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public InstanceMeta withFileId(String fileId) {
        this.fileId = fileId;
        return this;
    }

    @JsonProperty("fileVersionId")
    public String getFileVersionId() {
        return fileVersionId;
    }

    @JsonProperty("fileVersionId")
    public void setFileVersionId(String fileVersionId) {
        this.fileVersionId = fileVersionId;
    }

    public InstanceMeta withFileVersionId(String fileVersionId) {
        this.fileVersionId = fileVersionId;
        return this;
    }

}
