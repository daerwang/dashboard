/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.common.model;

import com.google.gson.annotations.SerializedName;

/**
 * The Class AmlBatchCifDatatable.
 * 
 * @author Marinell Medina
 * @since 04.16.2015
 */
public class AmlBatchCifDatatable {
	
	@SerializedName("0")
	private String cifReference;
	
	@SerializedName("1")
	private String auditDescription;
	
	@SerializedName("2")
	private String status;
	
	@SerializedName("3")
	private String iseriesname;
	
	@SerializedName("DT_RowId")
	private String amlBatchCifId;

	public String getCifReference() {
		return cifReference;
	}

	public void setCifReference(String cifReference) {
		this.cifReference = cifReference;
	}

	public String getAuditDescription() {
		return auditDescription;
	}

	public void setAuditDescription(String auditDescription) {
		this.auditDescription = auditDescription;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAmlBatchCifId() {
		return amlBatchCifId;
	}

	public void setAmlBatchCifId(String amlBatchCifId) {
		this.amlBatchCifId = amlBatchCifId;
	}

	public String getIseriesname() {
		return iseriesname;
	}

	public void setIseriesname(String iseriesname) {
		this.iseriesname = iseriesname;
	}
}
