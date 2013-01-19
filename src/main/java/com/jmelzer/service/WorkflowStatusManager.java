/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 04.01.13 
*
*/


package com.jmelzer.service;

import com.jmelzer.data.model.WorkflowStatus;

import java.util.List;

public interface WorkflowStatusManager {
    void save(WorkflowStatus workflowStatus);

    List<WorkflowStatus> getAll();
}
