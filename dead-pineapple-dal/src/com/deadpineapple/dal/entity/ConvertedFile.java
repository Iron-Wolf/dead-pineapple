package com.deadpineapple.dal.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by 15256 on 01/03/2016.
 */
public class ConvertedFile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany
    @JoinColumn
    private UserAccount user;

    @OneToMany(mappedBy = "mainFile")
    private List<SplitFile> splitFiles;

    private int size;

    private Boolean isConverted;

    private String filePath;

    private String originalName;

    private String oldType;

    private String newType;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date creationDate;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date convertedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<SplitFile> getSplitFiles() {
        return splitFiles;
    }

    public void setSplitFiles(List<SplitFile> splitFiles) {
        this.splitFiles = splitFiles;
    }

    public UserAccount getUser() {
        return user;
    }

    public void setUser(UserAccount user) {
        this.user = user;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Boolean getConverted() {
        return isConverted;
    }

    public void setConverted(Boolean converted) {
        isConverted = converted;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getOldType() {
        return oldType;
    }

    public void setOldType(String oldType) {
        this.oldType = oldType;
    }

    public String getNewType() {
        return newType;
    }

    public void setNewType(String newType) {
        this.newType = newType;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getConvertedDate() {
        return convertedDate;
    }

    public void setConvertedDate(Date convertedDate) {
        this.convertedDate = convertedDate;
    }
}
