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

/**
 * Represents a Label as in an Item can be assigned multiple Labels
 * allows for categorization of Items web 2.0 style
 */
@Entity
@Table(name = "label")
public class Label extends ModelBase {
    private static final long serialVersionUID = -745137151059463600L;

    String name;

    public Label() {
    }

    public Label(String name) {
        this.name = name;
    }

    @Column(name = "name", length = 2048, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
