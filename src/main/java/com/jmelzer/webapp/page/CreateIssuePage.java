/*
 * Copyright (c) jmelzer 2011.
 * All rights reserved.
 */

package com.jmelzer.webapp.page;

import com.jmelzer.data.model.Issue;
import com.jmelzer.data.model.ui.SelectOptionI;
import com.jmelzer.data.model.ui.UiField;
import com.jmelzer.data.model.ui.View;
import com.jmelzer.data.uimodel.Field;
import com.jmelzer.data.uimodel.IPanel;
import com.jmelzer.data.uimodel.StringModel;
import com.jmelzer.service.IssueManager;
import com.jmelzer.service.Viewmanager;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CreateIssuePage extends MainPage {


    private static final long serialVersionUID = -8961942944711139077L;
    Issue issue;

    Map<Long, Object> model = new HashMap<Long, Object>();
    @SpringBean(name = "viewManager")
    Viewmanager viewmanager;
    @SpringBean(name = "issueManager")
    IssueManager issueManager;


    /**
     * Constructor that is invoked when page is invoked without a session.
     *
     * @param parameters Page parameters
     */
    public CreateIssuePage(final PageParameters parameters) {

        issue = new Issue();


        final IssueForm form = new IssueForm("formid");
        add(form);


        View view = viewmanager.getView("createissue");
        List<UiField> uiFields = view.getTabs().get(0).getFields();
        ListView<UiField> listView = new ListView<UiField>("list", uiFields) {
            private static final long serialVersionUID = -4341539092876343983L;

            Field projectField;
            @Override
            protected void populateItem(ListItem<UiField> item) {
                UiField uiField = item.getModelObject();
                Field wicketField = uiField.getWicketField();
//                if (wicketField.getId() == Field.FIXVERSION_ID) {
//                    return;
//                }
                if (wicketField.dependsOn(Field.PROJECT_ID)) {
                    wicketField.setParent(projectField);
                }
                Panel component = wicketField.getWicketComponent();
                Object imodel = ((IPanel)component).getModel();
                if (wicketField.getId() == Field.PROJECT_ID) {
                    projectField = wicketField;
                }
                model.put(wicketField.getId(), imodel);
                item.add(component);
            }
        };
        form.add(listView);
    }

    class IssueForm extends Form {

        private static final long serialVersionUID = -4592218575449159145L;

        public IssueForm(String id) {
            super(id);

        }

        @Override
        public void onSubmit() {
            Issue issue = new Issue();

            issue.setSummary(((StringModel) model.get(Field.SUMMARY_ID)).getString());
            issue.setDescription(((StringModel) model.get(Field.DESCRIPTION_ID)).getString());
            String comp = (String) ((Model)model.get(Field.COMPONENT_ID)).getObject();
            String ver = (String) ((Model)model.get(Field.FIXVERSION_ID)).getObject();
            //we have to wait for converting fields
            String orgTime = ((StringModel)model.get(Field.ORGESTIMATE_ID)).getString();
            String leftTime = ((StringModel)model.get(Field.REMAININGESTIMATE_ID)).getString();
            System.out.println("ver = " + ver);
            issue.setDueDate(((PropertyModel<Date>) model.get(Field.DUEDATE_ID)).getObject());
            issueManager.create(issue, getKey(Field.PROJECT_ID),
                                getKey(Field.ISSUETYPE_ID),
                                getKey(Field.PRIORITY_ID),
                                comp);
        }

        Long getKey(long id) {
            return ((SelectOptionI) ((Model) model.get(id)).getObject()).getKey();
        }
    }


}

