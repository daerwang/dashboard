package com.oceanbank.webapp.restoauth.converter;

import com.oceanbank.webapp.common.model.DashboardCommentResponse;
import com.oceanbank.webapp.restoauth.model.DashboardComment;

public class DashboardCommentConverter implements DashboardConverter<DashboardComment, DashboardCommentResponse>{

	@Override
	public DashboardComment convertFromBean(DashboardCommentResponse response) {
		DashboardComment upload = new DashboardComment();
		if(response.getId() != 0)
			upload.setId(response.getId());
		upload.setTableId(response.getTableId());
		upload.setTableName(response.getTableName());
		upload.setDescription(response.getDescription());
		upload.setCreatedby(response.getCreatedby());
		upload.setCreatedon(response.getCreatedOn());
		upload.setModifiedby(response.getModifiedby());
		upload.setMessage(response.getMessage());
		
		return upload;
	}

	@Override
	public DashboardCommentResponse convertFromEntity(DashboardComment entity) {
		
		DashboardCommentResponse response = new DashboardCommentResponse();
		response.setId(entity.getId());
		response.setTableId(entity.getTableId());
		response.setTableName(entity.getTableName());
		response.setDescription(entity.getDescription());
		response.setCreatedby(entity.getCreatedby());
		response.setCreatedOn(entity.getCreatedon());
		response.setModifiedby(entity.getModifiedby());
		response.setMessage(entity.getMessage());
		
		return response;
	}

}
