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
import com.jmelzer.data.dao.StatusDao;
import com.jmelzer.data.model.Project;
import com.jmelzer.data.model.Status;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("statusDao")
public class StatusDaoHbm extends AbstractDaoHbm<Status> implements StatusDao {


}
