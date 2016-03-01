package com.deadpineapple.dal.entity;

import javax.persistence.*;

/**
 * Created by 15256 on 02/03/2016.
 */
public class SplitFile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany
    @JoinColumn
    private ConvertedFile mainFile;

    private int size;

    private Boolean isConverted;

    private String splitFilePath;

    private int workerServerNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getWorkerServerNumber() {
        return workerServerNumber;
    }

    public void setWorkerServerNumber(int workerServerNumber) {
        this.workerServerNumber = workerServerNumber;
    }

    public ConvertedFile getMainFile() {
        return mainFile;
    }

    public void setMainFile(ConvertedFile mainFile) {
        this.mainFile = mainFile;
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

    public String getSplitFilePath() {
        return splitFilePath;
    }

    public void setSplitFilePath(String splitFilePath) {
        this.splitFilePath = splitFilePath;
    }
}
