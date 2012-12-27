/*
 * Copyright (c) jmelzer 2011.
 * All rights reserved.
 */

package com.jmelzer.webapp;

import com.jmelzer.webapp.examples.ChoicePage;
import com.jmelzer.webapp.page.*;
import com.jmelzer.webapp.page.secure.UserSettings;
import com.jmelzer.webapp.security.MyAuthenticatedWebSession;
import org.apache.wicket.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.target.coding.IndexedParamUrlCodingStrategy;
import org.apache.wicket.request.target.coding.QueryStringUrlCodingStrategy;
import org.apache.wicket.settings.IRequestCycleSettings;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.util.file.Folder;
import org.apache.wicket.util.time.Duration;
import wicket.contrib.tinymce.settings.TinyMCESettings;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 *
 * @see com.jmelzer.Start#main(String[])
 */
public class WicketApplication extends AuthenticatedWebApplication {

    boolean isInitialized = false;
    TinyMCESettings tinyMCESettings;

    /** Constructor */
    public WicketApplication() {

    }


    /** @see org.apache.wicket.Application#getHomePage() */
    public Class<HomePage> getHomePage() {
        return HomePage.class;
    }

    private void setListeners() {
        addComponentInstantiationListener(new SpringComponentInjector(this));
    }


    @Override
    protected void init() {
        if (isInitialized) {
            return;
        }
        super.init();
        setListeners();
        isInitialized = true;
        mount(new QueryStringUrlCodingStrategy("login", LoginPage.class));
        mount(new QueryStringUrlCodingStrategy("signup", SignupPage.class));
        mount(new QueryStringUrlCodingStrategy("registerfinish", SignupResultPage.class));
        mount(new QueryStringUrlCodingStrategy("sendpassword", SendPasswordPage.class));
        mount(new QueryStringUrlCodingStrategy("changepassword", ChangePasswordPage.class));
        mount(new QueryStringUrlCodingStrategy("createissue", CreateIssuePage.class));
        mount(new IndexedParamUrlCodingStrategy("issue", ShowIssuePage.class));
        mount(new QueryStringUrlCodingStrategy("choice", ChoicePage.class));
        mount(new QueryStringUrlCodingStrategy("secure/usersettings", UserSettings.class));

        String configurationType = getConfigurationType();
        if (DEVELOPMENT.equalsIgnoreCase(configurationType)) {
            System.out.println("You are in DEVELOPMENT mode");
            getResourceSettings().setResourcePollFrequency(Duration.ONE_SECOND);
        }
        getRequestCycleSettings().setRenderStrategy(IRequestCycleSettings.REDIRECT_TO_RENDER);

//        tinyMCESettings = new TinyMCESettings(TinyMCESettings.Theme.simple);
//        tinyMCESettings.setToolbarLocation(TinyMCESettings.Location.top);
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
        //todo inject
        return new Folder("c:\\tmp\\wreckcontrol");
    }

    public TinyMCESettings getTinyMCESettings() {
        if (tinyMCESettings == null) {
            tinyMCESettings = new TinyMCESettings(TinyMCESettings.Theme.simple);
            tinyMCESettings.setToolbarLocation(TinyMCESettings.Location.top);
        }
        return tinyMCESettings;
    }

}
