/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 28.05.12 
*
*/


package com.jmelzer.webapp.page;

import com.jmelzer.webapp.security.MyAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

import javax.servlet.http.Cookie;

public class LoginPage extends MainPage {
    private static final long serialVersionUID = 7767013194879565534L;

    /**
     * Login page constituents are the same as Login.html except that
     * it is made up of equivalent Wicket components
     */

    private TextField userIdField;
    private PasswordTextField passField;
    private LoginForm form;
    CheckBox checkBox;

    /**
     * Constructor that is invoked when page is invoked without a session.
     *
     * @param parameters Page parameters
     */
    public LoginPage(final PageParameters parameters) {

        /**
         * The first parameter to all Wicket component constructors is
         * the same as the ID that is used in the template
         */

        userIdField = new RequiredTextField("userId", new Model(""));
        userIdField.setLabel(new Model("Username"));
        passField = new PasswordTextField("password", new Model(""));
        passField.setRequired(true);

        form = new LoginForm("loginForm");

        checkBox = new CheckBox("rememberMe", Model.of(form.rememberMe));
        form.add(checkBox);

        /* Make sure that password field shows up during page re-render **/

        passField.setResetPassword(false);


        form.add(userIdField);
        form.add(passField);
        add(form);
    }

    // Define your LoginForm and override onSubmit
    class LoginForm extends Form {
        private static final long serialVersionUID = 8238794639474122221L;
        Boolean rememberMe = false;
        public LoginForm(String id) {
            super(id);

        }

        @Override
        public void onSubmit() {
            String userId = LoginPage.this.getUserId();
            String password = LoginPage.this.getPassword();
            MyAuthenticatedWebSession session = (MyAuthenticatedWebSession) AuthenticatedWebSession.get();
            if(session.signIn(userId, password)) {
                //SPRING_SECURITY_REMEMBER_ME_COOKIE
                if (getRememberMe()) {
                    PersistentRememberMeToken token = session.persistCookie(userId);
                    setCookie(new String[] {token.getSeries(), token.getTokenValue()}, 100000);

                }
                setDefaultResponsePageIfNecessary();
            } else {
                error(getString("login.failed"));
                setResponsePage(LoginPage.class);
            }
        }

        private void setCookie(String[] tokens, int maxAge) {
            String cookieValue = encodeCookie(tokens);
            Cookie cookie = new Cookie("SPRING_SECURITY_REMEMBER_ME_COOKIE", cookieValue);
            cookie.setMaxAge(maxAge);
            ((WebResponse)getRequestCycle().getResponse()).addCookie(cookie);
        }
        protected String encodeCookie(String[] cookieTokens) {
            StringBuilder sb = new StringBuilder();
            for(int i=0; i < cookieTokens.length; i++) {
                sb.append(cookieTokens[i]);

                if (i < cookieTokens.length - 1) {
                    sb.append(":");
                }
            }

            String value = sb.toString();

            sb = new StringBuilder(new String(Base64.encode(value.getBytes())));

            while (sb.charAt(sb.length() - 1) == '=') {
                sb.deleteCharAt(sb.length() - 1);
            }

            return sb.toString();
        }
        private void setDefaultResponsePageIfNecessary() {
            continueToOriginalDestination();
            setResponsePage(getApplication().getHomePage());

        }
        protected boolean getRememberMe() {
            Boolean b = (Boolean) checkBox.getDefaultModelObject();
            return b;
        }
    }

    /** Helper methods to retrieve the userId and the password * */

    protected String getUserId() {
        return userIdField.getDefaultModelObjectAsString();
    }

    protected String getPassword() {
        return passField.getDefaultModelObjectAsString();
    }


}

