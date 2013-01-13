/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 28.05.12 
*
*/


package com.jmelzer.webapp.security;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

import java.util.Date;

public class MyAuthenticatedWebSession extends AuthenticatedWebSession {
    private static final Logger logger = LoggerFactory.getLogger(MyAuthenticatedWebSession.class);
    private static final long serialVersionUID = -4412500193758872082L;

    @SpringBean(name = "authenticationManager")
    private AuthenticationManager authenticationManager;

    @SpringBean(name = "tokenRepository")
    private JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl;

    @SpringBean(name = "rememberMeService")
    MyPersistentTokenBasedRememberMeServices rememberMeServices;

    public MyAuthenticatedWebSession(Request request) {
        super(request);
        injectDependencies();
        ensureDependenciesNotNull();
    }

    private void ensureDependenciesNotNull() {
        if (authenticationManager == null) {
            throw new IllegalStateException("AdminSession requires an authenticationManager.");
        }
    }

    private void injectDependencies() {
        Injector.get().inject(this);
    }

    @Override
    public boolean authenticate(String username, String password) {
        boolean authenticated = false;
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            authenticated = authentication.isAuthenticated();
        } catch (AuthenticationException e) {
            logger.warn("User '{}' failed to login. Reason: {}", username, e.getMessage());
            authenticated = false;
        }
        return authenticated;
    }

    public PersistentRememberMeToken persistCookie(String userId) {
        PersistentRememberMeToken persistentRememberMeToken = new PersistentRememberMeToken(userId,
                                                                                            rememberMeServices.generateSeriesData(),
                                                                                            rememberMeServices.generateTokenData(), new Date());
        jdbcTokenRepositoryImpl.createNewToken(persistentRememberMeToken);
        return persistentRememberMeToken;
    }

    @Override
    public Roles getRoles() {
        Roles roles = new Roles();
        getRolesIfSignedIn(roles);
        return roles;
    }

    private void getRolesIfSignedIn(Roles roles) {
        //hack
        if (!isSignedIn()) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication!=null && !"anonymousUser".equals(authentication.getPrincipal())) {
                signIn(true);
            }
        }
        if (isSignedIn()) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            addRolesFromAuthentication(roles, authentication);
        }
    }

    private void addRolesFromAuthentication(Roles roles, Authentication authentication) {
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            roles.add(authority.getAuthority());
        }
    }
    public void loginWithRememberMe() {
        signIn(true);
    }
}
