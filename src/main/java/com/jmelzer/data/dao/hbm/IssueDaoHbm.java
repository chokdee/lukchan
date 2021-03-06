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
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
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
    public String buildQueryString(Long project, Long workflowStatus, Long issueType, Long userId) {
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
        if (userId!= null) {
            queryString += " and ";
            queryString += " assignee.id = " + userId;
        }
        return queryString;
    }

    @Override
    public List<Issue> fullTextQuery(String text) {
        FullTextEntityManager fullTextEntityManager  = Search.getFullTextEntityManager(entityManager);

// create native Lucene query unsing the query DSL
// alternatively you can write the Lucene query using the Lucene query parser
// or the Lucene programmatic API. The Hibernate Search DSL is recommended though
        QueryBuilder qb = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder().forEntity( Issue.class ).get();
        org.apache.lucene.search.Query query = qb
                .keyword()
                .onFields("summary", "comments.text")
                .matching(text)
                .createQuery();


// wrap Lucene query in a javax.persistence.Query
        FullTextQuery persistenceQuery =
                fullTextEntityManager.createFullTextQuery(query, Issue.class);

        org.apache.lucene.search.Sort sort = new Sort(new SortField("id", SortField.LONG));
        persistenceQuery.setSort(sort);

// execute search
        List result = persistenceQuery.getResultList();


        return result;
    }

}
