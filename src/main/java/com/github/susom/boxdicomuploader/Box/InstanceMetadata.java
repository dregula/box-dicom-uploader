
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
    "ContentDate",
    "ContentTime",
    "ImageOrientationPatient",
    "ImagePositionPatient",
    "InstanceNumber",
    "NumberOfFrames",
    "SOPInstanceUID",
    "AcquisitionDate",
    "AcquisitionTime",
    "Columns",
    "PixelSpacing",
    "Rows"
})
public class InstanceMetadata implements Serializable
{

    @JsonProperty("ContentDate")
    private String contentDate;
    @JsonProperty("ContentTime")
    private String contentTime;
    @JsonProperty("ImageOrientationPatient")
    private List<Integer> imageOrientationPatient = null;
    @JsonProperty("ImagePositionPatient")
    private List<Integer> imagePositionPatient = null;
    @JsonProperty("InstanceNumber")
    private Integer instanceNumber;
    @JsonProperty("NumberOfFrames")
    private Integer numberOfFrames;
    @JsonProperty("SOPInstanceUID")
    private String sOPInstanceUID;
    @JsonProperty("AcquisitionDate")
    private String acquisitionDate;
    @JsonProperty("AcquisitionTime")
    private String acquisitionTime;
    @JsonProperty("Columns")
    private Integer columns;
    @JsonProperty("PixelSpacing")
    private List<Double> pixelSpacing = null;
    @JsonProperty("Rows")
    private Integer rows;
    private final static long serialVersionUID = 3383752941170605635L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public InstanceMetadata() {
    }

    /**
     * 
     * @param contentDate
     * @param imageOrientationPatient
     * @param numberOfFrames
     * @param imagePositionPatient
     * @param pixelSpacing
     * @param sOPInstanceUID
     * @param columns
     * @param acquisitionTime
     * @param acquisitionDate
     * @param contentTime
     * @param instanceNumber
     * @param rows
     */
    public InstanceMetadata(String contentDate, String contentTime, List<Integer> imageOrientationPatient, List<Integer> imagePositionPatient, Integer instanceNumber, Integer numberOfFrames, String sOPInstanceUID, String acquisitionDate, String acquisitionTime, Integer columns, List<Double> pixelSpacing, Integer rows) {
        super();
        this.contentDate = contentDate;
        this.contentTime = contentTime;
        this.imageOrientationPatient = imageOrientationPatient;
        this.imagePositionPatient = imagePositionPatient;
        this.instanceNumber = instanceNumber;
        this.numberOfFrames = numberOfFrames;
        this.sOPInstanceUID = sOPInstanceUID;
        this.acquisitionDate = acquisitionDate;
        this.acquisitionTime = acquisitionTime;
        this.columns = columns;
        this.pixelSpacing = pixelSpacing;
        this.rows = rows;
    }

    @JsonProperty("ContentDate")
    public String getContentDate() {
        return contentDate;
    }

    @JsonProperty("ContentDate")
    public void setContentDate(String contentDate) {
        this.contentDate = contentDate;
    }

    public InstanceMetadata withContentDate(String contentDate) {
        this.contentDate = contentDate;
        return this;
    }

    @JsonProperty("ContentTime")
    public String getContentTime() {
        return contentTime;
    }

    @JsonProperty("ContentTime")
    public void setContentTime(String contentTime) {
        this.contentTime = contentTime;
    }

    public InstanceMetadata withContentTime(String contentTime) {
        this.contentTime = contentTime;
        return this;
    }

    @JsonProperty("ImageOrientationPatient")
    public List<Integer> getImageOrientationPatient() {
        return imageOrientationPatient;
    }

    @JsonProperty("ImageOrientationPatient")
    public void setImageOrientationPatient(List<Integer> imageOrientationPatient) {
        this.imageOrientationPatient = imageOrientationPatient;
    }

    public InstanceMetadata withImageOrientationPatient(List<Integer> imageOrientationPatient) {
        this.imageOrientationPatient = imageOrientationPatient;
        return this;
    }

    @JsonProperty("ImagePositionPatient")
    public List<Integer> getImagePositionPatient() {
        return imagePositionPatient;
    }

    @JsonProperty("ImagePositionPatient")
    public void setImagePositionPatient(List<Integer> imagePositionPatient) {
        this.imagePositionPatient = imagePositionPatient;
    }

    public InstanceMetadata withImagePositionPatient(List<Integer> imagePositionPatient) {
        this.imagePositionPatient = imagePositionPatient;
        return this;
    }

    @JsonProperty("InstanceNumber")
    public Integer getInstanceNumber() {
        return instanceNumber;
    }

    @JsonProperty("InstanceNumber")
    public void setInstanceNumber(Integer instanceNumber) {
        this.instanceNumber = instanceNumber;
    }

    public InstanceMetadata withInstanceNumber(Integer instanceNumber) {
        this.instanceNumber = instanceNumber;
        return this;
    }

    @JsonProperty("NumberOfFrames")
    public Integer getNumberOfFrames() {
        return numberOfFrames;
    }

    @JsonProperty("NumberOfFrames")
    public void setNumberOfFrames(Integer numberOfFrames) {
        this.numberOfFrames = numberOfFrames;
    }

    public InstanceMetadata withNumberOfFrames(Integer numberOfFrames) {
        this.numberOfFrames = numberOfFrames;
        return this;
    }

    @JsonProperty("SOPInstanceUID")
    public String getSOPInstanceUID() {
        return sOPInstanceUID;
    }

    @JsonProperty("SOPInstanceUID")
    public void setSOPInstanceUID(String sOPInstanceUID) {
        this.sOPInstanceUID = sOPInstanceUID;
    }

    public InstanceMetadata withSOPInstanceUID(String sOPInstanceUID) {
        this.sOPInstanceUID = sOPInstanceUID;
        return this;
    }

    @JsonProperty("AcquisitionDate")
    public String getAcquisitionDate() {
        return acquisitionDate;
    }

    @JsonProperty("AcquisitionDate")
    public void setAcquisitionDate(String acquisitionDate) {
        this.acquisitionDate = acquisitionDate;
    }

    public InstanceMetadata withAcquisitionDate(String acquisitionDate) {
        this.acquisitionDate = acquisitionDate;
        return this;
    }

    @JsonProperty("AcquisitionTime")
    public String getAcquisitionTime() {
        return acquisitionTime;
    }

    @JsonProperty("AcquisitionTime")
    public void setAcquisitionTime(String acquisitionTime) {
        this.acquisitionTime = acquisitionTime;
    }

    public InstanceMetadata withAcquisitionTime(String acquisitionTime) {
        this.acquisitionTime = acquisitionTime;
        return this;
    }

    @JsonProperty("Columns")
    public Integer getColumns() {
        return columns;
    }

    @JsonProperty("Columns")
    public void setColumns(Integer columns) {
        this.columns = columns;
    }

    public InstanceMetadata withColumns(Integer columns) {
        this.columns = columns;
        return this;
    }

    @JsonProperty("PixelSpacing")
    public List<Double> getPixelSpacing() {
        return pixelSpacing;
    }

    @JsonProperty("PixelSpacing")
    public void setPixelSpacing(List<Double> pixelSpacing) {
        this.pixelSpacing = pixelSpacing;
    }

    public InstanceMetadata withPixelSpacing(List<Double> pixelSpacing) {
        this.pixelSpacing = pixelSpacing;
        return this;
    }

    @JsonProperty("Rows")
    public Integer getRows() {
        return rows;
    }

    @JsonProperty("Rows")
    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public InstanceMetadata withRows(Integer rows) {
        this.rows = rows;
        return this;
    }

}
