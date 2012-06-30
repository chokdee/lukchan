/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 27.05.12 
*
*/


package com.jmelzer.data.dao.hbm;

import com.jmelzer.data.dao.IssueDao;
import com.jmelzer.data.model.Issue;
import org.springframework.stereotype.Repository;

@Repository
public class IssueDaoHbm extends AbstractDaoHbm<Issue> implements IssueDao {

}
