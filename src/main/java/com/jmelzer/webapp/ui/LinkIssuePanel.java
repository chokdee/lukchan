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
import com.jmelzer.webapp.page.ShowIssuePage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class LinkIssuePanel extends Panel {

    private static final long serialVersionUID = -1249266452194393237L;

    public LinkIssuePanel(String id, IModel<Issue> model) {
        super(id, model);
        PageParameters pageParameters = new PageParameters();
        pageParameters.set(0, model.getObject().getPublicId());
        Link<ShowIssuePage> issueLink = new BookmarkablePageLink<ShowIssuePage>("issueLink", ShowIssuePage.class, pageParameters);
        issueLink.add(new Label("issueLinkLabel", model.getObject().getPublicId()));
        add(issueLink);
    }

}
