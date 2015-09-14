/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.common.model;

import com.google.gson.annotations.SerializedName;

/**
 * The Class UserDataTableResponse.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
public class UserDataTableResponse {
	
	@SerializedName("0")
	private String firstname;
	@SerializedName("1")
	private String lastname;
	@SerializedName("2")
	private String username;
	@SerializedName("3")
	private String password;
	@SerializedName("4")
	private String email;
    @SerializedName("DT_RowId")
	private String userId;
    
    
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
    
}
