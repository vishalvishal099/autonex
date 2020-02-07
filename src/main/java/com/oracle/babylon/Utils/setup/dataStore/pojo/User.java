package com.oracle.babylon.Utils.setup.dataStore.pojo;

/**
 * Class contains fields related to a User, along with the setters and getters for the fields
 * Author : vsinghsi
 */
public class User {

    private String fullName;
    private String username;
    private String password;
    private String projectName;
    private String projectId;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
