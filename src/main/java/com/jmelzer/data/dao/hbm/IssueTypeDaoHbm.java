/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 27.05.12 
*
*/


package com.jmelzer.data.dao.hbm;

import com.jmelzer.data.dao.IssueTypeDao;
import com.jmelzer.data.model.IssueType;
import org.springframework.stereotype.Repository;

@Repository("issueTypeDao")
public class IssueTypeDaoHbm extends AbstractDaoHbm<IssueType> implements IssueTypeDao {

}
