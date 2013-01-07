/*
 * Copyright (c) jmelzer 2011.
 * All rights reserved.
 */


package com.jmelzer.webapp.page;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

/**
 * Border that holds layout elements that can be reused by pages.
 *
 * @author Eelco Hillenius
 */
public class TemplateBorder extends Border {

    private static final long serialVersionUID = 6487521775698193616L;


    /**
     * Constructor
     *
     * @param id
     */
    public TemplateBorder(String id) {
        super(id);

        FeedbackPanel feedback = new FeedbackPanel("feedback");
        addToBorder(feedback);
        AbstractLink link;
        if (!MainPage.isLoggedIn()) {
            link = new BookmarkablePageLink("loginlogout", LoginPage.class);
            link.add(new Label("loginlogoutLabel", "Login"));
        } else {
            link = new ExternalLink("loginlogout", "j_spring_security_logout" );
            link.add(new Label("loginlogoutLabel", "Logout"));
        }

        addToBorder(link);

//        add(new Label("feedback"));
    }
}