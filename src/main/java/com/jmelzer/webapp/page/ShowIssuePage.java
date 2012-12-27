/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 01.07.12 
*
*/


package com.jmelzer.webapp.page;

import com.jmelzer.data.model.Comment;
import com.jmelzer.data.model.Issue;
import com.jmelzer.data.uimodel.StringModel;
import com.jmelzer.service.IssueManager;
import com.visural.wicket.component.nicedit.RichTextEditor;
import org.apache.wicket.Page;
import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebResource;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.resource.ContextRelativeResource;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;

public class ShowIssuePage extends MainPage {
    private static final long serialVersionUID = 8265687758416857282L;

    @SpringBean(name = "issueManager")
    IssueManager issueManager;

    String currentName;

    public ShowIssuePage(final PageParameters parameters) {
        currentName = parameters.getString("0");
        if (currentName == null) {
            //todo redirect
            return;
        }
        Issue issue = getIssue();
        addDirectly(new Label("title", String.format("[%s] %s",
                                                     issue.getPublicId(), issue.getSummary())));
        add(new Label("projectname", issue.getProject().getName()));
        Link link = new BookmarkablePageLink("self2", ShowIssuePage.class, parameters);
        link.add(new Label("issuesummary", issue.getSummary()));
        add(link);
        link = new BookmarkablePageLink("self", ShowIssuePage.class, parameters);
        add(link);
        link.add(new Label("name", currentName));

        add(new Label("issuetypelabel", new StringResourceModel("issuetype", new Model(""))));
        add(new Label("issuetype", issue.getType().getType()));
        add(new Image("issuetypeimage",new ContextRelativeResource(issue.getType().getIconPath())));

        add(new Label("prioritylabel", new StringResourceModel("priority", new Model(""))));
        add(new Label("priority", issue.getPriority().getName()));

        add(new Label("statuslabel", new StringResourceModel("status", new Model(""))));
        add(new Label("status", issue.getStatus().getName()));
        add(new Image("statusimage",new ContextRelativeResource(issue.getStatus().getIconPath())));

        add(new Label("assigneelabel", new StringResourceModel("assignee", new Model(""))));
        add(new Label("assignee", issue.getAssigneeName()));

        add(new Label("reporterlabel", new StringResourceModel("reporter", new Model(""))));
        add(new Label("reporter", issue.getReporterName()));

        add(new Label("creationlabel", new StringResourceModel("creation", new Model(""))));
        add(DateLabel.forDateStyle("creation", new Model<Date>(issue.getCreationDate()), "MM"));

        add(new Label("updatelabel", new StringResourceModel("update", new Model(""))));
        add(DateLabel.forDateStyle("update", new Model<Date>(issue.getUpdateDate()), "MM"));

        add(new Label("desclabel", new StringResourceModel("desc", new Model(""))));
        Label desc = new Label("desc", issue.getDescription());
        desc.setEscapeModelStrings(false);
        add(desc);

//        createUploadPage();
        createCommentPage();
    }

    private Issue getIssue() {
        Issue issue = issueManager.getIssueByShortName(currentName);
        if (issue == null) {
            //todo
        }
        return issue;
    }

    private void createCommentPage() {
        final ModalWindow modalWindow;
        add(modalWindow = new ModalWindow("modalComment"));

        modalWindow.setPageMapName("modal-2");
        modalWindow.setCookieName("modal-2");

        modalWindow.setPageCreator(new ModalWindow.PageCreator() {

            private static final long serialVersionUID = -8304783732807067434L;

            public Page createPage() {
                return new CommentPage(ShowIssuePage.this, modalWindow);
            }
        });
        modalWindow.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {

            private static final long serialVersionUID = 7005964314882175967L;

            public void onClose(AjaxRequestTarget target) {
                System.out.println();
//                target.addComponent(result);
            }
        });
        modalWindow.setCloseButtonCallback(new ModalWindow.CloseButtonCallback() {

            private static final long serialVersionUID = -7300960030846812464L;

            public boolean onCloseButtonClicked(AjaxRequestTarget target) {
//                setResult("Modal window 1 - close button");
                System.out.println();
                return true;
            }
        });
        Form ajaxForm = new Form("modalForm1");
        add(ajaxForm);
        ajaxForm.add(new AjaxButton("comment") {
            private static final long serialVersionUID = 3307386729657199285L;

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                modalWindow.show(target);
            }

        });
//        ajaxForm.add(new RichTextEditor<String>("textfield", new StringModel("")));

    }
    private void createUploadPage() {
        final ModalWindow modal1;
        add(modal1 = new ModalWindow("modal1"));

        modal1.setPageMapName("modal-1");
        modal1.setCookieName("modal-1");

        modal1.setPageCreator(new ModalWindow.PageCreator() {
            private static final long serialVersionUID = -8304783732807067434L;

            public Page createPage() {
                return new UploadPage(ShowIssuePage.this, modal1);
            }
        });
        modal1.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
            private static final long serialVersionUID = 7005964314882175967L;

            public void onClose(AjaxRequestTarget target) {
//                target.addComponent(result);
            }
        });
        modal1.setCloseButtonCallback(new ModalWindow.CloseButtonCallback() {
            private static final long serialVersionUID = -7300960030846812464L;

            public boolean onCloseButtonClicked(AjaxRequestTarget target) {
//                setResult("Modal window 1 - close button");
                return true;
            }
        });
        Form ajaxForm = new Form("ajaxform");
        add(ajaxForm);
        ajaxForm.add(new AjaxButton("upload") {
            private static final long serialVersionUID = 3307386729657199285L;

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                modal1.show(target);
            }

//            @Override
//            public void onClick(AjaxRequestTarget target) {
//
//            }
        });
    }

    public void setUploadComment(String comment) {

    }

    public void addComment(String string) {
//        getIssue().addComment(new Comment(string));
        String username = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        issueManager.addComment(currentName, string, username);
    }
}
