/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 09.06.12 
*
*/


package com.jmelzer.data.uimodel;

import com.jmelzer.data.dao.PriorityDao;
import com.jmelzer.data.model.Priority;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;

@Component("priorityfield")
@Scope("prototype")
public class PriorityField extends AbstractField implements Serializable {

    private static final long serialVersionUID = -1623330705261073579L;
    //cache inside prototype
    Panel panel;

    @Resource
    transient PriorityDao priorityDao;

    @Override
    public long getId() {
        return PRIORITY_ID;
    }

    @Override
    public Panel getWicketComponent() {
        if (panel == null) {
            //todo read default from db
            Priority model = new Priority();
            model.setId(13L);
            model.setName("Major");
            panel = new GenericChoiceComponent(new Model(model),
                                               priorityDao.findAllByOrder(),
                                               "priority",
                                               false);
        }
        return panel;
    }

    @Override
    public boolean dependsOn(long id) {
        return false;
    }

    @Override
    public void setParent(Field field) {
    }


}
