/*
 * Copyright (c) jmelzer 2011.
 * All rights reserved.
 */

package com.jmelzer.webapp.page;

import com.jmelzer.data.model.*;
import com.jmelzer.service.*;
import com.jmelzer.webapp.utils.PageParametersUtil;
import org.junit.Test;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.Resource;
import java.util.Date;

public class ShowIssuePageIntegrationTest extends AbstractPageIntegrationTest {

    @Resource
    IssueManager issueManager;
    @Resource
    ProjectManager projectManager;
    @Resource
    IssueTypeManager issueTypeManager;
    @Resource
    PriorityManager priorityManager;
    @Resource
    WorkflowStatusManager workflowStatusManager;
    @Resource
    UserService userManager;

    @Test
    public void testRedirect() {
        SecurityContextHolder.clearContext();

        //start and render the test page
        tester.startPage(ShowIssuePage.class, PageParametersUtil.createWithOneParam("BLU-1"));

        //,ust be redirect, no issue exists
        tester.assertRenderedPage(HomePage.class);


    }

    @Test
    public void testShow() {
        SecurityContextHolder.clearContext();

        Issue issue = createIssue();

        //start and render the test page
        tester.startPage(ShowIssuePage.class, PageParametersUtil.createWithOneParam(issue.getPublicId()));

        tester.assertRenderedPage(ShowIssuePage.class);
        //todo test if everytrhing was rendered
        // test comment actions


    }

    private Issue createIssue() {
        Project project = new Project();
        project.setName("blablub");
        project.setShortName("BLA");
        projectManager.save(project);

        IssueType issueType = new IssueType("Bug", "images/bug.gif");
        issueTypeManager.save(issueType);

        Priority priority = new Priority("P1");
        priorityManager.save(priority);

        WorkflowStatus workflowStatus = new WorkflowStatus("1", 1);
        workflowStatusManager.save(workflowStatus);

        User user = userManager.createUser("bla@bla.de", "aaa", "42", "aaa");

        Issue issue = new Issue();

        issue.setSummary("sum");
        issue.setDescription("desc");
        issue.setDueDate(new Date());
        issueManager.create(issue, project.getId(),
                            issueType.getId(),
                            priority.getId(),
                            null,
                            user.getLoginName());
        return issue;
    }
}
