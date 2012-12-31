/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 09.06.12 
*
*/


package com.jmelzer.data.uimodel;

import com.jmelzer.data.dao.UserDao;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import javax.annotation.Resource;

public abstract class BaseUserField extends AbstractField {

    private static final long serialVersionUID = -7086054058565182367L;

    //cache inside prototype
    Panel panel;

    String name;

    @Resource
    transient UserDao userDao;

    protected BaseUserField(String name) {
        this.name = name;
    }

    @Override
    public Panel getWicketComponent() {
        if (panel == null) {
            //todo check the roles
            panel = new GenericChoiceComponent(new Model(""),userDao.findAll(), name, true);
        }
        return panel;
    }
}
