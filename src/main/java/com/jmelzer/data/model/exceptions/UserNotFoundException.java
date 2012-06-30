/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 03.06.12 
*
*/


package com.jmelzer.data.model.exceptions;

public class UserNotFoundException extends Exception {

    private static final long serialVersionUID = 8619913468425655140L;

    public UserNotFoundException(String email) {
        super("use " + email + " not found");
    }


}
