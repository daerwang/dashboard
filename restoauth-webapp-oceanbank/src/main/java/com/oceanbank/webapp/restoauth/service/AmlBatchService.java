/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.restoauth.service;

import java.util.List;

import com.oceanbank.webapp.common.model.AmlBatchRequestResponse;
import com.oceanbank.webapp.restoauth.model.AmlBatchCif;
import com.oceanbank.webapp.restoauth.model.AmlBatchRequest;
import com.oceanbank.webapp.restoauth.model.DashboardComment;
import com.oceanbank.webapp.restoauth.model.DashboardLog;
import com.oceanbank.webapp.restoauth.model.DashboardUpload;

/**
 * The Interface AmlBatchService.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
public interface AmlBatchService {
	
	/**
	 * Generate an AML Batch request id.
	 *
	 * @param the id as reference
	 */
	String generateDashboardAmlBatchRequestId(Integer id);
	
	/**
	 * Create an AML Batch container.
	 *
	 * @param the object to create
	 */
	AmlBatchCif createAmlBatchCif(AmlBatchCif container);
	String createManyAmlBatchCif(List<AmlBatchCif> cifList);
	
	/**
	 * Update aml batch cif.
	 *
	 * @param container the container
	 * @return the aml batch cif
	 */
	AmlBatchCif updateAmlBatchCif(AmlBatchCif container);
	
	/**
	 * Delete AML batch cif.
	 *
	 * @param the object
	 */
	void deleteAmlBatchCif(AmlBatchCif entity);
	
	/**
	 * Create a AML Batch request.
	 *
	 * @param the object
	 */
	AmlBatchRequest createAmlBatchRequest(AmlBatchRequest entity);
	
	/**
	 * Update a AML Batch request.
	 *
	 * @param the object
	 */
	AmlBatchRequest updateAmlBatchRequest(AmlBatchRequest entity);
	
	/**
	 * Delete a AML Batch request.
	 *
	 * @param the object
	 */
	void deleteAmlBatchRequest(AmlBatchRequest entity);

	
	/**
	 * Find AML Batch request by id.
	 *
	 * @param the id
	 */
	AmlBatchRequest findAmlBatchRequestById(Integer id);
	
	/**
	 * Find AML Batch container by request id.
	 *
	 * @param the request id
	 */
	List<AmlBatchCif> findAmlBatchCifByRequestId(String requestId);
	
	/**
	 * Find by request id.
	 *
	 * @param requestId the request id
	 */
	AmlBatchRequest findByRequestId(String requestId);
	
	/**
	 * Find aml batch cif by id.
	 *
	 * @param id the id
	 * @return the aml batch cif
	 */
	AmlBatchCif findAmlBatchCifById(Integer id);
	
	/**
	 * Find AML Batch request by datatable search.
	 *
	 * @param text to search
	 */
	List<AmlBatchRequest> findAmlBatchByDatatableSearch(String search);
	
	/**
	 * Find aml batch cif by datatable search.
	 *
	 * @param search the search
	 * @return the list
	 */
	List<AmlBatchCif> findAmlBatchCifByDatatableSearch(String search, String requestId);
	String determineAmlBatchRequestStatus(String requestId);
	
	DashboardLog createDashboardLog(DashboardLog entity);
	String deleteDashboardLog(Long id);
	List<DashboardLog> findDashboardLogByTableIdAndTableName(Integer tableId, String tableName);
	
	DashboardUpload createDashboardUpload(DashboardUpload entity);
	List<DashboardUpload> findDashboardUploadByTableIdAndTableName(Integer tableId, String tableName);
	DashboardUpload findDashboardUploadById(Long id);
	String deleteDashboardUpload(DashboardUpload entity);
	
	DashboardComment createDashboardComment(DashboardComment entity);
	List<DashboardComment> findDashboardCommentByTableIdAndTableName(Integer tableId, String tableName);
	DashboardComment findDashboardCommentById(Integer id);
	String deleteDashboardComment(DashboardComment entity);
	
	String executeAmlBatchRequestApproval(String requestId, String storedProcedure);
	String executeAmlBatchRequestReversal(String requestId, String storedProcedure);
	String executeAmlBatchRequestDisapproval(String requestId, String storedProcedure);
	String findStoredProcedureByBank(String transactionType, String bankSchema);
	String executeStoredProcedure(String requestId, String storedProcedureCommand);
}
