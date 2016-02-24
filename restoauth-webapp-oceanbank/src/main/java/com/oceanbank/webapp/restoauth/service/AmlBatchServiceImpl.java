/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.restoauth.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ComparisonChain;
import com.oceanbank.webapp.common.model.DashboardConstant;
import com.oceanbank.webapp.common.util.RestUtil;
import com.oceanbank.webapp.restoauth.dao.AmlBatchCifRepository;
import com.oceanbank.webapp.restoauth.dao.AmlBatchRequestRepository;
import com.oceanbank.webapp.restoauth.dao.DashboardCommentRepository;
import com.oceanbank.webapp.restoauth.dao.DashboardLogRepository;
import com.oceanbank.webapp.restoauth.dao.DashboardUploadRepository;
import com.oceanbank.webapp.restoauth.model.AmlBatchCif;
import com.oceanbank.webapp.restoauth.model.AmlBatchRequest;
import com.oceanbank.webapp.restoauth.model.DashboardComment;
import com.oceanbank.webapp.restoauth.model.DashboardLog;
import com.oceanbank.webapp.restoauth.model.DashboardUpload;


/**
 * The Class AmlBatchServiceImpl.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@Service
@Transactional(readOnly = true)
public class AmlBatchServiceImpl implements AmlBatchService{
	
	/** The aml batch container. */
	@Autowired
	private AmlBatchCifRepository amlBatchCif;
	
	/** The aml batch request. */
	@Autowired
	private AmlBatchRequestRepository amlBatchRequest;
	
	@Autowired
	private DashboardLogRepository dashboardLog;
	
	@Autowired
	private DashboardUploadRepository dashboardUpload;
	
	@Autowired
	private DashboardCommentRepository dashboardComment;
	
	@PersistenceContext
	EntityManager em;
	
	/**
	 * Instantiates a new {@link AmlBatchServiceImpl}.
	 */
	public AmlBatchServiceImpl(){}

	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.restoauth.service.AmlBatchService#generateDashboardAmlBatchRequestId(java.lang.Integer)
	 */
	@Override
	public String generateDashboardAmlBatchRequestId(Integer id) {

		final String prefix = "AML";
		final Integer year = Calendar.getInstance().get(Calendar.YEAR);
		final String generatedId = String.format("%04d", id); 
		
		final String requestId = prefix + "-" + year + "-" + generatedId; 
				
		return requestId;
	}

	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.restoauth.service.AmlBatchService#createAmlBatchContainer(com.oceanbank.webapp.restoauth.model.DashboardAmlBatchContainer)
	 */
	@Override
	public AmlBatchCif createAmlBatchCif(AmlBatchCif container) {
		
		final AmlBatchCif newContainer = amlBatchCif.save(container);
		
		return newContainer;
	}

	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.restoauth.service.AmlBatchService#createAmlBatchRequest(com.oceanbank.webapp.restoauth.model.DashboardAmlBatchRequest)
	 */
	@Transactional
	@Override
	public AmlBatchRequest createAmlBatchRequest(AmlBatchRequest entity) {
		
		// set requestId to white space first
		final Random randomGenerator = new Random();
		entity.setRequestId(randomGenerator.nextInt(100) + "");
		
		AmlBatchRequest newRequest = amlBatchRequest.save(entity);
		
		// then update requestId to be unique
		final String requestId = generateDashboardAmlBatchRequestId(newRequest.getId());
		newRequest.setRequestId(requestId);

		newRequest = amlBatchRequest.save(newRequest);

		return newRequest;
	}

	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.restoauth.service.AmlBatchService#updateAmlBatchRequest(com.oceanbank.webapp.restoauth.model.DashboardAmlBatchRequest)
	 */
	@Override
	public AmlBatchRequest updateAmlBatchRequest(AmlBatchRequest entity) {
		
		final AmlBatchRequest updated = amlBatchRequest.save(entity);

		return updated;
	}

	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.restoauth.service.AmlBatchService#findByRequestId(java.lang.String)
	 */
	@Override
	public AmlBatchRequest findByRequestId(String requestId) {
		
		return amlBatchRequest.findByRequestIdIs(requestId);
	}
	
	@Override
	public AmlBatchCif findAmlBatchCifById(Integer id){
		
		return amlBatchCif.findAmlBatchCifById(id);
	}

	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.restoauth.service.AmlBatchService#findAmlBatchByDatatableSearch(java.lang.String)
	 */
	@Override
	public List<AmlBatchRequest> findAmlBatchByDatatableSearch(String search) {
		
		List<AmlBatchRequest> entityList = null;

		// check search parameter if null or empty
		if (!RestUtil.isNullOrEmpty(search)) {
			entityList = amlBatchRequest.findByDatatableSearch("%"+ search + "%");
		} else {
//			Page <DashboardAmlBatchRequest> data  = amlBatchRequest.findAll(new PageRequest(0, 10000));
//			entityList = data.getContent();
			entityList = amlBatchRequest.findAll();
		}

		Collections.sort(entityList,new Comparator<AmlBatchRequest>() {

			@Override
			public int compare(AmlBatchRequest o1, AmlBatchRequest o2) {
				return ComparisonChain.start()
						.compare(o2.getId(), o1.getId())
						.result();
			}
		});

		return entityList;
	}

	@Override
	public List<AmlBatchCif> findAmlBatchCifByDatatableSearch(String search, String requestId){
		List<AmlBatchCif> entityList = null;

		if (!RestUtil.isNullOrEmpty(search)) {
			entityList = amlBatchCif.findByDatatableSearch("%"+ search + "%", requestId);
		} else {
			entityList = amlBatchCif.findByRequestIdIs(requestId, new PageRequest(0, 100, new Sort(Sort.Direction.DESC, "id")));
		}


		return entityList;
	}
	

	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.restoauth.service.AmlBatchService#deleteAmlBatchRequest(com.oceanbank.webapp.restoauth.model.DashboardAmlBatchRequest)
	 */
	@Override
	public void deleteAmlBatchRequest(AmlBatchRequest entity) {
		
		amlBatchRequest.delete(entity);
	}

	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.restoauth.service.AmlBatchService#deleteAmlBatchContainer(com.oceanbank.webapp.restoauth.model.DashboardAmlBatchContainer)
	 */
	@Override
	public void deleteAmlBatchCif(AmlBatchCif entity) {
		amlBatchCif.delete(entity);
		
	}

	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.restoauth.service.AmlBatchService#findAmlBatchRequestById(java.lang.Integer)
	 */
	@Override
	public AmlBatchRequest findAmlBatchRequestById(Integer id) {

		return amlBatchRequest.findByIdIs(id);
	}

	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.restoauth.service.AmlBatchService#findAmlBatchContainerByRequestId(java.lang.String)
	 */
	@Override
	public List<AmlBatchCif> findAmlBatchCifByRequestId(String requestId) {
		return amlBatchCif.findByRequestIdIs(requestId);
	}

	@Override
	public AmlBatchCif updateAmlBatchCif(AmlBatchCif container) {
		return amlBatchCif.save(container);
	}

	@Override
	public String executeAmlBatchRequestApproval(String requestId, String storedProcedure) {

		StoredProcedureQuery proc = em.createStoredProcedureQuery(storedProcedure);
		proc.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
		proc.registerStoredProcedureParameter(2, String.class, ParameterMode.OUT);

		proc.setParameter(1, requestId);
		proc.execute();
		
		final String result = (String) proc.getOutputParameterValue(2);
			
		return result;
	}
	
	@Override
	public String executeStoredProcedure(String requestId, String storedProcedureCommand) {

		StoredProcedureQuery proc = em.createStoredProcedureQuery(storedProcedureCommand);
		proc.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
		proc.registerStoredProcedureParameter(2, String.class, ParameterMode.OUT);

		proc.setParameter(1, requestId);
		proc.execute();

		final String result = (String) proc.getOutputParameterValue(2);

		return result;
	}

	@Override
	public String executeAmlBatchRequestReversal(String requestId, String storedProcedure) {
	
		StoredProcedureQuery proc = em.createStoredProcedureQuery(storedProcedure);
		proc.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
		proc.registerStoredProcedureParameter(2, String.class, ParameterMode.OUT);

		proc.setParameter(1, requestId);
		proc.execute();
		
		final String result = (String) proc.getOutputParameterValue(2);
			
		return result;
	}
	
	@Override
	public String executeAmlBatchRequestDisapproval(String requestId, String storedProcedure) {
		StoredProcedureQuery proc = em.createStoredProcedureQuery(storedProcedure);
		proc.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
		proc.registerStoredProcedureParameter(2, String.class, ParameterMode.OUT);

		proc.setParameter(1, requestId);
		proc.execute();
		
		final String result = (String) proc.getOutputParameterValue(2);
			
		return result;
	}

	@Override
	public String determineAmlBatchRequestStatus(String requestId) {
		String result = null;
		final List<AmlBatchCif> list = findAmlBatchCifByRequestId(requestId);
		
		if(list.isEmpty()){
			//result = DashboardConstant.AML_BATCH_STATUS_NO_CIF_TO_PROCESS;
			result = DashboardConstant.AML_BATCH_STATUS_AWAITING_EXECUTION;
		}else{
			final List<AmlBatchCif> list2 = amlBatchCif.findByRequestIdAndStatus(requestId, DashboardConstant.AML_BATCH_STATUS_CIF_NOT_FOUND);
			final List<AmlBatchCif> list3 = amlBatchCif.findByRequestIdAndStatus(requestId, DashboardConstant.AML_BATCH_STATUS_APPROVED);
			final List<AmlBatchCif> list4 = amlBatchCif.findByRequestIdAndStatus(requestId, DashboardConstant.AML_BATCH_STATUS_ONE_OR_MORE_CIF_NOT_FOUND);
			final List<AmlBatchCif> list5 = amlBatchCif.findByRequestIdAndStatus(requestId, DashboardConstant.AML_BATCH_STATUS_REVERSED);
			final List<AmlBatchCif> list6 = amlBatchCif.findByRequestIdAndStatus(requestId, DashboardConstant.AML_BATCH_STATUS_CIF_NOT_IN_REVERSAL_MEMORY);
			final List<AmlBatchCif> list7 = amlBatchCif.findByRequestIdAndStatus(requestId, DashboardConstant.AML_BATCH_STATUS_DISAPPROVED);
			if(!list2.isEmpty() || !list6.isEmpty() || !list4.isEmpty()){
				result = DashboardConstant.AML_BATCH_STATUS_NOT_COMPLETE;
			}else if(!list3.isEmpty() || !list5.isEmpty() || !list7.isEmpty()){
				// check if all CIF are approved
				if(list3.size()  == list.size() || list5.size()  == list.size() || list7.size()  == list.size()){
					result = DashboardConstant.AML_BATCH_STATUS_COMPLETE;
				}else{
					result = DashboardConstant.AML_BATCH_STATUS_AWAITING_EXECUTION;
				}
			}else{
				result = DashboardConstant.AML_BATCH_STATUS_AWAITING_EXECUTION;
			}
		}
		return result;
	}

	@Override
	@Transactional
	public String createManyAmlBatchCif(List<AmlBatchCif> cifList) {
		for(AmlBatchCif entity : cifList){
			amlBatchCif.save(entity);
		}
		return "OK";
	}

	@Override
	public DashboardLog createDashboardLog(DashboardLog entity) {
		return dashboardLog.save(entity);
	}

	@Override
	public String deleteDashboardLog(Long id) {
		dashboardLog.delete(id);
		return "OK";
	}

	@Override
	public List<DashboardLog> findDashboardLogByTableIdAndTableName(Integer tableId, String tableName) {
		
		return dashboardLog.findByTableIdAndTableName(tableId, tableName);
	}

	@Override
	public List<DashboardUpload> findDashboardUploadByTableIdAndTableName(Integer tableId, String tableName) {
		
		List<DashboardUpload> list = new ArrayList<DashboardUpload>();
		try {
			list = dashboardUpload.findByTableIdAndTableName(tableId, tableName);
		} catch (NullPointerException e) {
			// do nothing
		}
		
		return list;
	}
	
    public List<DashboardUpload> findDashboardUploadByTableName(String tableName) {

		List<DashboardUpload> list = new ArrayList<DashboardUpload>();
		try {
			list = dashboardUpload.findByTableName(tableName);
		} catch (NullPointerException e) {
			// do nothing
		}

		return list;
	}

    public List<DashboardUpload> findDashboardUploadByTableNameAndDescription(String tableName, String description){
		return dashboardUpload.findByTableNameAndDescription(tableName, description);
	}

	@Override
	public DashboardUpload findDashboardUploadById(Long id){
		return dashboardUpload.findAmlBatchUploadById(id.intValue());
	}

	@Override
	public DashboardUpload createDashboardUpload(DashboardUpload entity) {
		return dashboardUpload.save(entity);
	}

	@Override
	public String deleteDashboardUpload(DashboardUpload entity) {
		dashboardUpload.delete(entity);
		return "OK";
	}

	@Override
	public DashboardComment createDashboardComment(DashboardComment entity) {
		return dashboardComment.save(entity);
	}

	@Override
	public List<DashboardComment> findDashboardCommentByTableIdAndTableName(
			Integer tableId, String tableName) {
		List<DashboardComment> list = new ArrayList<DashboardComment>();
		try {
			list = dashboardComment.findByTableIdAndTableName(tableId, tableName);
		} catch (NullPointerException e) {
			// do nothing
		}
		
		return list;
	}

	@Override
	public DashboardComment findDashboardCommentById(Integer id) {
		return dashboardComment.findDashboardCommentById(id);
	}

	@Override
	public String deleteDashboardComment(DashboardComment entity) {
		dashboardComment.delete(entity);
		return "OK";
	}

	@Override
	public String findStoredProcedureByBank(String transactionType, String bankSchema) {
		String storedProcedure = null;

		if(transactionType.equalsIgnoreCase("approval")){
			if(bankSchema.equalsIgnoreCase("100")){
				storedProcedure = "IBMOB700.execute_aml_approval_103";
			}
			if(bankSchema.equalsIgnoreCase("16")){
				storedProcedure = "IBMOB700.execute_aml_approval_16";
			}
			if(bankSchema.equalsIgnoreCase("117")){
				storedProcedure = "IBMOB700.execute_aml_approval_117";
			}
			if(bankSchema.equalsIgnoreCase("118")){
				storedProcedure = "IBMOB700.execute_aml_approval_118";
			}
			if(bankSchema.equalsIgnoreCase("700")){
				storedProcedure = "IBMOB700.execute_aml_approval_700";
			}

			//outcome = executeAmlBatchRequestApproval(requestId, storedProcedure);
		}
		if(transactionType.equalsIgnoreCase("disapproval")){
			if(bankSchema.equalsIgnoreCase("100")){
				storedProcedure = "IBMOB700.execute_aml_disapproval_103";
			}
			if(bankSchema.equalsIgnoreCase("16")){
				storedProcedure = "IBMOB700.execute_aml_disapproval_16";
			}
			if(bankSchema.equalsIgnoreCase("117")){
				storedProcedure = "IBMOB700.execute_aml_disapproval_117";
			}
			if(bankSchema.equalsIgnoreCase("118")){
				storedProcedure = "IBMOB700.execute_aml_disapproval_118";
			}
			if(bankSchema.equalsIgnoreCase("700")){
				storedProcedure = "IBMOB700.execute_aml_disapproval_700";
			}

			//outcome = executeAmlBatchRequestDisapproval(requestId, storedProcedure);
		}

		return storedProcedure;
	}

}
