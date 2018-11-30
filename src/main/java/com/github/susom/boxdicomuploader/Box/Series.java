
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
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "dicomMetadata",
    "meta",
    "objects"
})
public class Series implements Serializable
{

    @JsonProperty("dicomMetadata")
    private SeriesMetadata dicomMetadata;
    @JsonProperty("meta")
    private SeriesMeta meta;
    @JsonProperty("objects")
    private List<Instance> objects = null;
    private final static long serialVersionUID = 3095062878437194581L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Series() {
    }

    /**
     * 
     * @param objects
     * @param meta
     * @param dicomMetadata
     */
    public Series(SeriesMetadata dicomMetadata, SeriesMeta meta, List<Instance> objects) {
        super();
        this.dicomMetadata = dicomMetadata;
        this.meta = meta;
        this.objects = objects;
    }

    @JsonProperty("dicomMetadata")
    public SeriesMetadata getDicomMetadata() {
        return dicomMetadata;
    }

    @JsonProperty("dicomMetadata")
    public void setDicomMetadata(SeriesMetadata dicomMetadata) {
        this.dicomMetadata = dicomMetadata;
    }

    public Series withDicomMetadata(SeriesMetadata dicomMetadata) {
        this.dicomMetadata = dicomMetadata;
        return this;
    }

    @JsonProperty("meta")
    public SeriesMeta getMeta() {
        return meta;
    }

    @JsonProperty("meta")
    public void setMeta(SeriesMeta meta) {
        this.meta = meta;
    }

    public Series withMeta(SeriesMeta meta) {
        this.meta = meta;
        return this;
    }

    @JsonProperty("objects")
    public List<Instance> getObjects() {
        return objects;
    }

    @JsonProperty("objects")
    public void setObjects(List<Instance> objects) {
        this.objects = objects;
    }

    public Series withObjects(List<Instance> objects) {
        this.objects = objects;
        return this;
    }

}
