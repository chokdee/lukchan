/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 02.06.12 
*
*/


package com.jmelzer.service.impl;

import com.jmelzer.data.dao.ActivationCodeDao;
import com.jmelzer.data.model.ActivationCode;
import com.jmelzer.data.model.User;
import com.jmelzer.data.model.exceptions.ActivationCodeException;
import com.jmelzer.data.model.exceptions.UserNotFoundException;
import com.jmelzer.data.util.DateUtilsJm;
import com.jmelzer.service.RegistrationService;
import com.jmelzer.service.UserService;
import org.apache.commons.lang.time.DateUtils;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component("registrationService")
public class RegistrationServiceImpl implements RegistrationService {

    @Resource
    private JavaMailSender mailSender;
    @Resource
    private VelocityEngine velocityEngine;

    @Resource
    ActivationCodeDao activationCodeDao;

    @Resource
    UserService userService;

    @Override
    @Transactional
    public User register(String email, String userName, String pw, String name) {

        // Do the registration logic...
        User user = userService.createUser(email, userName, pw, name);
        ActivationCode activationCode = createCode(user, ActivationCode.REGISTER);
        activationCodeDao.save(activationCode);
        sendConfirmationEmail(user, activationCode.getActivationcode());
        return user;
    }

    private ActivationCode createCode(User user, String type) {
        ActivationCode activationCode = new ActivationCode();
        activationCode.setUserId(user.getId());
        activationCode.setUsed(false);
        activationCode.setType(type);
        Date date = DateUtils.addDays(new Date(), 10);
        activationCode.setExpireDate(date);
        activationCode.setActivationcode(new Md5PasswordEncoder().encodePassword(UUID.randomUUID().toString(), null));
        return activationCode;
    }

    @Override
    @Transactional
    public void finish(String activationCodeAsString) throws ActivationCodeException {
        ActivationCode code = activationCodeDao.findOneByExample(new ActivationCode(activationCodeAsString, ActivationCode.REGISTER));
        if (code == null) {
            throw new ActivationCodeException(ActivationCodeException.Type.notexist);
        }
        if (code.getUsed() != null && code.getUsed()) {
            throw new ActivationCodeException(ActivationCodeException.Type.used);
        }
        code.setUsed(true);
        userService.unlockUser(code.getUserId());
        activationCodeDao.save(code);
    }

    @Override
    @Transactional
    public void sendPasswordLink(String email) throws UserNotFoundException {
        User user = userService.findUserByEmail(email);
        if (user == null) {
            throw new UserNotFoundException(email);
        }
        ActivationCode activationCode = createCode(user, ActivationCode.PW);
        activationCodeDao.save(activationCode);
        sendPasswordLinkEmail(user, activationCode.getActivationcode());
    }

    @Override
    @Transactional
    public void changePassword(String codeAsString, String newPassword) throws ActivationCodeException {
        ActivationCode code = activationCodeDao.findOneByExample(new ActivationCode(codeAsString, ActivationCode.PW));
        if (code == null) {
            throw new ActivationCodeException(ActivationCodeException.Type.notexist);
        }
        if (code.getUsed() != null && code.getUsed()) {
            throw new ActivationCodeException(ActivationCodeException.Type.used);
        }
        userService.changePassword(code.getUserId(), newPassword);
        code.setUsed(true);
        activationCodeDao.save(code);
    }

    private void sendConfirmationEmail(final User user, final String activationcode) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setTo(user.getEmail());
                message.setSubject("Confirm mail");
                message.setFrom("webmaster@wreckcontrol.net"); // could be parameterized...
                Map model = new HashMap();
                model.put("user", user);
                model.put("Registrierungslink", "http://wreckcontrol.net:8080/wreckcontrol/registerfinish?key=" + activationcode);
                String text = VelocityEngineUtils.mergeTemplateIntoString(
                        velocityEngine, "mailtemplates/de/userregistration.vm", model);
                message.setText(text, true);
            }
        };
        mailSender.send(preparator);
    }

    private void sendPasswordLinkEmail(final User user, final String activationcode) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setTo(user.getEmail());
                message.setSubject("Password forget");
                message.setFrom("webmaster@wreckcontrol.net"); // could be parameterized...
                Map model = new HashMap();
                model.put("user", user);
                model.put("subject", "Password vergessen");  //todo localize
                model.put("passwordlink", "http://wreckcontrol.net:8080/wreckcontrol/changepassword?key=" + activationcode);
                String text = VelocityEngineUtils.mergeTemplateIntoString(
                        velocityEngine, "mailtemplates/de/forget_password.vm", model);
                message.setText(text, true);
            }
        };
        mailSender.send(preparator);
    }
}
