package com.deadpineapple.dal.entity;

import com.sun.istack.internal.NotNull;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
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

    private String password;

    private Date creationDate;

    private String firstName;

    private String lastName;

    @OneToMany(mappedBy = "userAccount")
    private List<ConvertedFile> convertedFiles;

    @Formula("(SELECT SUM(cf.size) FROM ConvertedFile cf WHERE id=cf.userAccountId)")
    private int totalSize;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }
}
