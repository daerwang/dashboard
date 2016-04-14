/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.dashboard.service;

import java.util.List;

import com.oceanbank.webapp.common.handler.AjaxResponseHandler;
import com.oceanbank.webapp.common.model.BootstrapValidatorResponse;
import com.oceanbank.webapp.common.model.ChangePassword;
import com.oceanbank.webapp.common.model.DataTablesRequest;
import com.oceanbank.webapp.common.model.ObDashboardRoles;
import com.oceanbank.webapp.common.model.UserResponse;



/**
 * The Interface UserService.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
public interface UserService {
	
	/**
	 * Find user by username.
	 *
	 * @param username the username
	 * @return the user response
	 */
	UserResponse findUserByUsername(String username);
	
	/**
	 * Find user by username with bootstrap validator.
	 *
	 * @param username the username
	 * @return the bootstrap validator response
	 */
	BootstrapValidatorResponse findUserByUsernameWithBootstrapValidator(String username);
	
	/**
	 * Find user by username with bootstrap validator original.
	 *
	 * @param originalUsername the original username
	 * @param newUsername the new username
	 * @return the bootstrap validator response
	 */
	BootstrapValidatorResponse findUserByUsernameWithBootstrapValidatorOriginal(String originalUsername, String newUsername);
	
	/**
	 * Search user data table.
	 *
	 * @param datatableRequest the datatable request
	 * @return the list
	 */
	List<UserResponse> searchUserDataTable(DataTablesRequest datatableRequest);
	
	/**
	 * Find user by userid.
	 *
	 * @param user_id the user_id
	 * @return the user response
	 */
	UserResponse findUserByUserid(Integer user_id);
	
	/**
	 * Gets the ob dashboard roles.
	 *
	 * @param category the category
	 * @return the ob dashboard roles
	 */
	ObDashboardRoles getObDashboardRoles(String category);
	
	
	/**
	 * Update user.
	 *
	 * @param request the request
	 * @return the user response
	 */
	UserResponse updateUser(UserResponse request);
	
	/**
	 * Creates the user.
	 *
	 * @param request the request
	 * @return the user response
	 */
	UserResponse createUser(UserResponse request);
	
	/**
	 * Delete user.
	 *
	 * @param user_id the user_id
	 * @return the string
	 */
	String deleteUser(Integer user_id);
	
}
