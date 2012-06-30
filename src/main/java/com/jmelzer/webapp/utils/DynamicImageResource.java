/*
 * Copyright (c) jmelzer 2012.
 * All rights reserved.
 */


package com.jmelzer.webapp.utils;

import org.apache.wicket.markup.html.image.resource.RenderedDynamicImageResource;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DynamicImageResource extends RenderedDynamicImageResource {
    private static final long serialVersionUID = 1155758337517707877L;
    BufferedImage media;

    public DynamicImageResource(int width, int height, BufferedImage media) {
        super(width, height);
        this.media = media;
        setCacheable(false);
    }

    @Override
    protected boolean render(Graphics2D graphics) {
        graphics.drawImage(media, 0, 0, null);
        return true;
    }

}