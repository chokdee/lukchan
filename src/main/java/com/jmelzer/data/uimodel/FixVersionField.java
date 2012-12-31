/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 09.06.12 
*
*/


package com.jmelzer.data.uimodel;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component("fixversionfield")
@Scope("prototype")
public class FixVersionField extends BaseVersionField implements Serializable {

    private static final long serialVersionUID = -4871766369513930360L;

    public FixVersionField() {
        super("fixversion");
    }

    @Override
    public long getId() {
        return FIXVERSION_ID;
    }



}
