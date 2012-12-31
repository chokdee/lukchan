/*
 * Copyright (c) jmelzer 2011.
 * All rights reserved.
 */

package com.jmelzer.webapp.page;


import com.jmelzer.service.ActivityLogManager;
import com.jmelzer.webapp.ui.widgets.ActivityLogWidget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ro.fortsoft.wicket.dashboard.Dashboard;
import ro.fortsoft.wicket.dashboard.DefaultDashboard;
import ro.fortsoft.wicket.dashboard.web.DashboardPanel;


/** Homepage */
public class HomePage extends MainPage {

    private static final long serialVersionUID = 1L;

    Dashboard dashboard;
    @SpringBean(name = "activityLogManager")
    ActivityLogManager activityLogManager;

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
        dashboard = new DefaultDashboard("default", "Default");

        ActivityLogWidget logWidget = new ActivityLogWidget("1");
        logWidget.setActivityLogs(activityLogManager.getLatestActivities());
        dashboard.addWidget(logWidget);
        add(new DashboardPanel("dashboard", new Model<Dashboard>(dashboard)));


    }

}

