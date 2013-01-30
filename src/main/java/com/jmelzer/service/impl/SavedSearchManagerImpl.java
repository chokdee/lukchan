/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 04.01.13 
*
*/


package com.jmelzer.service.impl;

import com.jmelzer.data.dao.IssueTypeDao;
import com.jmelzer.data.dao.SavedSearchDao;
import com.jmelzer.data.dao.UserDao;
import com.jmelzer.data.model.IssueType;
import com.jmelzer.data.model.SavedSearch;
import com.jmelzer.data.model.User;
import com.jmelzer.service.IssueTypeManager;
import com.jmelzer.service.SavedSearchManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("savedSearchManager")
public class SavedSearchManagerImpl implements SavedSearchManager {

    @Resource
    SavedSearchDao savedSearchDao;

    @Override
    @Transactional(readOnly = false)
    public void save(SavedSearch savedSearch) {
        savedSearchDao.save(savedSearch);
    }

    @Override
    @Transactional(readOnly = true)
    public SavedSearch getByName(String name) {
        return savedSearchDao.findByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SavedSearch> getAllByUser(String username) {
        return savedSearchDao.findByUserName(username);
    }
}
