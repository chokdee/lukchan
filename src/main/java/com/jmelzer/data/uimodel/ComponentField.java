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
import com.jmelzer.data.model.ui.SelectOption;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Component("componentfield")
@Scope("prototype")
public class ComponentField extends AbstractField {

    private static final long serialVersionUID = 6387818099470096780L;

    //cache inside prototype
    Panel panel;

    Field parent;
    IModel parentModel;

    @Resource
    transient ProjectDao projectDao;

    @Override
    public long getId() {
        return COMPONENT_ID;
    }

    @Override
    public Panel getWicketComponent() {
        if (panel == null) {
            panel = new GenericChoiceComponent(  new Model<String>(), new MyReadOnlyModel(), "component", false,
                                               ((GenericChoiceComponent) parent.getWicketComponent()).getDownChoice());
        }
        return panel;
    }

    @Override
    public boolean dependsOn(long id) {
        return id == PROJECT_ID;
    }

    @Override
    public void setParent(Field field) {
        parent = field;
        parentModel = (IModel) ((GenericChoiceComponent) parent.getWicketComponent()).getModel();
        parent.addChild(this);
    }

    class MyReadOnlyModel extends AbstractReadOnlyModel<List<? extends String>> {
        private static final long serialVersionUID = -1361780932967140989L;

        @Override
        public List<String> getObject() {
            if (parentModel.getObject() instanceof SelectOption) {
                SelectOption option = (SelectOption) parentModel.getObject();
                System.out.println("key = " + option.getKeyForOption());
                List<String> list = new ArrayList<String>();
                Set<com.jmelzer.data.model.Component> components =  projectDao.findOne(option.getKeyForOption()).getComponents();
                for (com.jmelzer.data.model.Component component : components) {
                    list.add(component.getName());
                }
                return list;

            }
            return Collections.emptyList();
        }

    }
}
