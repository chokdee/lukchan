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

import com.jmelzer.data.model.ActivityLog;
import com.jmelzer.data.model.Issue;
import com.jmelzer.data.util.DateUtilsJm;
import com.jmelzer.data.util.HumanTime;
import com.jmelzer.service.impl.ImageUtil;
import com.jmelzer.webapp.page.HomePage;
import com.jmelzer.webapp.page.ShowIssuePage;
import com.jmelzer.webapp.ui.widgets.MyIssuesWidget;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.image.resource.BufferedDynamicImageResource;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.ContextRelativeResource;
import ro.fortsoft.wicket.dashboard.Widget;
import ro.fortsoft.wicket.dashboard.web.WidgetView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * shows list of the assigned issue from the user who is logged in.
 */
public class MyIssuesWidgetView extends WidgetView {

    private static final long serialVersionUID = 1L;

    public MyIssuesWidgetView(String id, Model<Widget> model) {
        super(id, model);

        MyIssuesWidget widget = (MyIssuesWidget) model.getObject();
        widget.setTitle(getString("title"));
        //comments
//        DataView dataView = new DataView<Issue>("myissues", widget) {
//
//
//            private static final long serialVersionUID = 111175602000890044L;
//
//            @Override
//            protected void populateItem(Item<Issue> item) {
//                Issue issue = item.getModelObject();
//
//                //table
//                // icon | Issue key | Priority | Status | Summary
//                item.add(new Image("issuetypeimage", new ContextRelativeResource(issue.getType().getIconPath())));
//
//                PageParameters pageParameters = new PageParameters();
//                pageParameters.set(0, issue.getPublicId());
//                Link<ShowIssuePage> issueLink = new BookmarkablePageLink<ShowIssuePage>("issueLink", ShowIssuePage.class, pageParameters);
//                issueLink.add(new Label("issueLinkLabel", issue.getPublicId()));
//                item.add(issueLink);
//
//                item.add(new Label("priority", issue.getPriority().getName()));
//
//
//            }
//        };
//        dataView.setItemsPerPage(4);
//        add(dataView);
//        add(new PagingNavigator("navigator", dataView));

        List<IColumn<Issue, String>> columns = new ArrayList<IColumn<Issue, String>>();

//

        columns.add(new PropertyColumn<Issue, String>(new Model<String>("ID"), "id"));
//        columns.add(new PropertyColumn<Issue>(new Model<String>("First Name"), "firstName",
//                                                "firstName"));
//        columns.add(new PropertyColumn<Issue>(new Model<String>("Last Name"), "lastName",
//                                                "lastName"));
//        columns.add(new PropertyColumn<Issue>(new Model<String>("Home Phone"), "homePhone"));
//        columns.add(new PropertyColumn<Issue>(new Model<String>("Cell Phone"), "cellPhone"));

        add(new AjaxFallbackDefaultDataTable<Issue, String>("table", columns, widget, 4));
    }

}
