package com.deadpineapple.dal.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Created by mikael on 30/03/16.
 */
@StaticMetamodel(SplitFile.class)
public class SplitFile_ {
    public static volatile SingularAttribute<SplitFile, Long> id;
    public static volatile SingularAttribute<SplitFile, Long> convertedFileId;
    public static volatile SingularAttribute<SplitFile, ConvertedFile> convertedFile;
    public static volatile SingularAttribute<SplitFile, Integer> size;
    public static volatile SingularAttribute<SplitFile, Boolean> isConverted;
    public static volatile SingularAttribute<SplitFile, String> splitFilePath;
    public static volatile SingularAttribute<SplitFile, Integer> workerServerNumber;
}
