/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 27.05.12 
*
*/


package com.jmelzer.data.dao.hbm;

import com.jmelzer.data.dao.ProjectDao;
import com.jmelzer.data.model.Project;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("projectDao")
public class ProjectDaoHbm extends AbstractDaoHbm<Project> implements ProjectDao {

    @Override
    public List<String> findAllForAutoCompletion() {
        List<Project> projects = super.findAll();
        List<String> names = new ArrayList<String>(projects.size());
        for (Project project : projects) {
            names.add(project.getName());
        }
        return names;
    }

}
