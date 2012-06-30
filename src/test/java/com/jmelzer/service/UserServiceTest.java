/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 02.06.12 
*
*/


package com.jmelzer.service;

import com.jmelzer.data.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;

@ContextConfiguration(locations = {
        "classpath:spring.xml"
}
)
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class UserServiceTest {
    @Resource
    UserService userService;

    @Test
    public void testCreateUser() throws Exception {
        User user = userService.createUser("bla@bla.de", "test", "testpw", "name");
        assertNotNull(user);

        try {
            userService.createUser("bla@bla.de", "test", "testpw", "name");
            fail("duplicate entry");
        } catch (DuplicateKeyException e) {
            //ok
        }
    }
}
