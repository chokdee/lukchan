/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 27.05.12 
*
*/


package com.jmelzer.data.dao;

import com.jmelzer.data.model.SavedSearch;

import java.util.List;

public interface SavedSearchDao extends AbstractDao<SavedSearch> {

    SavedSearch findByName(String name);

    List<SavedSearch> findByUserName(String username);
}
