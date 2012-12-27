/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 27.05.12 
*
*/


package com.jmelzer.data.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Comment for an Issue, can be in HTML format.
 */
@Entity
@Table(name = "comment")
public class Comment extends ModelBase {
    private static final long serialVersionUID = -745137151059463600L;

    String text;
    Date creationDate;
    User owner;

    public Comment() {
        creationDate = new Date();
    }

    public Comment(String text, User owner) {
        this();
        this.text = text;
        this.owner = owner;
    }

    @Column(name = "text", length = 4048, nullable = false)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    @Column(name = "creation_date", nullable = false)
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "owner_id", nullable = false)
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
