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

/**
 * Represents a saved search that can be triggered bz the GUI etc.
 */
@Entity
@Table(name = "saved_search")
public class SavedSearch extends ModelBase {
    private static final long serialVersionUID = -3790508766898022628L;

    String name;
    String query;
    User owner;
    //add here necessary groups if the searches shall be shared

    public SavedSearch() {
    }

    public SavedSearch(String name, String query, User owner) {
        this.name = name;
        this.query = query;
        this.owner = owner;
    }

    @Column(name = "name", length = 2048, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column(name = "query", length = 2048, nullable = false)
    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", nullable = false)
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
