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
import com.jmelzer.data.util.DateUtilsJm;
import com.jmelzer.data.util.HumanTime;
import com.jmelzer.service.impl.ImageUtil;
import com.jmelzer.webapp.page.HomePage;
import com.jmelzer.webapp.page.ShowIssuePage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.image.resource.BufferedDynamicImageResource;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import ro.fortsoft.wicket.dashboard.Widget;
import ro.fortsoft.wicket.dashboard.web.WidgetView;

/**
 */
public class ActivityLogWidgetView extends WidgetView {

	private static final long serialVersionUID = 1L;

	public ActivityLogWidgetView(String id, Model<Widget> model) {
		super(id, model);

		ActivityLogWidget widget = (ActivityLogWidget) model.getObject();
        //comments
        ListView listView = new ListView<ActivityLog>("logs", widget.getActivityLogs()) {


            private static final long serialVersionUID = 111175602000890044L;

            @Override
            protected void populateItem(ListItem<ActivityLog> item) {
                ActivityLog activityLog = item.getModelObject();
                Link link = new BookmarkablePageLink("userlink", HomePage.class, null);
                link.add(new Label("username", activityLog.getUpdateAuthor().getUsername()));
                BufferedDynamicImageResource resource = new BufferedDynamicImageResource();
                resource.setImage(ImageUtil.calcImage(activityLog.getUpdateAuthor().getAvatar()));
                Image image = new Image("userimage",resource);
                link.add(image);
                item.add(link);
                Label label = new Label("logAction", activityLog.getActivityAsString());
                label.setEscapeModelStrings(false);
                item.add(label);
                label = new Label("logDetail", activityLog.getDetail());
                label.setEscapeModelStrings(false);
                item.add(label);
                HumanTime ht = new HumanTime(DateUtilsJm.diffSince(activityLog.getUpdated()));
                Label label1 = new Label("logDate", ht.getApproximately());
                item.add(label1);

                PageParameters pageParameters = new PageParameters();
                pageParameters.set(0, activityLog.getIssue().getPublicId());
                Link issueLink = new BookmarkablePageLink("issueLink", ShowIssuePage.class, pageParameters);
                issueLink.add(new Label("issueLinkLabel", activityLog.getIssue().getPublicId()));
                item.add(issueLink);

            }
        };
        add(listView);
	}

}
