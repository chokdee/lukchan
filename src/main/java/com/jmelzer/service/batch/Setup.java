/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 30.05.12 
*
*/


package com.jmelzer.service.batch;

import com.jmelzer.data.dao.*;
import com.jmelzer.data.model.*;
import com.jmelzer.data.model.ui.UiField;
import com.jmelzer.data.model.ui.View;
import com.jmelzer.data.model.ui.ViewTab;
import com.jmelzer.data.uimodel.Field;
import com.jmelzer.data.util.StreamUtils;
import com.jmelzer.service.IssueManager;
import com.jmelzer.service.impl.EasyDbSetup;
import org.apache.commons.io.FileUtils;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import java.io.File;
import java.io.IOException;

public class Setup extends AbstractBatch {

    public static void main(String[] args) {
        //delete index directory
        try {
            FileUtils.deleteDirectory(new File("c:/tmp/lukchan/indexes"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        Setup batch = new Setup();
        batch.run();
    }

    @Override
    void doIt() throws Exception {
        EasyDbSetup easyDbSetup = (EasyDbSetup) context.getBean("easyDbSetup");
        easyDbSetup.setup();
    }


}
