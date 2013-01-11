/*
 * Copyright (c) jmelzer 2012.
 * All rights reserved.
 */


package com.jmelzer.webapp.utils;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.model.IModel;

public class StaticImage extends WebComponent {

    private static final long serialVersionUID = -1349674186662329212L;

    public StaticImage(String id, IModel model) {
        super(id, model);
    }

    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);
        checkComponentTag(tag, "img");
        tag.put("src", getDefaultModelObjectAsString());
        // since Wicket 1.4 you need to use getDefaultModelObjectAsString() instead of getModelObjectAsString()
    }

}