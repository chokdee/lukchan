/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 27.05.12 
*
*/


package com.jmelzer.data.dao;

import com.jmelzer.data.model.*;

import java.util.List;

public interface IssueDao extends AbstractDao<Issue> {

    Issue findIssueByShortName(String shortName);

    void saveComment(Long commentId, String string, User user);


    Issue deleteComment(Long commentId);

    List<Issue> getAssignedIssues(User user);

    Issue deleteAttachment(Long attachmentId);

    List<Issue> customQuery(String query);

    String buildQueryString(Long project, Long workflowStatus, Long issueType);
}
