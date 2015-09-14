package com.oceanbank.webapp.common.model;

import com.google.gson.annotations.SerializedName;

public class DashboardUploadDatatable {
	
	
	@SerializedName("0")
	private String filename;
	@SerializedName("1")
	private String createdby;
	@SerializedName("2")
	private String createdon;
	@SerializedName("DT_RowId")
	private String uploadId;
	
	
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public String getCreatedon() {
		return createdon;
	}
	public void setCreatedon(String createdon) {
		this.createdon = createdon;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getUploadId() {
		return uploadId;
	}
	public void setUploadId(String uploadId) {
		this.uploadId = uploadId;
	}
	
}
