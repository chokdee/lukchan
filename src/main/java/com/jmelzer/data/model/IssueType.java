/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 27.05.12 
*
*/


package com.jmelzer.data.model;

import com.jmelzer.data.model.ui.SelectOptionI;

import javax.persistence.*;

@Entity
@Table(name = "issue_type",
       uniqueConstraints = {@UniqueConstraint(columnNames = {"type"})}
)
public class IssueType extends ModelBase implements SelectOptionI {
    private static final long serialVersionUID = -4180731928076309612L;


    String type;
    String iconPath;

    public IssueType() {
    }

    public IssueType(String type) {
        this.type = type;
    }

    public IssueType(String type, String iconPath) {
        this.type = type;
        this.iconPath = iconPath;
    }

    @Column(nullable = false)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "icon_path")
    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    @Override
    @Transient
    public Long getKeyForOption() {
        return getId();
    }

    @Override
    @Transient
    public String getValueForOption() {
        return getType();
    }
}
