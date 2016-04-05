/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.restoauth.dao;

import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.oceanbank.webapp.restoauth.model.DashboardUser;

/**
 * The Interface UserRepository.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<DashboardUser, Integer> {
	
	/**
	 * Find by user id is.
	 *
	 * @param user_id the user_id
	 * @return the dashboard user
	 */
	DashboardUser findByUserIdIs(Integer userId);

	/**
	 * Find by username.
	 *
	 * @param username the username
	 * @return the dashboard user
	 */
	DashboardUser findByUsername(String username);
	
	/**
	 * Find by username and password.
	 *
	 * @param username the username
	 * @param password the password
	 * @return the dashboard user
	 */
	DashboardUser findByUsernameAndPassword(String username, String password);
	
	/**
	 * Find by username containing.
	 *
	 * @param username the username
	 * @return the list
	 */
	List<DashboardUser> findByUsernameContaining(String username);
	
	/**
	 * Update users username.
	 *
	 * @param user_id the user_id
	 * @param username the username
	 */
	@Modifying
	@Transactional
	@Query("UPDATE DashboardUser u SET u.username = ?2 WHERE u.userId = ?1")
	void updateUsersUsername(Integer userId, String username);
	
	/**
	 * Find by search criteria.
	 *
	 * @param searchParameter the search parameter
	 * @return the list
	 */
	@Query("SELECT u FROM DashboardUser u WHERE u.username LIKE %:search% OR u.firstname LIKE %:search% OR u.lastname LIKE %:search% OR u.email LIKE %:search%")
	List<DashboardUser> findBySearchCriteria(@Param("search") String searchParameter);
	
	

	/**
	 * Plus1 backed by other named stored procedure.
	 *
	 * @param arg the arg
	 * @return the integer
	 */
	@Procedure(name = "User.plus1")
	Integer plus1BackedByOtherNamedStoredProcedure(@Param("arg") Integer arg);

	/**
	 * Plus1inout.
	 *
	 * @param arg the arg
	 * @return the integer
	 */
	@Procedure(procedureName = "allen.plus1inout")
	Integer plus1inout(Integer arg);
}
