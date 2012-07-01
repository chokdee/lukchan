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
import org.apache.wicket.datetime.DateConverter;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Date;

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
        Link link = new BookmarkablePageLink("self2", ShowIssuePage.class, parameters);
        link.add(new Label("issuesummary", issue.getSummary()));
        add(link);
        link = new BookmarkablePageLink("self", ShowIssuePage.class, parameters);
        add(link);
        link.add(new Label("name", issueName));

        add(new Label("issuetypelabel", new StringResourceModel("issuetype", new Model(""))));
        add(new Label("issuetype", issue.getType().getType()));

        add(new Label("prioritylabel", new StringResourceModel("priority", new Model(""))));
        add(new Label("priority", issue.getPriority().getName()));

        add(new Label("assigneelabel", new StringResourceModel("assignee", new Model(""))));
        add(new Label("assignee", issue.getAssigneeName()));

        add(new Label("reporterlabel", new StringResourceModel("reporter", new Model(""))));
        add(new Label("reporter", issue.getReporterName()));

        add(new Label("creationlabel", new StringResourceModel("creation", new Model(""))));
        add(DateLabel.forDateStyle("creation", new Model<Date>(issue.getCreationDate()) ,"MM"));

        add(new Label("updatelabel", new StringResourceModel("update", new Model(""))));
        add(DateLabel.forDateStyle("update", new Model<Date>(issue.getUpdateDate()) ,"MM"));

        add(new Label("desclabel", new StringResourceModel("desc", new Model(""))));
        Label desc = new Label("desc", issue.getDescription());
        desc.setEscapeModelStrings(false);
        add(desc);
    }
}
