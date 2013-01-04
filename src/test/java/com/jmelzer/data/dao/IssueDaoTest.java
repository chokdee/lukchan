/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 27.05.12 
*
*/


package com.jmelzer.data.dao;

import com.jmelzer.data.model.*;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import javax.persistence.PersistenceException;

import java.util.Date;

import static junit.framework.Assert.*;

public class IssueDaoTest extends AbstractBaseDaoTest{

    @Resource
    IssueDao issueDao;
    @Resource
    LabelDao labelDao;
    @Resource
    PriorityDao priorityDao;
    @Resource
    IssueTypeDao issueTypeDao;
    @Resource
    UserDao userDao;
    @Resource
    ProjectDao projectDao;
    @Resource
    StatusDao statusDao;

    IssueType issueType;
    Project project;

    WorkflowStatus workflowStatus;
    User user;

    @Before
    public void before() {
        project = new Project();
        project.setName("ppp");
        project.setShortName("P");
        projectDao.save(project);
        {
            ProjectVersion projectVersion = new ProjectVersion();
            projectVersion.setVersionNumber("0.9");
            project.addVersion(projectVersion);
        }
        {
            ProjectVersion projectVersion = new ProjectVersion();
            projectVersion.setVersionNumber("1.0");
            project.addVersion(projectVersion);
        }

        projectDao.save(project);
        {
            Component component = new Component("service");
            project.addComponent(component);
        }
        projectDao.save(project);

        issueType = new IssueType("bla");
        issueTypeDao.save(issueType);
        assertNotNull(issueType.getId());

        workflowStatus = new WorkflowStatus("testi", 100);
        statusDao.save(workflowStatus);

        user = DaoUtil.createUser("1", userDao);

    }
    @Test
    public void saveError() {
        Issue issue = new Issue();

        try {
            issueDao.save(issue);
            fail("no type is set");
        } catch (PersistenceException e) {
            //ok
        }
    }
    @Test
    public void saveDuplicateKey() {
        Issue issue = createIssue();
        issueDao.save(issue);
        assertNotNull(issue.getId());

        Issue child = new Issue();
        child.setType(issueType);
        child.setPublicId("UST-1");
        issue.addChild(child);
        child.setReporter(user);
        child.setWorkflowStatus(workflowStatus);
        try {
            issueDao.save(issue);
            fail("unique key");
        } catch (PersistenceException e) {
            assertTrue(e.getMessage().contains("unique constraint"));
        }
    }

    @Test
    public void saveLabel() {
        Issue issue = createIssue();
        issueDao.save(issue);
        assertNotNull(issue.getId());

        Label label = new Label("label");
        issue.addLabel(label);
        try {
            issueDao.save(issue);
            fail();
        } catch (IllegalStateException e) {
            //ok
            assertTrue(e.getMessage().contains("TransientObjectException"));
            assertNull("labels shall not be persistent indirectly",
                       label.getId());
        }

    }

    private Issue createIssue() {
        Issue issue = new Issue();

        issue.setType(issueType);
        issue.setPublicId("UST-1");
        project.addIssue(issue);
        issue.setSummary("summmmm");
        issue.setDueDate(new Date());
        issue.setReporter(user);
        issue.setWorkflowStatus(workflowStatus);
        return issue;
    }

    @Test
    public void save() {


        Issue issue = new Issue();

        issue.setType(issueType);
        issue.setPublicId("UST-1");
        issue.setWorkflowStatus(workflowStatus);
        issue.setReporter(user);
        project.addIssue(issue);
        issue.addComponent(project.getComponent(0));
        issue.addComponent(project.getComponent(1));
        issue.setSummary("summmmm");
        issue.setDueDate(new Date());
        issueDao.save(issue);
        assertNotNull(issue.getId());

        Issue child = new Issue();
        child.setType(issueType);
        child.setPublicId("UST-2");
        child.setWorkflowStatus(workflowStatus);
        child.setReporter(user);
        issue.addChild(child);
        issueDao.save(issue);

        assertNotNull(child.getId());

        Attachment attachment = new Attachment();
        issue.addAttachment(attachment);

        issueDao.save(issue);
        assertNotNull(attachment.getId());

        Label label = new Label("label");
        labelDao.save(label);
        issue.addLabel(label);
        issueDao.save(issue);

        Issue issueDb = issueDao.findOne(issue.getId());
        assertNotNull(issueDb);
        assertEquals("label", issueDb.getLabels().iterator().next().getName());

        Priority priority = new Priority("High");
        priorityDao.save(priority);
        issueDb.setPriority(priority);
        issueDao.save(issueDb);



        issueDb.setAssignee(user);
        issueDao.save(issueDb);

        issueDb = issueDao.findOne(issue.getId());
        assertNotNull(issueDb.getAssignee());

        issue.addAffectedVersion(project.getVersionByName("0.9"));
        issue.addAffectedVersion(project.getVersionByName("1.0"));
        issue.addFixInVersion(project.getVersionByName("1.0"));
        issue.addFixInVersion(project.getVersionByName("0.9"));

        issueDao.save(issueDb);

        issueDb = issueDao.findOne(issue.getId());
        assertEquals(2, issueDb.getAffectedVersions().size());
        assertEquals(2, issueDb.getFixInVersions().size());

        issueDb.addComment(new Comment("1", user));
        issueDb.addComment(new Comment("2", user));

        issueDao.save(issueDb);
        issueDb = issueDao.findOne(issue.getId());
        assertEquals(2, issueDb.getComments().size());
        assertEquals("1", issueDb.getComments().iterator().next().getText());

    }
    @Test
    public void testComment() {
        Issue issue = createIssue();
        issue.addComment(new Comment("1", user));
        issueDao.save(issue);

        Issue issueDb = issueDao.findOne(issue.getId());
        Comment comment = issueDb.getComments().iterator().next();
        assertEquals("1", comment.getText());
        issueDao.saveComment(comment.getId(), "changed", user);

        issueDb = issueDao.findOne(issue.getId());
        comment = issueDb.getComments().iterator().next();
        assertEquals("changed", comment.getText());
        assertEquals(user.getUsername(), comment.getOwner().getUsername());


        issueDao.deleteComment(comment.getId());
    }
}
