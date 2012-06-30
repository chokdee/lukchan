/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 27.05.12 
*
*/


package com.jmelzer.data.dao;

import com.jmelzer.data.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserDao extends AbstractDao<User> {

    User findByUserNameNonLocked(String username);
    User findByUserName(String username);
    User findByEmail(String email);
}
