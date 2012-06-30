/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 16.06.12 
*
*/


package com.jmelzer.data.uimodel;

import com.visural.wicket.component.nicedit.Button;
import com.visural.wicket.component.nicedit.RichTextEditor;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;

public class DescriptionPanel extends Panel implements IPanel {
    private static final long serialVersionUID = -1375817533031427579L;

    StringModel model;

    public DescriptionPanel(StringModel stringModel, String labelName, boolean required) {
        super("component");
        this.model = stringModel;
        Label label = new Label("descriptionLabel", new StringResourceModel(labelName, new Model("")));
        add(label);
        if (required) {
            label.add(new SimpleAttributeModifier("class", "required"));
        }
        RichTextEditor field = new RichTextEditor("description", model) {
            private static final long serialVersionUID = 8783873065984693004L;

            @Override
            public boolean isButtonEnabled(Button button) {
                switch (button) {
                    case bold:
                    case italic:
                    case ol:
                    case ul:
                    case underline:
                    case fontSize:
                    case upload:
                    case right:
                    case indent:
                        return true;
                    default:
                        return false;
                }
            }
        };
        field.setRequired(required);
        field.setLabel(new StringResourceModel(labelName, new Model("")));
        add(field);
        add(new ComponentFeedbackPanel("descFieldErr", field));
    }

    @Override
    public Object getModel() {
        return model;
    }
}
