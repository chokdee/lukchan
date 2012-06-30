/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 27.05.12 
*
*/


package com.jmelzer.data.dao;

import com.jmelzer.data.model.ui.View;

public interface ViewDao extends AbstractDao<View> {

    View findByName(String viewid);
}
