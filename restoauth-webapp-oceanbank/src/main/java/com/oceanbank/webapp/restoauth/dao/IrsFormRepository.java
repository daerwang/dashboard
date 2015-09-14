/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.restoauth.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.oceanbank.webapp.restoauth.model.IrsFormCustomer;

/**
 * The Interface IrsFormRepository.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@Transactional(readOnly = true)
@Qualifier("irsFormCustomer")
public interface IrsFormRepository extends JpaRepository<IrsFormCustomer, Long>{

	
	
	/**
	 * Find by customer by name like.
	 *
	 * @param name the name
	 * @param pageable the pageable
	 * @return the list
	 */
	List<IrsFormCustomer> findByCustomerByNameLike(@Param("search") String name, Pageable pageable);
	
	/**
	 * Find by customer pk.
	 *
	 * @param name the name
	 * @param account the account
	 * @param tin the tin
	 * @param gross the gross
	 * @return the irs form customer
	 */
	IrsFormCustomer findByCustomerPk(@Param("name") String name, @Param("account") String account, @Param("tin") String tin, @Param("gross") String gross);
	
	/**
	 * Find by datatable search.
	 *
	 * @param search the search
	 * @return the list
	 */
	List<IrsFormCustomer> findByDatatableSearch(@Param("search") String search);
	
	
	/**
	 * Find by short name containing.
	 *
	 * @param name the name
	 * @return the list
	 */
	List<IrsFormCustomer> findByShortNameContaining(String name);
	
	/**
	 * Find by mail code distinct.
	 *
	 * @return the list
	 */
	List<String> findByMailCodeDistinct();
	
	/**
	 * Find by mail code.
	 *
	 * @param codes the codes
	 * @return the list
	 */
	List<IrsFormCustomer> findByMailCode(@Param("search") List<String> codes);
}
