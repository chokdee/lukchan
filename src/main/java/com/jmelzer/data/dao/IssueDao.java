/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 27.05.12 
*
*/


package com.jmelzer.data.dao;

import com.jmelzer.data.model.Issue;
import com.jmelzer.data.model.User;

public interface IssueDao extends AbstractDao<Issue> {

    Issue findIssueByShortName(String shortName);

    void saveComment(Long commentId, String string, User user);


    Issue deleteComment(Long commentId);
}
