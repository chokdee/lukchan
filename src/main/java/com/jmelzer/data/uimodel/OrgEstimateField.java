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

@Component("orgestimatefield")
@Scope("prototype")
public class OrgEstimateField extends AbstractField {

    private static final long serialVersionUID = -1623330705261073579L;
    //cache inside prototype
    TextPanel panel;

    @Override
    public long  getId() {
        return ORGESTIMATE_ID;
    }

    @Override
    public Panel getWicketComponent() {
        if (panel == null) {
            panel = new TextPanel(new StringModel(""), "orgestimate", false);
        }
        return panel;
    }

}
