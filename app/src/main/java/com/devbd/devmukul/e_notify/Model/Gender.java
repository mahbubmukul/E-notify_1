package com.devbd.devmukul.e_notify.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Gender implements Resource,Serializable {

    @SerializedName("projectId")
    private int projectId;
    @SerializedName("projectName")
    private String projectName;


    public Gender(int projectId,  String projectName) {
        this.projectId = projectId;
        this.projectName = projectName;
    }


    @Override
    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    @Override
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public String toString() {
        return "District{" +
                ", nodeID=" + projectId +
                ", nodeTitle='" + projectName + '\'' +
                '}';
    }
}
