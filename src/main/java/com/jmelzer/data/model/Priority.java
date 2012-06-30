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

/** Represents a Prority, can be extend from the admin panels. */
@Entity
@Table(name = "priority")
public class Priority extends ModelBase implements SelectOptionI {
    private static final long serialVersionUID = -745137151059463600L;

    int order;
    String name;
    String statusColour;

    public Priority() {
    }

    public Priority(String name) {
        this.name = name;
    }

    public Priority(String name, int order) {
        this.name = name;
        this.order = order;
    }

    @Column(name = "name", length = 2048, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "status_colour")
    public String getStatusColour() {
        return statusColour;
    }

    public void setStatusColour(String statusColour) {
        this.statusColour = statusColour;
    }
    @Column(name = "porder", nullable = false, unique = true)
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    @Transient
    public Long getKey() {
        return getId();
    }

    @Override
    @Transient
    public String getValue() {
        return getName();
    }
}
