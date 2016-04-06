/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.restoauth.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oceanbank.webapp.common.handler.RestSqlException;
import com.oceanbank.webapp.common.model.DashboardConstant;
import com.oceanbank.webapp.common.model.UserResponse;
import com.oceanbank.webapp.common.util.RestUtil;
import com.oceanbank.webapp.restoauth.converter.UserConverter;
import com.oceanbank.webapp.restoauth.dao.RoleRepository;
import com.oceanbank.webapp.restoauth.dao.UserRepository;
import com.oceanbank.webapp.restoauth.model.DashboardRole;
import com.oceanbank.webapp.restoauth.model.DashboardUser;
/**
 * The Class UserServiceImpl.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@Service
public class UserServiceImpl implements UserService {
	
	/** The user repository. */
	@Autowired
    private UserRepository userRepository;
	
	/** The role repository. */
	@Autowired
	private RoleRepository roleRepository;
    
    /**
     * Instantiates a new {@link UserServiceImpl}.
     */
    public UserServiceImpl(){}
    
    
	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.restoauth.service.UserService#createUser(com.oceanbank.webapp.restoauth.api.UserResponse)
	 */
	@Override
	public UserResponse createUser(UserResponse response) {
    	
    	final DashboardUser user = UserConverter.convertFromBean(response);
    	// save a User
    	final DashboardUser newUser = userRepository.save(user);
		
		// then save a Role for that User
    	final List<DashboardRole> roleList = user.getRoleses();
		
		if(roleList != null && roleList.size() > 0){
			// save each Role name
			for(DashboardRole role : roleList){
				role = new DashboardRole(role.getRoleName(), newUser, response.getCreatedby(), response.getModifiedby());
				roleRepository.save(role);
			}
		}else{
			// if no Role assigned, then set default Role
			final DashboardRole role = new DashboardRole(DashboardConstant.ROLE_GUEST, newUser, response.getCreatedby(), response.getModifiedby());
			roleRepository.save(role);
		}
		
		response = UserConverter.convertFromEntity(newUser);
		
		return response;
	}

    
	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.restoauth.service.UserService#findUsersContaining(java.lang.String)
	 */
	@Override
	public List<DashboardUser> findUsersContaining(String username) {
		
		return userRepository.findByUsernameContaining(username);
	}

	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.restoauth.service.UserService#findByUserIdIs(java.lang.Integer)
	 */
	@Override
	public DashboardUser findByUserIdIs(Integer user_id) {
		
		return userRepository.findByUserIdIs(user_id);
	}


	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.restoauth.service.UserService#findAllUsers()
	 */
	@Override
	public List<DashboardUser> findAllUsers() {
		
		return userRepository.findAll();
	}


	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.restoauth.service.UserService#deleteUser(java.lang.Integer)
	 */
	@Override
	public void deleteUser(Integer user_id) {
		
		userRepository.delete(new DashboardUser(user_id));
		
	}


	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.restoauth.service.UserService#updateUsername(java.lang.Integer, java.lang.String)
	 */
	@Override
	public void updateUsername(Integer user_id, String username) {
		
		userRepository.updateUsersUsername(user_id, username);
	}


	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.restoauth.service.UserService#findUserByUsername(java.lang.String)
	 */
	@Override
	public DashboardUser findUserByUsername(String username) {
		
		return userRepository.findByUsername(username);
	}


	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.restoauth.service.UserService#findUserWithRoles(java.lang.Integer)
	 */
	@Override
	public DashboardUser findUserWithRoles(Integer user_id) {

		List<DashboardRole> roleses = null;
		
		try {
			roleses = roleRepository.findByUseridIs(user_id);
		} catch (Exception e) {
			throw new RestSqlException(e.getMessage());
		}

		if(roleses == null || roleses.size() == 0){
			throw new RestSqlException("There is no Role found.");
		}
		
		final DashboardUser user = userRepository.findByUserIdIs(user_id);
		user.setRoleses(roleses);
		
		
		return user;
	}


	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.restoauth.service.UserService#findUserWithRoles(java.lang.String)
	 */
	@Override
	public DashboardUser findUserWithRoles(String username) {
		final DashboardUser user = userRepository.findByUsername(username);
		
		return findUserWithRoles(user.getUserId());
	}


	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.restoauth.service.UserService#findUserWithSearch(java.lang.String)
	 */
	@Override
	public List<DashboardUser> findUserWithSearch(String searchParameter) {
		List<DashboardUser> userList = null;
		
		if(!RestUtil.isNullOrEmpty(searchParameter)){
			userList = userRepository.findBySearchCriteria(searchParameter);
		}else{
			userList = userRepository.findAll();
		}
		
		return userList;
	}


	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.restoauth.service.UserService#updateUser(com.oceanbank.webapp.restoauth.api.UserResponse)
	 */
	@Override
	public DashboardUser updateUser(UserResponse response) {
		// get the selected User and update it
		DashboardUser user = userRepository.findByUserIdIs(response.getUserId());
		user.setFirstname(response.getFirstname());
		user.setLastname(response.getLastname());
		user.setUsername(response.getUsername());
//		user.setPassword(response.getPassword());
		user.setEmail(response.getEmail());
		user.setModifiedby(response.getModifiedby());
		user.setIseriesname(response.getIseriesname());
		user.setAccountNonLocked(response.getAccountNonLocked());
		user.setAccountNonExpired(response.getAccountNonExpired());
		

		// set the variables
		List<String> roleNamesList = new ArrayList<String>();
		final List<DashboardRole> roleList = roleRepository.findByUseridIs(user.getUserId());
		String[] roleNames = null;
		
		if(roleList != null && roleList.size() > 0){
			// get the chosen Roles from checbox of client
			roleNames = response.getRoleNames();
			if(roleNames != null){
				roleNamesList = Arrays.asList(roleNames);
			}else{
				// create the list
				for(DashboardRole r : roleList){
					roleNamesList.add(r.getRoleName());
				}
			}
			
			// save checkbox chosen
			for(String r : roleNamesList){
				// check if already a Role, if not save
				Boolean isRoleExist = false;
				for(DashboardRole role : roleList){
					if(role.getRoleName().equalsIgnoreCase(r)){
						isRoleExist = true;
						break;
					}
				}
				if(!isRoleExist){
					final DashboardRole newRole = new DashboardRole(r, user, response.getModifiedby(),response.getModifiedby());
					roleRepository.saveAndFlush(newRole);
				}
			}
			
			// delete not in chosen box
			for(DashboardRole role : roleList){
				Boolean isRoleStillThere = false;
				for(String r : roleNamesList){
					
					if(role.getRoleName().equalsIgnoreCase(r)){
						isRoleStillThere = true;
						break;
					}
				}
				if(!isRoleStillThere){
					roleRepository.delete(role);
				}
				
			}
			
		}
		
		// capture new updated Role list
		List<DashboardRole> updatedRoles = new ArrayList<DashboardRole>();
		updatedRoles = roleRepository.findByUseridIs(user.getUserId());
		user.setRoleses(updatedRoles);
		
		user = userRepository.saveAndFlush(user);
		
		return user;
	}


	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.restoauth.service.UserService#changeUserPassword(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public DashboardUser changeUserPassword(String username, String oldPassword, String newPassword) {
		
		final DashboardUser user = userRepository.findByUsernameAndPassword(username, oldPassword);
		
		if(user != null){
			user.setPassword(newPassword);
			final DashboardUser updatedUser = userRepository.save(user);
			
			return updatedUser;
		}
		
		return null;
	}

	

}
