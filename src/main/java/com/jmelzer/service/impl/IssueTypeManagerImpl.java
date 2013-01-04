/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 04.01.13 
*
*/


package com.jmelzer.service.impl;

import com.jmelzer.data.dao.IssueTypeDao;
import com.jmelzer.data.model.IssueType;
import com.jmelzer.service.IssueTypeManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("issueTypeManager")
public class IssueTypeManagerImpl implements IssueTypeManager {

    @Resource
    IssueTypeDao issueTypeDao;

    @Override
    @Transactional
    public void save(IssueType issueType) {
        issueTypeDao.save(issueType);
    }
}
