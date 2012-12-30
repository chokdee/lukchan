/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 29.12.12 
*
*/


package com.jmelzer.service;

import com.jmelzer.data.model.ActivityLog;
import com.jmelzer.data.model.Issue;

import java.util.List;

public interface ActivityLogManager {

    void addActivity(String username, Issue issue, ActivityLog.Action action, String body);
    List<ActivityLog> getLatestActivities();
}
