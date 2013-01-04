/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 04.01.13 
*
*/


package com.jmelzer.service.impl;

import com.jmelzer.data.dao.StatusDao;
import com.jmelzer.data.model.WorkflowStatus;
import com.jmelzer.service.WorkflowStatusManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("workflowStatusManager")
public class WorkflowStatusManagerImpl implements WorkflowStatusManager {

    @Resource
    StatusDao statusDao;

    @Override
    @Transactional
    public void save(WorkflowStatus workflowStatus) {
        statusDao.save(workflowStatus);
    }
}
