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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;

@ContextConfiguration(locations = {
        "classpath:spring.xml"}
)
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ActivityLogDaoTest {

    @Resource
    ActivityLogDao activityLogDao;
    @Resource
    IssueDao issueDao;
    @Resource
    UserDao userDao;

    Issue issue;

    @Test
    public void saveFailed() {
        ActivityLog activityLog = new ActivityLog(ActivityLog.Action.COMMENT_ISSUE);

        try {
            activityLogDao.save(activityLog);
            fail("mandatory fields not set");
        } catch (ConstraintViolationException e) {
            //ok
        }


    }

    @Before
    public void before() {
        issue = issueDao.findOne(1L);
    }

    @Test
    public void save() {
        ActivityLog activityLog = new ActivityLog(ActivityLog.Action.COMMENT_ISSUE);
        activityLog.setIssue(issue);
        activityLog.setAuthor(userDao.findByUserName("developer"));
        activityLog.setUpdateAuthor(userDao.findByUserName("developer"));
        activityLog.setActivityAsString("bla");
        activityLogDao.save(activityLog);
        assertNotNull(activityLog.getId());

    }

    @Test
    public void findLast() {
        List<ActivityLog> list = activityLogDao.findLast(0, 1);
        assertNotNull(list);
        assertEquals(1, list.size());
    }
}
