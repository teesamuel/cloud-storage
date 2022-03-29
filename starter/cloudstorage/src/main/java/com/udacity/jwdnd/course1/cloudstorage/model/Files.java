package com.udacity.jwdnd.course1.cloudstorage.model;

import org.apache.ibatis.annotations.Mapper;

import java.sql.Blob;

public class Files {

    private Integer fileId;
    private String fileName;
    private String contentType;
    private String fileSize;
    private  byte [] filedata;
    private Integer userId;



    public Files() {
    }

    public Files(Integer fileId, String fileName, String contentType, String fileSize, byte [] filedata, Integer userId) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.filedata = filedata;
        this.userId = userId;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public byte[] getFiledata() {
        return filedata;
    }

    public void setFiledata(byte[] filedata) {
        this.filedata = filedata;
    }

}

