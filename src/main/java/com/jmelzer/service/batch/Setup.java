/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 30.05.12 
*
*/


package com.jmelzer.service.batch;

import com.jmelzer.data.dao.*;
import com.jmelzer.data.model.*;
import com.jmelzer.data.model.ui.UiField;
import com.jmelzer.data.model.ui.View;
import com.jmelzer.data.model.ui.ViewTab;
import com.jmelzer.data.uimodel.Field;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

public class Setup extends AbstractBatch {

    public static void main(String[] args) {

        Setup batch = new Setup();
        batch.run();
    }

    @Override
    void doIt() throws Exception {
        Md5PasswordEncoder encoder = new Md5PasswordEncoder();
//        if (true) {
//            System.out.println(encoder.encodePassword("11", "jm"));
//            return;
//        }
        UserDao userDao = (UserDao) context.getBean("userDao");
        ProjectDao projectDao = (ProjectDao) context.getBean("projectDao");
        PriorityDao priorityDao = (PriorityDao) context.getBean("priorityDao");
        IssueTypeDao issueTypeDao = (IssueTypeDao) context.getBean("issueTypeDao");
        ViewDao viewDao = (ViewDao) context.getBean("viewDao");
        ActivationCodeDao activationCodeDao = (ActivationCodeDao) context.getBean("activationCodeDao");
        {
            User user = userDao.findByUserNameNonLocked("admin");
            if (user == null) {
                user = new User();
            }

            user.setName("admin");
            user.setPassword(encoder.encodePassword("42", "admin"));
            user.setEmail("admin@wreckcontrol.net");
            user.setLoginName("admin");
            user.setLocked(false);

            userDao.save(user);
        }
        {
            User user = userDao.findByUserNameNonLocked("jm");
            if (user == null) {
                user = new User();
            }
            user.setName("jm");
            user.setPassword(encoder.encodePassword("42", "jm"));
            user.setEmail("jm@wreckcontrol.net");
            user.setLoginName("jm");
            user.setLocked(false);
            userDao.save(user);
        }
        {
            User user = userDao.findByUserNameNonLocked("developer");
            if (user == null) {
                user = new User();
            }
            user.setName("developer");
            user.setPassword(encoder.encodePassword("42", "developer"));
            user.setEmail("developer@wreckcontrol.net");
            user.setLoginName("developer");
            user.setLocked(false);
            userDao.save(user);
        }

        {
            //dummy project for testing
            Project project = new Project();
            project.setName("Test");
            project.setShortName("TST");
            Project projectDb = projectDao.findOneByExample(project);
            if (projectDb != null) {
                project = projectDb;
            }
            project.setLead(userDao.findByUserName("jm"));
            projectDao.save(project);

            {
                Component component = new Component("service");
                project.addComponent(component);
            }
            {
                Component component = new Component("frontend");
                project.addComponent(component);
            }
            {
                ProjectVersion projectVersion = new ProjectVersion();
                projectVersion.setVersionNumber("0.9");
                project.addVersion(projectVersion);
            }
            {
                ProjectVersion projectVersion = new ProjectVersion();
                projectVersion.setVersionNumber("1.0");
                project.addVersion(projectVersion);
            }
            projectDao.save(project);
        }
        {
            issueTypeDao.save(new IssueType("Bug", "images/bug.gif"));
            issueTypeDao.save(new IssueType("Feature", "images/newfeature.gif"));
            issueTypeDao.save(new IssueType("Task", "images/task.gif"));
        }
        createPriorities(priorityDao);
        createViews(viewDao);
    }

    private void createPriorities(PriorityDao priorityDao) {
        priorityDao.save(new Priority("Blocker", 1));
        priorityDao.save(new Priority("Critical", 2));
        priorityDao.save(new Priority("Major", 3));
        priorityDao.save(new Priority("Minor", 4));
        priorityDao.save(new Priority("Trivial", 5));
    }

    private void createViews(ViewDao viewDao) {
        View view = new View();
        view.setName("createissue");
        view.setDecription("Standard view for creating issues");
        ViewTab viewTab = new ViewTab();
        viewTab.setName("Tab1");
        view.addTab(viewTab);
        {
            UiField field = new UiField();
            field.setFieldId(Field.ISSUETYPE_ID);
            field.setPosition(2);
            viewTab.addField(field);
        }
        {
            UiField field = new UiField();
            field.setFieldId(Field.PROJECT_ID);
            field.setPosition(1);
            viewTab.addField(field);
        }
        {
            UiField field = new UiField();
            field.setFieldId(Field.SUMMARY_ID);
            field.setPosition(3);
            viewTab.addField(field);
        }
        {
            UiField field = new UiField();
            field.setFieldId(Field.PRIORITY_ID);
            field.setPosition(4);
            viewTab.addField(field);
        }
        {
            UiField field = new UiField();
            field.setFieldId(Field.DUEDATE_ID);
            field.setPosition(5);
            viewTab.addField(field);
        }
        {
            UiField field = new UiField();
            field.setFieldId(Field.COMPONENT_ID);
            field.setPosition(6);
            viewTab.addField(field);
        }
        {
            UiField field = new UiField();
            field.setFieldId(Field.FIXVERSION_ID);
            field.setPosition(7);
            viewTab.addField(field);
        }
        {
            UiField field = new UiField();
            field.setFieldId(Field.AFFECTED_VERSION_ID);
            field.setPosition(8);
            viewTab.addField(field);
        }
        {
            UiField field = new UiField();
            field.setFieldId(Field.ASSIGNEE_ID);
            field.setPosition(9);
            viewTab.addField(field);
        }
        {
            UiField field = new UiField();
            field.setFieldId(Field.DESCRIPTION_ID);
            field.setPosition(10);
            viewTab.addField(field);
        }
        {
            UiField field = new UiField();
            field.setFieldId(Field.REMAININGESTIMATE_ID);
            field.setPosition(11);
            viewTab.addField(field);
        }
        {
            UiField field = new UiField();
            field.setFieldId(Field.ORGESTIMATE_ID);
            field.setPosition(12);
            viewTab.addField(field);
        }
        viewDao.save(view);
    }
}
