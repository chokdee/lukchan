/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 04.01.13 
*
*/


package com.jmelzer.service;

import com.jmelzer.data.model.SavedSearch;

import java.util.List;

public interface SavedSearchManager {
    void save(SavedSearch savedSearch);
    SavedSearch getByName(String name);
    List<SavedSearch> getAllByUser(String username);
}
