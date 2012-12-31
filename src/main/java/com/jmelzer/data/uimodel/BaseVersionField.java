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
import com.jmelzer.data.model.ProjectVersion;
import com.jmelzer.data.model.ui.SelectOption;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class BaseVersionField extends AbstractField {

    private static final long serialVersionUID = -7086054058565182367L;

    //cache inside prototype
    Panel panel;

    Field parent;
    IModel parentModel;

    String name;

    @Resource
    transient ProjectDao projectDao;

    protected BaseVersionField(String name) {
        this.name = name;
    }

    @Override
    public Panel getWicketComponent() {
        if (panel == null) {
            panel = new GenericChoiceComponent(  new Model<String>(), new VersionModel(), name, false,
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

    class VersionModel extends AbstractReadOnlyModel<List<? extends String>> {

        private static final long serialVersionUID = 2076546576987677583L;

        @Override
        public List<String> getObject() {
            if (parentModel.getObject() instanceof SelectOption) {
                SelectOption option = (SelectOption) parentModel.getObject();
                List<String> list = new ArrayList<String>();
                Set<ProjectVersion> versions =  projectDao.findOne(option.getKey()).getVersions();
                for (ProjectVersion version : versions) {
                    list.add(version.getVersionNumber());
                }
                return list;

            }
            List<String> list = new ArrayList<String>();
            list.add("Please select first a project");
            return list;
        }

    }
}
