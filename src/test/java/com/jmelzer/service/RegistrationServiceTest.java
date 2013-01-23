/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 02.06.12 
*
*/


package com.jmelzer.service;

import com.jmelzer.data.dao.ActivationCodeDao;
import com.jmelzer.data.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jvnet.mock_javamail.Mailbox;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@ContextConfiguration(locations = {
        "classpath:spring.xml"
}
)
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
@ActiveProfiles(profiles = "test")
@RunWith(SpringJUnit4ClassRunner.class)
public class RegistrationServiceTest {

    @Resource
    RegistrationService registrationService;

    @Resource
    UserManager userManager;

    @Resource
    ActivationCodeDao activationCodeDao;

    final String EMAIL = "admin@wreckcontrol.net";
    @Test
    public void testRegister() throws Exception {

        List inbox = Mailbox.get("bla@bla.de");
        assertEquals("wrong number of E-Mails", 0, inbox.size());

        User user = registrationService.register("bla@bla.de", "username", "pw", "name");
        inbox = Mailbox.get(user.getEmail());
        assertEquals("wrong number of E-Mails", 1, inbox.size());
        String content = ((String)((Mailbox) inbox).get(0).getContent());
        assertTrue(content, content.contains("<b>Hallo "));

    }

    @Test
    public void testPw() throws Exception {

        userManager.createUser(EMAIL, "admin", "42", "admin");

        int sizeBefore = activationCodeDao.findAll().size();

        List inbox = Mailbox.get(EMAIL);
        assertEquals("wrong number of E-Mails", 0, inbox.size());

        registrationService.sendPasswordLink(EMAIL);
        inbox = Mailbox.get(EMAIL);
        assertEquals("wrong number of E-Mails", 1, inbox.size());
        String content = ((String)((Mailbox) inbox).get(0).getContent());
        assertTrue(content, content.contains("<b>Hallo "));

        assertEquals(sizeBefore+1, activationCodeDao.findAll().size());
    }
}
