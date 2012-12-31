/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 28.05.12 
*
*/


package com.jmelzer.webapp.page;

import com.jmelzer.data.model.User;
import com.jmelzer.service.RegistrationService;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.springframework.dao.DuplicateKeyException;

public class SignupPage extends MainPage {
    private static final long serialVersionUID = 7767013194879565534L;

    @SpringBean(name = "registrationService")
    RegistrationService registrationService;

    /**
     * Login page constituents are the same as Login.html except that
     * it is made up of equivalent Wicket components
     */
    User model = new User();
    private SignupForm form;


    /**
     * Constructor that is invoked when page is invoked without a session.
     *
     * @param parameters Page parameters
     */
    public SignupPage(final PageParameters parameters) {

        /**
         * The first parameter to all Wicket component constructors is
         * the same as the ID that is used in the template
         */
        form = new SignupForm("signupForm");
        add(form);

        TextField userIdField;
        TextField emailField;
        PasswordTextField passField;
        PasswordTextField confirmPassField;

        userIdField = new RequiredTextField("userId", new PropertyModel(model,  "loginName"));
        form.add(new Label("usernameLabel", new StringResourceModel("userId", new Model(""))));
        passField = new PasswordTextField("password", new PropertyModel(model,  "password"));
        form.add(new Label("passwordLabel", new StringResourceModel("password", new Model(""))));
        //todo localize all
        passField.setRequired(true);
        confirmPassField= new PasswordTextField("passwordConfirm", new Model(""));
        confirmPassField.setRequired(true);
        RequiredTextField fullNameField = new RequiredTextField("fullName", new PropertyModel(model,  "name"));
        fullNameField.setLabel(new Model("Full Name"));
        emailField = new RequiredTextField("email", new PropertyModel(model,  "email"));
        emailField.add(EmailAddressValidator.getInstance());
        emailField.setLabel(new Model("E-Mail"));




        /* Make sure that password field shows up during page re-render **/

        passField.setResetPassword(false);
        confirmPassField.setResetPassword(false);
        form.add(new EqualPasswordInputValidator(passField, confirmPassField));

        form.add(userIdField);
        form.add(new ComponentFeedbackPanel("userIdFieldErr", userIdField));
        form.add(new ComponentFeedbackPanel("passwordFieldErr", passField));
        form.add(new ComponentFeedbackPanel("passwordConfirmFieldErr", confirmPassField));
        form.add(new ComponentFeedbackPanel("fullNameFieldErr", fullNameField));
        form.add(new ComponentFeedbackPanel("emailFieldErr", emailField));
        form.add(passField);
        form.add(confirmPassField);
        form.add(fullNameField);
        form.add(emailField);

    }

    class SignupForm extends Form {
        private static final long serialVersionUID = 8238794639474122221L;
        public SignupForm(String id) {
            super(id);

        }

        @Override
        public void onSubmit() {

            try {
                registrationService.register(model.getEmail(), model.getLoginName(), model.getPassword(), model.getName());
            } catch (DuplicateKeyException e) {
                error("Es gibt bereits einen User mit diesem Namen.");
            }
            info("Sie erhalten in kuerze eine Email mit einem Registrierungslink");
            setResponsePage(LoginPage.class);
        }

    }


}

