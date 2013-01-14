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
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import wicket.contrib.tinymce.TinyMceBehavior;
import wicket.contrib.tinymce.ajax.TinyMceAjaxSubmitModifier;

public class CommentPage extends WebPage {
    private static final long serialVersionUID = -1248292189048177184L;

    ShowIssuePage caller;
    StringModel textModel = new StringModel("");
    private String value;
    Long commentId;

    public CommentPage(ShowIssuePage page, final ModalWindow window,
                       String value, final Long commentId) {
        this.caller = page;
        this.value = value;
        this.commentId = commentId;

        AjaxLink closeLink = new AjaxLink("close") {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick(AjaxRequestTarget target) {
                window.close(target);
            }
        };
        add(closeLink);
        closeLink.add(new Label("cancellink", new StringResourceModel("cancellink", new Model(""))));

        final Form form = new Form("commentForm");
        add(form);
        add(new Label("commentlabel", new StringResourceModel("commentlabel", new Model(""))));
        textModel.setString(value);
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
                if (commentId == null) {
                    caller.addComment(textModel.getString());
                } else {
                    caller.modifyComment(commentId, textModel.getString());
                }
                window.close(target);
//                target.add(caller);
            }
        };
        form.add(submitButton);
        submitButton.add(new TinyMceAjaxSubmitModifier());

    }


    public void setValue(String value) {
        this.value = value;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }
}
