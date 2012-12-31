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
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.PersistenceException;

import static junit.framework.Assert.*;

public class ProjectDaoTest extends AbstractBaseDaoTest{

    @Resource
    ProjectDao projectDao;

    @Resource
    IssueTypeDao issueTypeDao;

    @Test
    public void saveError() {
        Project project = new Project();

        try {
            projectDao.save(project);
            fail("mandotory fields not set");
        } catch (PersistenceException e) {
            //ok
        }
}
    @Test
    public void save() {
        Project project = new Project();
        project.setName("bla");
        project.setShortName("bla");
        projectDao.save(project);
        assertNotNull(project.getId());
    }

    @Test
    public void issues() {
        Project project = new Project();
        project.setName("bbb");
        project.setShortName("b");
        projectDao.save(project);

        IssueType issueType = new IssueType("bla");
        issueTypeDao.save(issueType);
        assertNotNull(issueType.getId());

        Issue issue = new Issue();
        issue.setType(issueType);


        project.addIssue(issue);
        projectDao.save(project);
        assertNull("shall not be saved", issue.getId());

        Component component = new Component("frontend");
        project.addComponent(component);
        projectDao.save(project);
        assertNotNull(component.getId());

    }
    @Test
    public void properties() {
        Project project = new Project();
        project.setName("bbb");
        project.setShortName("bbb");
        projectDao.save(project);

        DynamicProperty property = new DynamicProperty("bla", DynamicProperty.INTEGER,
                                                       "1");
        assertEquals(1, property.getIntValue());
        project.addProperty(property);
        projectDao.save(project);

        assertNotNull(property.getId());
    }
}