/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.oceanbank.webapp.common.model.Roles;



/**
 * The Class Users.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
public class Users implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The user id. */
	private int userId;
	
	/** The username. */
	private String username;
	
	/** The password. */
	private String password;
	
	/** The firstname. */
	private String firstname;
	
	/** The lastname. */
	private String lastname;
	
	/** The email. */
	private String email;
	
	/** The enabled. */
	private int enabled;
	
	/** The roleses. */
	private List<Roles> roleses = new ArrayList<Roles>();
	
	/**
	 * Instantiates a new users.
	 */
	public Users() {
	}

	/**
	 * Instantiates a new users.
	 *
	 * @param userId the user id
	 */
	public Users(int userId) {
		this.userId = userId;
	}
	
	/**
	 * Instantiates a new users.
	 *
	 * @param username the username
	 * @param password the password
	 */
	public Users(String username, String password) {
		this.username = username;
		this.password = password;
	}

	/**
	 * Instantiates a new users.
	 *
	 * @param userId the user id
	 * @param username the username
	 * @param password the password
	 * @param enabled the enabled
	 */
	public Users(int userId, String username, String password, int enabled) {
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
	 * Gets the enabled.
	 *
	 * @return the enabled
	 */
	public int getEnabled() {
		return enabled;
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
	public List<Roles> getRoleses() {
		return roleses;
	}

	/**
	 * Sets the roleses.
	 *
	 * @param roleses the new roleses
	 */
	public void setRoleses(List<Roles> roleses) {
		this.roleses = roleses;
	}
}
