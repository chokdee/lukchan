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
    private long filePrefix;
    private String fileName;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @Column(name = "file_prefix")
    public long getFilePrefix() {
        return filePrefix;
    }

    public void setFilePrefix(long filePrefix) {
        this.filePrefix = filePrefix;
    }

    @Column(name = "file_name")
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
