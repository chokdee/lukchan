/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 27.05.12 
*
*/


package com.jmelzer.data.dao.hbm;

import com.jmelzer.data.dao.IssueDao;
import com.jmelzer.data.model.*;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class IssueDaoHbm extends AbstractDaoHbm<Issue> implements IssueDao {

    @Override
    public Issue findIssueByShortName(String shortName) {
        return querySingleResult("from Issue where publicId = ?", shortName);
    }

    @Override
    public void saveComment(Long commentId, String string, User user) {
        Comment comment = getEntityManager().find(Comment.class, commentId);
        comment.setText(string);
        comment.setOwner(user);
        getEntityManager().persist(comment);
    }

    @Override
    public Issue deleteComment(Long commentId) {
        Issue issue = querySingleResult("select i from Issue i join i.comments c where c.id = ?",
                                            commentId);
        issue.removeComment(commentId);
        save(issue);
        return issue;
    }

    @Override
    public List<Issue> getAssignedIssues(User user) {
        Query query = getEntityManager().createQuery("select i from Issue i where i.assignee.id = :id");
        query.setParameter("id", user.getId());
        return query.getResultList();
    }

    @Override
    public Issue deleteAttachment(Long attachmentId) {
        Issue issue = querySingleResult("select i from Issue i join i.attachments a where a.id = ?",
                                        attachmentId);
        issue.removeAttachment(attachmentId);
        save(issue);
        return issue;
    }

    @Override
    public List<Issue> customQuery(String queryString) {
        String q = "select i from Issue i where 1 = 1 ";
        Query query = getEntityManager().createQuery(q + queryString);
        return query.getResultList();

    }

    @Override
    public String buildQueryString(Long project, Long workflowStatus, Long issueType) {
        String queryString = "";
        if (project != null) {
            queryString += " and ";
            queryString += " project.id = " + project;
        }
        if (issueType != null) {
            queryString += " and ";
            queryString += " type.id = " + issueType;
        }
        if (workflowStatus!= null) {
            queryString += " and ";
            queryString += " workflowStatus.id = " + workflowStatus;
        }
        return queryString;
    }
//    public List<Issue> findIssues(Long project, Long workflowStatus, Long issueType) {
//
//        Query query = getEntityManager().createQuery("select i from Issue i where 1 = 1 " + queryString);
//        return query.getResultList();
//
//    }


}
