
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
    "series"
})
public class Study implements Serializable
{

    @JsonProperty("dicomMetadata")
    private StudyMetadata dicomMetadata;
    @JsonProperty("meta")
    private StudyMeta meta;
    @JsonProperty("series")
    private List<Series> series = null;
    private final static long serialVersionUID = 3702698535288034761L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Study() {
    }

    /**
     * 
     * @param series
     * @param meta
     * @param dicomMetadata
     */
    public Study(StudyMetadata dicomMetadata, StudyMeta meta, List<Series> series) {
        super();
        this.dicomMetadata = dicomMetadata;
        this.meta = meta;
        this.series = series;
    }

    @JsonProperty("dicomMetadata")
    public StudyMetadata getDicomMetadata() {
        return dicomMetadata;
    }

    @JsonProperty("dicomMetadata")
    public void setDicomMetadata(StudyMetadata dicomMetadata) {
        this.dicomMetadata = dicomMetadata;
    }

    public Study withDicomMetadata(StudyMetadata dicomMetadata) {
        this.dicomMetadata = dicomMetadata;
        return this;
    }

    @JsonProperty("meta")
    public StudyMeta getMeta() {
        return meta;
    }

    @JsonProperty("meta")
    public void setMeta(StudyMeta meta) {
        this.meta = meta;
    }

    public Study withMeta(StudyMeta meta) {
        this.meta = meta;
        return this;
    }

    @JsonProperty("series")
    public List<Series> getSeries() {
        return series;
    }

    @JsonProperty("series")
    public void setSeries(List<Series> series) {
        this.series = series;
    }

    public Study withSeries(List<Series> series) {
        this.series = series;
        return this;
    }

}
