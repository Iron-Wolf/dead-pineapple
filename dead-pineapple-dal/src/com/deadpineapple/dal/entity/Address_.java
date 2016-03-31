package com.deadpineapple.dal.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 *
 * @author mikael
 */

@StaticMetamodel(Address.class)
public class Address_ {
    public static volatile SingularAttribute<Address, Long> id;
    public static volatile SingularAttribute<Address, Integer> zipCode;
    public static volatile SingularAttribute<Address, String> city;
    public static volatile SingularAttribute<Address, String> country;
}
