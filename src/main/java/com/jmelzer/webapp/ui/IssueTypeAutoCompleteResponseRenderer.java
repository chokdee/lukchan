/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 08.06.12 
*
*/


package com.jmelzer.webapp.ui;

import com.jmelzer.data.model.IssueType;
import org.apache.wicket.Response;
import org.apache.wicket.protocol.http.WebResponse;
import org.wicketstuff.objectautocomplete.ObjectAutoCompleteResponseRenderer;

import java.util.Iterator;

public class IssueTypeAutoCompleteResponseRenderer extends ObjectAutoCompleteResponseRenderer<IssueType> {
    private static final long serialVersionUID = -3794324367115006968L;

    @Override
    public void onRequest(Iterator<IssueType> pComps, WebResponse pResponse, String pInput) {
        pResponse.write("<div>");
        pResponse.write("<ul style=\"list-style-type:none\">");
        while (pComps.hasNext()) {
            IssueType issueType = pComps.next();
            renderObject(issueType, pResponse, pInput);
        }
        pResponse.write("</ul>");
        pResponse.write("</div>");
    }

    @Override
    protected String getIdValue(IssueType object) {
        return "" + object.getId();
    }

    @Override
    protected void renderChoice(IssueType issueType, Response pResponse, String criteria) {
        pResponse.write("<img src=\"" + issueType.getIconPath() + "\" alt=\"" + issueType.getType() + "\">");
        pResponse.write("<span style=\"margin-left: 5px;\" class=\"typename\">" + issueType.getType() + "</span>");
        pResponse.write("</img>");
    }

    @Override
    protected String getTextValue(IssueType pObject) {
        return pObject.getType();
    }
}

