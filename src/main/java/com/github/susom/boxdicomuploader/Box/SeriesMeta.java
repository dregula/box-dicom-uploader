
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
    "folderId"
})
public class SeriesMeta implements Serializable
{

    @JsonProperty("folderId")
    private String folderId;
    private final static long serialVersionUID = -7751773279309363385L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public SeriesMeta() {
    }

    /**
     * 
     * @param folderId
     */
    public SeriesMeta(String folderId) {
        super();
        this.folderId = folderId;
    }

    @JsonProperty("folderId")
    public String getFolderId() {
        return folderId;
    }

    @JsonProperty("folderId")
    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    public SeriesMeta withFolderId(String folderId) {
        this.folderId = folderId;
        return this;
    }

}
