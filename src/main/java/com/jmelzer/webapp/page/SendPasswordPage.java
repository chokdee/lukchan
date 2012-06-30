/*
 * Copyright (c) jmelzer 2011.
 * All rights reserved.
 */

package com.jmelzer.webapp.page;

import com.jmelzer.data.model.exceptions.ActivationCodeException;
import com.jmelzer.data.model.exceptions.UserNotFoundException;
import com.jmelzer.service.RegistrationService;
import org.apache.wicket.PageParameters;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.context.SecurityContextHolder;

public class SendPasswordPage extends MainPage {

    private static final long serialVersionUID = -4416806836497210809L;

    @SpringBean(name = "registrationService")
    RegistrationService registrationService;

    TextField emailField;

    /**
     * Constructor that is invoked when page is invoked without a session.
     *
     * @param parameters Page parameters
     */
    public SendPasswordPage(final PageParameters parameters) {

        ForgetPasswordForm form = new ForgetPasswordForm("formid");
        add(form);

        emailField = new RequiredTextField("email", new Model(""));
        form.add(new Label("emailLabel", new StringResourceModel("email", new Model(""))));
        emailField.add(EmailAddressValidator.getInstance());
        emailField.setLabel(new Model("E-Mail"));
        form.add(emailField);
        form.add(new ComponentFeedbackPanel("emailFieldErr", emailField));

    }

    class ForgetPasswordForm extends Form {

        private static final long serialVersionUID = -4592218575449159145L;

        public ForgetPasswordForm(String id) {
            super(id);

        }

        @Override
        public void onSubmit() {

            try {
                registrationService.sendPasswordLink(emailField.getDefaultModelObjectAsString());
            } catch (UserNotFoundException e) {
                error("Es gibt keinen User mit diesem Namen.");
                return;
            }
            info("Sie erhalten in kuerze eine Email mit einem Registrierungslink");
            setResponsePage(LoginPage.class);
        }

    }

}

