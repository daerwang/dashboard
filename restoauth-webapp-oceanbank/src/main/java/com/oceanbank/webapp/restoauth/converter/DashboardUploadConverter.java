package com.oceanbank.webapp.restoauth.converter;

import com.oceanbank.webapp.common.model.DashboardUploadResponse;
import com.oceanbank.webapp.restoauth.model.DashboardUpload;

public class DashboardUploadConverter implements DashboardConverter<DashboardUpload, DashboardUploadResponse>{

	@Override
	public DashboardUpload convertFromBean(DashboardUploadResponse response) {
		DashboardUpload upload = new DashboardUpload();
		if(response.getId() != 0)
			upload.setId(response.getId());
		upload.setTableId(response.getTableId());
		upload.setTableName(response.getTableName());
		upload.setDescription(response.getDescription());
		upload.setCreatedby(response.getCreatedby());
		upload.setCreatedon(response.getCreatedOn());
		upload.setModifiedby(response.getModifiedby());
		upload.setFileName(response.getFilename());
		
		return upload;
	}

	@Override
	public DashboardUploadResponse convertFromEntity(DashboardUpload entity) {
		
		DashboardUploadResponse response = new DashboardUploadResponse();
		response.setId(entity.getId());
		response.setTableId(entity.getTableId());
		response.setTableName(entity.getTableName());
		response.setDescription(entity.getDescription());
		response.setCreatedby(entity.getCreatedby());
		response.setCreatedOn(entity.getCreatedon());
		response.setModifiedby(entity.getModifiedby());
		response.setFilename(entity.getFileName());
		
		return response;
	}

}
