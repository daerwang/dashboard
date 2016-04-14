/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.restoauth.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToMany;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.oceanbank.webapp.restoauth.listener.AbstractAuditEntityTimestamp;


/**
 * The Class DashboardUser.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@Entity
@Table(name = "DashboardUser")
@NamedStoredProcedureQuery(name = "User.plus1", procedureName = "allen.plus1inout", parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "arg", type = Integer.class),
		@StoredProcedureParameter(mode = ParameterMode.OUT, name = "res", type = Integer.class) })
public class DashboardUser extends AbstractAuditEntityTimestamp implements java.io.Serializable {


	private static final long serialVersionUID = 1L;
	

	private int userId;
	private String username;
	private String password;
	private String firstname;
	private String lastname;
	private String iseriesname;
	private String email;
	private int enabled;
	
	@Column(name = "accountNonLocked")
	private Integer accountNonLocked;

	@Column(name = "accountNonExpired")
	private Integer accountNonExpired;

	private List<DashboardRole> roleses = new ArrayList<DashboardRole>();
	private String createdby;
	private String modifiedby;
	
	@Column(name = "resetToken")
	private String resetToken;
	
	@Column(name = "resetExpiry")
	private Date resetExpiry;
	
	@Transient
	private String newPassword;

	/**
	 * Instantiates a new dashboard user.
	 */
	public DashboardUser() {
	}

	/**
	 * Instantiates a new dashboard user.
	 *
	 * @param userId the user id
	 */
	public DashboardUser(int userId) {
		this.userId = userId;
	}

	/**
	 * Instantiates a new dashboard user.
	 *
	 * @param username the username
	 * @param password the password
	 */
	public DashboardUser(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	/**
	 * Instantiates a new dashboard user.
	 *
	 * @param username the username
	 * @param password the password
	 * @param email the email
	 */
	public DashboardUser(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
	}

	/**
	 * Instantiates a new dashboard user.
	 *
	 * @param userId the user id
	 * @param username the username
	 * @param password the password
	 * @param enabled the enabled
	 */
	public DashboardUser(int userId, String username, String password, int enabled) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.enabled = enabled;

	}

	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID", unique = true, nullable = false)
	public int getUserId() {
		return this.userId;
	}

	/**
	 * Sets the user id.
	 *
	 * @param userId the new user id
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	@Column(name = "USERNAME")
	public String getUsername() {
		return this.username;
	}

	/**
	 * Sets the username.
	 *
	 * @param username the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	@Column(name = "PASSWORD")
	public String getPassword() {
		return this.password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the enabled.
	 *
	 * @return the enabled
	 */
	@Column(name = "ENABLED", nullable = false)
	public int getEnabled() {
		return this.enabled;
	}

	/**
	 * Sets the enabled.
	 *
	 * @param enabled the new enabled
	 */
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	/**
	 * Gets the roleses.
	 *
	 * @return the roleses
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
	public List<DashboardRole> getRoleses() {
		return roleses;
	}

	/**
	 * Sets the roleses.
	 *
	 * @param roleses the new roleses
	 */
	public void setRoleses(List<DashboardRole> roleses) {
		this.roleses = roleses;
	}

	/**
	 * Gets the firstname.
	 *
	 * @return the firstname
	 */
	@Column(name = "FIRSTNAME")
	public String getFirstname() {
		return firstname;
	}

	/**
	 * Sets the firstname.
	 *
	 * @param firstname the new firstname
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * Gets the lastname.
	 *
	 * @return the lastname
	 */
	@Column(name = "LASTNAME")
	public String getLastname() {
		return lastname;
	}

	/**
	 * Sets the lastname.
	 *
	 * @param lastname the new lastname
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
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

	@Column(name = "ISERIESNAME")
	public String getIseriesname() {
		return iseriesname;
	}

	public void setIseriesname(String iseriesname) {
		this.iseriesname = iseriesname;
	}

	@Transient
	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public Integer getAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(Integer accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public Integer getAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(Integer accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public String getResetToken() {
		return resetToken;
	}

	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}

	public Date getResetExpiry() {
		return resetExpiry;
	}

	public void setResetExpiry(Date resetExpiry) {
		this.resetExpiry = resetExpiry;
	}

}
