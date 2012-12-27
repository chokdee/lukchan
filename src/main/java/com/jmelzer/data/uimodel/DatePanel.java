/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 16.06.12 
*
*/


package com.jmelzer.data.uimodel;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;

import java.util.Date;

public class DatePanel extends Panel implements IPanel {
    private static final long serialVersionUID = -7474408835148134032L;

    PropertyModel dateFieldModel;
    Date date;

    public DatePanel(String labelName, boolean required) {
        super("component");
        dateFieldModel = new PropertyModel(this, "date");
        Label label = new Label("dateLabel", new StringResourceModel(labelName, new Model("")));
        add(label);
        if (required) {
            label.add(AttributeModifier.replace("class", "required"));
        }
        DateTextField tf = new DateTextField("date", dateFieldModel);
        tf.setRequired(required);
        tf.setLabel(new StringResourceModel(labelName, new Model("")));
        add(tf);
        DatePicker datePicker = new DatePicker();
        datePicker.setShowOnFieldClick(true);
//        datePicker.setAutoHide(true);
        tf.add(datePicker);
        add(new ComponentFeedbackPanel("FieldErr", tf));
    }

    @Override
    public Object getModel() {
        return dateFieldModel;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
