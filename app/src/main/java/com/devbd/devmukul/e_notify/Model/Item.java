
package com.devbd.devmukul.e_notify.Model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item implements Serializable, Resource {

    @SerializedName("projectId")
    @Expose
    private int projectId;
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
    private long startDate;
    @SerializedName("endDate")
    @Expose
    private long endDate;
    @SerializedName("cost")
    @Expose
    private long cost;
    @SerializedName("priority")
    @Expose
    private String priority;
    @SerializedName("createdOn")
    @Expose
    private long createdOn;
    @SerializedName("iconPath")
    @Expose
    private String iconPath;
    @SerializedName("tasks")
    @Expose
    private List<Task> tasks = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Item() {
    }

    /**
     * 
     * @param createdOn
     * @param startDate
     * @param iconPath
     * @param shortDescription
     * @param priority
     * @param longDescription
     * @param endDate
     * @param tasks
     * @param projectId
     * @param cost
     * @param projectName
     */
    public Item(int projectId, String projectName, String shortDescription, String longDescription, long startDate, long endDate, long cost, String priority, long createdOn, String iconPath, List<Task> tasks) {
        super();
        this.projectId = projectId;
        this.projectName = projectName;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.startDate = startDate;
        this.endDate = endDate;
        this.cost = cost;
        this.priority = priority;
        this.createdOn = createdOn;
        this.iconPath = iconPath;
        this.tasks = tasks;
    }

    public Item(String projectName, String shortDescription, String longDescription, long startDate, long endDate, long cost) {
        super();
        this.projectName = projectName;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.startDate = startDate;
        this.endDate = endDate;
        this.cost = cost;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
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

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
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

    public long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

}
