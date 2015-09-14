/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.restoauth.converter;

import com.oceanbank.webapp.common.model.AmlBatchCifResponse;
import com.oceanbank.webapp.restoauth.model.AmlBatchCif;

/**
 * The Class AmlBatchContainerConverter.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
public class AmlBatchCifConverter implements DashboardConverter<AmlBatchCif, AmlBatchCifResponse>{
	

	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.restoauth.converter.DashboardConverter#convertFromBean(java.lang.Object)
	 */
	@Override
	public AmlBatchCif convertFromBean(AmlBatchCifResponse response) {

		final AmlBatchCif cif = new AmlBatchCif();
				
		cif.setId(response.getId());
		cif.setRequestId(response.getRequestId());
		cif.setTransactionType(response.getTransactionType());
		cif.setCifReference(response.getCifReference());
		cif.setAuditDescription(response.getAuditDescription());
		cif.setStatus(response.getStatus());
		cif.setCreatedby(response.getCreatedby());
		cif.setModifiedby(response.getModifiedby());
		cif.setIseriesname(response.getIseriesname());
		
		return cif;
	}

	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.restoauth.converter.DashboardConverter#convertFromEntity(java.lang.Object)
	 */
	@Override
	public AmlBatchCifResponse convertFromEntity(AmlBatchCif entity) {

		final AmlBatchCifResponse response = new AmlBatchCifResponse();
		
		response.setId(entity.getId());
		response.setRequestId(entity.getRequestId());
		response.setTransactionType(entity.getTransactionType());
		response.setCifReference(entity.getCifReference());
		response.setAuditDescription(entity.getAuditDescription());
		response.setStatus(entity.getStatus());
		response.setCreatedby(entity.getCreatedby());
		response.setIseriesname(entity.getIseriesname());
		
		return response;
	}

}
