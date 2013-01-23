/*
 * Copyright (c) jmelzer 2011.
 * All rights reserved.
 */

package com.jmelzer.webapp.page;

import com.jmelzer.data.model.*;
import com.jmelzer.service.*;
import com.jmelzer.webapp.utils.PageParametersUtil;
import org.apache.commons.io.FileUtils;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.util.file.File;
import org.apache.wicket.util.tester.FormTester;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

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
    UserManager userManager;
    @Resource
    PageTestUtil pageTestUtil;
    @Resource(name = "attachment.path")
    String attachmentPath;

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
    @Test
    public void testUpload() throws IOException {
        SecurityContextHolder.clearContext();

        java.io.File dir = new java.io.File(attachmentPath);
        if (dir.exists()) {
            FileUtils.cleanDirectory(dir);
        }
        Issue issue = createIssue();

        //start and render the test page
        tester.startPage(ShowIssuePage.class, PageParametersUtil.createWithOneParam(issue.getPublicId()));

        tester.assertRenderedPage(ShowIssuePage.class);

        //no upload button shall be visible, cause of security reasons
        tester.assertInvisible("border:border_body:modalForm1:upload");

        pageTestUtil.login(tester);
        tester.assertLabel("border:loginlogout:loginlogoutLabel", "Logout");
        tester.startPage(ShowIssuePage.class, PageParametersUtil.createWithOneParam(issue.getPublicId()));
        tester.assertLabel("border:loginlogout:loginlogoutLabel", "Logout");
        tester.assertVisible("border:border_body:modalForm1:upload");
        tester.assertComponent("border:border_body:modalForm1:upload", AjaxButton.class);

        //security prepared for upload
        org.apache.wicket.Component modalWindow = tester.getComponentFromLastRenderedPage("border:border_body:modalUpload");
        assertFalse(((ModalWindow) modalWindow).isShown());
        tester.executeAjaxEvent("border:border_body:modalForm1:upload", "onclick");
        modalWindow = tester.getComponentFromLastRenderedPage("border:border_body:modalUpload");
        assertTrue(((ModalWindow) modalWindow).isShown());

        //I was not able to get the form in the modal window, but Wicket can handle this....
        ShowIssuePage showIssuePage = (ShowIssuePage) tester.getLastRenderedPage();

        UploadPage uploadPage = new UploadPage(showIssuePage, (ModalWindow) modalWindow);
        tester.startPage(uploadPage);
        tester.assertRenderedPage(UploadPage.class);

        FormTester formTester = tester.newFormTester("html5Upload");
        formTester.setFile("fileInput", new File("c:\\tmp\\1.txt"), MediaType.TEXT_PLAIN.toString());
        formTester.submit();

        tester.executeAjaxEvent("html5Upload:ok", "onclick");

        //test business logic
        assertEquals("original file must be there", 1, FileUtils.listFiles(dir, new String[] {"txt"}, false).size());
        assertEquals("preview file must be there", 1, FileUtils.listFiles(dir, new String[] {"jpg"}, false).size());

        //attachment must be show in the section attachments part

    }
}
