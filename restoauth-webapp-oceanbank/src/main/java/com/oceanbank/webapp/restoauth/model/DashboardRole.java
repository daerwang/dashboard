/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.restoauth.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.oceanbank.webapp.restoauth.listener.AbstractAuditEntityTimestamp;


/**
 * The Class DashboardRole.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@Entity
@Table(name = "DashboardRole")
public class DashboardRole extends AbstractAuditEntityTimestamp implements java.io.Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The role id. */
	private int roleId;
	
	/** The users. */
	private DashboardUser users;
	
	/** The role name. */
	private String roleName;
	
	/** The createdby. */
	private String createdby;
	
	/** The modifiedby. */
	private String modifiedby;

	/**
	 * Instantiates a new dashboard role.
	 */
	public DashboardRole() {
	}

	/**
	 * Instantiates a new dashboard role.
	 *
	 * @param roleName the role name
	 * @param users the users
	 */
	public DashboardRole(String roleName, DashboardUser users) {
		this.roleName = roleName;
		this.users = users;
	}
	
	/**
	 * Instantiates a new dashboard role.
	 *
	 * @param roleName the role name
	 * @param users the users
	 * @param modifiedBy the modified by
	 */
	public DashboardRole(String roleName, DashboardUser users, String modifiedBy) {
		this.roleName = roleName;
		this.users = users;
		this.modifiedby = modifiedBy;
	}
	
	/**
	 * Instantiates a new dashboard role.
	 *
	 * @param roleName the role name
	 * @param users the users
	 * @param createdBy the created by
	 * @param modifiedBy the modified by
	 */
	public DashboardRole(String roleName, DashboardUser users, String createdBy, String modifiedBy) {
		this.roleName = roleName;
		this.users = users;
		this.createdby = createdBy;
		this.modifiedby = modifiedBy;
	}

	/**
	 * Instantiates a new dashboard role.
	 *
	 * @param roleName the role name
	 */
	public DashboardRole(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * Instantiates a new dashboard role.
	 *
	 * @param roleId the role id
	 * @param users the users
	 * @param roleName the role name
	 */
	public DashboardRole(int roleId, DashboardUser users, String roleName) {
		this.roleId = roleId;
		this.users = users;
		this.roleName = roleName;
	}

	/**
	 * Gets the role id.
	 *
	 * @return the role id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ROLE_ID", unique = true, nullable = false)
	public int getRoleId() {
		return this.roleId;
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
	 * Gets the users.
	 *
	 * @return the users
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", nullable = false)
	public DashboardUser getUsers() {
		return this.users;
	}

	/**
	 * Sets the users.
	 *
	 * @param users the new users
	 */
	public void setUsers(DashboardUser users) {
		this.users = users;
	}

	/**
	 * Gets the role name.
	 *
	 * @return the role name
	 */
	@Column(name = "ROLE_NAME", nullable = false, length = 45)
	public String getRoleName() {
		return this.roleName;
	}

	/**
	 * Sets the role name.
	 *
	 * @param roleName the new role name
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * Gets the createdby.
	 *
	 * @return the createdby
	 */
	@Column(name = "CREATEDBY")
	public String getCreatedby() {
		return this.createdby;
	}
	
	/**
	 * Sets the createdby.
	 *
	 * @param createdby the new createdby
	 */
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	
	/**
	 * Gets the modifiedby.
	 *
	 * @return the modifiedby
	 */
	@Column(name = "MODIFIEDBY")
	public String getModifiedby() {
		return this.modifiedby;
	}

	/**
	 * Sets the modifiedby.
	 *
	 * @param modifiedby the new modifiedby
	 */
	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
	}
}
