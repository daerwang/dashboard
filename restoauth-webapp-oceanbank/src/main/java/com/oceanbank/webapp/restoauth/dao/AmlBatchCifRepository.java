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

import com.oceanbank.webapp.restoauth.model.AmlBatchCif;

/**
 * The Interface DashboardAmlBatchCifRepository.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@Transactional(readOnly = true)
@Qualifier("amlBatchContainer")
public interface AmlBatchCifRepository extends JpaRepository<AmlBatchCif, Long> {
	
	/**
	 * Find by request id is.
	 *
	 * @param requestId the request id
	 * @return the list
	 */
	List<AmlBatchCif> findByRequestIdIs(String requestId);
	List<AmlBatchCif> findByRequestIdIs(String requestId, Pageable pageable);
	List<AmlBatchCif> findByRequestIdAndStatus(String requestId, String status);
	
	/**
	 * Find aml batch cif by id.
	 *
	 * @param id the id
	 * @return the aml batch cif
	 */
	AmlBatchCif findAmlBatchCifById(@Param("id") Integer id);
	
	/**
	 * Find by datatable search.
	 *
	 * @param search the search
	 * @return the list
	 */
	List<AmlBatchCif> findByDatatableSearch(@Param("search") String search, @Param("requestId") String requestId);
}
