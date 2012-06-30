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
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "project")
public class Project extends ModelBase implements Serializable, SelectOptionI {
    private static final long serialVersionUID = 4113434938484652578L;

    String name;
    String shortName;
    Long issueCounter;

    private Set<Issue> issues = new LinkedHashSet<Issue>();
    private Set<Component> components = new LinkedHashSet<Component>();
    private Set<ProjectVersion> versions = new LinkedHashSet<ProjectVersion>();

    User lead;

    @OneToMany(cascade = {CascadeType.REMOVE}, mappedBy = "project")
    public Set<Issue> getIssues() {
        return issues;
    }

    public void setIssues(Set<Issue> issues) {
        this.issues = issues;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "short", nullable = false)
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @OneToOne
    public User getLead() {
        return lead;
    }

    public void setLead(User lead) {
        this.lead = lead;
    }

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<Component> getComponents() {
        return components;
    }

    public void setComponents(Set<Component> components) {
        this.components = components;
    }

    public void addComponent(Component component) {
        components.add(component);
        component.setProject(this);
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Project");
        sb.append("{name='").append(name).append('\'');
        sb.append(", shortName='").append(shortName).append('\'');
        sb.append(", lead=").append(lead);
        sb.append('}');
        return sb.toString();
    }

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<ProjectVersion> getVersions() {
        return versions;
    }

    public ProjectVersion getVersionByName(String name) {
        for (ProjectVersion version : versions) {
            if (name.equals(version.getVersionNumber())) {
                return version;
            }
        }
        return null;
    }

    public void setVersions(Set<ProjectVersion> versions) {
        this.versions = versions;
    }

    public void addVersion(ProjectVersion projectVersion) {
        versions.add(projectVersion);
        projectVersion.setProject(this);
    }
    public void addIssue(Issue issue) {
        issues.add(issue);
        issue.setProject(this);
    }

    public Component getComponent(int i) {
        Iterator<Component> itr = components.iterator();
        int count = 0;
        while (itr.hasNext()) {
            Component c = itr.next();
            if (count++ == i) {
                return c;
            }
        }
        return null;
    }
    @Column(name = "issue_counter")
    public Long getIssueCounter() {
        return issueCounter;
    }

    public void setIssueCounter(Long issueCounter) {
        this.issueCounter = issueCounter;
    }
}
