/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 30.12.12 
*
*/


package com.jmelzer.service;

import com.jmelzer.data.model.WorkflowStatus;

public interface WorkflowManager {

    WorkflowStatus getFirstStatus();
}
