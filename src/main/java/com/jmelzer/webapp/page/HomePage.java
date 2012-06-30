/*
 * Copyright (c) jmelzer 2011.
 * All rights reserved.
 */

package com.jmelzer.webapp.page;

import org.apache.wicket.PageParameters;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.basic.Label;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/** Homepage */
public class HomePage extends MainPage {

    private static final long serialVersionUID = 1L;


    /**
     * Constructor that is invoked when page is invoked without a session.
     *
     * @param parameters Page parameters
     */
    public HomePage(final PageParameters parameters) {


        String welcome;
        if (isLoggedIn()) {
            welcome = "Welcome to Wreckcontrol you are logged in ;-)";
        } else {
            welcome = "Welcome to Wreckcontrol";
        }
        add(new Label("welcome", welcome));
    }

}

