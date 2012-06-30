/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 24.06.12 
*
*/


package com.jmelzer.data.uimodel;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("assigneefield")
@Scope("prototype")
public class AssigneeField extends BaseUserField {
    private static final long serialVersionUID = -5978610797588905670L;

    public AssigneeField() {
        super("assignee");
    }

    @Override
    public long getId() {
        return ASSIGNEE_ID;
    }
}
