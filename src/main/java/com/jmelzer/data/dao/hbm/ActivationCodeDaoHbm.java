/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 27.05.12 
*
*/


package com.jmelzer.data.dao.hbm;

import com.jmelzer.data.dao.ActivationCodeDao;
import com.jmelzer.data.model.ActivationCode;
import org.springframework.stereotype.Repository;

@Repository("activationCodeDao")
public class ActivationCodeDaoHbm extends AbstractDaoHbm<ActivationCode> implements ActivationCodeDao {

}
