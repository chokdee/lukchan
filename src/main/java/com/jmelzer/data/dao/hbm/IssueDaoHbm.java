/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 27.05.12 
*
*/


package com.jmelzer.data.dao.hbm;

import com.jmelzer.data.dao.IssueDao;
import com.jmelzer.data.model.Issue;
import com.jmelzer.data.model.User;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository
public class IssueDaoHbm extends AbstractDaoHbm<Issue> implements IssueDao {

    @Override
    public Issue findIssueByShortName(String shortName) {
        Query query = getCurrentSession().createQuery("from Issue where publicId = ?");
        query.setParameter(0, shortName);
        return (Issue) query.uniqueResult();
    }
}
