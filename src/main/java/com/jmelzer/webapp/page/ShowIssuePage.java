/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 01.07.12 
*
*/


package com.jmelzer.webapp.page;

import com.jmelzer.data.model.Issue;
import com.jmelzer.service.IssueManager;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class ShowIssuePage extends MainPage {
    private static final long serialVersionUID = 8265687758416857282L;

    @SpringBean(name = "issueManager")
    IssueManager issueManager;

    public ShowIssuePage(final PageParameters parameters) {
        final String issueName = parameters.getString("0");
        if (issueName == null) {
            //todo redirect
            return;
        }
        Issue issue = issueManager.getIssueByShortName(issueName);
        if (issue == null) {
            //todo
        }
        add(new Label("projectname", issue.getProject().getName()));
        add(new Label("issuesummary", issue.getSummary()));
        Link link = new BookmarkablePageLink("self", ShowIssuePage.class, parameters);
        add(link);
        link.add(new Label("name", issueName));
    }
}
