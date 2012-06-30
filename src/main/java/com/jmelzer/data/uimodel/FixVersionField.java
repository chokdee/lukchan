/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 09.06.12 
*
*/


package com.jmelzer.data.uimodel;

import com.jmelzer.data.dao.ProjectDao;
import com.jmelzer.data.model.ProjectVersion;
import com.jmelzer.data.model.ui.SelectOption;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component("fixversionfield")
@Scope("prototype")
public class FixVersionField extends BaseVersionField implements Serializable {

    private static final long serialVersionUID = -4871766369513930360L;

    public FixVersionField() {
        super("fixversion");
    }

    @Override
    public long getId() {
        return FIXVERSION_ID;
    }



}
