/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 27.05.12 
*
*/


package com.jmelzer.data.dao.hbm;

import com.jmelzer.data.dao.ViewDao;
import com.jmelzer.data.model.ui.View;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

@Repository("viewDao")
public class ViewDaoHbm extends AbstractDaoHbm<View> implements ViewDao {

    @Override
    public View findByName(String viewid) {
        return querySingleResult("from View where name = ?", viewid);
    }
}
