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
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service("activityLogManager")
public class ActivityLogManagerImpl implements ActivityLogManager, ApplicationContextAware {


    @Resource
    ActivityLogDao activityLogDao;

    @Resource
    UserDao userDao;

    ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    @Override
    public void addActivity(String username, Issue issue, ActivityLog.Action action) {
        ActivityLog activityLog = new ActivityLog(action);
        activityLog.setIssue(issue);
        activityLog.setAuthor(userDao.findByUserName(username));
        activityLog.setUpdateAuthor(userDao.findByUserName(username));

        String activityAsString = null;
        //todo: i18n
        switch (action) {
            case CREATE_ISSUE:
                activityAsString = "{action.created} <ISSUE>";
                break;
            case COMMENT_ISSUE:
                activityAsString = "{action.commented} <ISSUE>";
                break;
        }
        activityLog.setActivityAsString(activityAsString);
        activityLogDao.save(activityLog);
    }

    public List<ActivityLog> getLatestActivities() {
        List<ActivityLog> list = activityLogDao.findLast(0, 5);
        List<ActivityLog> retList = new ArrayList<ActivityLog>(5);
        int n = 0;
        for (ActivityLog activityLog : list) {

            retList.add(activityLog);
            String activityAsString = activityLog.getActivityAsString();
            String prop = activityAsString.substring(activityAsString.indexOf('{')+1,
                                                     activityAsString.indexOf('}'));
            String msgTranslated = context.getMessage(prop, null, Locale.getDefault());
            String newStr = activityAsString.replace("{"+prop+"}", msgTranslated);
            activityLog.setActivityAsString(newStr);
            if (++n > 5) {
                break;
            }
        }
        return retList;
    }
}
