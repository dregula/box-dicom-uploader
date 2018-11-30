
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
    "SeriesDescription",
    "SeriesInstanceUID",
    "SeriesNumber"
})
public class SeriesMetadata implements Serializable
{

    @JsonProperty("SeriesDescription")
    private String seriesDescription;
    @JsonProperty("SeriesInstanceUID")
    private String seriesInstanceUID;
    @JsonProperty("SeriesNumber")
    private String seriesNumber;
    private final static long serialVersionUID = -1091174979711996115L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public SeriesMetadata() {
    }

    /**
     * 
     * @param seriesDescription
     * @param seriesNumber
     * @param seriesInstanceUID
     */
    public SeriesMetadata(String seriesDescription, String seriesInstanceUID, String seriesNumber) {
        super();
        this.seriesDescription = seriesDescription;
        this.seriesInstanceUID = seriesInstanceUID;
        this.seriesNumber = seriesNumber;
    }

    @JsonProperty("SeriesDescription")
    public String getSeriesDescription() {
        return seriesDescription;
    }

    @JsonProperty("SeriesDescription")
    public void setSeriesDescription(String seriesDescription) {
        this.seriesDescription = seriesDescription;
    }

    public SeriesMetadata withSeriesDescription(String seriesDescription) {
        this.seriesDescription = seriesDescription;
        return this;
    }

    @JsonProperty("SeriesInstanceUID")
    public String getSeriesInstanceUID() {
        return seriesInstanceUID;
    }

    @JsonProperty("SeriesInstanceUID")
    public void setSeriesInstanceUID(String seriesInstanceUID) {
        this.seriesInstanceUID = seriesInstanceUID;
    }

    public SeriesMetadata withSeriesInstanceUID(String seriesInstanceUID) {
        this.seriesInstanceUID = seriesInstanceUID;
        return this;
    }

    @JsonProperty("SeriesNumber")
    public String getSeriesNumber() {
        return seriesNumber;
    }

    @JsonProperty("SeriesNumber")
    public void setSeriesNumber(String seriesNumber) {
        this.seriesNumber = seriesNumber;
    }

    public SeriesMetadata withSeriesNumber(String seriesNumber) {
        this.seriesNumber = seriesNumber;
        return this;
    }

}
