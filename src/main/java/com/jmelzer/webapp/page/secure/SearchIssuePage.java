/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 14.01.13 
*
*/


package com.jmelzer.webapp.page.secure;

import com.jmelzer.data.model.Issue;
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
import com.jmelzer.webapp.ui.IssueIconPanel;
import com.jmelzer.webapp.ui.IssueListProvider;
import com.jmelzer.webapp.ui.LinkIssuePanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WicketForgeJavaIdInspection")
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

    WebMarkupContainer resultPanel;
    private final Model selectedIssueType;
    private final Model selectedProject;
    private final Model selectedWfStatus;
    private IssueListProvider issueListProvider;

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
                                                          selectedProject = new Model(""),
                                                          list,
                                                          new GenericChoiceRenderer());
        add(projectChoice);
        projectChoice.add(new AjaxTriggerSearch());
        projectChoice.setOutputMarkupId(true);

        add(new Label("issueTypeLabel", "Issue Type:"));
        List<IssueType> issueTypes = issueTypeManager.getAll();
        list = new ArrayList<SelectOptionI>(issueTypes.size());
        for (IssueType issueType : issueTypes) {
            list.add(new SelectOption(issueType.getKeyForOption(), issueType.getValueForOption()));
        }
        final DropDownChoice issueTypeChoice = new DropDownChoice("issueTypeList",
                                                                  selectedIssueType = new Model(""),
                                                                  list,
                                                                  new GenericChoiceRenderer());
        add(issueTypeChoice);
        issueTypeChoice.add(new AjaxTriggerSearch());
        issueTypeChoice.setOutputMarkupId(true);

        add(new Label("statusLabel", "Status:"));
        List<WorkflowStatus> workflowStatuses = workflowStatusManager.getAll();
        list = new ArrayList<SelectOptionI>(workflowStatuses.size());
        for (WorkflowStatus workflowStatuse : workflowStatuses) {
            list.add(new SelectOption(workflowStatuse.getKeyForOption(), workflowStatuse.getValueForOption()));
        }
        final DropDownChoice wfStatusChoice = new DropDownChoice("statusList",
                                                                 selectedWfStatus = new Model(""),
                                                                 list,
                                                                 new GenericChoiceRenderer());
        add(wfStatusChoice);
        wfStatusChoice.add(new AjaxTriggerSearch());
        wfStatusChoice.setOutputMarkupId(true);

        createResultPanel();
    }

    private void createResultPanel() {
        resultPanel = new WebMarkupContainer("resultPanel");
        resultPanel.setOutputMarkupId(true);
        add(resultPanel);

        System.out.println("selectedIssueType = " + selectedIssueType);

        List<IColumn<Issue, String>> columns = new ArrayList<IColumn<Issue, String>>();
        List<Issue> issueList = issueManager.getAssignedIssues("developer");

        // Typ (Icon) | Issue key | Priority | Status | Summary
        columns.add(new AbstractColumn<Issue, String>(new Model<String>("Type")) {

            private static final long serialVersionUID = 8923217104203573321L;

            @Override
            public void populateItem(Item<ICellPopulator<Issue>> cellItem, String componentId,
                                     IModel<Issue> model) {
                cellItem.add(new IssueIconPanel(componentId, model));
            }
        });
        columns.add(new AbstractColumn<Issue, String>(new Model<String>("ID")) {

            private static final long serialVersionUID = 1334159427720431587L;

            @Override
            public void populateItem(Item<ICellPopulator<Issue>> cellItem, String componentId,
                                     IModel<Issue> model) {
                cellItem.add(new LinkIssuePanel(componentId, model));
            }
        });
//        columns.add(new PropertyColumn<Issue, String>(new Model<String>(getString("id")), "publicId"));
        columns.add(new PropertyColumn<Issue, String>(new Model<String>(getString("priority")), "priority.name"));
        columns.add(new PropertyColumn<Issue, String>(new Model<String>(getString("status")), "workflowStatus.name"));
        columns.add(new PropertyColumn<Issue, String>(new Model<String>(getString("summary")), "summary"));

        resultPanel.add(new AjaxFallbackDefaultDataTable<Issue, String>("result", columns,
                                                                        issueListProvider = new IssueListProvider(issueList), 20));
    }

    private void filterIssues() {

        List<Issue> issueList = issueManager.findIssues(getKeyForOption(selectedProject),
                                                        getKeyForOption(selectedWfStatus),
                                                        getKeyForOption(selectedIssueType));
        issueListProvider.setIssues(issueList);
    }
    private Long getKeyForOption(Model model) {
        if (model.getObject() instanceof SelectOptionI) {
            return ((SelectOptionI) model.getObject()).getKeyForOption();
        } else {
            return null;
        }
    }

    class AjaxTriggerSearch extends AjaxFormComponentUpdatingBehavior {

        private static final long serialVersionUID = 8900134452591999405L;

        public AjaxTriggerSearch() {
            super(("onchange"));
        }
        @Override
        protected void onUpdate(AjaxRequestTarget target) {
            filterIssues();
            target.add(resultPanel);
        }

    }
}
