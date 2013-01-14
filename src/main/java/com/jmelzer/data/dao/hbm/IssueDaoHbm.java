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
import com.jmelzer.data.model.Comment;
import com.jmelzer.data.model.Issue;
import com.jmelzer.data.model.User;
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
        Query query = getEntityManager().createQuery("FROM Issue i where i.assignee.id = ?");
        query.setParameter(1, user.getId());
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


}
