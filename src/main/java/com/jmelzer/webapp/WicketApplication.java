/*
 * Copyright (c) jmelzer 2011.
 * All rights reserved.
 */

package com.jmelzer.webapp;

import com.jmelzer.webapp.examples.ChoicePage;
import com.jmelzer.webapp.page.*;
import com.jmelzer.webapp.page.secure.SearchIssuePage;
import com.jmelzer.webapp.page.secure.UserSettings;
import com.jmelzer.webapp.security.MyAuthenticatedWebSession;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.core.request.mapper.MountedMapper;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.settings.IRequestCycleSettings;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.util.file.Folder;
import org.apache.wicket.util.time.Duration;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import ro.fortsoft.wicket.dashboard.WidgetRegistry;
import ro.fortsoft.wicket.dashboard.web.DashboardContext;
import ro.fortsoft.wicket.dashboard.web.DashboardContextInjector;
import wicket.contrib.tinymce.settings.TinyMCESettings;


/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 *
 * @see com.jmelzer.Start#main(String[])
 */
public class WicketApplication extends AuthenticatedWebApplication implements ApplicationContextAware {

    boolean isInitialized = false;
    TinyMCESettings tinyMCESettings;
    ApplicationContext ctx;


    /** Constructor */
    public WicketApplication() {

    }


    /** @see org.apache.wicket.Application#getHomePage() */
    public Class<HomePage> getHomePage() {
        return HomePage.class;
    }

    private void setListeners() {
        getComponentInstantiationListeners().add(new SpringComponentInjector(this, ctx));
    }


    @Override
    protected void init() {
        if (isInitialized) {
            return;
        }
        super.init();
        setListeners();
        isInitialized = true;
        mount(new MountedMapper("login", LoginPage.class));
        mount(new MountedMapper("signup", SignupPage.class));
        mount(new MountedMapper("registerfinish", SignupResultPage.class));
        mount(new MountedMapper("sendpassword", SendPasswordPage.class));
        mount(new MountedMapper("changepassword", ChangePasswordPage.class));
        mount(new MountedMapper("createissue", CreateIssuePage.class));
        mountPage("/issue", ShowIssuePage.class);
        mountPage("/searchissue", SearchIssuePage.class);
        mount(new MountedMapper("choice", ChoicePage.class));
        mount(new MountedMapper("secure/usersettings", UserSettings.class));

        RuntimeConfigurationType configurationType = getConfigurationType();
        if (RuntimeConfigurationType.DEVELOPMENT == configurationType) {
            System.out.println("You are in DEVELOPMENT mode");
            getResourceSettings().setResourcePollFrequency(Duration.ONE_SECOND);
        }
        getRequestCycleSettings().setRenderStrategy(IRequestCycleSettings.RenderStrategy.REDIRECT_TO_RENDER);

        // >>> begin dashboard settings

        // register some widgets
        DashboardContext dashboardContext = new DashboardContext();
        WidgetRegistry widgetRegistry = dashboardContext.getWidgetRegistry();
//        widgetRegistry.registerWidget(new LoremIpsumWidgetDescriptor());

        // add dashboard context injector
        DashboardContextInjector dashboardContextInjector = new DashboardContextInjector(dashboardContext);
        getComponentInstantiationListeners().add(dashboardContextInjector);


    }

    @Override
    protected Class<? extends WebPage> getSignInPageClass() {
        return LoginPage.class;
    }

    @Override
    protected Class<? extends AuthenticatedWebSession> getWebSessionClass() {
        return MyAuthenticatedWebSession.class;
    }

    public Folder getUploadFolder() {
        return new Folder((String) ctx.getBean("attachment.path"));
    }

    public TinyMCESettings getTinyMCESettings() {
        if (tinyMCESettings == null) {
            tinyMCESettings = new TinyMCESettings(TinyMCESettings.Theme.simple);
            tinyMCESettings.setToolbarLocation(TinyMCESettings.Location.top);
        }
        return tinyMCESettings;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

}
