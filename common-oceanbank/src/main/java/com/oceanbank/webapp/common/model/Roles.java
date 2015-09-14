/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.common.model;

import java.io.Serializable;

import com.oceanbank.webapp.common.model.Users;



/**
 * The Class Roles.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
public class Roles implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The role id. */
	private int roleId;
	
	/** The users. */
	private Users users;
	
	/** The role name. */
	private String roleName;
	
	
	/**
	 * Gets the role id.
	 *
	 * @return the role id
	 */
	public int getRoleId() {
		return roleId;
	}
	
	/**
	 * Gets the users.
	 *
	 * @return the users
	 */
	public Users getUsers() {
		return users;
	}
	
	/**
	 * Gets the role name.
	 *
	 * @return the role name
	 */
	public String getRoleName() {
		return roleName;
	}
	
	/**
	 * Sets the role id.
	 *
	 * @param roleId the new role id
	 */
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	/**
	 * Sets the users.
	 *
	 * @param users the new users
	 */
	public void setUsers(Users users) {
		this.users = users;
	}
	
	/**
	 * Sets the role name.
	 *
	 * @param roleName the new role name
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}
