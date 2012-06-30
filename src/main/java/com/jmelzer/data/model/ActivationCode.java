/* 
* Copyright (C) allesklar.com AG
* All rights reserved.
*
* Author: juergi
* Date: 27.05.12 
*
*/


package com.jmelzer.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "activation_code")
/**
 * class for registration process.
 */
public class ActivationCode extends ModelBase {

    private static final long serialVersionUID = -258649769594791305L;

    String activationcode;
    Long userId;
    Date expireDate;
    Boolean used;
    String type;
    public static final String REGISTER = "R";
    public static final String PW = "P";

    public ActivationCode() {
    }

    public ActivationCode(String activationcode, String type) {
        this.activationcode = activationcode;
        this.type = type;
    }
    @Column(name = "activation_code" , nullable = false)
    public String getActivationcode() {
        return activationcode;
    }

    public void setActivationcode(String activationcode) {
        this.activationcode = activationcode;
    }

    @Column(name = "user_id" , nullable = false)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "expire_date" , nullable = false)
    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }
    @Column(name = "used" , nullable = false)
    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }
    @Column(name = "type" , nullable = false)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
