/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 27.05.12 
*
*/


package com.jmelzer.data.dao.hbm;

import com.jmelzer.data.dao.ActivityLogDao;
import com.jmelzer.data.dao.StatusDao;
import com.jmelzer.data.model.ActivityLog;
import com.jmelzer.data.model.Status;
import org.springframework.stereotype.Repository;

@Repository("activityLogDao")
public class ActivityLogDaoHbm extends AbstractDaoHbm<ActivityLog> implements ActivityLogDao {


}
