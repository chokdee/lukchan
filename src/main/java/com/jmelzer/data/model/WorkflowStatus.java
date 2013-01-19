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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Workflow Status entity
 */
@Entity
@Table(name = "workflow_status")
public class WorkflowStatus extends ModelBase implements SelectOptionI {
    private static final long serialVersionUID = -394877377723475416L;

    String name;
    int value;
    String iconPath;

    public WorkflowStatus() {
    }

    public WorkflowStatus(String name, int value) {
        this.name = name;
        this.value = value;
    }

    @Column(name = "name", length = 2048, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "value", nullable = false, unique = true)
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Column(name = "icon_path", length = 2048, nullable = true)
    public String getIconPath() {
        if (iconPath == null) {
            return "images/default_status.png";
        }
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
        return getName();
    }
}
