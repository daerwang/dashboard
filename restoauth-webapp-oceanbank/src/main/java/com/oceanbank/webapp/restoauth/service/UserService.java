/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.restoauth.service;

import java.util.List;

import com.oceanbank.webapp.common.model.UserResponse;
import com.oceanbank.webapp.restoauth.model.DashboardUser;

/**
 * The Interface UserService.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
public interface UserService {
	
	
	/**
	 * Find users containing.
	 *
	 * @param username the username
	 * @return the list
	 */
	List<DashboardUser> findUsersContaining(String username);
	
	/**
	 * Find by user id is.
	 *
	 * @param user_id the user_id
	 * @return the dashboard user
	 */
	DashboardUser findByUserIdIs(Integer user_id);
	
	/**
	 * Find all users.
	 *
	 * @return the list
	 */
	List<DashboardUser> findAllUsers();
	
	/**
	 * Delete user.
	 *
	 * @param user_id the user_id
	 */
	void deleteUser(Integer user_id);
	
	/**
	 * Update username.
	 *
	 * @param user_id the user_id
	 * @param username the username
	 */
	void updateUsername(Integer user_id, String username);
	
	/**
	 * Find user by username.
	 *
	 * @param username the username
	 * @return the dashboard user
	 */
	DashboardUser findUserByUsername(String username);
	
	/**
	 * Find user with roles.
	 *
	 * @param user_id the user_id
	 * @return the dashboard user
	 */
	DashboardUser findUserWithRoles(Integer user_id);
	
	/**
	 * Find user with roles.
	 *
	 * @param username the username
	 * @return the dashboard user
	 */
	DashboardUser findUserWithRoles(String username);
	
	/**
	 * Find user with search.
	 *
	 * @param searchParameter the search parameter
	 * @return the list
	 */
	List<DashboardUser> findUserWithSearch(String searchParameter);
	
	/**
	 * Update user.
	 *
	 * @param response the response
	 * @return the dashboard user
	 */
	DashboardUser updateUser(UserResponse response);
	
	/**
	 * Creates the user.
	 *
	 * @param response the response
	 * @return the user response
	 */
	UserResponse createUser(UserResponse response);
	
	/**
	 * Change user password.
	 *
	 * @param username the username
	 * @param oldPassword the old password
	 * @param newPassword the new password
	 * @return the dashboard user
	 */
	DashboardUser changeUserPassword(String username, String oldPassword, String newPassword);
	
}
