/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.common.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


/**
 * The Class RoleResponse.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleResponse {
	
	/** The roleid. */
	private int roleid;
	
	/** The userid. */
	private int userid;
	
	/** The role name. */
	private String roleName;
	
	/**
	 * Instantiates a new role response.
	 */
	public RoleResponse(){}
	
	
	/**
	 * Instantiates a new role response.
	 *
	 * @param roleid the roleid
	 * @param userid the userid
	 * @param roleName the role name
	 */
	public RoleResponse(int roleid, int userid, String roleName){
		this.roleid = roleid;
		this.userid = userid;
		this.roleName = roleName;
	}
	
	/**
	 * Gets the roleid.
	 *
	 * @return the roleid
	 */
	public int getRoleid() {
		return roleid;
	}
	
	/**
	 * Sets the roleid.
	 *
	 * @param roleid the new roleid
	 */
	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}
	
	/**
	 * Gets the userid.
	 *
	 * @return the userid
	 */
	public int getUserid() {
		return userid;
	}
	
	/**
	 * Sets the userid.
	 *
	 * @param userid the new userid
	 */
	public void setUserid(int userid) {
		this.userid = userid;
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
	 * Sets the role name.
	 *
	 * @param roleName the new role name
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
