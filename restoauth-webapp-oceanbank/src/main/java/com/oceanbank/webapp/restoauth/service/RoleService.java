/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.restoauth.service;

import java.util.List;

import com.oceanbank.webapp.restoauth.model.DashboardRole;

/**
 * The Interface RoleService.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
public interface RoleService {
	
	/**
	 * Gets the role by user id.
	 *
	 * @param user_id the user_id
	 * @return the role by user id
	 */
	List<DashboardRole> getRoleByUserId(Integer user_id);
}
