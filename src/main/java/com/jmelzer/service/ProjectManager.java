/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 04.01.13 
*
*/


package com.jmelzer.service;

import com.jmelzer.data.model.Project;

import java.util.List;

public interface ProjectManager {
    void save(Project project);

    List<Project> getAll();

}
