/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.restoauth.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.oceanbank.webapp.restoauth.model.BankDetail;

/**
 * The Interface BankDetailRepository.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@Transactional(readOnly = true)
public interface BankDetailRepository extends JpaRepository<BankDetail, Long>{

	/**
	 * Find by fld14e is.
	 *
	 * @param bankName the bank name
	 * @return the bank detail
	 */
	@Query("SELECT u FROM BankDetail u WHERE u.fld_14e =:name")
	BankDetail findByFld14eIs(@Param("name")String bankName);
	
}
