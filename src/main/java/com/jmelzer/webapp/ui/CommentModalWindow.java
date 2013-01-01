/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 01.01.13 
*
*/


package com.jmelzer.webapp.ui;

import com.jmelzer.webapp.page.CommentPage;
import com.jmelzer.webapp.page.ShowIssuePage;
import org.apache.wicket.Page;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.model.IModel;

public class CommentModalWindow extends ModalWindow implements ModalWindow.PageCreator {
    private static final long serialVersionUID = -2138289060842559711L;
    ShowIssuePage showIssuePage;
    Long commentId;
    String value;

    public CommentModalWindow(String id, ShowIssuePage showIssuePage) {
        super(id);
        setPageCreator(this);
        this.showIssuePage = showIssuePage;
    }

    public CommentModalWindow(String id, IModel<?> model) {
        super(id, model);
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
//        getCommentPage().setCommentId(commentId);
    }

    public void setValue(String value) {
        this.value = value;
//        getCommentPage().setValue(value);
    }
    CommentPage getCommentPage() {
        return (CommentPage) getPage();
    }
    @Override
    public Page createPage() {
        return new CommentPage(showIssuePage, this, value, commentId);
    }
}
