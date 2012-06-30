/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 27.05.12 
*
*/


package com.jmelzer.data.dao.hbm;

import com.jmelzer.data.dao.UserDao;
import com.jmelzer.data.model.User;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDaoHbm extends AbstractDaoHbm<User> implements UserDao {

    @Override
    public User findByUserNameNonLocked(String username) {
        Query query = getCurrentSession().createQuery("from User where loginName = ? and locked = false");
        query.setParameter(0, username);
        return (User) query.uniqueResult();
    }

    @Override
    public User findByUserName(String username) {
        Query query = getCurrentSession().createQuery("from User where loginName = ? ");
        query.setParameter(0, username);
        return (User) query.uniqueResult();
    }

    @Override
    public User findByEmail(String email) {
        Query query = getCurrentSession().createQuery("from User where email = ? ");
        query.setParameter(0, email);
        return (User) query.uniqueResult();
    }
}
