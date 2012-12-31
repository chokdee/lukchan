/*
 * Copyright (c) jmelzer 2011.
 * All rights reserved.
 */

package com.jmelzer.webapp.page;

import com.jmelzer.data.model.exceptions.ActivationCodeException;
import com.jmelzer.service.RegistrationService;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class ChangePasswordPage extends MainPage {

    private static final long serialVersionUID = -4416806836497210809L;

    @SpringBean(name = "registrationService")
    RegistrationService registrationService;

    TextField pwField;
    String key;

    /**
     * Constructor that is invoked when page is invoked without a session.
     *
     * @param parameters Page parameters
     */
    public ChangePasswordPage(final PageParameters parameters) {

        key = parameters.get("key").toString();
        if (key == null ) {
            error("dieser key ist nicht gueltig");
            return;
        }

        ChangePasswordForm form = new ChangePasswordForm("formid");
        add(form);

        pwField = new PasswordTextField("password", new Model(""));
        pwField.setRequired(true);
        form.add(new Label("passwordLabel", new StringResourceModel("password", new Model(""))));
        pwField.setLabel(new Model("password"));
        form.add(pwField);
        form.add(new ComponentFeedbackPanel("passwordFieldErr", pwField));

        TextField confirmPassField= new PasswordTextField("passwordConfirm", new Model(""));
        confirmPassField.setRequired(true);
        form.add(new Label("confirmPasswordLabel", new StringResourceModel("confirmPassword", new Model(""))));
        pwField.setLabel(new Model("confirmPassword"));
        form.add(confirmPassField);
        form.add(new ComponentFeedbackPanel("passwordConfirmFieldErr", confirmPassField));

    }

    class ChangePasswordForm extends Form {
        private static final long serialVersionUID = 6085867626655291167L;

        public ChangePasswordForm(String id) {
            super(id);

        }

        @Override
        public void onSubmit() {
            try {
                registrationService.changePassword(key, pwField.getDefaultModelObjectAsString());
            } catch (ActivationCodeException e) {
                if (e.getType() == ActivationCodeException.Type.notexist) {
                    error("Dieser Key ist nicht gueltig");
                }
                if (e.getType() == ActivationCodeException.Type.used) {
                    error("Dieser Key wurde bereits verwendet");
                }
                return;
            }
            info("Sie koennen sich jetzt mit dem neuen Passwort anmelden");
            setResponsePage(LoginPage.class);
        }

    }

}

