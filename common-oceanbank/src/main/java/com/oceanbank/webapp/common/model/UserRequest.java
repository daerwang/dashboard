/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.common.model;

/**
 * The Class UserRequest.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
public class UserRequest {
	
	/** The username. */
	private String username;
	
	/** The email. */
	private String email;
	
	/** The password. */
	private String password;
	
	/** The matching password. */
	private String matchingPassword;
	
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
	 * Gets the matching password.
	 *
	 * @return the matching password
	 */
	public String getMatchingPassword() {
		return matchingPassword;
	}
	
	/**
	 * Sets the matching password.
	 *
	 * @param matchingPassword the new matching password
	 */
	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}
}
