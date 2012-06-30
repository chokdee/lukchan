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
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository("viewDao")
public class ViewDaoHbm extends AbstractDaoHbm<View> implements ViewDao {

    @Override
    public View findByName(String viewid) {
        Query query = getCurrentSession().createQuery("from View where name = ? ");
        query.setParameter(0, viewid);
        return (View) query.uniqueResult();
    }
}
