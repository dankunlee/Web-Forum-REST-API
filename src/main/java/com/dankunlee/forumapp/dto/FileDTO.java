package com.dankunlee.forumapp.dto;

public class FileDTO {
    private String fileName;
    private String fileType;
    private String fileUri;
    private Long fileSize;

    public FileDTO(String fileName, String fileType, String fileUri, Long fileSize) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileUri = fileUri;
        this.fileSize = fileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileUri() {
        return fileUri;
    }

    public void setFileUri(String fileUri) {
        this.fileUri = fileUri;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }
}
