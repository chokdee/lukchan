/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 27.05.12 
*
*/


package com.jmelzer.data.dao.hbm;

import com.jmelzer.data.dao.UserRoleDao;
import com.jmelzer.data.model.UserRole;
import org.springframework.stereotype.Repository;

@Repository("userRoleDao")
public class UserRoleDaoHbm extends AbstractDaoHbm<UserRole> implements UserRoleDao {


}
