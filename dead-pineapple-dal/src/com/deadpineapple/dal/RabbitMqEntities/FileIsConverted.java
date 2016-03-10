package com.deadpineapple.dal.RabbitMqEntities;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 15256 on 10/03/2016.
 */
public class FileIsConverted implements Serializable {
    private String newFileName;
    private Date creationDate;
    private Boolean wasSuccessFull;
    private String newFileType;
private Long fileId;

    public FileIsConverted() {
    }

    public FileIsConverted(long fileId,String newFileName,  Boolean wasSuccessFull, String newFileType) {
        this.newFileName = newFileName;
        this.creationDate = new Date();
        this.wasSuccessFull = wasSuccessFull;
        this.newFileType = newFileType;
        this.fileId = fileId;
    }

    public String getNewFileName() {
        return newFileName;
    }

    public void setNewFileName(String newFileName) {
        this.newFileName = newFileName;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Boolean getWasSuccessFull() {
        return wasSuccessFull;
    }

    public void setWasSuccessFull(Boolean wasSuccessFull) {
        this.wasSuccessFull = wasSuccessFull;
    }

    public String getNewFileType() {
        return newFileType;
    }

    public void setNewFileType(String newFileType) {
        this.newFileType = newFileType;
    }

    @Override
    public String toString() {
        return "FileIsConverted{" +
                "newFileName='" + newFileName + '\'' +
                ", creationDate=" + creationDate +
                ", wasSuccessFull=" + wasSuccessFull +
                ", newFileType='" + newFileType + '\'' +
                ", fileId=" + fileId +
                '}';
    }
}
