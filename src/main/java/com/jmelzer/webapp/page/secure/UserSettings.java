/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 28.05.12 
*
*/


package com.jmelzer.webapp.page.secure;

import com.jmelzer.webapp.page.MainPage;
import org.apache.wicket.markup.html.basic.Label;

public class UserSettings extends MainPage {

    private static final long serialVersionUID = -7619583288631041965L;

    public UserSettings() {
        add(new Label("test", "test"));
    }
}
