/*
 * Copyright 2012 Decebal Suiu
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with
 * the License. You may obtain a copy of the License in the LICENSE file, or at:
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.jmelzer.webapp.ui.widgets;

import com.jmelzer.data.model.Issue;
import com.jmelzer.webapp.ui.IssueIconPanel;
import com.jmelzer.webapp.ui.LinkIssuePanel;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import ro.fortsoft.wicket.dashboard.Widget;
import ro.fortsoft.wicket.dashboard.web.WidgetView;

import java.util.ArrayList;
import java.util.List;

/** shows list of the assigned issue from the user who is logged in. */
public class MyIssuesWidgetView extends WidgetView {

    private static final long serialVersionUID = 1L;

    public MyIssuesWidgetView(String id, Model<Widget> model) {
        super(id, model);

        MyIssuesWidget widget = (MyIssuesWidget) model.getObject();
        widget.setTitle(getString("title"));

        List<IColumn<Issue, String>> columns = new ArrayList<IColumn<Issue, String>>();

//
        // Typ (Icon) | Issue key | Priority | Status | Summary
        columns.add(new AbstractColumn<Issue, String>(new Model<String>("Type")) {
            private static final long serialVersionUID = 1319453425862074180L;

            @Override
            public void populateItem(Item<ICellPopulator<Issue>> cellItem, String componentId,
                                     IModel<Issue> model) {
                cellItem.add(new IssueIconPanel(componentId,  model));
            }
        });
        columns.add(new AbstractColumn<Issue, String>(new Model<String>("ID")) {

            private static final long serialVersionUID = -3712859337288763874L;

            @Override
            public void populateItem(Item<ICellPopulator<Issue>> cellItem, String componentId,
                                     IModel<Issue> model) {
                cellItem.add(new LinkIssuePanel(componentId, model));
            }
        });
//        columns.add(new PropertyColumn<Issue, String>(new Model<String>(getString("id")), "publicId"));
        columns.add(new PropertyColumn<Issue, String>(new Model<String>(getString("priority")), "priority.name"));
        columns.add(new PropertyColumn<Issue, String>(new Model<String>(getString("status")), "workflowStatus.name"));
        columns.add(new PropertyColumn<Issue, String>(new Model<String>(getString("summary")), "summary"));

        add(new AjaxFallbackDefaultDataTable<Issue, String>("table", columns, widget, 4));
    }


}
