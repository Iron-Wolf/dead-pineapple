package com.deadpineapple.dal.dao;

import com.deadpineapple.dal.entity.ConvertedFile;

/**
 * Created by mikael on 31/03/16.
 */
public interface IConvertedFileDao {
    public ConvertedFile createFile(ConvertedFile file);
    public ConvertedFile updateFile(ConvertedFile file);
    public ConvertedFile findById(Long id);
}