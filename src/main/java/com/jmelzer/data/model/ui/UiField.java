/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 09.06.12 
*
*/


package com.jmelzer.data.model.ui;

import com.jmelzer.data.model.ModelBase;
import com.jmelzer.data.uimodel.Field;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * meta description of the
 * field in a tab in a view.
 */
@Entity
@Table(name = "uifield")
public class UiField extends ModelBase {
    private static final long serialVersionUID = -2211140415489163783L;

    Integer position;
    /** id of the real field. */
    Long fieldId;
    private Field wicketField;

    @Column(name = "position", nullable = false, unique = true)
    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
    @Column(name = "field_id", nullable = false)
    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    public void setWicketField(Field wicketField) {
        this.wicketField = wicketField;
    }
    @Transient
    public Field getWicketField() {
        return wicketField;
    }
}
