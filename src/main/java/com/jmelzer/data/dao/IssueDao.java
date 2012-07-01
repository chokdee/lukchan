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

public interface IssueDao extends AbstractDao<Issue> {

    Issue findIssueByShortName(String shortName);
}
