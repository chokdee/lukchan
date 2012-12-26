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
import org.hibernate.TransientObjectException;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.Date;

import static junit.framework.Assert.*;

@ContextConfiguration(locations = {
        "classpath:spring.xml"}
)
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class IssueDaoTest {

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

    Status status;

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

        status = new Status("testi", 100);
        statusDao.save(status);

    }
    @Test
    public void saveError() {
        Issue issue = new Issue();

        try {
            issueDao.save(issue);
            fail("no type is set");
        } catch (ConstraintViolationException e) {
            //ok
        }
    }
    @Test
    public void saveDuplicateKey() {
        Issue issue = new Issue();

        issue.setType(issueType);
        issue.setPublicId("UST-1");
        project.addIssue(issue);
        issue.setSummary("summmmm");
        issue.setDueDate(new Date());
        issueDao.save(issue);
        assertNotNull(issue.getId());

        Issue child = new Issue();
        child.setType(issueType);
        child.setPublicId("UST-1");
        issue.addChild(child);
        try {
            issueDao.save(issue);
            fail("unique key");
        } catch (ConstraintViolationException e) {
            //duplicate key
            assertTrue(e.getMessage().contains("UST-1"));
        }
    }

    @Test
    public void saveLabel() {
        Issue issue = new Issue();

        issue.setType(issueType);
        issue.setPublicId("UST-1");
        project.addIssue(issue);
        issue.setSummary("summmmm");
        issue.setDueDate(new Date());
        issueDao.save(issue);
        assertNotNull(issue.getId());

        Label label = new Label("label");
        issue.addLabel(label);
        try {
            issueDao.save(issue);
            fail();
        } catch (TransientObjectException e) {
            //ok
            assertNull("labels shall not be persistent indirectly",
                       label.getId());
        }

    }
    @Test
    public void save() {
        User user = new User("1", "2", "3");
        userDao.save(user);

        Issue issue = new Issue();

        issue.setType(issueType);
        issue.setPublicId("UST-1");
        issue.setStatus(status);
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
        child.setStatus(status);
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

        issueDb.addComment(new Comment("1"));
        issueDb.addComment(new Comment("2"));

        issueDao.save(issueDb);
        issueDb = issueDao.findOne(issue.getId());
        assertEquals(2, issueDb.getComments().size());
        assertEquals("1", issueDb.getComments().iterator().next().getText());

    }

    public void testCreate() {

    }
}
