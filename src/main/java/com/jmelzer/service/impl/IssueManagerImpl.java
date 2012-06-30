/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 17.06.12 
*
*/


package com.jmelzer.service.impl;

import com.jmelzer.data.dao.IssueDao;
import com.jmelzer.data.dao.IssueTypeDao;
import com.jmelzer.data.dao.PriorityDao;
import com.jmelzer.data.dao.ProjectDao;
import com.jmelzer.data.model.Component;
import com.jmelzer.data.model.Issue;
import com.jmelzer.data.model.Project;
import com.jmelzer.service.IssueManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("issueManager")
public class IssueManagerImpl implements IssueManager {
    private static final long serialVersionUID = 747495665864600796L;

    @Resource
    IssueDao issueDao;

    @Resource
    ProjectDao  projectDao;
    @Resource
    IssueTypeDao issueTypeDao;
    @Resource
    PriorityDao priorityDao;

    @Override
    @Transactional
    public void create(Issue issue, Long projectId, Long issueTypeId, Long prioId, String componentName) {
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
        issueDao.save(issue);
        issue.getProject().setIssueCounter(id);
        projectDao.save(issue.getProject());
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
