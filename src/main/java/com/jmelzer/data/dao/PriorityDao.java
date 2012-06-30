/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 27.05.12 
*
*/


package com.jmelzer.data.dao;

import com.jmelzer.data.model.Priority;

import java.util.List;

public interface PriorityDao extends AbstractDao<Priority> {

    List< Priority > findAllByOrder();
}
