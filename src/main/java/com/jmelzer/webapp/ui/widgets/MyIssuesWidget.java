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
import org.apache.wicket.Localizer;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.util.SingleSortState;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import ro.fortsoft.wicket.dashboard.AbstractWidget;
import ro.fortsoft.wicket.dashboard.Widget;
import ro.fortsoft.wicket.dashboard.web.WidgetView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * show the activities from activity log
 */
public class MyIssuesWidget extends AbstractWidget implements ISortableDataProvider<Issue, String> {

    private static final long serialVersionUID = -8782525898781644014L;
    //todo move to database queries
    private transient List<Issue> issues;
    private final SingleSortState<String> state = new SingleSortState<String>();


    public MyIssuesWidget(String id, String title) {
        super();


        this.id = id;
        this.title = title;
    }

    public WidgetView createView(String viewId) {
        return new MyIssuesWidgetView(viewId, new Model<Widget>(this));
    }

    public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }

    @Override
    public Iterator<? extends Issue> iterator(long first, long count) {
        int n = 0;
        List<Issue> result = new ArrayList<Issue>((int)count);
        for (Issue issue : issues) {
            if (n >= first && result.size() < count) {
                result.add(issue);
            }
            n++;
        }
        return result.iterator();
    }

    @Override
    public long size() {
        return issues.size();
    }

    @Override
    public IModel<Issue> model(Issue object) {
        return new Model<Issue>(object);
    }

    @Override
    public void detach() {
    }

    @Override
    public ISortState<String> getSortState() {
        return state;
    }
}
