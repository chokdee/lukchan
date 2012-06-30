/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 17.06.12 
*
*/


package com.jmelzer.data.uimodel;

import org.apache.wicket.model.IModel;

public class StringModel implements IModel {
    private static final long serialVersionUID = 4189412126555851793L;
    String string;

    public StringModel(String s) {
        this.string = s;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    @Override
    public Object getObject() {
        return string;
    }

    @Override
    public void setObject(Object object) {
        string = (String) object;
    }

    @Override
    public void detach() {
        string = "";
    }
}
