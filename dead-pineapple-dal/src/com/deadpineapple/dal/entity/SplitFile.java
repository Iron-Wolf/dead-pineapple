package com.deadpineapple.dal.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by 15256 on 02/03/2016.
 */
@Entity
public class SplitFile implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private long size;

    private Boolean isConverted;

    private String splitFilePath;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
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
