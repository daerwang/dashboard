/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.restoauth.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.oceanbank.webapp.common.handler.RestSqlException;
import com.oceanbank.webapp.common.model.RoleResponse;
import com.oceanbank.webapp.common.model.UserResponse;
import com.oceanbank.webapp.restoauth.model.DashboardRole;
import com.oceanbank.webapp.restoauth.model.DashboardUser;
import com.oceanbank.webapp.common.model.RestWebServiceUrl;
import com.oceanbank.webapp.restoauth.service.UserServiceImpl;


/**
 * The Class RoleController.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@RestController
public class RoleController {
	
	/**
	 * Instantiates a new role controller.
	 */
	public RoleController(){}
	
	/** The userservice. */
	@Autowired
	private UserServiceImpl userservice;
	
	/** The logger. */
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	/**
	 * Gets the user with roles.
	 *
	 * @param user_id the user_id
	 * @return the api response
	 */
	@RequestMapping(value = RestWebServiceUrl.GET_USER_WITH_ROLES, method = RequestMethod.GET)
	public UserResponse getUserWithRoles(@PathVariable("userid") int userId){
	
		LOGGER.info("Getting User with user_id = " + userId);
		
		DashboardUser user = new DashboardUser();
		
		try {
			user = userservice.findUserWithRoles(userId);
		} catch(RestSqlException e){
			LOGGER.info("There is probably no Role found with userid " + userId);
			throw new RestSqlException(e.getMessage());
		}catch (Exception e) {
			LOGGER.info(e.getMessage());
		}
		
		
		final UserResponse response = new UserResponse();
		response.setUsername(user.getUsername());
		response.setPassword(user.getPassword());
		response.setEmail(user.getEmail());
		
		final List<RoleResponse> roleResponseList = new ArrayList<RoleResponse>();
		for(DashboardRole role: user.getRoleses()){
			roleResponseList.add(new RoleResponse(role.getRoleId(), role.getUsers().getUserId(), role.getRoleName()));
		}
		response.setRoleses(roleResponseList);
		
		return response;
	}
	
	/**
	 * Gets the user with roles by username.
	 *
	 * @param username the username
	 * @return the api response
	 */
	@RequestMapping(value = RestWebServiceUrl.GET_USER_WITH_ROLES_BY_USERNAME, method = RequestMethod.GET)
	public UserResponse getUserWithRolesByUsername(@PathVariable("username") String username){
	
		LOGGER.info("Getting User and its Role with username = " + username);
		
		DashboardUser user = null;
		
		try {
			user = userservice.findUserWithRoles(username);
		} catch(RestSqlException e){
			LOGGER.info("There is probably no Role found with username " + username);
			throw new RestSqlException(e.getMessage());
		}catch (Exception e) {
			LOGGER.info(e.getMessage());
			throw new RestSqlException(e.getMessage());
		}
		
		
		final UserResponse response = new UserResponse();
		response.setUsername(user.getUsername());
		response.setPassword(user.getPassword());
		response.setEmail(user.getEmail());
		
		final List<RoleResponse> roleResponseList = new ArrayList<RoleResponse>();
		for(DashboardRole role: user.getRoleses()){
			roleResponseList.add(new RoleResponse(role.getRoleId(), role.getUsers().getUserId(), role.getRoleName()));
		}
		response.setRoleses(roleResponseList);
		
		return response;
	}
	
	/**
	 * Handle sql errors.
	 *
	 * @param ex the ex
	 * @return the string
	 */
	@ExceptionHandler(RestSqlException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleSqlErrors(RestSqlException ex) {
        LOGGER.error("Part 2 " + ex.getMessage());
        
        return ex.getMessage();
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
        LOGGER.error("Part 3 " + ex.getMessage());
        
        return ex.getMessage();
    }
}
