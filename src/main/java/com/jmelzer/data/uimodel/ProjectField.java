/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 09.06.12 
*
*/


package com.jmelzer.data.uimodel;

import com.jmelzer.data.dao.ProjectDao;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;

@Component("projectfield")
@Scope("prototype")
public class ProjectField extends AbstractField {

    private static final long serialVersionUID = -1623330705261073579L;
    //cache inside prototype
    GenericChoiceComponent panel;


    @Resource
    transient ProjectDao projectDao;

    @Override
    public long  getId() {
        return PROJECT_ID;
    }

    @Override
    public Panel getWicketComponent() {
        if (panel == null) {
            panel = new GenericChoiceComponent(new Model(""),projectDao.findAll(), "project", true);
        }
        return panel;
    }

    @Override
    public void addChild(Field field) {
        panel.addChild(((GenericChoiceComponent)field.getWicketComponent()).getDownChoice());
    }
}
