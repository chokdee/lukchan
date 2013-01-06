/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 06.01.13 
*
*/


package com.jmelzer.webapp.page;

import com.jmelzer.data.model.User;
import com.jmelzer.service.UserService;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class PageTestUtil {
    @Resource
    UserService userService;

    public User login(WicketTester tester) {
        User user = userService.createUser("bla@bla.de", "admin", "42", "admin");

        //start and render the test page
        tester.startPage(LoginPage.class);

        FormTester form = tester.newFormTester("border:border_body:loginForm");
        // set the parameters for each component in the form
        // notice that the name is relative to the form - so it's 'username', not 'form:username' as in assertComponent
        form.setValue("userId", "admin");
        // unset value is empty string (wicket binds this to null, so careful if your setter does not expect nulls)
        form.setValue("password", "42");

        form.submit();


        //assert rendered page class
        tester.assertRenderedPage(HomePage.class);
        return user;
    }
}
