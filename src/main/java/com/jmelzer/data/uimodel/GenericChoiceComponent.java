/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 10.06.12 
*
*/


package com.jmelzer.data.uimodel;

import com.jmelzer.data.model.ui.SelectOption;
import com.jmelzer.data.model.ui.SelectOptionI;
import com.jmelzer.webapp.ui.GenericChoiceRenderer;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;

import java.util.ArrayList;
import java.util.List;

public class GenericChoiceComponent extends Panel implements IPanel {

    private static final long serialVersionUID = 9088219273321546299L;

    IModel model;
    DropDownChoice downChoice;
    List<Component> children = new ArrayList<Component>();

    public GenericChoiceComponent(IModel model, IModel choices, String labelName, boolean required,
                                  final DropDownChoice parent) {
        super("component");
        downChoice = new DropDownChoice("choice",
                                        model,
                                        choices,
                                        new GenericChoiceRenderer());
        createLabelStuff(model, labelName, required);
        downChoice.setOutputMarkupId(true);
    }

    private void createLabelStuff(IModel model, String labelName, boolean required) {
        downChoice.setRequired(required);
        Label label = new Label("choiceLabel", new StringResourceModel(labelName, new Model("")));
        add(label);
        if (required) {
            label.add(AttributeModifier.replace("class", "required"));
        }
        downChoice.setLabel(new StringResourceModel(labelName, new Model("")));
        add(downChoice);
        add(new ComponentFeedbackPanel("FieldErr", downChoice));
        this.model = model;
    }

    public GenericChoiceComponent(IModel model, List choices, String labelName, boolean required) {
        super("component");
        createFields(model, choices, labelName, required);
    }

    private void createFields(IModel model, List choices, String labelName, boolean required) {
        List<SelectOptionI> list = new ArrayList<SelectOptionI>(choices.size());
        for (Object o : choices) {
            SelectOptionI choice = (SelectOptionI) o;
            list.add(new SelectOption(choice.getKey(), choice.getValue()));
        }
        downChoice = new DropDownChoice("choice",
                                        model,
                                        list,
                                        new GenericChoiceRenderer());
        downChoice.add(new AjaxFormComponentUpdatingBehavior("onchange") {
            private static final long serialVersionUID = 6554405151227058085L;

            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                for (Component child : children) {
                    child.setOutputMarkupId(true);
                    target.add(child);

                }
                System.out.println("target = " + target);
            }
        });
        downChoice.setOutputMarkupId(true);
        createLabelStuff(model, labelName, required);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof GenericChoiceComponent;
    }

    @Override
    public Object getModel() {
        return model;
    }

    public DropDownChoice getDownChoice() {
        return downChoice;
    }
    public void addChild(Component component) {
        children.add(component);
    }
}
