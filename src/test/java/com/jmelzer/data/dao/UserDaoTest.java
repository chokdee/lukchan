/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 27.05.12 
*
*/


package com.jmelzer.data.dao;

import com.jmelzer.data.model.User;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.PersistenceException;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;


public class UserDaoTest extends AbstractBaseDaoTest {

    @Resource
    UserDao userDao;


    @Test
    public void save() {
        User user = new User();
        user.setName("bla");
        user.setLoginName("bla");
        user.setPassword("blub");
        user.setEmail("bla@bla.de");
        user.setAvatar(new byte[] {0,1});
        userDao.save(user);
        assertNotNull(user.getId());
    }

    @Test
    public void saveError() {
        User user = new User();

        try {
            userDao.save(user);
            fail("mandotory fields not set");
        } catch (PersistenceException e) {
            //ok
        }
    }
}
