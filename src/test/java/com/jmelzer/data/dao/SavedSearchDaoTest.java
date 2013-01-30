/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 27.05.12 
*
*/


package com.jmelzer.data.dao;

import com.jmelzer.data.model.Label;
import com.jmelzer.data.model.SavedSearch;
import com.jmelzer.data.model.User;
import org.junit.Test;

import javax.annotation.Resource;
import javax.persistence.PersistenceException;

import static junit.framework.Assert.*;


public class SavedSearchDaoTest extends AbstractBaseDaoTest{

    @Resource
    SavedSearchDao savedSearchDao;
    @Resource
    UserDao userDao;

    @Test
    public void saveFailed() {
        SavedSearch savedSearch = new SavedSearch();

        try {
            savedSearchDao.save(savedSearch);
            fail("mandatory fields not set");
        } catch (PersistenceException e) {
            //ok
        }


    }
    @Test
    public void save() {
        User user = DaoUtil.createUser("1", userDao);

        SavedSearch savedSearch = new SavedSearch("myname", "where bla = 'blub'", user);
        savedSearchDao.save(savedSearch);
        assertNotNull(savedSearch.getId());

    }
    @Test
    public void findByName() {
        User user = DaoUtil.createUser("1", userDao);
        SavedSearch savedSearch = new SavedSearch("myname", "where bla = 'blub'", user);
        savedSearchDao.save(savedSearch);

        assertNotNull(savedSearchDao.findByName("myname"));
    }
    @Test
    public void findByUsername() {
        User user = DaoUtil.createUser("1", userDao);
        SavedSearch savedSearch = new SavedSearch("myname", "where bla = 'blub'", user);
        savedSearchDao.save(savedSearch);

        assertEquals(0, savedSearchDao.findByUserName("fsdkf").size());
        assertEquals(1, savedSearchDao.findByUserName("1").size());
    }
}
