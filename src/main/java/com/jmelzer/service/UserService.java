/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 02.06.12 
*
*/


package com.jmelzer.service;

import com.jmelzer.data.model.User;

public interface UserService {
    User createUser(String email, String userName, String pw, String name);

    void unlockUser(Long userId);

    User findUserByEmail(String email);

    void changePassword(Long userId, String newPassword);
}
