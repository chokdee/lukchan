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
import org.apache.wicket.model.Model;
import ro.fortsoft.wicket.dashboard.AbstractWidget;
import ro.fortsoft.wicket.dashboard.Widget;
import ro.fortsoft.wicket.dashboard.web.WidgetView;

import java.util.List;

/**
 * show the activities from activity log
 */
public class ActivityLogWidget extends AbstractWidget {

    private static final long serialVersionUID = -8782525898781644014L;
    private transient List<ActivityLog> activityLogs;

    public ActivityLogWidget(String id, String title) {
        super();

        this.id = id;
        this.title = title;
    }

    public WidgetView createView(String viewId) {
        return new ActivityLogWidgetView(viewId, new Model<Widget>(this));
    }

    public List<ActivityLog> getActivityLogs() {
        return activityLogs;
    }

    public void setActivityLogs(List<ActivityLog> activityLogs) {
        this.activityLogs = activityLogs;
    }
}
