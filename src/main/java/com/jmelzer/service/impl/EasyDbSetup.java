package com.jmelzer.service.impl;

import com.jmelzer.data.dao.*;
import com.jmelzer.data.model.*;
import com.jmelzer.data.model.ui.UiField;
import com.jmelzer.data.model.ui.View;
import com.jmelzer.data.model.ui.ViewTab;
import com.jmelzer.data.uimodel.Field;
import com.jmelzer.data.util.StreamUtils;
import com.jmelzer.service.IssueManager;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;

/**
 *
 */
public class EasyDbSetup {

    protected static Logger logger = LoggerFactory.getLogger(EasyDbSetup.class);

    Md5PasswordEncoder encoder = new Md5PasswordEncoder();
    @Resource
    UserDao userDao;
    @Resource
    UserRoleDao userRoleDao;
    @Resource
    ProjectDao projectDao;
    @Resource
    StatusDao statusDao;
    @Resource
    PriorityDao priorityDao;
    @Resource
    IssueTypeDao issueTypeDao;
    @Resource
    IssueManager issueManager;
    @Resource
    ViewDao viewDao;
    @Resource
    ActivationCodeDao activationCodeDao;
    @Resource
    JpaTransactionManager transactionManager;

    boolean load;

    public void setLoad(boolean load) {
        this.load = load;
    }

    @PostConstruct
    public void init() {
        if (!load) {
            return;
        }
        TransactionTemplate template = new TransactionTemplate(transactionManager);
        template.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus transactionStatus) {
                setup();
                User user = userDao.findByUserNameNonLocked("admin");
                Assert.isTrue(user != null);
                return null;
            }
        });

        //validate the transaction
        template.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus transactionStatus) {
                User user = userDao.findByUserNameNonLocked("admin");
                Assert.isTrue(user != null);
                return null;
            }
        });

    }

    public void setup() {

        try {


            UserRole userRoleDev = new UserRole(UserRole.Roles.ROLE_DEVELOPER.toString());
            userRoleDao.save(userRoleDev);
            UserRole userRoleAdmin = new UserRole(UserRole.Roles.ROLE_ADMIN.toString());
            userRoleDao.save(userRoleAdmin);
            UserRole userRoleUser = new UserRole(UserRole.Roles.ROLE_USER.toString());
            userRoleDao.save(userRoleUser);


            User dev;
            User jm;
            User admin;
            {
                admin = userDao.findByUserNameNonLocked("admin");
                if (admin == null) {
                    admin = new User();
                }

                admin.setName("admin");
                admin.setPassword(encoder.encodePassword("42", "admin"));
                admin.setEmail("admin@wreckcontrol.net");
                admin.setLoginName("admin");
                admin.setLocked(false);
                admin.setAvatar(StreamUtils.toByteArray(getClass().getResourceAsStream("admin.png")));
                admin.addRole(userRoleAdmin);
                userDao.save(admin);
                admin = userDao.findByUserNameNonLocked("admin");
                Assert.isTrue(admin != null);
            }
            {
                jm = userDao.findByUserNameNonLocked("jm");
                if (jm == null) {
                    jm = new User();
                }
                jm.setName("jm");
                jm.setPassword(encoder.encodePassword("42", "jm"));
                jm.setEmail("jm@wreckcontrol.net");
                jm.setLoginName("jm");
                jm.setLocked(false);
                jm.setAvatar(StreamUtils.toByteArray(getClass().getResourceAsStream("user.png")));
                jm.addRole(userRoleUser);
                userDao.save(jm);
            }
            {
                dev = userDao.findByUserNameNonLocked("developer");
                if (dev == null) {
                    dev = new User();
                }
                dev.setName("developer");
                dev.setPassword(encoder.encodePassword("42", "developer"));
                dev.setEmail("developer@wreckcontrol.net");
                dev.setLoginName("developer");
                dev.setLocked(false);
                dev.setAvatar(StreamUtils.toByteArray(getClass().getResourceAsStream("user.png")));
                dev.addRole(userRoleDev);
                userDao.save(dev);
            }
            WorkflowStatus workflowStatusOpen;
            WorkflowStatus workflowStatusInProgress;
            {
                statusDao.save(workflowStatusOpen = new WorkflowStatus("Open", 1));
                statusDao.save(workflowStatusInProgress = new WorkflowStatus("In Progress", 2));
                statusDao.save(new WorkflowStatus("Resolved", 3));
                statusDao.save(new WorkflowStatus("Reopened", 4));
                statusDao.save(new WorkflowStatus("Closed", 5));
            }
            Project projectTst;
            {
                //dummy project for testing
                projectTst = new Project();
                projectTst.setName("Test");
                projectTst.setShortName("TST");
                Project projectDb = projectDao.findOneByExample(projectTst);
                if (projectDb != null) {
                    projectTst = projectDb;
                }
                projectTst.setLead(userDao.findByUserName("jm"));
                projectDao.save(projectTst);

                {
                    Component component = new Component("service");
                    projectTst.addComponent(component);
                }
                {
                    Component component = new Component("frontend");
                    projectTst.addComponent(component);
                }
                {
                    ProjectVersion projectVersion = new ProjectVersion();
                    projectVersion.setVersionNumber("0.9");
                    projectTst.addVersion(projectVersion);
                }
                {
                    ProjectVersion projectVersion = new ProjectVersion();
                    projectVersion.setVersionNumber("1.0");
                    projectTst.addVersion(projectVersion);
                }
                projectDao.save(projectTst);
            }
            Project projectDummy;
            {
                //dummy project for testing
                projectDummy = new Project();
                projectDummy.setName("Dummy");
                projectDummy.setShortName("DUM");
                Project projectDb = projectDao.findOneByExample(projectDummy);
                if (projectDb != null) {
                    projectDummy = projectDb;
                }
                projectDummy.setLead(userDao.findByUserName("developer"));
                projectDao.save(projectDummy);

                {
                    Component component = new Component("service");
                    projectDummy.addComponent(component);
                }
                {
                    Component component = new Component("frontend");
                    projectDummy.addComponent(component);
                }
                {
                    ProjectVersion projectVersion = new ProjectVersion();
                    projectVersion.setVersionNumber("0.9");
                    projectDummy.addVersion(projectVersion);
                }
                {
                    ProjectVersion projectVersion = new ProjectVersion();
                    projectVersion.setVersionNumber("1.0");
                    projectDummy.addVersion(projectVersion);
                }
                projectDao.save(projectDummy);
            }
            IssueType issueTypeBug;
            IssueType issueTypeFeature;
            {
                issueTypeDao.save(issueTypeBug = new IssueType("Bug", "images/bug.gif"));
                issueTypeDao.save(issueTypeFeature = new IssueType("Feature", "images/newfeature.gif"));
                issueTypeDao.save(new IssueType("Task", "images/task.gif"));
            }
            Priority prio = createPriorities(priorityDao);
            createViews(viewDao);
            for (int i = 0; i < 10; i++) {
                logger.info("create issue # " + i);
                if (i % 2 == 0) {
                    createSampleIssue(issueManager, projectTst.getId(),
                                      workflowStatusOpen,
                                      issueTypeBug.getId(), prio.getId(), "service",
                                      dev, jm.getUsername());
                } else {
                    createSampleIssue(issueManager, projectTst.getId(),
                                      workflowStatusInProgress,
                                      issueTypeFeature.getId(), prio.getId(), "service",
                                      dev, admin.getUsername());
                }
            }
            for (int i = 0; i < 10; i++) {
                logger.info("create issue # " + i);
                if (i % 2 == 0) {
                    createSampleIssue(issueManager, projectDummy.getId(),
                                      workflowStatusOpen,
                                      issueTypeBug.getId(), prio.getId(), "service",
                                      dev, jm.getUsername());
                } else {
                    createSampleIssue(issueManager, projectDummy.getId(),
                                      workflowStatusInProgress,
                                      issueTypeFeature.getId(), prio.getId(), "service",
                                      dev, admin.getUsername());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createSampleIssue(IssueManager issueManager,
                                   Long projectId,
                                   WorkflowStatus workflowStatus,
                                   Long issueTypeId,
                                   Long prioId,
                                   String componentName,
                                   User assignee,
                                   String reporter) throws InterruptedException {
        Issue issue = new Issue();
        issue.setAssignee(assignee);
        issue.setSummary("this is an example test issue.");
        issue.setDescription("Um <bold>Suchwort</bold>-Analysen erstellen zu k\u00F6nnen ben\u00F6tigen wir die M\u00F6glichkeit, einen Blick in den Index (in die f\u00FCr eine Kampagne erstellten Suchw\u00F6rter) zu werfen." +
                             "<br>" +
                             "Dabei haben wir im wesentlich zwei Anforderungen:.");
        issue.setWorkflowStatus(workflowStatus);
        issueManager.create(issue,
                            projectId,
                            issueTypeId,
                            prioId,
                            componentName,
                            reporter);

        issueManager.addComment(issue.getPublicId(), "Das ist doch alles nix hier", "developer");
        Thread.sleep(100L);
        issueManager.addComment(issue.getPublicId(), "Doch doch dat funktioniert doch alles", "admin");
        Thread.sleep(100L);
        issueManager.addComment(issue.getPublicId(), "Doch doch dat funktioniert doch alles", "admin");
        Thread.sleep(100L);
        issueManager.addComment(issue.getPublicId(), " <H2>Demonstrating a few HTML features</H2>\\n" +
                                                     "\n" +
                                                     "</CENTER>\n" +
                                                     "\n" +
                                                     "HTML is really a very simple language. It consists of ordinary text, with commands that are enclosed by \"<\" and \">\" characters, or bewteen an \"&\" and a \";\". <P>\n" +
                                                     " ", "developer");
    }

    private Priority createPriorities(PriorityDao priorityDao) {
        priorityDao.save(new Priority("Blocker", 1));
        priorityDao.save(new Priority("Critical", 2));
        Priority prio = new Priority("Major", 3);
        priorityDao.save(prio);
        priorityDao.save(new Priority("Minor", 4));
        priorityDao.save(new Priority("Trivial", 5));
        return prio;
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
