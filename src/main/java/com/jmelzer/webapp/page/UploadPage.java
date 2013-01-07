/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 01.07.12 
*
*/


package com.jmelzer.webapp.page;

import com.jmelzer.webapp.WicketApplication;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.file.Folder;
import org.apache.wicket.util.lang.Bytes;

import java.io.File;

public class UploadPage extends WebPage {
    private static final long serialVersionUID = 432735795419261813L;

    ShowIssuePage caller;
    ModalWindow window;

    public UploadPage(ShowIssuePage page, final ModalWindow window) {
        this.caller = page;
        this.window = window;

        init();
    }

    public void init() {
        add(new AjaxLink("close") {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick(AjaxRequestTarget target) {
                window.close(target);
            }
        });
        final FileUploadForm html5UploadForm = new FileUploadForm("html5Upload");
        add(html5UploadForm);
    }

    /** Form for uploads. */
    private class FileUploadForm extends Form<Void> {
        private static final long serialVersionUID = 1067014592676883583L;
        FileUploadField fileUploadField;
        File newFile;

        /**
         * Construct.
         *
         * @param name Component name
         */
        public FileUploadForm(String name) {
            super(name);

            // set this form to multipart mode (allways needed for uploads!)
            setMultiPart(true);

            // Add one file input field
            add(fileUploadField = new FileUploadField("fileInput"));

            //todo configure
            setMaxSize(Bytes.megabytes(100));

            AjaxButton submitButton = new AjaxButton("ok", this) {
                private static final long serialVersionUID = 4469574567509719475L;

                @Override
                protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                    caller.uploadCompleted(newFile);
                    window.close(target);
                }
            };
            add(submitButton);
        }

        /** @see org.apache.wicket.markup.html.form.Form#onSubmit() */
        @Override
        protected void onSubmit() {
            FileUpload upload = fileUploadField.getFileUpload();
            if (upload != null) {
                // Create a new file
                newFile = new File(getUploadFolder(), upload.getClientFileName());

                // Check new file, delete if it already existed
//                    checkFileExists(newFile);
                try {
                    // Save to new file
                    newFile.createNewFile();
                    upload.writeTo(newFile);

                    UploadPage.this.info("saved file: " + upload.getClientFileName());


                } catch (Exception e) {
                    newFile = null;
                    throw new IllegalStateException("Unable to write file", e);
                }
            }
        }
    }

    private Folder getUploadFolder() {
        return ((WicketApplication) Application.get()).getUploadFolder();
    }
}
