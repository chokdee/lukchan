/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 08.06.12 
*
*/


package com.jmelzer.webapp.ui;

import com.jmelzer.data.model.ui.SelectOptionI;
import org.apache.wicket.markup.html.form.IChoiceRenderer;

public class GenericChoiceRenderer implements IChoiceRenderer {

    private static final long serialVersionUID = -1506140656987486996L;

    @Override
    public Object getDisplayValue(Object object) {
        if (object instanceof String) {
            return object;
        }
        return ((SelectOptionI) object).getValueForOption();
    }

    @Override
    public String getIdValue(Object object, int index) {
        if (object instanceof String) {
            return (String) object;
        } else if (object instanceof SelectOptionI) {
            return "" + ((SelectOptionI) object).getKeyForOption();
        } else {
            return null;
        }
    }
}
