/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.restoauth.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.oceanbank.webapp.restoauth.model.MailCodeDetail;

/**
 * The Interface MailCodeRepository.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@Transactional(readOnly = true)
public interface MailCodeRepository extends JpaRepository<MailCodeDetail, Long>{
	
	/**
	 * Find by mail code.
	 *
	 * @param mailCode the mail code
	 * @return the mail code detail
	 */
	MailCodeDetail findByMailCode(String mailCode);
}
