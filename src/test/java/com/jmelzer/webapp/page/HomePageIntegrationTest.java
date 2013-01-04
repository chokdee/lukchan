/*
 * Copyright (c) jmelzer 2011.
 * All rights reserved.
 */

package com.jmelzer.webapp.page;

import org.junit.Test;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class HomePageIntegrationTest extends AbstractPageIntegrationTest {

    @Test
    public void testRenderMyPage() {
        SecurityContextHolder.clearContext();

        //start and render the test page
        tester.startPage(HomePage.class);

        //assert rendered page class
        tester.assertRenderedPage(HomePage.class);

        //assert rendered label component
        tester.assertLabel("border:border_body:welcome", "Welcome to Wreckcontrol");
    }
}
