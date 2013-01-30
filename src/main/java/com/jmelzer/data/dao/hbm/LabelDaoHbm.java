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
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

@Repository
public class LabelDaoHbm extends AbstractDaoHbm<Label> implements LabelDao {

    @Override
    public Label findByName(String name) {
        Query query = getEntityManager().createQuery("select l from Label l where l.name = :name");
        query.setParameter("name", name);
        return (Label) query.getSingleResult();
    }
}
