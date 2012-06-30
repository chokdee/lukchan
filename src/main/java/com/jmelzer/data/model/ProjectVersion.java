/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 08.06.12 
*
*/


package com.jmelzer.data.model;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "project_version")
public class ProjectVersion extends ModelBase {

    private static final long serialVersionUID = 524711670224095283L;

    String versionNumber;
    private Set<Issue> issues = new LinkedHashSet<Issue>();
    Project project;

    @Column(name = "version")
    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }
    @OneToMany(cascade = {CascadeType.ALL})
    public Set<Issue> getIssues() {
        return issues;
    }

    public void setIssues(Set<Issue> issues) {
        this.issues = issues;
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
