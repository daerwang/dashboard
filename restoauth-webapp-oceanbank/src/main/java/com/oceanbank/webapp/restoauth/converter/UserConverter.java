/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.restoauth.converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oceanbank.webapp.common.model.RoleResponse;
import com.oceanbank.webapp.common.model.UserResponse;
import com.oceanbank.webapp.restoauth.model.DashboardRole;
import com.oceanbank.webapp.restoauth.model.DashboardUser;

/**
 * The Class UserConverter.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
public class UserConverter {
	
	/**
	 * Instantiates a new user converter.
	 */
	private UserConverter(){}
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(UserConverter.class);
	
	/**
	 * Convert from bean.
	 *
	 * @param response the response
	 * @return the dashboard user
	 */
	public static DashboardUser convertFromBean(UserResponse response){
		final DashboardUser user = new DashboardUser();
		user.setUserId(response.getUserId());
		user.setFirstname(response.getFirstname());
		user.setLastname(response.getLastname());
		user.setUsername(response.getUsername());
		user.setPassword(response.getPassword());
		user.setEmail(response.getEmail());
		user.setIseriesname(response.getIseriesname());
		user.setAccountNonLocked(response.getAccountNonLocked());

		user.setCreatedby(response.getCreatedby());
		user.setModifiedby(response.getModifiedby());
		
		List<String> roleNameList = null;
		Integer size = 0;
		try {
			roleNameList = Arrays.asList(response.getRoleNames());
			size = roleNameList.size(); 
		} catch (Exception e) {
			roleNameList = null;
			LOGGER.info(e.getMessage());
		}	
		
		final List<DashboardRole> roleList = new ArrayList<DashboardRole>();
		if(roleNameList != null && size > 0){
			for(String roleName : roleNameList){
				final DashboardRole role = new DashboardRole();
				role.setRoleName(roleName);
				roleList.add(role);
			}
		}
		
		user.setRoleses(roleList);		
		
		return user;
	}
	
	/**
	 * Convert from entity.
	 *
	 * @param user the user
	 * @return the user response
	 */
	public static UserResponse convertFromEntity(DashboardUser user){
		final UserResponse response = new UserResponse();
		response.setUserId(user.getUserId());
		response.setFirstname(user.getFirstname());
		response.setLastname(user.getLastname());
		response.setUsername(user.getUsername());
		response.setEmail(user.getEmail());
		response.setPassword(user.getPassword());
		response.setIseriesname(user.getIseriesname());
		
		response.setAccountNonLocked(user.getAccountNonLocked());

		response.setCreatedby(user.getCreatedby());
		response.setModifiedby(user.getModifiedby());
		
		// check if Roles are available
		// get Roles via db call to make sure Roles are picked up
		List<DashboardRole> roleListOut = null;
		Integer size = 0;
		try {
			roleListOut = user.getRoleses();
			size = roleListOut.size(); 
		} catch (Exception e) {
			roleListOut = null;
			LOGGER.info(e.getMessage());
		}
	
		final List<RoleResponse> roleList = new ArrayList<RoleResponse>();
		final List<String> roleNamesList = new ArrayList<String>();
		
		if(roleListOut != null && size > 0){
			
			for(DashboardRole role : roleListOut){
				final RoleResponse roleResponse = new RoleResponse();
				roleResponse.setRoleid(role.getRoleId());
				roleResponse.setRoleName(role.getRoleName());
				roleList.add(roleResponse);
				roleNamesList.add(role.getRoleName());
			}
			
			response.setRoleses(roleList);		
			final String[] roleArray = new String[roleNamesList.size()];
			response.setRoleNames(roleNamesList.toArray(roleArray));
		}
		
		
		
		return response;
	}
}
