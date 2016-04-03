package com.deadpineapple.dal.entity;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;
import java.util.List;

/**
 * Created by mikael on 30/03/16.
 */
@StaticMetamodel(ConvertedFile.class)
public class ConvertedFile_ {
    public static volatile SingularAttribute<ConvertedFile, Long> id;
    public static volatile ListAttribute<ConvertedFile, SplitFile> splitFiles;
    public static volatile SingularAttribute<ConvertedFile, Integer> size;
    public static volatile SingularAttribute<ConvertedFile, Boolean> isConverted;
    public static volatile SingularAttribute<ConvertedFile, String> filePath;
    public static volatile SingularAttribute<ConvertedFile, String> originalName;
    public static volatile SingularAttribute<ConvertedFile, String> oldType;
    public static volatile SingularAttribute<ConvertedFile, String> newType;
    public static volatile SingularAttribute<ConvertedFile, Date> creationDate;
    public static volatile SingularAttribute<ConvertedFile, Date> convertedDate;
    public static volatile SingularAttribute<ConvertedFile, UserAccount> userAccount;
}
