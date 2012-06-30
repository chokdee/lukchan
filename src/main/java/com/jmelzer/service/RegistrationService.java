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
import com.jmelzer.data.model.exceptions.ActivationCodeException;
import com.jmelzer.data.model.exceptions.UserNotFoundException;

public interface RegistrationService {

    User register(String email, String userName, String pw, String name);
    void finish(String activationCode) throws ActivationCodeException;

    void sendPasswordLink(String email) throws UserNotFoundException;

    void changePassword(String code, String newPassword) throws ActivationCodeException;
}
