/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.common.model;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


/**
 * The Class UserResponse.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponse {
	
	/** The user id. */
	private int userId;
	
	/** The username. */
	private String username;
    
    /** The email. */
    private String email;
    
    /** The password. */
    private String password;
    
    /** The firstname. */
    private String firstname;
	
	/** The lastname. */
	private String lastname;
	
	private String iseriesname;
    
    /** The roleses. */
    private List<RoleResponse> roleses;
    
    /** The role names. */
    private String[] roleNames;

    private String createdby;

	private String modifiedby;
	
	private Integer accountNonLocked;

	private Integer accountNonExpired;
	
	private String resetToken;

	private Date createdon;
	private Date modifiedon;

	/**
	 * Instantiates a new user response.
	 */
	public UserResponse(){}
    
    public UserResponse(String username){
    	this.username = username;
    }
    
	/**
	 * Gets the roleses.
	 *
	 * @return the roleses
	 */
	public List<RoleResponse> getRoleses() {
		return roleses;
	}
	
	/**
	 * Sets the roleses.
	 *
	 * @param roleses the new roleses
	 */
	public void setRoleses(List<RoleResponse> roleses) {
		this.roleses = roleses;
	}
	
	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
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
	 * Gets the email.
	 *
	 * @return the email
	 */
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
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
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
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public int getUserId() {
		return userId;
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
	 * Gets the firstname.
	 *
	 * @return the firstname
	 */
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
	 * Gets the role names.
	 *
	 * @return the role names
	 */
	public String[] getRoleNames() {
		return roleNames;
	}
	
	/**
	 * Sets the role names.
	 *
	 * @param roleNames the new role names
	 */
	public void setRoleNames(String[] roleNames) {
		this.roleNames = roleNames;
	}
    
	/**
	 * Gets the createdby.
	 *
	 * @return the createdby
	 */
	public String getCreatedby() {
		return createdby;
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
	public String getModifiedby() {
		return modifiedby;
	}

	/**
	 * Sets the modifiedby.
	 *
	 * @param modifiedby the new modifiedby
	 */
	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
	}

	public String getIseriesname() {
		return iseriesname;
	}

	public void setIseriesname(String iseriesname) {
		this.iseriesname = iseriesname;
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

	public Date getCreatedon() {
		return createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	public Date getModifiedon() {
		return modifiedon;
	}

	public void setModifiedon(Date modifiedon) {
		this.modifiedon = modifiedon;
	}

	public String getResetToken() {
		return resetToken;
	}

	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}
}
