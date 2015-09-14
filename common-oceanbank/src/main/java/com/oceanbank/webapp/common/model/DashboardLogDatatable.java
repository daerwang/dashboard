package com.oceanbank.webapp.common.model;

import com.google.gson.annotations.SerializedName;

public class DashboardLogDatatable {
	
	
	@SerializedName("0")
	private String createdby;
	@SerializedName("1")
	private String description;
	@SerializedName("2")
	private String createdon;
	@SerializedName("DT_RowId")
	private String logId;
	
	
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCreatedon() {
		return createdon;
	}
	public void setCreatedon(String createdon) {
		this.createdon = createdon;
	}
	public String getLogId() {
		return logId;
	}
	public void setLogId(String logId) {
		this.logId = logId;
	}
	
}
