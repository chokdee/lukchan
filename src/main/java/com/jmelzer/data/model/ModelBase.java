/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 27.05.12 
*
*/


package com.jmelzer.data.model;

import org.hibernate.search.annotations.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@SuppressWarnings("serial")
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class ModelBase implements Serializable {

    private Long id;
    private long version;

    Set<DynamicProperty> properties = new LinkedHashSet<DynamicProperty>();

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Field(index = Index.YES, store = Store.YES, analyze = Analyze.NO )
    @NumericField
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToMany(cascade = {CascadeType.ALL})
    public Set<DynamicProperty> getProperties() {
        return properties;
    }

    public void setProperties(Set<DynamicProperty> properties) {
        this.properties = properties;
    }

    public void addProperty(DynamicProperty property) {
        properties.add(property);
    }
    @Version
    @Column(name="optlock")
    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
