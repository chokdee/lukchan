/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 19.01.13 
*
*/


package com.jmelzer.webapp.ui;

import com.jmelzer.data.model.Issue;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.ContextRelativeResource;

public class IssueIconPanel extends Panel {

    private static final long serialVersionUID = 7111107923251146485L;

    public IssueIconPanel(String id, IModel<Issue> model) {
        super(id, model);
        add(new Image("issuetypeimage", new ContextRelativeResource(model.getObject().getType().getIconPath())));
    }
}