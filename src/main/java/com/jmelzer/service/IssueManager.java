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

public interface IssueManager {
    void create(Issue issue, Long projectId, Long issueTypeId, Long prioId, String componentName);

    Issue getIssueByShortName(String issueName);
}
