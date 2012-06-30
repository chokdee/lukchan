/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 02.06.12 
*
*/


package com.jmelzer.service.impl;

import com.jmelzer.data.dao.UserDao;
import com.jmelzer.data.model.User;
import com.jmelzer.service.UserService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    Md5PasswordEncoder encoder = new Md5PasswordEncoder();

    @Resource
    UserDao userDao;

    @Override
    @Transactional
    public User createUser(String email, String userName, String pw, String name) {
        User userDB = userDao.findByUserName(userName);
        if (userDB != null) {
            throw new DuplicateKeyException("username " + userName + " allready exists.");
        }

        User user =  new User();
        user.setEmail(email);
        user.setLoginName(userName);
        user.setName(name);
        user.setPassword(encoder.encodePassword(pw, user.getLoginName()));

        userDao.save(user);

        return user;
    }

    @Override
    public void unlockUser(Long userId) {
        User user = userDao.findOne(userId);
        user.setLocked(false);
        userDao.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    @Transactional
    public void changePassword(Long userId, String newPassword) {
        User user = userDao.findOne(userId);
        user.setPassword(encoder.encodePassword(newPassword, user.getLoginName()));
        userDao.save(user);
    }
}
