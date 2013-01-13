/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 27.05.12 
*
*/


package com.jmelzer.data.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "attachment")
/**
 * Represents a file attachment.  Files will be stored on the local
 * file system, not within the database.
 * When an Attachment is first uploaded and stored, it is prefixed
 * with the value of the id generated on the database insert.
 * This filePrefix property is stored separately to smoothly
 * handle database migrations.  So even if a database export-import
 * changes the id column values, the files within the attachments
 * folder can be used as is, without resorting to mass renaming.
 */
public class Attachment implements Serializable {
    private static final long serialVersionUID = -2530591468787970176L;

    Long id;

    private Attachment previous;
    private String fileName;
    private String previewFileName;

    private static Map<String, String> types = new HashMap<String, String>();

    static {
        types.put("image/gif", ".gif");
        types.put("image/jpeg", ".jpg");
        types.put("image/png", ".png");
        //todo add mime types here
    }

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToOne
    public Attachment getPrevious() {
        return previous;
    }

    public void setPrevious(Attachment previous) {
        this.previous = previous;
    }

    @Column(name = "file_name", nullable = false)
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = id + fileName.substring(fileName.lastIndexOf("."));
    }
    @Column(name = "preview_file_name", nullable = true)
    public String getPreviewFileName() {
        return previewFileName;
    }

    public void setPreviewFileName(String previewFileName) {
        this.previewFileName = previewFileName;
    }
}
