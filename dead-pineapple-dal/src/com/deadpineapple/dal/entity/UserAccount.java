package com.deadpineapple.dal.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by 15256 on 01/03/2016.
 */
@Entity
public class UserAccount implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;

    private Timestamp creationDate;

    @OneToMany
    @JoinColumn
    private List<ConvertedFile> convertedFiles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ConvertedFile> getConvertedFiles() {
        return convertedFiles;
    }

    public void setConvertedFiles(List<ConvertedFile> convertedFiles) {
        this.convertedFiles = convertedFiles;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
