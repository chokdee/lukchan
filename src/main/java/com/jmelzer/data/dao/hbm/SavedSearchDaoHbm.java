/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 27.05.12 
*
*/


package com.jmelzer.data.dao.hbm;

import com.jmelzer.data.dao.SavedSearchDao;
import com.jmelzer.data.model.SavedSearch;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class SavedSearchDaoHbm extends AbstractDaoHbm<SavedSearch> implements SavedSearchDao {

    @Override
    public SavedSearch findByName(String name) {
        Query query = getEntityManager().createQuery("select s from SavedSearch s where s.name = :searchname");
        query.setParameter("searchname", name);
        return (SavedSearch) query.getSingleResult();
    }

    @Override
    public List<SavedSearch> findByUserName(String username) {
        Query query = getEntityManager().createQuery("select s from SavedSearch s join s.owner o " +
                                                     "where o.loginName = :username");
        query.setParameter("username", username);
        return query.getResultList();
    }
}
