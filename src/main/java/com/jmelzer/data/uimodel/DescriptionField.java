/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 09.06.12 
*
*/


package com.jmelzer.data.uimodel;

import org.apache.wicket.markup.html.panel.Panel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("descriptionfield")
@Scope("prototype")
public class DescriptionField extends AbstractField {

    private static final long serialVersionUID = -1623330705261073579L;
    //cache inside prototype
    DescriptionPanel panel;

    @Override
    public long  getId() {
        return DESCRIPTION_ID;
    }

    @Override
    public Panel getWicketComponent() {
        if (panel == null) {
            panel = new DescriptionPanel(new StringModel(""), "description", false);
        }
        return panel;
    }

}
