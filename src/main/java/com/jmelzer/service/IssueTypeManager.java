/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 04.01.13 
*
*/


package com.jmelzer.service;

import com.jmelzer.data.model.IssueType;

import java.util.List;

public interface IssueTypeManager {
    void save(IssueType issueType);
    List<IssueType> getAll();
}
