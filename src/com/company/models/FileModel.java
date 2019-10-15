package com.company.models;

public class FileModel {
    private String owner = null;
    private String fileName = null;

    public FileModel(String owner, String fileName) {
        this.owner = owner;
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public String getOwner() {
        return owner;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
