/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.restoauth.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.oceanbank.webapp.restoauth.model.AmlBatchRequest;


/**
 * The Interface DashboardAmlBatchRequestRepository.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@Transactional(readOnly = true)
@Qualifier("amlBatchRequest")
public interface AmlBatchRequestRepository extends JpaRepository<AmlBatchRequest, Long>{
	
	/**
	 * Find by request id is.
	 *
	 * @param requestId the request id
	 * @return the dashboard aml batch request
	 */
	AmlBatchRequest findByRequestIdIs(String requestId);
	
	/**
	 * Find by id is.
	 *
	 * @param id the id
	 * @return the dashboard aml batch request
	 */
	AmlBatchRequest findByIdIs(Integer id);
	
	/**
	 * Find by datatable search.
	 *
	 * @param search the search
	 * @return the list
	 */
	List<AmlBatchRequest> findByDatatableSearch(@Param("search") String search);
	
	/**
	 * Plus1inout.
	 *
	 * @param arg the arg
	 * @return the integer
	 */
	@Procedure(procedureName = "XPERTV700.DASHBOARDSPBSAGETLOGBYTYPE")
	Integer plus1inout(Integer arg);

}
