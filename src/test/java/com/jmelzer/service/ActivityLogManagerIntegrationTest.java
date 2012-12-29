/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 02.06.12 
*
*/


package com.jmelzer.service;

import com.jmelzer.data.dao.ActivationCodeDao;
import com.jmelzer.data.model.ActivityLog;
import com.jmelzer.data.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jvnet.mock_javamail.Mailbox;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@ContextConfiguration(locations = {
        "classpath:spring.xml"
}
)
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class ActivityLogManagerIntegrationTest {

    @Resource
    ActivityLogManager activityLogManager;

    @Test
    public void getLatestActivities() {
        List<ActivityLog> list = activityLogManager.getLatestActivities();
        assertNotNull(list);
        assertTrue(list.size() > 0);
        for (ActivityLog activityLog : list) {
            assertFalse(activityLog.getActivityAsString().contains("{"));
            assertFalse(activityLog.getActivityAsString().contains("}"));
        }
    }
}
