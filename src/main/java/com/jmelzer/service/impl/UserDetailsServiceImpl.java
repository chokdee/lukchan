/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 30.05.12 
*
*/


package com.jmelzer.service.impl;

import com.jmelzer.data.dao.UserDao;
import com.jmelzer.data.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.beans.Transient;


@Component("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    UserDao userDao;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUserNameNonLocked(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        user.fillAuthorities();
        return user;
    }
}
