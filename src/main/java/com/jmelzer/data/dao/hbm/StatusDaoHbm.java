/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 27.05.12 
*
*/


package com.jmelzer.data.dao.hbm;

import com.jmelzer.data.dao.StatusDao;
import com.jmelzer.data.model.WorkflowStatus;
import org.springframework.stereotype.Repository;

@Repository("statusDao")
public class StatusDaoHbm extends AbstractDaoHbm<WorkflowStatus> implements StatusDao {


}
