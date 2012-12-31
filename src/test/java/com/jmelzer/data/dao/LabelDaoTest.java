/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 27.05.12 
*
*/


package com.jmelzer.data.dao;

import com.jmelzer.data.model.Label;
import com.jmelzer.data.model.User;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.PersistenceException;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;


public class LabelDaoTest extends AbstractBaseDaoTest{

    @Resource
    LabelDao labelDao;


    @Test
    public void saveFailed() {
        Label label = new Label();

        try {
            labelDao.save(label);
            fail("mandatory fields not set");
        } catch (PersistenceException e) {
            //ok
        }


    }
    @Test
    public void save() {
        Label label = new Label("bla");
        labelDao.save(label);
        assertNotNull(label.getId());

    }
    @Test
    public void findByName() {
        Label label = new Label("bla");
        labelDao.save(label);

        assertNotNull(labelDao.findByName("bla"));


    }
}
