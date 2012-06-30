/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 09.06.12 
*
*/


package com.jmelzer.data.uimodel;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.panel.Panel;

public interface Field {

    static final long ISSUETYPE_ID = 1;
    static final long PROJECT_ID = 2;
    static final long SUMMARY_ID = 3;
    static final long PRIORITY_ID = 4;
    static final long DUEDATE_ID = 5;
    static final long COMPONENT_ID = 6;
    static final long FIXVERSION_ID = 7;
    static final long AFFECTED_VERSION_ID = 8;
    static final long DESCRIPTION_ID = 9;
    static final long ASSIGNEE_ID = 10;
    static final long ORGESTIMATE_ID = 11;
    static final long REMAININGESTIMATE_ID = 12;

    /**
     * The unique id of the field
     */
    long getId();

    Panel getWicketComponent();

    boolean dependsOn(long id);

    void setParent(Field field);

    void addChild(Field fixVersionField);
}
