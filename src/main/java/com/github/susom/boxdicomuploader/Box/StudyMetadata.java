
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
    "Modality",
    "PatientBirthDate",
    "PatientID",
    "PatientName",
    "ReferringPhysicianName",
    "StudyDate",
    "StudyDescription",
    "StudyInstanceUID"
})
public class StudyMetadata implements Serializable
{

    @JsonProperty("Modality")
    private String modality;
    @JsonProperty("PatientBirthDate")
    private String patientBirthDate;
    @JsonProperty("PatientID")
    private String patientID;
    @JsonProperty("PatientName")
    private String patientName;
    @JsonProperty("ReferringPhysicianName")
    private String referringPhysicianName;
    @JsonProperty("StudyDate")
    private String studyDate;
    @JsonProperty("StudyDescription")
    private String studyDescription;
    @JsonProperty("StudyInstanceUID")
    private String studyInstanceUID;
    private final static long serialVersionUID = -3275023473329260312L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public StudyMetadata() {
    }

    /**
     * 
     * @param patientBirthDate
     * @param studyInstanceUID
     * @param studyDescription
     * @param referringPhysicianName
     * @param patientName
     * @param studyDate
     * @param modality
     * @param patientID
     */
    public StudyMetadata(String modality, String patientBirthDate, String patientID, String patientName, String referringPhysicianName, String studyDate, String studyDescription, String studyInstanceUID) {
        super();
        this.modality = modality;
        this.patientBirthDate = patientBirthDate;
        this.patientID = patientID;
        this.patientName = patientName;
        this.referringPhysicianName = referringPhysicianName;
        this.studyDate = studyDate;
        this.studyDescription = studyDescription;
        this.studyInstanceUID = studyInstanceUID;
    }

    @JsonProperty("Modality")
    public String getModality() {
        return modality;
    }

    @JsonProperty("Modality")
    public void setModality(String modality) {
        this.modality = modality;
    }

    public StudyMetadata withModality(String modality) {
        this.modality = modality;
        return this;
    }

    @JsonProperty("PatientBirthDate")
    public String getPatientBirthDate() {
        return patientBirthDate;
    }

    @JsonProperty("PatientBirthDate")
    public void setPatientBirthDate(String patientBirthDate) {
        this.patientBirthDate = patientBirthDate;
    }

    public StudyMetadata withPatientBirthDate(String patientBirthDate) {
        this.patientBirthDate = patientBirthDate;
        return this;
    }

    @JsonProperty("PatientID")
    public String getPatientID() {
        return patientID;
    }

    @JsonProperty("PatientID")
    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public StudyMetadata withPatientID(String patientID) {
        this.patientID = patientID;
        return this;
    }

    @JsonProperty("PatientName")
    public String getPatientName() {
        return patientName;
    }

    @JsonProperty("PatientName")
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public StudyMetadata withPatientName(String patientName) {
        this.patientName = patientName;
        return this;
    }

    @JsonProperty("ReferringPhysicianName")
    public String getReferringPhysicianName() {
        return referringPhysicianName;
    }

    @JsonProperty("ReferringPhysicianName")
    public void setReferringPhysicianName(String referringPhysicianName) {
        this.referringPhysicianName = referringPhysicianName;
    }

    public StudyMetadata withReferringPhysicianName(String referringPhysicianName) {
        this.referringPhysicianName = referringPhysicianName;
        return this;
    }

    @JsonProperty("StudyDate")
    public String getStudyDate() {
        return studyDate;
    }

    @JsonProperty("StudyDate")
    public void setStudyDate(String studyDate) {
        this.studyDate = studyDate;
    }

    public StudyMetadata withStudyDate(String studyDate) {
        this.studyDate = studyDate;
        return this;
    }

    @JsonProperty("StudyDescription")
    public String getStudyDescription() {
        return studyDescription;
    }

    @JsonProperty("StudyDescription")
    public void setStudyDescription(String studyDescription) {
        this.studyDescription = studyDescription;
    }

    public StudyMetadata withStudyDescription(String studyDescription) {
        this.studyDescription = studyDescription;
        return this;
    }

    @JsonProperty("StudyInstanceUID")
    public String getStudyInstanceUID() {
        return studyInstanceUID;
    }

    @JsonProperty("StudyInstanceUID")
    public void setStudyInstanceUID(String studyInstanceUID) {
        this.studyInstanceUID = studyInstanceUID;
    }

    public StudyMetadata withStudyInstanceUID(String studyInstanceUID) {
        this.studyInstanceUID = studyInstanceUID;
        return this;
    }

}
