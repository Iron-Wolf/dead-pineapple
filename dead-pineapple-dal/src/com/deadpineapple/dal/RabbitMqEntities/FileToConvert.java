package com.deadpineapple.dal.RabbitMqEntities;

import java.io.Serializable;

/**
 * Created by 15256 on 10/03/2016.
 */


public class FileToConvert implements Serializable {
    private Long fileId;
    public String fileName;
    public String convertionType;
    public String convertionEncoding;

    public FileToConvert() {
    }

    public FileToConvert(long fileId, String fileName, String convertionType,String convertionEncoding ) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.convertionType = convertionType;
        this.convertionEncoding = convertionEncoding;
    }

    public String getConvertionEncoding() {
        return convertionEncoding;
    }

    public void setConvertionEncoding(String convertionEncoding) {
        this.convertionEncoding = convertionEncoding;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getConvertionType() {
        return convertionType;
    }

    public void setConvertionType(String convertionType) {
        this.convertionType = convertionType;
    }

    @Override
    public String toString() {
        return "FileToConvert{" +
                "fileId=" + fileId +
                ", fileName='" + fileName + '\'' +
                ", convertionType='" + convertionType + '\'' +
                '}';
    }
}
