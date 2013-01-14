/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 01.07.12 
*
*/


package com.jmelzer.webapp.page;

import com.jmelzer.data.util.FileUtil;
import com.jmelzer.data.util.ImageMagick;
import com.jmelzer.data.util.StreamUtils;
import com.jmelzer.service.impl.ImageUtil;
import com.jmelzer.webapp.WicketApplication;
import com.jmelzer.webapp.utils.StaticImage;
import org.apache.commons.io.FileUtils;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.image.resource.BufferedDynamicImageResource;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.file.Files;
import org.apache.wicket.util.file.Folder;
import org.apache.wicket.util.lang.Bytes;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class UploadPage extends WebPage {
    private static final long serialVersionUID = 432735795419261813L;

    @SpringBean(name = "imageMagick")
    ImageMagick imageMagick;

    private FileListView fileListView;

    /** List view for files in upload folder. */
    private class FileListView extends ListView<File> {
        private static final long serialVersionUID = 2243952788684803700L;

        /**
         * Construct.
         *
         * @param name  Component name
         * @param files The file list model
         */
        public FileListView(String name, final IModel<List<File>> files) {
            super(name, files);
        }

        /** @see ListView#populateItem(ListItem) */
        @Override
        protected void populateItem(ListItem<File> listItem) {
            final File file = listItem.getModelObject();
//            listItem.add(new Label("file", file.getName()));
            BufferedDynamicImageResource resource = new BufferedDynamicImageResource();
            try {
                resource.setImage(ImageIO.read(new ByteArrayInputStream(StreamUtils.loadFile(file))));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Image image = new Image("previewimage", resource);
            listItem.add(image);

            listItem.add(new Link<Void>("delete") {
                private static final long serialVersionUID = 8071881443297620986L;

                @Override
                public void onClick() {
                    Files.remove(file);
                    info("Deleted " + file);
                }
            });
        }
    }

    ShowIssuePage caller;
    ModalWindow window;

    public UploadPage(ShowIssuePage page, final ModalWindow window) {
        this.caller = page;
        this.window = window;

        init();
        try {
            FileUtils.cleanDirectory(getUploadFolder().getAbsoluteFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init() {

        final FileUploadForm html5UploadForm = new FileUploadForm("html5Upload");
        add(html5UploadForm);
        html5UploadForm.add(new Label("label_entry1", getString("label.entry1")));
        html5UploadForm.add(new Label("label_entry2", getString("label.entry2")));
        add(new Label("title", getString("title")));

        // Add folder view
        add(new Label("current_file", getString("uploaded.attachments")));
        fileListView = new FileListView("fileList", new LoadableDetachableModel<List<File>>() {
            private static final long serialVersionUID = -6496403057605422710L;

            @Override
            protected List<File> load() {
                return Arrays.asList(getUploadFolder().listFiles(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        return name.contains("preview");
                    }
                }));
            }
        });
        add(fileListView);

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
                    caller.uploadCompleted(getUploadFolder().listFiles(new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String name) {
                            return !name.contains("preview");
                        }
                    }));
                    window.close(target);
                }
            };
            add(submitButton);
            getUploadFolder().delete();
        }

        /** @see org.apache.wicket.markup.html.form.Form#onSubmit() */
        @Override
        protected void onSubmit() {
            FileUpload upload = fileUploadField.getFileUpload();
            if (upload != null) {
                // Create a new file

                newFile = new File(getUploadFolder(), upload.getClientFileName());
                String filePreviewName = FileUtil.addPreview(newFile.getAbsolutePath());

                // Check new file, delete if it already existed
//                    checkFileExists(newFile);
                try {
                    // Save to new file
                    newFile.createNewFile();
                    upload.writeTo(newFile);
                    imageMagick.applyThumbnail(newFile.getAbsolutePath(), filePreviewName, 120, 120, 90);

                    UploadPage.this.info("saved file: " + upload.getClientFileName());


                } catch (Exception e) {
                    newFile = null;
                    throw new IllegalStateException("Unable to write file", e);
                }
            }
        }
    }

    private Folder getUploadFolder() {
        File tmpDir = new File(System.getProperty("java.io.tmpdir"));
        File sessDir = new File(tmpDir, getSession().getId());
        if (!sessDir.exists()) {
            sessDir.mkdirs();
        }
        return new Folder(sessDir);
//        return ((WicketApplication) Application.get()).getUploadFolder();
    }
}
