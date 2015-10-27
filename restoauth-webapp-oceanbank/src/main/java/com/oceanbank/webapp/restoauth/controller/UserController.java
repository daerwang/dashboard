/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.restoauth.controller;


import com.oceanbank.webapp.common.model.BootstrapValidatorResponse;
import com.oceanbank.webapp.common.model.ChangePassword;
import com.oceanbank.webapp.common.model.DataTablesRequest;
import com.oceanbank.webapp.common.model.UserResponse;
import com.oceanbank.webapp.common.model.RestWebServiceUrl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.oceanbank.webapp.restoauth.converter.UserConverter;
import com.oceanbank.webapp.restoauth.model.DashboardRole;
import com.oceanbank.webapp.restoauth.model.DashboardUser;
import com.oceanbank.webapp.restoauth.service.RoleServiceImpl;
import com.oceanbank.webapp.restoauth.service.UserServiceImpl;

/**
 * The Class UserController.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@RestController
public class UserController {
	
	/**
	 * Instantiates a new user controller.
	 */
	public UserController(){}

	/** The userservice. */
	@Autowired
	private UserServiceImpl userservice;
	
	/** The roleservice. */
	@Autowired
	private RoleServiceImpl roleservice;
	
	/** The logger. */
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	
	/**
	 * Change user password.
	 *
	 * @param changePassword the change password
	 * @return the user response
	 */
	@RequestMapping(value = RestWebServiceUrl.EXECUTE_CHANGE_PASSWORD, method = RequestMethod.PUT)
	public UserResponse changeUserPassword(@RequestBody ChangePassword changePassword){
	
		final String username = changePassword.getUsername();
		final String oldPassword = changePassword.getOldPassword();
		final String newPassword = changePassword.getNewPassword();
		
		LOGGER.info("Changing Password for Username: " + username);
		
		final DashboardUser user = userservice.changeUserPassword(username, oldPassword, newPassword);
		
		if(user == null){
			return new UserResponse();
		}
		
		final UserResponse result = UserConverter.convertFromEntity(user);
		
		return result;
	}
	
	/**
	 * Gets the user by search on datatables.
	 *
	 * @param datatableRequest the datatable request
	 * @return the user by search on datatables
	 */
	@RequestMapping(value = RestWebServiceUrl.GET_USER_SEARCH_BY_DATATABLE, method = RequestMethod.POST)
	public List<UserResponse> getUserBySearchOnDatatables(@RequestBody DataTablesRequest datatableRequest){
	
		Integer pageLength = 0;
    	Integer pageNumber = 0;
		
    	final String searchParameter = datatableRequest.getValue();
    	pageLength = datatableRequest.getLength();
    	pageNumber = datatableRequest.getStart();
		
		LOGGER.info("Getting Datatable request for page length " + pageLength + " and under page number " + pageNumber + ".");
		
		
		final List<UserResponse> userResponseList = new ArrayList<UserResponse>();
		
		List<DashboardUser> userList = userservice.findUserWithSearch(searchParameter);
		
		for(DashboardUser u: userList){
			final UserResponse response = new UserResponse();
			response.setUserId(u.getUserId());
			response.setFirstname(u.getFirstname());
			response.setLastname(u.getLastname());
			response.setUsername(u.getUsername());
			response.setPassword(u.getPassword());
			response.setEmail(u.getEmail());
			userResponseList.add(response);
		}
		
		userList = null;
		
		return userResponseList;
	}
	
	/**
	 * Gets the user by username using bootstrap validator.
	 *
	 * @param userResponse the user response
	 * @return the user by username using bootstrap validator
	 */
	@RequestMapping(value = RestWebServiceUrl.GET_USER_USERNAME_BY_BOOTSTRAP_VALIDATOR, method = RequestMethod.POST)
	public BootstrapValidatorResponse getUserByUsernameUsingBootstrapValidator(@RequestBody UserResponse userResponse){
		final String username = userResponse.getUsername();
		
		LOGGER.info("Getting User with username = " + username);
		
		final DashboardUser user = userservice.findUserByUsername(username);
		
		final BootstrapValidatorResponse response = new BootstrapValidatorResponse();
		
		if(user != null){
			response.setValid(false);
		}else{
			response.setValid(true);
		}
		
		return response;
	}
	
	/**
	 * Gets the user by username using bootstrap validator original.
	 *
	 * @param username the username
	 * @param userResponse the user response
	 * @return the user by username using bootstrap validator original
	 */
	@RequestMapping(value = RestWebServiceUrl.GET_USER_USERNAME_BY_BOOTSTRAP_VALIDATOR_ORIGINAL, method = RequestMethod.POST)
	public BootstrapValidatorResponse getUserByUsernameUsingBootstrapValidatorOriginal(@PathVariable("originalUsername") String username, @RequestBody UserResponse userResponse){
		final String newUsername = userResponse.getUsername();
		
		LOGGER.info("Getting User with New username = " + newUsername);
		
		final BootstrapValidatorResponse response = new BootstrapValidatorResponse();
		if(newUsername.toLowerCase().trim().equalsIgnoreCase(username.toLowerCase().trim())){
			response.setValid(true);
			return response;
		}
		
		final DashboardUser user = userservice.findUserByUsername(newUsername);
		
		if(user != null){
			response.setValid(false);
		}else{
			response.setValid(true);
		}
		
		return response;
	}
	
	/**
	 * Gets the user by username.
	 *
	 * @param username the username
	 * @return the user by username
	 */
	@RequestMapping(value = RestWebServiceUrl.GET_USER_USERNAME, method = RequestMethod.GET)
	public UserResponse getUserByUsername(@PathVariable("username") String username){
	
		LOGGER.info("Getting User with username = " + username);
		
		final DashboardUser user = userservice.findUserWithRoles(username);
		
		final UserResponse response = UserConverter.convertFromEntity(user);
		
		return response;
	}
	
	
	/**
	 * Gets the user.
	 *
	 * @param user_id the user_id
	 * @return the user
	 */
	@RequestMapping(value = RestWebServiceUrl.GET_USER, method = RequestMethod.GET)
	public UserResponse getUser(@PathVariable("userid") Integer userId){
	
		LOGGER.info("Getting User with user_id = " + userId);
		
		final DashboardUser user = userservice.findUserWithRoles(userId);
		
		
		final UserResponse response = UserConverter.convertFromEntity(user);
		
		return response;
	}
	
	/**
	 * Delete user.
	 *
	 * @param user_id the user_id
	 * @return the string
	 */
	@RequestMapping(value = RestWebServiceUrl.DELETE_USER, method = RequestMethod.DELETE)
	public String deleteUser(@PathVariable("userid") int userId){
	
		LOGGER.info("Deleting User with user_id = " + userId);
		
		userservice.deleteUser(userId);
		
		return "The User with Id " + userId + " is deleted.";
	}
	
	/**
	 * Update user.
	 *
	 * @param userResponse the user response
	 * @return the user response
	 */
	@RequestMapping(value = RestWebServiceUrl.UPDATE_USER, method = RequestMethod.PUT)
	public UserResponse updateUser(@RequestBody UserResponse userResponse){
		
		final DashboardUser user = userservice.updateUser(userResponse);
		
		// for lazily loaded User, ensure that Roles are included before converting to bean
		final List<DashboardRole> entityRoles = roleservice.getRoleByUserId(user.getUserId());
		user.setRoleses(entityRoles);
		
		
		UserResponse result = null;
		result = UserConverter.convertFromEntity(user);
		
		return result;
	}
	
	/**
	 * Gets the all user.
	 *
	 * @return the all user
	 */
	@RequestMapping(value = RestWebServiceUrl.GET_ALL_USER, method = RequestMethod.GET)
	public List<UserResponse> getAllUser(){
	
		LOGGER.info("Getting all Users");
		
		final List<DashboardUser> userList = userservice.findAllUsers();
		final List<UserResponse> userResponseList = new ArrayList<UserResponse>();
		
		for(DashboardUser user: userList){
			final UserResponse response = UserConverter.convertFromEntity(user);
			userResponseList.add(response);
		}
		
		
		return userResponseList;
	}
	
	/**
	 * Creates the user.
	 *
	 * @param userResponse the user response
	 * @return the user response
	 */
	@RequestMapping(value = RestWebServiceUrl.CREATE_USER, method = RequestMethod.POST)
	public UserResponse createUser(@RequestBody UserResponse userResponse){
		final UserResponse response = userservice.createUser(userResponse);
		
		return response;
	}
	
	
	
	
	
	
	
	
	
	/**
	 * Handle server errors.
	 *
	 * @param ex the ex
	 * @return the string
	 */
	@ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleServerErrors(Exception ex) {
        LOGGER.error("UserRestController Message 1 " + ex.getMessage());
        
        return ex.getMessage();
    }
	 
    
}