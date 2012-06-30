/*
 * Copyright (c) jmelzer 2011.
 * All rights reserved.
 */

package com.jmelzer.webapp.page;

import com.jmelzer.data.model.exceptions.ActivationCodeException;
import com.jmelzer.service.RegistrationService;
import org.apache.wicket.PageParameters;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.security.core.context.SecurityContextHolder;

/** Homepage */
public class SignupResultPage extends MainPage {

    private static final long serialVersionUID = -4416806836497210809L;

    @SpringBean(name = "registrationService")
    RegistrationService registrationService;

    /**
     * Constructor that is invoked when page is invoked without a session.
     *
     * @param parameters Page parameters
     */
    public SignupResultPage(final PageParameters parameters) {

        String key = parameters.getString("key");
        if (key == null ) {
            add(new Label("welcome", "Dieser Key ist nicht gueltig"));
            return;
        }

        try {
            registrationService.finish(key);
        } catch (ActivationCodeException e) {
            if (e.getType() == ActivationCodeException.Type.notexist) {
                add(new Label("welcome", "Dieser Key ist nicht gueltig"));
                return;
            }
            if (e.getType() == ActivationCodeException.Type.used) {
                add(new Label("welcome", "Dieser Key wurde bereits verwendet"));
                return;
            }
        }
        add(new Label("welcome", "Willkommen in Wreckcontrol, Sie haben sich erfolgreich registriert und koennen sich nun einloggen."));
    }

}

