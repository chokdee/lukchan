/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 17.06.12 
*
*/


package com.jmelzer.service;

import com.jmelzer.data.model.Issue;
import com.jmelzer.data.model.IssueType;
import com.jmelzer.data.model.Project;
import com.jmelzer.data.model.WorkflowStatus;

import java.util.List;

import java.io.File;

public interface IssueManager {
    void create(Issue issue, Long projectId, Long issueTypeId, Long prioId, String componentName, String reporter);

    Issue getIssueByShortName(String issueName);

    void addComment(String issueName, String comment, String username);

    void modifyComment(Long commentId, Long issueId, String string, String username);

    void deleteComment(Long commentId, String username);

    List<Issue> getAssignedIssues(String username);

    void addAttachment(Issue issue, File[] file, String username);

    void deleteAttachment(Long attachmentId, String username);

    List<Issue> findIssues(Long project, Long workflowStatus, Long issueType, Long userId);
    String buildQuery(Long project, Long workflowStatus, Long issueType, Long userId);
}
