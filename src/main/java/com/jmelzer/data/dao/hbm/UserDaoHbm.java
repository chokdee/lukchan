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
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

@Repository("userDao")
public class UserDaoHbm extends AbstractDaoHbm<User> implements UserDao {

    @Override
    public User findByUserNameNonLocked(String username) {
        return querySingleResult("from User where loginName = ? and locked = false", username);
    }

    @Override
    public User findByUserName(String username) {
        Query query = getEntityManager().createQuery("from User where loginName = ? ", User.class);
        query.setParameter(1, username);
        return (User) query.getSingleResult();
    }

    @Override
    public User findByEmail(String email) {
        Query query = getEntityManager().createQuery("from User where email = ? ", User.class);
        query.setParameter(1, email);
        return (User) query.getSingleResult();
    }
}
