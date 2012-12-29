/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 17.06.12 
*
*/


package com.jmelzer.service.impl;

import com.jmelzer.data.dao.ActivityLogDao;
import com.jmelzer.data.dao.UserDao;
import com.jmelzer.data.model.ActivityLog;
import com.jmelzer.data.model.Issue;
import com.jmelzer.service.ActivityLogManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("activityLogManager")
public class ActivityLogManagerImpl implements ActivityLogManager {


    @Resource
    ActivityLogDao activityLogDao;

    @Resource
    UserDao userDao;

    @Override
    public void addActivity(String username, Issue issue, ActivityLog.Action action) {
        ActivityLog activityLog = new ActivityLog(action);
        activityLog.setIssue(issue);
        activityLog.setAuthor(userDao.findByUserName(username));
        activityLog.setUpdateAuthor(userDao.findByUserName(username));
    }
}
