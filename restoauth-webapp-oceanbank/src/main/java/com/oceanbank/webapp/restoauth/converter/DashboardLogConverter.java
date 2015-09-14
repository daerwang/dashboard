package com.oceanbank.webapp.restoauth.converter;

import com.oceanbank.webapp.common.model.DashboardLogResponse;
import com.oceanbank.webapp.restoauth.model.DashboardLog;

public class DashboardLogConverter implements DashboardConverter<DashboardLog, DashboardLogResponse>{

	@Override
	public DashboardLog convertFromBean(DashboardLogResponse response) {
		DashboardLog log = new DashboardLog();
		log.setId(response.getId());
		log.setTableId(response.getTableId());
		log.setTableName(response.getTableName());
		log.setDescription(response.getDescription());
		log.setCreatedby(response.getCreatedby());
		log.setCreatedon(response.getCreatedOn());
		log.setModifiedby(response.getModifiedby());
		
		return log;
	}

	@Override
	public DashboardLogResponse convertFromEntity(DashboardLog entity) {
		
		DashboardLogResponse response = new DashboardLogResponse();
		response.setId(entity.getId());
		response.setTableId(entity.getTableId());
		response.setTableName(entity.getTableName());
		response.setDescription(entity.getDescription());
		response.setCreatedby(entity.getCreatedby());
		response.setCreatedOn(entity.getCreatedon());
		response.setModifiedby(entity.getModifiedby());
		
		return response;
	}

}
