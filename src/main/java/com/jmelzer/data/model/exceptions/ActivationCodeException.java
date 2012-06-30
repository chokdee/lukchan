/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 03.06.12 
*
*/


package com.jmelzer.data.model.exceptions;

public class ActivationCodeException extends Exception {
    private static final long serialVersionUID = -7831837397189118153L;

    public static enum Type {
        used, notexist
    }
    Type type;

    public ActivationCodeException(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}
