/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 04.01.13 
*
*/


package com.jmelzer.service.impl;

import com.jmelzer.data.dao.ProjectDao;
import com.jmelzer.data.model.Project;
import com.jmelzer.service.ProjectManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("projectManager")
public class ProjectManagerImpl implements ProjectManager {
    @Resource
    ProjectDao projectDao;

    @Override
    @Transactional
    public void save(Project project) {
        projectDao.save(project);
        //todo add activity
    }
}
