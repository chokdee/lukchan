/*
 * Copyright (c) jmelzer 2011.
 * All rights reserved.
 */

package com.jmelzer.webapp.page;

import org.junit.Test;

public class HomePageIntegrationTest extends AbstractPageIntegrationTest {

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
