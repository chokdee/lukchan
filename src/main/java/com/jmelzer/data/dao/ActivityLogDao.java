/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 27.05.12 
*
*/


package com.jmelzer.data.dao;

import com.jmelzer.data.model.ActivityLog;

import java.util.List;

public interface ActivityLogDao extends AbstractDao<ActivityLog> {

    List<ActivityLog> findLast(int offset, int count);
}
