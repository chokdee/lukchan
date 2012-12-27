/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 01.07.12 
*
*/


package com.jmelzer.webapp.page;

import com.jmelzer.data.uimodel.StringModel;
import com.jmelzer.webapp.WicketApplication;

import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import wicket.contrib.tinymce.TinyMceBehavior;
import wicket.contrib.tinymce.ajax.TinyMceAjaxSubmitModifier;
import wicket.contrib.tinymce.settings.TinyMCESettings;

public class CommentPage extends WebPage {
    private static final long serialVersionUID = -1248292189048177184L;

    ShowIssuePage caller;
    StringModel textModel = new StringModel("");

    public CommentPage(ShowIssuePage page, final ModalWindow window) {
        this.caller = page;

        add(new AjaxLink("close") {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick(AjaxRequestTarget target) {
                window.close(target);
            }
        });
        final Form form = new Form("commentForm");
        add(form);
        TextArea textArea = new TextArea("textfield", textModel);
        textArea.add(new TinyMceBehavior(((WicketApplication) Application.get()).getTinyMCESettings()));
        form.add(textArea);

//        add(new HeaderContributor(new IHeaderContributor() {
//            private static final long serialVersionUID = 7351631938044011155L;
//
//            public void renderHead(IHeaderResponse response) {
//                response.renderJavascriptReference(TinyMCESettings.javaScriptReference());
//            }
//        }));

        AjaxButton submitButton = new AjaxButton("submit", form) {
            private static final long serialVersionUID = 4978089672616474658L;

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                System.out.println(textModel);
                caller.addComment(textModel.getString());
                window.close(target);
            }
        };
        form.add(submitButton);
        submitButton.add(new TinyMceAjaxSubmitModifier());

    }


}
