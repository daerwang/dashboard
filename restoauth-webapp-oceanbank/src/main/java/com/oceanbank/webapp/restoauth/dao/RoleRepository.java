/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.restoauth.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.oceanbank.webapp.restoauth.model.DashboardRole;



/**
 * The Interface RoleRepository.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
public interface RoleRepository extends JpaRepository<DashboardRole, Long>{
	
	/**
	 * Find by role id is.
	 *
	 * @param role_id the role_id
	 * @return the dashboard role
	 */
	DashboardRole findByRoleIdIs(Integer roleId);
	
	/**
	 * Find by userid is.
	 *
	 * @param user_id the user_id
	 * @return the list
	 */
	@Query("SELECT r from DashboardRole r WHERE r.users.userId = ?1")
	List<DashboardRole> findByUseridIs(Integer userId);
}
