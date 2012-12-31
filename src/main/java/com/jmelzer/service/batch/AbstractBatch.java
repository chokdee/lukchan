/*
 * Copyright (c) jmelzer 2011.
 * All rights reserved.
 */

package com.jmelzer.service.batch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author jmelzer
 *         todo
 */
public abstract class AbstractBatch {
    private static Log log = LogFactory.getLog(AbstractBatch.class);

    ApplicationContext context;

    protected String[] getConfigLocations() {
        return new String[]{
                "classpath*:spring.xml",
        };
    }

    protected void run() {
        if (context == null) {
            context = new ClassPathXmlApplicationContext(getConfigLocations());
        }

        try {
            final JpaTransactionManager transactionManager = (JpaTransactionManager) context.getBean("txManager");
            TransactionTemplate txTmpl = new TransactionTemplate(transactionManager);
            txTmpl.execute(new TransactionCallback<Object>() {
                public Object doInTransaction(TransactionStatus status) {
                    try {
                        doIt();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            });

        }
        catch (Exception e) {
            log.error("runit", e);


        }
    }

    abstract void doIt() throws Exception;



}