
package com.devbd.devmukul.e_notify.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProjectAddRequest {
    @SerializedName("projectName")
    @Expose
    private String projectName;
    @SerializedName("shortDescription")
    @Expose
    private String shortDescription;
    @SerializedName("longDescription")
    @Expose
    private String longDescription;
    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("endDate")
    @Expose
    private String endDate;

    /**
     * No args constructor for use in serialization
     *
     */
    public ProjectAddRequest() {
    }

    /**
     *

     * @param startDate
     * @param shortDescription
     * @param longDescription
     * @param endDate
     * @param projectName
     */


    public ProjectAddRequest(String projectName, String shortDescription, String longDescription, String startDate, String endDate) {
        super();
        this.projectName = projectName;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }


    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

}
