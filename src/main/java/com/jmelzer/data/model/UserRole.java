/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 30.12.12 
*
*/


package com.jmelzer.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user_role")
public class UserRole extends ModelBase {
    private static final long serialVersionUID = 7181113920788192505L;

    String name;

    public enum Roles {
        ROLE_DEVELOPER,
        ROLE_ADMIN,
        ROLE_USER
    }
    public UserRole() {
    }

    public UserRole(String name) {
        this.name = name;
    }

    @Column(length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
