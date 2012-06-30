/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 24.06.12 
*
*/


package com.jmelzer.webapp.examples;


import com.jmelzer.webapp.page.MainPage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import java.util.*;

/**
 * Linked select boxes example
 *
 * @author Igor Vaynberg (ivaynberg)
 */
public class ChoicePage extends MainPage {
    private static final long serialVersionUID = 8955795457793658993L;
    private String selectedMake;

    private final Map<String, List<String>> modelsMap = new HashMap<String, List<String>>(); // map:company->model

    /** @return Currently selected make */
    public String getSelectedMake() {
        return selectedMake;
    }

    /** @param selectedMake The make that is currently selected */
    public void setSelectedMake(String selectedMake) {
        this.selectedMake = selectedMake;
    }

    /** Constructor. */
    public ChoicePage() {
        modelsMap.put("AUDI", Arrays.asList("A4", "A6", "TT"));
        modelsMap.put("CADILLAC", Arrays.asList("CTS", "DTS", "ESCALADE", "SRX", "DEVILLE"));
        modelsMap.put("FORD", Arrays.asList("CROWN", "ESCAPE", "EXPEDITION", "EXPLORER", "F-150"));

        IModel<List<? extends String>> makeChoices = new AbstractReadOnlyModel<List<? extends String>>() {
            private static final long serialVersionUID = 3083223353505563226L;

            @Override
            public List<String> getObject() {
                return new ArrayList<String>(modelsMap.keySet());
            }

        };

        IModel<List<? extends String>> modelChoices = new AbstractReadOnlyModel<List<? extends String>>() {
            private static final long serialVersionUID = -582253860769928261L;

            @Override
            public List<String> getObject() {
                List<String> models = modelsMap.get(selectedMake);
                if (models == null) {
                    models = Collections.emptyList();
                }
                return models;
            }

        };

        Form<?> form = new Form("formid");
        add(form);

        final DropDownChoice<String> makes = new DropDownChoice<String>("makes",
                                                                        new PropertyModel<String>(this, "selectedMake"), makeChoices);

        final DropDownChoice<String> models = new DropDownChoice<String>("models",
                                                                         new Model<String>(), modelChoices);
        models.setOutputMarkupId(true);
        final DropDownChoice<String> models2 = new DropDownChoice<String>("models2",
                                                                         new Model<String>(), modelChoices);
        models2.setOutputMarkupId(true);

        form.add(makes);
        form.add(models);
        form.add(models2);

        makes.add(new AjaxFormComponentUpdatingBehavior("onchange") {
            private static final long serialVersionUID = -4971073888222316420L;

            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                target.addComponent(models);
                target.addComponent(models2);
            }
        });
    }
}