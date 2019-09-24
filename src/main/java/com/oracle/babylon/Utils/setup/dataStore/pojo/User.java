package com.oracle.babylon.Utils.setup.dataStore.pojo;

public class User {

    private String fullName;
    private String userName;
    private String password;
    private String project;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getProject() {
        return project;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProject(String project) {
        this.project = project;
    }


}
