/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.dashboard.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.oceanbank.webapp.common.exception.DashboardException;
import com.oceanbank.webapp.common.model.AmlBatchCifResponse;
import com.oceanbank.webapp.common.model.AmlBatchRequestResponse;
import com.oceanbank.webapp.common.model.DashboardCommentResponse;
import com.oceanbank.webapp.common.model.DashboardLogResponse;
import com.oceanbank.webapp.common.model.DashboardUploadResponse;
import com.oceanbank.webapp.common.model.DataTablesRequest;

/**
 * The Interface AmlBatchService.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
public interface AmlBatchService {

	/**
	 * Creates the aml batch request.
	 *
	 * @return the dashboard aml batch request response
	 */
	AmlBatchRequestResponse createAmlBatchRequest(AmlBatchRequestResponse response);
	AmlBatchCifResponse createAmlBatchCif(AmlBatchCifResponse response);
	String deleteAmlBatchCif(Integer id);
	AmlBatchCifResponse updateAmlBatchCif(AmlBatchCifResponse response);
	AmlBatchCifResponse findAmlBatchCif(Integer id);
	
	/**
	 * Gets the aml batch request by request id.
	 *
	 * @param requestId the request id
	 * @return the aml batch request by request id
	 */
	AmlBatchRequestResponse getAmlBatchRequestByRequestId(String requestId);
	
	/**
	 * Gets the aml batch request by id.
	 *
	 * @param id the id
	 * @return the aml batch request by id
	 */
	AmlBatchRequestResponse getAmlBatchRequestById(Integer id);
	
	/**
	 * Update aml batch request.
	 *
	 * @param bean the bean
	 * @return the dashboard aml batch request response
	 */
	AmlBatchRequestResponse updateAmlBatchRequest(AmlBatchRequestResponse bean);
	
	/**
	 * Search aml batch form data table.
	 *
	 * @param datatableRequest the datatable request
	 * @return the list
	 */
	List<AmlBatchRequestResponse> getAmlBatchDataTable(DataTablesRequest datatableRequest);
	List<AmlBatchCifResponse> getAmlBatchCifDataTable(DataTablesRequest datatableRequest);
	List<AmlBatchCifResponse> getAmlBatchCifByRequestId(String requestId);

	/**
	 * Delete aml batch request by request id.
	 *
	 * @param requestId the request id
	 * @return the string
	 */
	String deleteAmlBatchRequestByRequestId(String requestId);
	AmlBatchRequestResponse executeAmlBatchRequestApproval(String requestId);
	AmlBatchRequestResponse executeAmlBatchRequestReversal(String requestId);
	AmlBatchRequestResponse executeAmlBatchRequestDisapproval(String requestId);
	AmlBatchRequestResponse executeAmlApprovalOrDisapproval(AmlBatchRequestResponse amlBatchRequest);
	List<AmlBatchCifResponse> createAmlBatchCifFromExcel(String requestId, MultipartFile mpf, String createdBy) throws DashboardException;
	String createManyAmlBatchCif(List<AmlBatchCifResponse> list);
	List<DashboardLogResponse> getDashboardLogDataTable(DataTablesRequest datatableRequest);
	List<DashboardUploadResponse> getDashboardUploadDataTable(DataTablesRequest datatableRequest);
	String getUploadPermanentFileLocation();
	DashboardUploadResponse findDashboardUploadById(Long id);
	String deleteDashboardUpload(Long id);
	
	List<DashboardCommentResponse> getDashboardCommentDataTable(DataTablesRequest datatableRequest);
	DashboardCommentResponse findDashboardCommentById(Long id);
	DashboardCommentResponse createDashboardComment(DashboardCommentResponse response);
	DashboardCommentResponse updateDashboardComment(DashboardCommentResponse response);
	String deleteDashboardComment(Long id);
	
	void createExcelIntoTextFileToDisk(MultipartFile mpf) throws IOException, DashboardException;
	void saveAmlBatchRequestUploadFileToDisk(MultipartFile mpf, String createdBy, String requestId) throws IOException, DashboardException;
	DashboardUploadResponse createDashboardUpload(DashboardUploadResponse response);
}
