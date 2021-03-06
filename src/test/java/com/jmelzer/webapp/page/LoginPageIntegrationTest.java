/*
 * Copyright (c) jmelzer 2011.
 * All rights reserved.
 */

package com.jmelzer.webapp.page;

import com.jmelzer.service.UserManager;
import org.apache.wicket.util.tester.FormTester;
import org.junit.Test;

import javax.annotation.Resource;

public class LoginPageIntegrationTest extends AbstractPageIntegrationTest {

    @Resource
    UserManager userManager;
    @Resource
    PageTestUtil pageTestUtil;

    @Test
    public void testInvalidLogin() {

        //start and render the test page
        tester.startPage(LoginPage.class);

        FormTester form = tester.newFormTester("border:border_body:loginForm");
        // set the parameters for each component in the form
        // notice that the name is relative to the form - so it's 'username', not 'form:username' as in assertComponent
        form.setValue("userId", "test");
        // unset value is empty string (wicket binds this to null, so careful if your setter does not expect nulls)
        form.setValue("password", "");

        form.submit();


        //assert rendered page class
        tester.assertRenderedPage(LoginPage.class);

        //assert rendered label component
//        tester.as("border:border_body:welcome", "Welcome to Wreckcontrol");
        tester.assertErrorMessages(new String[] { "Bitte tragen Sie einen Wert im Feld 'password' ein." });
    }

    @Test
    public void testValidLogin() {

        pageTestUtil.login(tester);
        tester.assertLabel("border:loginlogout:loginlogoutLabel", "Logout");

    }
}
