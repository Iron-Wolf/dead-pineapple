package com.deadpineapple.dal.dao;

import com.deadpineapple.dal.entity.UserAccount;

/**
 * Created by mikael on 30/03/16.
 */
public interface IUserDao {
        public UserAccount createUser(UserAccount user);
        public UserAccount updateUser(UserAccount newUser);
        public Long totalUsers();
        public UserAccount find (String email);
        public UserAccount checkCredentials (String login, String password);
}
