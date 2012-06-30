/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 24.06.12 
*
*/


package com.jmelzer.data.uimodel;

import java.io.Serializable;

public abstract class AbstractField implements Field,Serializable {

    private static final long serialVersionUID = 6905363938926579238L;

    @Override
    public boolean dependsOn(long id) {
        return false;
    }

    @Override
    public void addChild(Field fixVersionField) {
    }

    @Override
    public void setParent(Field field) {
    }
}
