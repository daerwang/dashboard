/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.restoauth.converter;

import com.oceanbank.webapp.common.model.AmlBatchRequestResponse;
import com.oceanbank.webapp.restoauth.model.AmlBatchRequest;

/**
 * The Class AmlBatchRequestConverter.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
public class AmlBatchRequestConverter implements DashboardConverter<AmlBatchRequest, AmlBatchRequestResponse>{

	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.restoauth.converter.DashboardConverter#convertFromBean(java.lang.Object)
	 */
	@Override
	public AmlBatchRequest convertFromBean(AmlBatchRequestResponse response) {
		
		final String requestId = response.getRequestId();
		final String transactionType = response.getTransactionType();
		
		final AmlBatchRequest req =  new AmlBatchRequest();
		req.setId(response.getId());
		req.setName(response.getName());
		req.setDescription(response.getDescription());
		req.setRequestId(requestId);
		req.setTransactionType(transactionType);
		req.setStatus(response.getStatus());
		req.setBankSchema(response.getBankSchema());
		
		req.setCreatedby(response.getCreatedby());
		req.setModifiedby(response.getModifiedby());
		
		
		
		return req;
	}

	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.restoauth.converter.DashboardConverter#convertFromEntity(java.lang.Object)
	 */
	@Override
	public AmlBatchRequestResponse convertFromEntity(AmlBatchRequest entity) {
		
		final AmlBatchRequestResponse response = new AmlBatchRequestResponse();
		
		response.setId(entity.getId());
		response.setName(entity.getName());
		response.setDescription(entity.getDescription());
		response.setRequestId(entity.getRequestId());
		response.setTransactionType(entity.getTransactionType());
		response.setStatus(entity.getStatus());
		response.setBankSchema(entity.getBankSchema());
		
		response.setCreatedby(entity.getCreatedby());
		response.setCreatedOn(entity.getCreatedon());
		response.setModifiedby(entity.getModifiedby());
		response.setModifiedOn(entity.getModifiedon());
		

		return response;
	}

}
