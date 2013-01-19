/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 19.01.13 
*
*/


package com.jmelzer.webapp.ui;

import com.jmelzer.data.model.Issue;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortState;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.util.SingleSortState;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IssueListProvider implements ISortableDataProvider<Issue, String> {
    private static final long serialVersionUID = -3193759292044727352L;

    private transient List<Issue> issues;
    private final SingleSortState<String> state = new SingleSortState<String>();

    public IssueListProvider(List<Issue> issues) {
        this.issues = issues;
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
