/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 29.12.12 
*
*/


package com.jmelzer.data.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * class for presenting the activity feed.
 * every action will be added here that we need to show in the activity feed.
 * todo-performance: can be very slow in high performce env.
 */
@Entity
@Table(name = "activity_log")
public class ActivityLog extends ModelBase implements Serializable {

    private static final long serialVersionUID = 6339572579648114015L;

    User author;
    Date created;
    User updateAuthor;
    Date updated;
    String activityAsString;
    String detail;
    Issue issue;

    public enum Action {
        CREATE_ISSUE (1),
        COMMENT_ISSUE (2),
        WORKLOG_ISSUE (3),
        RESOLVE_ISSUE (4),
        CHANGE_COMMENT_ISSUE (5),
        DELETE_COMMENT_ISSUE (6),
        ADD_ATTACHMENT_ISSUE (7),
        //todo add more actions here
        ;

        private int value;

        Action(int value) {
            this.value = value;
        }
    }

    int actionTypeAsInt;
    Action actionType;

    public ActivityLog() {
    }

    public ActivityLog(Action action) {
        created = new Date();
        updated = new Date();
        actionType = action;
        actionTypeAsInt = actionType.value;
    }

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "author_id", nullable = false)
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "update_author_id", nullable = false)
    public User getUpdateAuthor() {
        return updateAuthor;
    }

    public void setUpdateAuthor(User updateAuthor) {
        this.updateAuthor = updateAuthor;
    }

    @Column(nullable = false)
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Column(nullable = false)
    public int getActionTypeAsInt() {
        return actionTypeAsInt;
    }

    public void setActionTypeAsInt(int actionTypeAsInt) {
        this.actionTypeAsInt = actionTypeAsInt;
    }
    @Transient
    public Action getActionType() {
        return actionType;
    }

    public void setActionType(Action actionType) {
        this.actionType = actionType;
    }

    @Column(nullable = false)
    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @Column(nullable = false)
    public String getActivityAsString() {
        return activityAsString;
    }

    public void setActivityAsString(String activityAsString) {
        this.activityAsString = activityAsString;
    }
    @ManyToOne(targetEntity = Issue.class)
    @JoinColumn(name = "issue_id")
    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }
    @Column(nullable = true, length = 2048)
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
