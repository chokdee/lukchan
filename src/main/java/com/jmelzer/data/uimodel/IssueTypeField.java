/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 09.06.12 
*
*/


package com.jmelzer.data.uimodel;

import com.jmelzer.data.dao.IssueTypeDao;
import com.jmelzer.data.model.IssueType;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;

@Component("issuetypeField")
@Scope("prototype")
public class IssueTypeField extends AbstractField {

    private static final long serialVersionUID = -1623330705261073579L;

    //cache inside prototype
    Panel panel;

    @Resource
    transient IssueTypeDao issueTypeDao;

    @Override
    public long  getId() {
        return ISSUETYPE_ID;
    }

    @Override
    public Panel getWicketComponent() {
        if (panel == null) {
            panel = new GenericChoiceComponent(new Model(""),issueTypeDao.findAll(), "issuetype", true);
        }
        return panel;
    }


}
