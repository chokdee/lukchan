/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 16.06.12 
*
*/


package com.jmelzer.data.uimodel;

import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;

public class TextPanel extends Panel implements IPanel {

    private static final long serialVersionUID = 193613674391871980L;

    StringModel model;

    public TextPanel(StringModel stringModel, String labelName, boolean required) {
        super("component");
        Label label = new Label("textLabel", new StringResourceModel(labelName, new Model("")));
        add(label);
        if (required) {
            label.add(new SimpleAttributeModifier("class", "required"));
        }
        TextField tf = new TextField("text", stringModel);
        tf.setRequired(required);
        tf.setLabel(new StringResourceModel(labelName, new Model("")));
        add(tf);
        add(new ComponentFeedbackPanel("FieldErr", tf));
        this.model = stringModel;
    }

    @Override
    public Object getModel() {
        return model;
    }
}
