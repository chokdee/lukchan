/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 17.06.12 
*
*/


package com.jmelzer.service.impl;

import com.jmelzer.data.model.Issue;
import com.jmelzer.data.model.Project;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;

public class IssueManagerImplTest {

    @Test
    public void testPublicId() {

        IssueManagerImpl issueManager = new IssueManagerImpl();
        Issue issue = new Issue();
        try {
            issueManager.generateNextPublicId(issue);
            fail("project is null");
        } catch (IllegalArgumentException e) {
            //ok
        }
        Project project = new Project();
        project.setShortName("TST");
        issue.setProject(project);
        long id = issueManager.generateNextPublicId(issue);
        assertNotNull(id);
        assertEquals(1L, id);

        project.setIssueCounter(99L);
        id = issueManager.generateNextPublicId(issue);
        assertEquals(100L, id);
    }
}
