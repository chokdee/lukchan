/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 17.06.12 
*
*/


package com.jmelzer.data.model.ui;

import java.io.Serializable;

public class SelectOption implements SelectOptionI, Serializable {
    private static final long serialVersionUID = 3952326124598342892L;
    Long key;
    String value;

    public SelectOption(Long key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public Long getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return value;
    }
}
