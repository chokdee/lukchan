/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 04.01.13 
*
*/


package com.jmelzer.webapp.utils;

import org.apache.wicket.request.mapper.parameter.PageParameters;

public class PageParametersUtil {
    public static PageParameters createWithOneParam(String param) {
        PageParameters pageParameters = new PageParameters();
        pageParameters.set(0, param);
        return pageParameters;
    }
}
