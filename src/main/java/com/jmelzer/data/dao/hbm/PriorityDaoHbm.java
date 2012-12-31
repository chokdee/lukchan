/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 27.05.12 
*
*/


package com.jmelzer.data.dao.hbm;

import com.jmelzer.data.dao.PriorityDao;
import com.jmelzer.data.model.Priority;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository("priorityDao")
public class PriorityDaoHbm extends AbstractDaoHbm<Priority> implements PriorityDao {

    @Override
    public List<Priority> findAllByOrder() {
        Query query = getEntityManager().createQuery("from Priority order by order");
        return query.getResultList();
    }
}
