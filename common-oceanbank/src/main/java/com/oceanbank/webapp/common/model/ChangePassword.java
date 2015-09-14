/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.common.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * The Class ChangePassword.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChangePassword {
	
	/** The username. */
	private String username;
	
	/** The old password. */
	private String oldPassword;
	
	/** The new password. */
	private String newPassword;
	
	/** The matching password. */
	private String matchingPassword;
	
	/**
	 * Instantiates a new change password.
	 */
	public ChangePassword(){}
	
	/**
	 * Instantiates a new change password.
	 *
	 * @param username the username
	 * @param oldPassword the old password
	 * @param newPassword the new password
	 */
	public ChangePassword(String username, String oldPassword, String newPassword){
		this.username = username;
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
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
	 * Gets the new password.
	 *
	 * @return the new password
	 */
	public String getNewPassword() {
		return newPassword;
	}
	
	/**
	 * Gets the old password.
	 *
	 * @return the old password
	 */
	public String getOldPassword() {
		return oldPassword;
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
	 * Sets the new password.
	 *
	 * @param newPassword the new new password
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	/**
	 * Sets the old password.
	 *
	 * @param oldPassword the new old password
	 */
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
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
