/*
 * Copyright (c) jmelzer 2011.
 * All rights reserved.
 */

package com.jmelzer.webapp.page;


import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

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

