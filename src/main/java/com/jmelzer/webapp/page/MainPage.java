/*
 * Copyright (c) jmelzer 2011.
 * All rights reserved.
 */


package com.jmelzer.webapp.page;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class MainPage extends WebPage {
    private static final long serialVersionUID = -3460787298344490939L;
    private Border border;

    public MarkupContainer addDirectly(final Component child) {
        return super.add(child);
    }
    public MarkupContainer add(final Component child) {
        // Add children of the page to the page's border component
        if (border == null) {
            // Create border and add it to the page
            border = new TemplateBorder("border");
            super.add(border);

        }
        border.add(child);
        return this;
    }

    public Border getBorder() {
        return border;
    }

    public static boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (authentication!=null && !"anonymousUser".equals(authentication.getPrincipal()));
    }

}