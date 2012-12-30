/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 30.12.12 
*
*/


package com.jmelzer.service.impl;

import com.jmelzer.data.dao.StatusDao;
import com.jmelzer.data.model.Status;
import com.jmelzer.service.WorkflowManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("workflowManager")
public class WorkflowManagerImpl implements WorkflowManager {

    @Resource
    StatusDao statusDao;

    @Override
    @Transactional(readOnly = true)
    public Status getFirstStatus() {
        return statusDao.findAll().get(0);
    }
}
