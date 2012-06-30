/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 09.06.12 
*
*/


package com.jmelzer.service.impl;

import com.jmelzer.data.dao.ViewDao;
import com.jmelzer.data.model.ui.UiField;
import com.jmelzer.data.model.ui.View;
import com.jmelzer.data.model.ui.ViewTab;
import com.jmelzer.service.Viewmanager;
import com.jmelzer.data.uimodel.Field;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component("viewManager")
public class ViewmanagerImpl implements Viewmanager, ApplicationContextAware {

    ApplicationContext applicationContext;
    @Resource
    ViewDao viewDao;

    /**
     * map id to bean id.
     */
    static Map<Long, String> fieldMap = new HashMap<Long, String>();
    static {
        fieldMap.put(Field.PROJECT_ID, "projectfield");
        fieldMap.put(Field.ISSUETYPE_ID, "issuetypeField");
        fieldMap.put(Field.SUMMARY_ID, "summaryfield");
        fieldMap.put(Field.PRIORITY_ID, "priorityfield");
        fieldMap.put(Field.DUEDATE_ID, "duedatefield");
        fieldMap.put(Field.COMPONENT_ID, "componentfield");
        fieldMap.put(Field.FIXVERSION_ID, "fixversionfield");
        fieldMap.put(Field.AFFECTED_VERSION_ID, "affectedversionfield");
        fieldMap.put(Field.DESCRIPTION_ID, "descriptionfield");
        fieldMap.put(Field.ASSIGNEE_ID, "assigneefield");
        fieldMap.put(Field.ORGESTIMATE_ID, "orgestimatefield");
        fieldMap.put(Field.REMAININGESTIMATE_ID, "remainingestimatefield");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public View getView(String viewid) {
        View view = viewDao.findByName(viewid);
        for (ViewTab viewTab : view.getTabs()) {
            for (UiField field : viewTab.getFields()) {
                Field springBean = (Field) applicationContext.getBean(fieldMap.get(field.getFieldId()));
                field.setWicketField(springBean);
            }
        }
        return view;
    }
}
