/*
 * Copyright (c) jmelzer 2011.
 * All rights reserved.
 */

package com.jmelzer.webapp.page;

import com.jmelzer.webapp.WicketApplication;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

/** Simple test using the WicketTester */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-web.xml", "classpath:spring.xml", "classpath:security.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
public class HomePageIntegrationTest {
    private WicketTester tester;
    @Autowired
    private ApplicationContext ctx;

    @Autowired
    private WicketApplication myWebApplication;

    @Before
    public void onSetUp() throws Exception {
        tester = new WicketTester(myWebApplication);
        myWebApplication.setApplicationContext(ctx);
    }

    @Test
    public void testRenderMyPage() {
        //start and render the test page
        tester.startPage(HomePage.class);

        //assert rendered page class
        tester.assertRenderedPage(HomePage.class);

        //assert rendered label component
        tester.assertLabel("border:border_body:welcome", "Welcome to Wreckcontrol");
    }
}
