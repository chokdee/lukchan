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
import com.jmelzer.data.model.ActivityLog;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository("activityLogDao")
public class ActivityLogDaoHbm extends AbstractDaoHbm<ActivityLog> implements ActivityLogDao {


    @Override
    public List<ActivityLog> findLast(int offset, int count) {
        Query query = getEntityManager().createQuery("from ActivityLog log order by  log.updated desc");
        query.setFirstResult(offset);
        query.setMaxResults(count);
        return query.getResultList();
    }
}
