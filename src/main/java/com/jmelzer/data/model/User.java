/*
 * Copyright 2002-2005 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jmelzer.data.model;

import com.jmelzer.data.model.ui.SelectOptionI;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Standard User entity with attributes such as name, password etc.
 *
 * We also tie in to the security framework and implement
 * the Acegi UserDetails interface so that Acegi can take care
 * of Authentication and Authorization
 */
@Entity
@Table(name = "user")
@Inheritance (strategy=InheritanceType.TABLE_PER_CLASS)
public class User extends ModelBase implements UserDetails, Serializable, Comparable<User>, SelectOptionI {
    
    private static final long serialVersionUID = -2388299912396255263L;

    private Integer type;
    private String loginName;
    private String name;
    private String password;
    private String email;
    private String locale;
    private boolean locked = true;
    byte[] avatar;

    private Set<Issue> assignedIssues = new LinkedHashSet<Issue>();
    private Set<UserRole> roles = new LinkedHashSet<UserRole>();

    Collection<GrantedAuthority> authorities;

    public User() {
    }

    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    //============ ACEGI UserDetails implementation ===============

    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }
    @Transient
    public boolean isAccountNonLocked() {
        return !isLocked();
    }
    @Transient
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Transient
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Transient
    public boolean isEnabled() {
        return true;
    }
    @Transient
    public String getUsername() {
        return getLoginName();
    }

    //=============================================================

    @Column(nullable = false)
    public String getPassword() {
        return password;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    @Column(nullable = false)
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getLocale() {
        return locale;
    }
    
    public void setLocale(String locale) {
        this.locale = locale;
    }    
    
    public boolean isLocked() {
        return locked;
    }
    
    public void setLocked(boolean locked) {
        this.locked = locked;
    }
    public Integer getType() {
        return type;
    }
    
    public void setType(Integer type) {
        this.type = type;
    }
    @Column(nullable = false)
    public String getLoginName() {
        return loginName;
    }
    
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
    

    public int compareTo(User u) {
        if(u == null) {
            return 1;
        }
        if(u.name == null) {
            if(name == null) {
                return 0;
            }
            return 1;            
        }
        if(name == null) {
            return -1;
        }
        return name.compareTo(u.name);
    }     
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        final User u = (User) o;
        return u.getLoginName().equals(loginName);
    }
    
    @Override
    public int hashCode() {
        if(loginName == null) {
            return 0;
        }
        return loginName.hashCode();
    }

    @OneToMany(cascade = {CascadeType.DETACH}, mappedBy = "assignee")
    public Set<Issue> getAssignedIssues() {
        return assignedIssues;
    }

    public void setAssignedIssues(Set<Issue> assignedIssues) {
        this.assignedIssues = assignedIssues;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("User");
        sb.append("{type=").append(type);
        sb.append(", loginName='").append(loginName).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", locale='").append(locale).append('\'');
        sb.append(", locked=").append(locked);
        sb.append(", assignedIssues=").append(assignedIssues);
        sb.append(", authorities=").append(authorities);
        sb.append('}');
        return sb.toString();
    }

    @Override
    @Transient
    public Long getKeyForOption() {
        return getId();
    }

    @Override
    @Transient
    public String getValueForOption() {
        return getName();
    }

    @Column( name = "avatar", nullable = false)
    @Lob
    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    @OneToMany
    @JoinTable(name = "USER_TO_ROLES", joinColumns = @JoinColumn(name = "USER_ID"),
               inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

    public void addRole(UserRole userRole) {
        roles.add(userRole);
    }
    @Transient
    public boolean isAdmin() {
        for (UserRole role : roles) {
            if (role.getName().equals(UserRole.Roles.ROLE_ADMIN.name())){
                return true;
            }
        }
        return false;
    }
    @Transient
    public void fillAuthorities() {
        GrantedAuthority[] authorities = new GrantedAuthority[roles.size()];
        int i = 0;
        for (UserRole role : roles) {
            authorities[i++] = new SimpleGrantedAuthority(role.getName());
        }
        setAuthorities(CollectionUtils.arrayToList(authorities));
    }
}
