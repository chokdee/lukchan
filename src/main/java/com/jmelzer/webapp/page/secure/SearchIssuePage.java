/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 14.01.13 
*
*/


package com.jmelzer.webapp.page.secure;

import com.jmelzer.data.model.IssueType;
import com.jmelzer.data.model.Project;
import com.jmelzer.data.model.WorkflowStatus;
import com.jmelzer.data.model.ui.SelectOption;
import com.jmelzer.data.model.ui.SelectOptionI;
import com.jmelzer.service.IssueManager;
import com.jmelzer.service.IssueTypeManager;
import com.jmelzer.service.ProjectManager;
import com.jmelzer.service.WorkflowStatusManager;
import com.jmelzer.webapp.page.MainPage;
import com.jmelzer.webapp.ui.GenericChoiceRenderer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.ArrayList;
import java.util.List;

public class SearchIssuePage extends MainPage {
    private static final long serialVersionUID = -3918755272288042123L;

    @SpringBean(name = "issueManager")
    IssueManager issueManager;

    @SpringBean(name = "projectManager")
    ProjectManager projectManager;

    @SpringBean(name = "issueTypeManager")
    IssueTypeManager issueTypeManager;

    @SpringBean(name = "workflowStatusManager")
    WorkflowStatusManager workflowStatusManager;

    public SearchIssuePage(final PageParameters parameters) {

        addDirectly(new Label("title", "Suche"));
        add(new Label("dummy", "dummy"));

        add(new Label("projectLabel", "Project:"));
        List<Project> projects = projectManager.getAll();
        List<SelectOptionI> list = new ArrayList<SelectOptionI>(projects.size());
        for (Project project : projects) {
            list.add(new SelectOption(project.getId(), project.getName()));
        }
        DropDownChoice projectChoice = new DropDownChoice("projectList",
                                                          new Model(""),
                                                          list,
                                                          new GenericChoiceRenderer());
        add(projectChoice);
        projectChoice.add(new AjaxFormComponentUpdatingBehavior("onchange") {


            private static final long serialVersionUID = -2461473282928483661L;

            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                //trigger search
                System.out.println("target = " + target);
            }
        });
        projectChoice.setOutputMarkupId(true);

        add(new Label("issueTypeLabel", "Issue Type:"));
        List<IssueType> issueTypes = issueTypeManager.getAll();
        list = new ArrayList<SelectOptionI>(issueTypes.size());
        for (IssueType issueType : issueTypes) {
            list.add(new SelectOption(issueType.getKeyForOption(), issueType.getValueForOption()));
        }
        final DropDownChoice issueTypeChoice = new DropDownChoice("issueTypeList",
                                                          new Model(""),
                                                          list,
                                                          new GenericChoiceRenderer());
        add(issueTypeChoice);
        issueTypeChoice.add(new AjaxFormComponentUpdatingBehavior("onchange") {
            private static final long serialVersionUID = 8900134452591999405L;

            @Override
            protected void onUpdate(AjaxRequestTarget target) {

                //todo trigger search
                System.out.println("target = " + target);
            }
        });
        issueTypeChoice.setOutputMarkupId(true);

        add(new Label("statusLabel", "Status:"));
        List<WorkflowStatus> workflowStatuses = workflowStatusManager.getAll();
        list = new ArrayList<SelectOptionI>(workflowStatuses.size());
        for (WorkflowStatus workflowStatuse : workflowStatuses) {
            list.add(new SelectOption(workflowStatuse.getKeyForOption(), workflowStatuse.getValueForOption()));
        }
        final DropDownChoice wfStatusChoice = new DropDownChoice("statusList",
                                                                  new Model(""),
                                                                  list,
                                                                  new GenericChoiceRenderer());
        add(wfStatusChoice);
        wfStatusChoice.add(new AjaxFormComponentUpdatingBehavior("onchange") {
            private static final long serialVersionUID = 8900134452591999405L;

            @Override
            protected void onUpdate(AjaxRequestTarget target) {

                //todo trigger search
                System.out.println("target = " + target);
            }
        });
        wfStatusChoice.setOutputMarkupId(true);


    }
}
