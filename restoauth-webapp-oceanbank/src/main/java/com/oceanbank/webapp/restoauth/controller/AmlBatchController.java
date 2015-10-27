/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.restoauth.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.oceanbank.webapp.common.exception.DashboardException;
import com.oceanbank.webapp.common.model.AmlBatchCifResponse;
import com.oceanbank.webapp.common.model.AmlBatchRequestResponse;
import com.oceanbank.webapp.common.model.DashboardCommentResponse;
import com.oceanbank.webapp.common.model.DashboardUploadResponse;
import com.oceanbank.webapp.common.model.DashboardConstant;
import com.oceanbank.webapp.common.model.DashboardLogResponse;
import com.oceanbank.webapp.common.model.DataTablesRequest;
import com.oceanbank.webapp.common.model.RestWebServiceUrl;
import com.oceanbank.webapp.restoauth.converter.AmlBatchCifConverter;
import com.oceanbank.webapp.restoauth.converter.AmlBatchRequestConverter;
import com.oceanbank.webapp.restoauth.converter.DashboardCommentConverter;
import com.oceanbank.webapp.restoauth.converter.DashboardConverter;
import com.oceanbank.webapp.restoauth.converter.DashboardLogConverter;
import com.oceanbank.webapp.restoauth.converter.DashboardUploadConverter;
import com.oceanbank.webapp.restoauth.model.AmlBatchCif;
import com.oceanbank.webapp.restoauth.model.AmlBatchRequest;
import com.oceanbank.webapp.restoauth.model.DashboardComment;
import com.oceanbank.webapp.restoauth.model.DashboardLog;
import com.oceanbank.webapp.restoauth.model.DashboardUpload;
import com.oceanbank.webapp.restoauth.model.DashboardUser;
import com.oceanbank.webapp.restoauth.service.AmlBatchServiceImpl;
import com.oceanbank.webapp.restoauth.service.As400SpServiceImpl;
import com.oceanbank.webapp.restoauth.service.UserServiceImpl;


/**
 * The Class AmlBatchController.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@RestController
public class AmlBatchController {
	
	/**
	 * Instantiates a new aml batch controller.
	 */
	public AmlBatchController(){}

	/** The dashboardservice. */
	@Autowired
	private AmlBatchServiceImpl dashboardservice;
	
	@Autowired
	private As400SpServiceImpl spService;
	
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
    ServletContext context; 
	
	/** The AmlBatchContainerConverter converter. */
	private DashboardConverter<AmlBatchCif, AmlBatchCifResponse> amlBatchCifConverter = new AmlBatchCifConverter();
	
	/** The AmlBatchRequestConverter converter. */
	private DashboardConverter<AmlBatchRequest, AmlBatchRequestResponse> amlBatchRequestConverter = new AmlBatchRequestConverter();
	private DashboardConverter<DashboardLog, DashboardLogResponse> dashboardLogConverter = new DashboardLogConverter();
	private DashboardConverter<DashboardUpload, DashboardUploadResponse> dashboardUploadConverter = new DashboardUploadConverter();
	private DashboardConverter<DashboardComment, DashboardCommentResponse> dashboardCommentConverter = new DashboardCommentConverter();
	
	/** The logger. */
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	private Boolean isProductionEnvironment(){
		Boolean isProd = false;
		String search = "as400prod";
		String contextConfigLocation= context.getInitParameter("contextConfigLocation");
		if(contextConfigLocation.toLowerCase().indexOf(search.toLowerCase()) != -1 ) {
			isProd = true;
		} 
		
		return isProd;
	}
	
	
	@RequestMapping(value = RestWebServiceUrl.EXECUTE_BATCH_APPROVAL_REQUEST_BY_REQUEST_ID, method = RequestMethod.GET)
	public AmlBatchRequestResponse executeAmlBatchRequestApproval(@PathVariable("requestId") String requestId) throws DashboardException{
				
		String result = null; 
		AmlBatchRequestResponse bean = null;
		AmlBatchRequest forUpdate = dashboardservice.findByRequestId(requestId);	
		
		String storedProcedure = isProductionEnvironment() ? DashboardConstant.SP_EXECUTE_AML_BATCH_APPROVAL : DashboardConstant.SP_EXECUTE_AML_BATCH_APPROVAL_700;
		
		// 1. execute SP
		result = dashboardservice.executeAmlBatchRequestApproval(requestId, storedProcedure).trim();
		//result = "complete";
		
		/*
		try {
			Thread.sleep(10000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		*/
		
		// 2. check SP output if OK or exception
		if(!result.equalsIgnoreCase("OK")){
			
			// of exception
			if(result.equalsIgnoreCase(DashboardConstant.AML_BATCH_STATUS_COMPLETE)){
				updateAmlBatchRequestStatus(requestId, forUpdate);
				insertLogForAudit(DashboardConstant.AML_MESSAGE_7 + "There is no request to process since all CIF are Approved.", forUpdate.getId(), forUpdate.getCreatedby());
				throw new DashboardException(DashboardConstant.AML_MESSAGE_EXCEPTION_1);
				
			}else if(result.equalsIgnoreCase(DashboardConstant.AML_BATCH_STATUS_NO_CIF_TO_PROCESS)){
				updateAmlBatchRequestStatus(requestId, forUpdate);
				insertLogForAudit(DashboardConstant.AML_MESSAGE_7 + DashboardConstant.AML_MESSAGE_EXCEPTION_2, forUpdate.getId(), forUpdate.getCreatedby());
				throw new DashboardException(DashboardConstant.AML_MESSAGE_EXCEPTION_2);
				
			}else if(result.equalsIgnoreCase(DashboardConstant.AML_BATCH_STATUS_ONE_OR_MORE_CIF_NOT_FOUND)){
				updateAmlBatchRequestStatus(requestId, forUpdate);
				insertLogForAudit(DashboardConstant.AML_MESSAGE_7 + DashboardConstant.AML_MESSAGE_EXCEPTION_4, forUpdate.getId(), forUpdate.getCreatedby());
				throw new DashboardException(DashboardConstant.AML_MESSAGE_EXCEPTION_4);
				
			}else{
				updateAmlBatchRequestStatus(requestId, forUpdate);
				insertLogForAudit(DashboardConstant.AML_MESSAGE_7 + DashboardConstant.AML_MESSAGE_EXCEPTION_3, forUpdate.getId(), forUpdate.getCreatedby());
				throw new DashboardException(DashboardConstant.AML_MESSAGE_EXCEPTION_3);
			}
			
		}else{
			
			updateAmlBatchRequestStatus(requestId, forUpdate);
			insertLogForAudit(DashboardConstant.AML_MESSAGE_6, forUpdate.getId(), forUpdate.getCreatedby());
			bean = amlBatchRequestConverter.convertFromEntity(forUpdate);
		}
		
		
		return bean;
	}
	
	private AmlBatchRequest updateAmlBatchRequestStatus(String requestId, AmlBatchRequest entity){
		String status = dashboardservice.determineAmlBatchRequestStatus(requestId);
		entity.setStatus(status);
		entity = dashboardservice.updateAmlBatchRequest(entity);
		
		return entity;
	}
	
	@RequestMapping(value = RestWebServiceUrl.EXECUTE_BATCH_REVERSAL_REQUEST_BY_REQUEST_ID, method = RequestMethod.GET)
	public AmlBatchRequestResponse executeAmlBatchRequestReversal(@PathVariable("requestId") String requestId) throws DashboardException{
				
		String result = null; 
		AmlBatchRequestResponse bean = null;
		AmlBatchRequest forUpdate = dashboardservice.findByRequestId(requestId);	
		
		String storedProcedure = isProductionEnvironment() ? DashboardConstant.SP_EXECUTE_AML_BATCH_REVERSAL : DashboardConstant.SP_EXECUTE_AML_BATCH_REVERSAL_700;
		
		// 1. execute SP
		result = dashboardservice.executeAmlBatchRequestReversal(requestId, storedProcedure).trim();
		//result = "OK";
		// 2. check SP output if OK or exception
		if(!result.equalsIgnoreCase("OK")){
			
			// of exception
			if(result.equalsIgnoreCase(DashboardConstant.AML_BATCH_STATUS_COMPLETE)){
				updateAmlBatchRequestStatus(requestId, forUpdate);
				insertLogForAudit(DashboardConstant.AML_MESSAGE_7 + "There is no request to process since all CIF are Reversed.", forUpdate.getId(), forUpdate.getCreatedby());
				throw new DashboardException(DashboardConstant.AML_MESSAGE_EXCEPTION_1);
				
			}else if(result.equalsIgnoreCase(DashboardConstant.AML_BATCH_STATUS_NO_CIF_TO_PROCESS)){
				updateAmlBatchRequestStatus(requestId, forUpdate);
				insertLogForAudit(DashboardConstant.AML_MESSAGE_7 + DashboardConstant.AML_MESSAGE_EXCEPTION_2, forUpdate.getId(), forUpdate.getCreatedby());
				throw new DashboardException(DashboardConstant.AML_MESSAGE_EXCEPTION_2);
				
			}else if(result.equalsIgnoreCase(DashboardConstant.AML_BATCH_STATUS_ONE_OR_MORE_CIF_NOT_FOUND)){
				updateAmlBatchRequestStatus(requestId, forUpdate);
				insertLogForAudit(DashboardConstant.AML_MESSAGE_7 + DashboardConstant.AML_MESSAGE_EXCEPTION_4, forUpdate.getId(), forUpdate.getCreatedby());
				throw new DashboardException(DashboardConstant.AML_MESSAGE_EXCEPTION_4);
				
			}else if(result.equalsIgnoreCase(DashboardConstant.AML_BATCH_STATUS_REVERSAL_RECORD_NOT_FOUND)){
				updateAmlBatchRequestStatus(requestId, forUpdate);
				insertLogForAudit(DashboardConstant.AML_MESSAGE_7 + DashboardConstant.AML_MESSAGE_EXCEPTION_5, forUpdate.getId(), forUpdate.getCreatedby());
				throw new DashboardException(DashboardConstant.AML_MESSAGE_EXCEPTION_4);
				
			}else{
				updateAmlBatchRequestStatus(requestId, forUpdate);
				insertLogForAudit(DashboardConstant.AML_MESSAGE_7 + DashboardConstant.AML_MESSAGE_EXCEPTION_3, forUpdate.getId(), forUpdate.getCreatedby());
				throw new DashboardException(DashboardConstant.AML_MESSAGE_EXCEPTION_3);
			}
			
		}else{
			
			updateAmlBatchRequestStatus(requestId, forUpdate);
			insertLogForAudit(DashboardConstant.AML_MESSAGE_6, forUpdate.getId(), forUpdate.getCreatedby());
			bean = amlBatchRequestConverter.convertFromEntity(forUpdate);
		}
		
		return bean;
	}
	
	@RequestMapping(value = RestWebServiceUrl.EXECUTE_BATCH_DISAPPROVAL_REQUEST_BY_REQUEST_ID, method = RequestMethod.GET)
	public AmlBatchRequestResponse executeAmlBatchRequestDisapproval(@PathVariable("requestId") String requestId) throws DashboardException{
				
		String result = null; 
		AmlBatchRequestResponse bean = null;
		AmlBatchRequest forUpdate = dashboardservice.findByRequestId(requestId);	
		
		String storedProcedure = isProductionEnvironment() ? DashboardConstant.SP_EXECUTE_AML_BATCH_DISAPPROVAL : DashboardConstant.SP_EXECUTE_AML_BATCH_DISAPPROVAL_700;
		
		// 1. execute SP
		result = dashboardservice.executeAmlBatchRequestDisapproval(requestId, storedProcedure).trim();
		//result = "complete";
		// 2. check SP output if OK or exception
		if(!result.equalsIgnoreCase("OK")){
			
			// of exception
			if(result.equalsIgnoreCase(DashboardConstant.AML_BATCH_STATUS_COMPLETE)){
				updateAmlBatchRequestStatus(requestId, forUpdate);
				insertLogForAudit(DashboardConstant.AML_MESSAGE_7 + "There is no request to process since all CIF are Disapproved.", forUpdate.getId(), forUpdate.getCreatedby());
				throw new DashboardException(DashboardConstant.AML_MESSAGE_EXCEPTION_1);
				
			}else if(result.equalsIgnoreCase(DashboardConstant.AML_BATCH_STATUS_NO_CIF_TO_PROCESS)){
				updateAmlBatchRequestStatus(requestId, forUpdate);
				insertLogForAudit(DashboardConstant.AML_MESSAGE_7 + DashboardConstant.AML_MESSAGE_EXCEPTION_2, forUpdate.getId(), forUpdate.getCreatedby());
				throw new DashboardException(DashboardConstant.AML_MESSAGE_EXCEPTION_2);
				
			}else if(result.equalsIgnoreCase(DashboardConstant.AML_BATCH_STATUS_ONE_OR_MORE_CIF_NOT_FOUND)){
				updateAmlBatchRequestStatus(requestId, forUpdate);
				insertLogForAudit(DashboardConstant.AML_MESSAGE_7 + DashboardConstant.AML_MESSAGE_EXCEPTION_4, forUpdate.getId(), forUpdate.getCreatedby());
				throw new DashboardException(DashboardConstant.AML_MESSAGE_EXCEPTION_4);
				
			}else{
				updateAmlBatchRequestStatus(requestId, forUpdate);
				insertLogForAudit(DashboardConstant.AML_MESSAGE_7 + DashboardConstant.AML_MESSAGE_EXCEPTION_3, forUpdate.getId(), forUpdate.getCreatedby());
				throw new DashboardException(DashboardConstant.AML_MESSAGE_EXCEPTION_3);
			}
			
		}else{
			
			updateAmlBatchRequestStatus(requestId, forUpdate);
			insertLogForAudit(DashboardConstant.AML_MESSAGE_6, forUpdate.getId(), forUpdate.getCreatedby());
			bean = amlBatchRequestConverter.convertFromEntity(forUpdate);
		}
		
		return bean;
	}
	
	/**
	 * Gets the {@link AmlBatchRequestResponse} datatable.
	 *
	 * @param datatable request parameter
	 */
	@RequestMapping(value = RestWebServiceUrl.GET_AML_BATCH_DATATABLE, method = RequestMethod.POST)
	public List<AmlBatchRequestResponse> getAmlBatchDatatables(@RequestBody DataTablesRequest datatableRequest){
		
    	final String searchParameter = datatableRequest.getValue();
		
		final List<AmlBatchRequest> irsSearchList = dashboardservice.findAmlBatchByDatatableSearch(searchParameter);
		
		final List<AmlBatchRequestResponse> resultList = new ArrayList<AmlBatchRequestResponse>();
		for (AmlBatchRequest entity : irsSearchList) {
			final AmlBatchRequestResponse response = amlBatchRequestConverter.convertFromEntity(entity);
			resultList.add(response);
		}
		
		return resultList;
	}
	
	@RequestMapping(value = RestWebServiceUrl.GET_AML_BATCH_CIF_DATATABLE, method = RequestMethod.POST)
	public List<AmlBatchCifResponse> getAmlBatchCifDatatables(@RequestBody DataTablesRequest datatableRequest){

		final List<AmlBatchCif> cifList = dashboardservice.findAmlBatchCifByDatatableSearch(datatableRequest.getValue(), datatableRequest.getAmlBatchRequestId());
		
		final List<AmlBatchCifResponse> resultList = new ArrayList<AmlBatchCifResponse>();
		for (AmlBatchCif entity : cifList) {
			final AmlBatchCifResponse response = amlBatchCifConverter.convertFromEntity(entity);
			resultList.add(response);
		}
		
		return resultList;
	}
	
	@RequestMapping(value = RestWebServiceUrl.GET_AML_BATCH_REQUEST_UPLOAD_DATATABLE, method = RequestMethod.POST)
	public List<DashboardUploadResponse> getDashboardUploadDatatables(@RequestBody DataTablesRequest datatableRequest){
		List<DashboardUpload> uploadList = new ArrayList<DashboardUpload>();
		final List<DashboardUploadResponse> resultList = new ArrayList<DashboardUploadResponse>();
		
		uploadList = dashboardservice.findDashboardUploadByTableIdAndTableName(datatableRequest.getAmlRequestId(), DashboardConstant.AML_BATCH_TABLE_NAME);
		
		if(!uploadList.isEmpty()){
			for (DashboardUpload entity : uploadList) {
				final DashboardUploadResponse response = dashboardUploadConverter.convertFromEntity(entity);
				resultList.add(response);
			}
		}

		return resultList;
	}
	
	@RequestMapping(value = RestWebServiceUrl.GET_DASHBOARD_COMMENT_DATATABLE, method = RequestMethod.POST)
	public List<DashboardCommentResponse> getDashboardCommentDatatables(@RequestBody DataTablesRequest datatableRequest){
		List<DashboardComment> uploadList = new ArrayList<DashboardComment>();
		final List<DashboardCommentResponse> resultList = new ArrayList<DashboardCommentResponse>();
		
		uploadList = dashboardservice.findDashboardCommentByTableIdAndTableName(datatableRequest.getAmlRequestId(), DashboardConstant.AML_BATCH_TABLE_NAME);
		
		if(!uploadList.isEmpty()){
			for (DashboardComment entity : uploadList) {
				final DashboardCommentResponse response = dashboardCommentConverter.convertFromEntity(entity);
				resultList.add(response);
			}
		}

		return resultList;
	}
	
	@RequestMapping(value = RestWebServiceUrl.GET_DASHBOARDLOG_DATATABLE, method = RequestMethod.POST)
	public List<DashboardLogResponse> getDashboardLogDatatables(@RequestBody DataTablesRequest datatableRequest){

		final List<DashboardLog> logList = dashboardservice.findDashboardLogByTableIdAndTableName(datatableRequest.getAmlRequestId(), DashboardConstant.AML_BATCH_TABLE_NAME);
		
		final List<DashboardLogResponse> resultList = new ArrayList<DashboardLogResponse>();
		for (DashboardLog entity : logList) {
			final DashboardLogResponse response = dashboardLogConverter.convertFromEntity(entity);
			resultList.add(response);
		}
		
		return resultList;
	}
	
	/**
	 * Gets the AML Batch request by request id.
	 *
	 * @param requestId the request id
	 * @return the api response
	 */
	@RequestMapping(value = RestWebServiceUrl.GET_AML_BATCH_REQUEST_BY_REQUEST_ID, method = RequestMethod.POST)
	public AmlBatchRequestResponse getAmlBatchRequestByRequestId(@PathVariable("requestId") String requestId){
		
		final AmlBatchRequest entity = dashboardservice.findByRequestId(requestId);
		
		final AmlBatchRequestResponse response = amlBatchRequestConverter.convertFromEntity(entity);
		
		return response;
	}
	
	@RequestMapping(value = RestWebServiceUrl.GET_AML_BATCH_REQUEST_BY_ID, method = RequestMethod.POST)
	public AmlBatchRequestResponse getAmlBatchRequestById(@PathVariable("id") Integer id){
		
		final AmlBatchRequest entity = dashboardservice.findAmlBatchRequestById(id);
		
		final AmlBatchRequestResponse response = amlBatchRequestConverter.convertFromEntity(entity);
		
		return response;
	}
	
	/**
	 * Creates the AML Batch request.
	 *
	 * @param object to create
	 * @return the api response
	 */
	@RequestMapping(value = RestWebServiceUrl.CREATE_AML_BATCH_REQUEST, method = RequestMethod.POST)
	public AmlBatchRequestResponse createAmlBatchRequest(@RequestBody AmlBatchRequestResponse response){
		
		AmlBatchRequest newRequest = new AmlBatchRequest();
		newRequest = dashboardservice.createAmlBatchRequest(amlBatchRequestConverter.convertFromBean(response));
		
		insertLogForAudit(DashboardConstant.AML_MESSAGE_4, newRequest.getId(), newRequest.getCreatedby());
		
		final AmlBatchRequestResponse result = amlBatchRequestConverter.convertFromEntity(newRequest);
				
		return result;
	}
	
	/**
	 * Update AML Batch request.
	 *
	 * @param object to update
	 * @return the api response
	 */
	@RequestMapping(value = RestWebServiceUrl.UPDATE_AML_BATCH_REQUEST, method = RequestMethod.POST)
	public AmlBatchRequestResponse updateAmlBatchRequest(@RequestBody AmlBatchRequestResponse response){

		AmlBatchRequest updated = dashboardservice.findByRequestId(response.getRequestId());
		updated.setName(response.getName());
		updated.setDescription(response.getDescription());
		updated.setTransactionType(response.getTransactionType());
		updated.setBankSchema(response.getBankSchema());
		updated.setModifiedby(response.getModifiedby());
		
		updated = updateAmlBatchRequestStatus(response.getRequestId(), updated);
		
		insertLogForAudit(DashboardConstant.AML_MESSAGE_5, updated.getId(), updated.getModifiedby());
		
		final AmlBatchRequestResponse result = amlBatchRequestConverter.convertFromEntity(updated);
				
		return result;
	}
	
	/**
	 * Delete AML Batch request.
	 *
	 * @param id the id
	 * @return a message if successful
	 */
	@RequestMapping(value = RestWebServiceUrl.DELETE_AML_BATCH_REQUEST_BY_REQUESTID, method = RequestMethod.DELETE)
	public String deleteAmlBatchRequest(@PathVariable("requestId") String requestId){
		
		final AmlBatchRequest request = dashboardservice.findByRequestId(requestId);
		final List<AmlBatchCif> list = dashboardservice.findAmlBatchCifByRequestId(request.getRequestId());
		if(list != null && list.size() > 0){
			for(AmlBatchCif c : list){
				dashboardservice.deleteAmlBatchCif(c);
			}
		}
		
		dashboardservice.deleteAmlBatchRequest(new AmlBatchRequest(request.getId()));
		
		return "OK!";
	}
	
	
	/**
	 * Creates the AML Batch container.
	 *
	 * @param object to create
	 * @return the api response
	 * @throws DashboardException 
	 */
	@RequestMapping(value = RestWebServiceUrl.CREATE_AML_BATCH_CIF, method = RequestMethod.POST)
	public AmlBatchCifResponse createAmlBatchCif(@RequestBody AmlBatchCifResponse response) throws DashboardException{

		AmlBatchCif cif = amlBatchCifConverter.convertFromBean(response);	
		DashboardUser user = userService.findUserByUsername(response.getCreatedby());
		String iseriesname = user.getIseriesname();
		if(iseriesname == null){
			throw new DashboardException("An iseries name is required. Please contact IT Helpdesk to setup your account.");
		}
		cif.setIseriesname(iseriesname);
		
		final AmlBatchRequest req = dashboardservice.findByRequestId(response.getRequestId());
		cif.setDashboardamlbatchrequest(req);
		cif.setTransactionType(req.getTransactionType());
		
		try {
			cif = dashboardservice.createAmlBatchCif(cif);
			
			insertLogForAudit(DashboardConstant.AML_MESSAGE_1, req.getId(), response.getCreatedby());  
			
		} catch (Exception e) {
			throw new DashboardException(e.getMessage(), e.getCause());
		}
		
		
		final AmlBatchCifResponse result = amlBatchCifConverter.convertFromEntity(cif);
		
		return result; 
	}
	
	@RequestMapping(value = RestWebServiceUrl.CREATE_MANY_AML_BATCH_CIF, method = RequestMethod.POST)
	public String createManyAmlBatchCif(@RequestBody List<AmlBatchCifResponse> list) throws DashboardException{
		List<AmlBatchCif> cifList = new ArrayList<AmlBatchCif>();
		String result = null;
		AmlBatchCifResponse response = list.get(0);
		String requestId = response.getRequestId();
		
		final AmlBatchRequest reqEntity = dashboardservice.findByRequestId(requestId);
		
		// check bean list if cif are unique
		final Set<String> dummy = new HashSet<String>();
		StringBuilder c = new StringBuilder();
		for(AmlBatchCifResponse r : list){
			if(!dummy.add(r.getCifReference().trim())){
				c.append(r.getCifReference().trim() + ", ");
			}
		}
		if(c.toString().length() > 1){
			throw new DashboardException("The Excel cannot be processed because it contains duplicate CIF. " + c.toString());
		}
		
		// validate existing cif
		final List<AmlBatchCif> cifEntityList = dashboardservice.findAmlBatchCifByRequestId(requestId);
		if(!cifEntityList.isEmpty()){
			StringBuilder b = new StringBuilder();
			for(AmlBatchCifResponse r : list){
				// check each Excel loaded cif to existing cif
				for(AmlBatchCif cif : cifEntityList){
					if(cif.getCifReference().trim().equalsIgnoreCase(r.getCifReference().trim())){
						b.append(r.getCifReference().trim() + ", ");
					}
				}
			}
			if(b.toString().length() > 1){
				throw new DashboardException("The following CIF cannot be saved because its already existing. " + b.toString());
			}
		}
		
		DashboardUser user = userService.findUserByUsername(response.getCreatedby());
		String iseriesname = user.getIseriesname();
		if(iseriesname == null){
			throw new DashboardException("An iseries name is required. Please contact IT Helpdesk to setup your account.");
		}

		for(AmlBatchCifResponse bean : list){
			AmlBatchCif cif = amlBatchCifConverter.convertFromBean(bean);	
			cif.setTransactionType(reqEntity.getTransactionType());
			cif.setDashboardamlbatchrequest(reqEntity);
			cif.setIseriesname(iseriesname);
			
			cifList.add(cif);
		}
		
		result = dashboardservice.createManyAmlBatchCif(cifList);
		
		insertLogForAudit(DashboardConstant.AML_MESSAGE_3, reqEntity.getId(), response.getCreatedby());
		
		return result; 
	}
	
	@RequestMapping(value = RestWebServiceUrl.DELETE_AML_BATCH_CIF_BY_ID, method = RequestMethod.DELETE)
	public String deleteAmlBatchCif(@PathVariable("id") Integer id) throws DashboardException{
		
		final AmlBatchCif request = dashboardservice.findAmlBatchCifById(id);

		// check amlbatchcif if reversed, approved or disapproved
		String status = request.getStatus() != null ? request.getStatus().trim() : "";
		if(status.equalsIgnoreCase("reversed") || status.equalsIgnoreCase("approved") || status.equalsIgnoreCase("disapproved")){
			throw new DashboardException("The CIF can no longer be deleted once Approved, Reversed or Disapproved");
		}
		
		dashboardservice.deleteAmlBatchCif(request);
		
		return "OK!";
	}
	
	@RequestMapping(value = RestWebServiceUrl.UPDATE_AML_BATCH_CIF, method = RequestMethod.POST)
	public AmlBatchCifResponse updateAmlBatchCif(@RequestBody AmlBatchCifResponse response){
		
		AmlBatchCif objToUpdate = dashboardservice.findAmlBatchCifById(response.getId());
		objToUpdate.setCifReference(response.getCifReference());
		objToUpdate.setAuditDescription(response.getAuditDescription());
		objToUpdate.setTransactionType(response.getTransactionType());
		objToUpdate.setStatus(response.getStatus());
		objToUpdate.setModifiedby(response.getModifiedby());
		
		objToUpdate = dashboardservice.updateAmlBatchCif(objToUpdate);
		
		final AmlBatchRequest req = dashboardservice.findByRequestId(objToUpdate.getRequestId());
		
		insertLogForAudit(DashboardConstant.AML_MESSAGE_2, req.getId(), response.getModifiedby());
		
		final AmlBatchCifResponse result = amlBatchCifConverter.convertFromEntity(objToUpdate);
				
		return result;
	}
	
	@RequestMapping(value = RestWebServiceUrl.FIND_AML_BATCH_CIF_BY_ID, method = RequestMethod.GET)
	public AmlBatchCifResponse findAmlBatchCifById(@PathVariable("id") Integer id){
		
		AmlBatchCif bean = dashboardservice.findAmlBatchCifById(id);
		
		final AmlBatchCifResponse result = amlBatchCifConverter.convertFromEntity(bean);
				
		return result;
	}
	
	
	/**
	 * Generate request id.
	 *
	 * @param id the id
	 * @return the formatted request id
	 */
	@RequestMapping(value = RestWebServiceUrl.GET_AML_BATCH_REQUEST_ID, method = RequestMethod.GET)
	public String generateRequestId(@PathVariable("id") Integer id){
		LOGGER.info("Generate Request Id");
		
		String requestId = null;
		requestId = dashboardservice.generateDashboardAmlBatchRequestId(id);
		
		return requestId;
	}
	
	private void insertLogForAudit(String description, Integer tableId, String createdBy){
		DashboardLog entity = new DashboardLog();
		
		entity.setTableId(tableId);
		entity.setTableName(DashboardConstant.AML_BATCH_TABLE_NAME);
		entity.setDescription(description);
		entity.setCreatedby(createdBy);
		
		dashboardservice.createDashboardLog(entity);
	}
	
	private void insertLogForAuditByTableName(String description, Integer tableId, String createdBy, String tableName){
		DashboardLog entity = new DashboardLog();
		
		entity.setTableId(tableId);
		entity.setTableName(tableName);
		entity.setDescription(description);
		entity.setCreatedby(createdBy);
		
		dashboardservice.createDashboardLog(entity);
	}
	
	@RequestMapping(value = RestWebServiceUrl.CREATE_AML_BATCH_REQUEST_UPLOAD, method = RequestMethod.POST)
	public DashboardUploadResponse createDashboardUpload(@RequestBody DashboardUploadResponse response) throws DashboardException{

		DashboardUpload upload = dashboardUploadConverter.convertFromBean(response);
		AmlBatchRequest entity = dashboardservice.findByRequestId(response.getRequestId());
		upload.setTableId(entity.getId());
		upload.setTableName(DashboardConstant.AML_BATCH_TABLE_NAME);
		try {
			upload = dashboardservice.createDashboardUpload(upload);
			
			insertLogForAuditByTableName(DashboardConstant.AML_MESSAGE_8 + " - " + upload.getFileName(), entity.getId(), response.getCreatedby(), DashboardConstant.DASHBOARD_UPLOAD_TABLE_NAME);  
			
		} catch (Exception e) {
			throw new DashboardException(e.getMessage(), e.getCause());
		}
		
		final DashboardUploadResponse result = dashboardUploadConverter.convertFromEntity(upload);
		
		return result; 
	}
	
	@RequestMapping(value = RestWebServiceUrl.FIND_AML_BATCH_UPLOAD_BY_ID, method = RequestMethod.GET)
	public DashboardUploadResponse findDashboardUploadById(@PathVariable("id") Long id){
		
		final DashboardUpload entity = dashboardservice.findDashboardUploadById(id);
		
		final DashboardUploadResponse response = dashboardUploadConverter.convertFromEntity(entity);
		
		return response;
	}
	
	@RequestMapping(value = RestWebServiceUrl.DELETE_AML_BATCH_UPLOAD_BY_ID, method = RequestMethod.DELETE)
	public String deleteDashboardUpload(@PathVariable("id") Long id){
		
		final DashboardUpload entity = dashboardservice.findDashboardUploadById(id);
		
		dashboardservice.deleteDashboardUpload(entity);
		
		return "OK!";
	}
	
	@RequestMapping(value = RestWebServiceUrl.CREATE_DASHBOARD_COMMENT, method = RequestMethod.POST)
	public DashboardCommentResponse createDashboardComment(@RequestBody DashboardCommentResponse response) throws DashboardException{

		DashboardComment upload = dashboardCommentConverter.convertFromBean(response);
		Integer tableId = response.getTableId();
		upload.setTableId(tableId);
		upload.setTableName(DashboardConstant.AML_BATCH_TABLE_NAME);
		try {
			upload = dashboardservice.createDashboardComment(upload);
			
			insertLogForAuditByTableName(DashboardConstant.AML_MESSAGE_9, tableId, response.getCreatedby(), DashboardConstant.DASHBOARD_COMMENT_TABLE_NAME);  
			
		} catch (Exception e) {
			throw new DashboardException(e.getMessage(), e.getCause());
		}
		
		final DashboardCommentResponse result = dashboardCommentConverter.convertFromEntity(upload);
		
		return result; 
	}
	
	@RequestMapping(value = RestWebServiceUrl.FIND_DASHBOARD_COMMENT_BY_ID, method = RequestMethod.GET)
	public DashboardCommentResponse findDashboardCommentById(@PathVariable("id") Integer id){
		
		final DashboardComment entity = dashboardservice.findDashboardCommentById(id);
		
		final DashboardCommentResponse response = dashboardCommentConverter.convertFromEntity(entity);
		
		return response;
	}
	
	@RequestMapping(value = RestWebServiceUrl.DELETE_DASHBOARD_COMMENT_BY_ID, method = RequestMethod.DELETE)
	public String deleteDashboardComment(@PathVariable("id") Integer id){
		
		final DashboardComment entity = dashboardservice.findDashboardCommentById(id);
		
		dashboardservice.deleteDashboardComment(entity);
		
		return "OK!";
	}
	
	@RequestMapping(value = RestWebServiceUrl.UPDATE_DASHBOARD_COMMENT, method = RequestMethod.POST)
	public DashboardCommentResponse updateDashboardComment(@RequestBody DashboardCommentResponse response){
		
		DashboardComment objToUpdate = dashboardservice.findDashboardCommentById(response.getId());
		objToUpdate.setMessage(response.getMessage());
		objToUpdate.setModifiedby(response.getModifiedby());
		
		insertLogForAudit(DashboardConstant.AML_MESSAGE_10, objToUpdate.getTableId(), response.getModifiedby());
		
		objToUpdate = dashboardservice.createDashboardComment(objToUpdate);
		
		final DashboardCommentResponse result = dashboardCommentConverter.convertFromEntity(objToUpdate);
				
		return result;
	}

}
