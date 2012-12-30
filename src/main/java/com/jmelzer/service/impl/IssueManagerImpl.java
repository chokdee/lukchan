/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 17.06.12 
*
*/


package com.jmelzer.service.impl;

import com.jmelzer.data.dao.*;
import com.jmelzer.data.model.*;
import com.jmelzer.service.ActivityLogManager;
import com.jmelzer.service.IssueManager;
import com.jmelzer.service.WorkflowManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("issueManager")
public class IssueManagerImpl implements IssueManager {
    private static final long serialVersionUID = 747495665864600796L;

    @Resource
    IssueDao issueDao;
    @Resource
    UserDao userDao;

    @Resource
    WorkflowManager workflowManager;

    @Resource
    ProjectDao  projectDao;
    @Resource
    IssueTypeDao issueTypeDao;
    @Resource
    PriorityDao priorityDao;
    @Resource
    ActivityLogManager activityLogManager;

    @Override
    @Transactional
    public void create(Issue issue, Long projectId, Long issueTypeId, Long prioId, String componentName, String reporter) {
        //todo do validation

        //reload entities
        issue.setProject(projectDao.findOne(projectId));
        issue.setType(issueTypeDao.findOne(issueTypeId));
        issue.setPriority(priorityDao.findOne(prioId));

        Long id = generateNextPublicId(issue);
        issue.setPublicId(issue.getProject().getShortName() + "-" + id);

        Component comp = findComponent(issue.getProject(), componentName);
        if (comp != null) {
            issue.addComponent(comp);
        }
        User user = userDao.findByUserName(reporter);
        issue.setReporter(user);
        issue.setStatus(workflowManager.getFirstStatus());
        issueDao.save(issue);
        issue.getProject().setIssueCounter(id);
        projectDao.save(issue.getProject());

        activityLogManager.addActivity(issue.getReporter().getUsername(),
                                       issue, ActivityLog.Action.CREATE_ISSUE);
    }

    @Override
    public Issue getIssueByShortName(String issueName) {
        return issueDao.findIssueByShortName(issueName);
    }

    @Override
    @Transactional
    public void addComment(String issueName, String comment, String username) {
        Issue issue = getIssueByShortName(issueName);
        if (issue == null) {
            throw new IllegalArgumentException(issueName + " doesn't exists");
        }
        User user = userDao.findByUserName(username);
        if (user == null) {
            throw new IllegalArgumentException(username + " doesn't exists");
        }
        issue.addComment(new Comment(comment, user));

        activityLogManager.addActivity(username, issue, ActivityLog.Action.COMMENT_ISSUE);
    }

    private Component findComponent(Project project, String componentName) {
        for (Component component : project.getComponents()) {
            if (component.getName().equals(componentName)) {
                return component;
            }
        }
        return null;
    }

    Long generateNextPublicId(Issue issue) {
        if (issue == null || issue.getProject() == null) {
            throw new IllegalArgumentException("issue or project is null");
        }
        Long counter = issue.getProject().getIssueCounter();
        if (counter == null) {
            counter = 1L;
        } else {
            counter++;
        }
        return counter;
    }
}
