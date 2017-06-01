
package com.devbd.devmukul.e_notify.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Task {

    @SerializedName("taskId")
    @Expose
    private int taskId;
    @SerializedName("taskName")
    @Expose
    private String taskName;
    @SerializedName("shortDescription")
    @Expose
    private String shortDescription;
    @SerializedName("longDescription")
    @Expose
    private String longDescription;
    @SerializedName("iconPath")
    @Expose
    private String iconPath;
    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("endDate")
    @Expose
    private String endDate;
    @SerializedName("cost")
    @Expose
    private long cost;
    @SerializedName("priority")
    @Expose
    private String priority;
    @SerializedName("createdOn")
    @Expose
    private String createdOn;
    @SerializedName("taskType")
    @Expose
    private String taskType;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Task() {
    }

    /**
     * 
     * @param createdOn
     * @param taskId
     * @param startDate
     * @param iconPath
     * @param shortDescription
     * @param priority
     * @param taskType
     * @param longDescription
     * @param endDate
     * @param cost
     * @param taskName
     */
    public Task(int taskId, String taskName, String shortDescription, String longDescription, String iconPath, String startDate, String endDate, long cost, String priority, String createdOn, String taskType) {
        super();
        this.taskId = taskId;
        this.taskName = taskName;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.iconPath = iconPath;
        this.startDate = startDate;
        this.endDate = endDate;
        this.cost = cost;
        this.priority = priority;
        this.createdOn = createdOn;
        this.taskType = taskType;
    }

    public Task(String taskName, String shortDescription, String longDescription, String startDate, String endDate, long cost, String taskType) {
        super();
        this.taskName = taskName;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.startDate = startDate;
        this.endDate = endDate;
        this.cost = cost;
        this.taskType = taskType;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
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

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
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

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

}
