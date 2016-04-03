package com.deadpineapple.dal.entity;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;
import java.util.List;

/**
 * Created by mikael on 30/03/16.
 */
@StaticMetamodel(UserAccount.class)
public class UserAccount_ {
    public static volatile SingularAttribute<UserAccount, Long> id;
    public static volatile SingularAttribute<UserAccount, String> email;
    public static volatile SingularAttribute<UserAccount, String> password;
    public static volatile SingularAttribute<UserAccount, Date> creationDate;
    public static volatile SingularAttribute<UserAccount, String> firstName;
    public static volatile SingularAttribute<UserAccount, String> lastName;
    public static volatile ListAttribute<UserAccount, ConvertedFile> convertedFiles;
}
