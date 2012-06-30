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

@Component("affectedversionfield")
@Scope("prototype")
public class AffectedVersionField extends BaseVersionField implements Serializable {

    private static final long serialVersionUID = -4871766369513930360L;

    public AffectedVersionField() {
        super("affectedversion");
    }

    @Override
    public long getId() {
        return AFFECTED_VERSION_ID;
    }



}
