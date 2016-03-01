package com.deadpineapple.dal.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by 15256 on 01/03/2016.
 */
@Entity
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date creationDate;

    @OneToMany(mappedBy = "user")
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
