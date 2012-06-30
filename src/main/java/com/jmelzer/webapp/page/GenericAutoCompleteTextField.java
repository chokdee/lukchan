/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 03.06.12 
*
*/


package com.jmelzer.webapp.page;

import com.jmelzer.data.dao.AbstractDao;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.model.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GenericAutoCompleteTextField<T extends Serializable> extends AutoCompleteTextField {
    private static final long serialVersionUID = 711533193194081742L;

    AbstractDao<T> abstractDao;
    List<String> cachedStrings;
    public GenericAutoCompleteTextField(String id, Class type, AbstractDao<T> abstractDao) {
        super(id, new Model(""));
        this.abstractDao = abstractDao;

        cachedStrings = abstractDao.findAllForAutoCompletion();
    }


    @Override
    protected Iterator getChoices(String input) {
        //todo filter
        return cachedStrings.iterator();
    }
}
