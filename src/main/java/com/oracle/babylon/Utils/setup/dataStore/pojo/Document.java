package com.oracle.babylon.Utils.setup.dataStore.pojo;

/**
 * Class which contains the fields related to document, along with the respective setters and getters
 * Author : susgopal
 */
public class Document {
    private String ConfidentialityFlag;
    private String accessList;
    private String documentNumber;
    private int documentStatusId;
    private String revision;
    private String comments;
    private String hasFile;
    private String attribute1;
    private String discipline;
    private int documentTypeId;
    private String title;
    private String revisionDate;
    private String project;
    private String username;
    private String fileToUpload;

    public String getUserId() {
        return accessList;
    }

    public void setUserId(String userId) {
        this.accessList = userId;
    }

    public String getConfidentialityFlag() {
        return ConfidentialityFlag;
    }

    public void setConfidentialityFlag(String confidentialityFlag) {
        this.ConfidentialityFlag = confidentialityFlag;
    }

    public String getFileToUpload() {
        return fileToUpload;
    }

    public void setFileToUpload(String fileToUpload) {
        this.fileToUpload = fileToUpload;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public int getDocumentStatusId() {
        return documentStatusId;
    }

    public void setDocumentStatusId(int documentStatusId) {
        this.documentStatusId = documentStatusId;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getHasFile() {
        return hasFile;
    }

    public void setHasFile(String hasFile) {
        this.hasFile = hasFile;
    }

    public String getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public int getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(int documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(String revisionDate) {
        this.revisionDate = revisionDate;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
