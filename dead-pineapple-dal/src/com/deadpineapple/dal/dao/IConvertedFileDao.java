package com.deadpineapple.dal.dao;

import com.deadpineapple.dal.entity.ConvertedFile;

/**
 * Created by mikael on 31/03/16.
 */
public interface IConvertedFileDao {
    public ConvertedFile createFile(ConvertedFile file);

    ConvertedFile findById(long id);

    public ConvertedFile updateFile(ConvertedFile newFile);
    public Long totalConvertedFile();
    public ConvertedFile findByOriginalName (String name);
}
