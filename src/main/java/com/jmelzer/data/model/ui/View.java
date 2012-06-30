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

@Entity
@Table(name = "view")
public class View extends ModelBase {
    private static final long serialVersionUID = 2242732315432572152L;

    String name;
    String decription;

    private List<ViewTab> tabs = new ArrayList<ViewTab>();

    @Column(unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "tab_id")
    @OrderBy("position")
    public List<ViewTab> getTabs() {
        return tabs;
    }

    public void setTabs(List<ViewTab> tabs) {
        this.tabs = tabs;
    }
    public void addTab(ViewTab tab) {
        tabs.add(tab);
    }
}
