/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 27.05.12 
*
*/


package com.jmelzer.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
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

    public Comment() {
        creationDate = new Date();
    }

    public Comment(String text) {
        this();
        this.text = text;
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
}
