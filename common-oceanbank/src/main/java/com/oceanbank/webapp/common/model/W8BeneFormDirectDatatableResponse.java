package com.oceanbank.webapp.common.model;

import com.google.gson.annotations.SerializedName;


public class W8BeneFormDirectDatatableResponse {
	

	@SerializedName("0")
	private String cif;

	@SerializedName("1")
	private String name;

	@SerializedName("2")
	private String physicalCountryInc;
	
	@SerializedName("3")
	private String officerCode;
	
	@SerializedName("DT_RowId")
	private String w8BeneFormId;
	
	
	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhysicalCountryInc() {
		return physicalCountryInc;
	}

	public void setPhysicalCountryInc(String physicalCountryInc) {
		this.physicalCountryInc = physicalCountryInc;
	}

	public String getW8BeneFormId() {
		return w8BeneFormId;
	}

	public void setW8BeneFormId(String w8BeneFormId) {
		this.w8BeneFormId = w8BeneFormId;
	}

	public String getOfficerCode() {
		return officerCode;
	}

	public void setOfficerCode(String officerCode) {
		this.officerCode = officerCode;
	}
    
 }
