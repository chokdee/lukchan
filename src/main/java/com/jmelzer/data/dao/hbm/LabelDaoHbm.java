/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 27.05.12 
*
*/


package com.jmelzer.data.dao.hbm;

import com.jmelzer.data.dao.LabelDao;
import com.jmelzer.data.model.Label;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository
public class LabelDaoHbm extends AbstractDaoHbm<Label> implements LabelDao {

    @Override
    public Label findByName(String name) {
        Query query = getCurrentSession().createQuery("from Label where name = ?");
        query.setParameter(0, name);
        return (Label) query.uniqueResult();
    }
}
