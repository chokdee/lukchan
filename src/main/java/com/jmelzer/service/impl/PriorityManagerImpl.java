/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 04.01.13 
*
*/


package com.jmelzer.service.impl;

import com.jmelzer.data.dao.PriorityDao;
import com.jmelzer.data.model.Priority;
import com.jmelzer.service.PriorityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("priorityManager")
public class PriorityManagerImpl implements PriorityManager {

    @Resource
    PriorityDao priorityDao;

    @Override
    @Transactional
    public void save(Priority priority) {
        priorityDao.save(priority);
    }
}
