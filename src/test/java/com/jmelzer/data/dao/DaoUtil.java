/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 31.12.12 
*
*/


package com.jmelzer.data.dao;

import com.jmelzer.data.model.User;

public class DaoUtil {
    public static User createUser(String username, UserDao userDao) {
        User user = new User();
        user.setLoginName(username);
        user.setName(username);
        user.setPassword("42");
        user.setEmail("bla@bla.de");
        user.setAvatar(new byte[] {0,1});
        userDao.save(user);
        return user;
    }
}
