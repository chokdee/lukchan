/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 03.06.12 
*
*/


package com.jmelzer.data.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

/** represents a component of a project. */
@Entity
@Table(name = "component")
public class Component extends ModelBase implements Serializable {
    private static final long serialVersionUID = -2675791025541387740L;

    String name;
    Project project;

    public Component(String name) {
        this.name = name;
    }

    public Component() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @ManyToOne(targetEntity = Project.class)
    @JoinColumn(name = "project_id")
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
