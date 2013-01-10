/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 09.06.12 
*
*/


package com.jmelzer.data.model.ui;

import com.jmelzer.data.model.ModelBase;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/** represents a tab on a view. */
@Entity
@Table(name = "view_tab")
public class ViewTab extends ModelBase {
    private static final long serialVersionUID = -4434551255575769488L;

    String name;
    private int position;

    private List<UiField> fields = new ArrayList<UiField>();

    @Column(name = "pos", unique = true)
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    @OneToMany(cascade = CascadeType.ALL)
    @OrderBy("position")
    public List<UiField> getFields() {
        return fields;
    }

    public void setFields(List<UiField> fields) {
        this.fields = fields;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addField(UiField field) {
        fields.add(field);
    }
}
