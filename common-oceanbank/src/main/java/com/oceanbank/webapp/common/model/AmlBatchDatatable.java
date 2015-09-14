/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.common.model;

import com.google.gson.annotations.SerializedName;

/**
 * The Class AmlBatchDatatableResponse.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
public class AmlBatchDatatable {
	
	/** The request id. */
	@SerializedName("0")
	private String requestId;
	
	/** The transaction type. */
	@SerializedName("1")
	private String transactionType;
	
	/** The name. */
	@SerializedName("2")
	private String name;
	
	/** The description. */
	@SerializedName("3")
	private String description;
	
	/** The batch status. */
	@SerializedName("4")
	private String batchStatus;
	
	/** The created by. */
	@SerializedName("5")
	private String createdBy;
	
	/** The created on. */
	@SerializedName("6")
	private String createdOn;
	
	@SerializedName("7")
	private String modifiedBy;
	
	@SerializedName("8")
	private String modifiedOn;
    
    /** The aml batch id. */
    @SerializedName("DT_RowId")
	private String amlBatchId;
    
    
    
	/**
	 * Gets the request id.
	 *
	 * @return the request id
	 */
	public String getRequestId() {
		return requestId;
	}
	
	/**
	 * Sets the request id.
	 *
	 * @param requestId the new request id
	 */
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	
	/**
	 * Gets the transaction type.
	 *
	 * @return the transaction type
	 */
	public String getTransactionType() {
		return transactionType;
	}
	
	/**
	 * Sets the transaction type.
	 *
	 * @param transactionType the new transaction type
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Gets the created by.
	 *
	 * @return the created by
	 */
	public String getCreatedBy() {
		return createdBy;
	}
	
	/**
	 * Sets the created by.
	 *
	 * @param createdBy the new created by
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	/**
	 * Gets the created on.
	 *
	 * @return the created on
	 */
	public String getCreatedOn() {
		return createdOn;
	}
	
	/**
	 * Sets the created on.
	 *
	 * @param createdOn the new created on
	 */
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	
	/**
	 * Gets the aml batch id.
	 *
	 * @return the aml batch id
	 */
	public String getAmlBatchId() {
		return amlBatchId;
	}
	
	/**
	 * Sets the aml batch id.
	 *
	 * @param amlBatchId the new aml batch id
	 */
	public void setAmlBatchId(String amlBatchId) {
		this.amlBatchId = amlBatchId;
	}
	
	/**
	 * Gets the batch status.
	 *
	 * @return the batch status
	 */
	public String getBatchStatus() {
		return batchStatus;
	}
	
	/**
	 * Sets the batch status.
	 *
	 * @param batchStatus the new batch status
	 */
	public void setBatchStatus(String batchStatus) {
		this.batchStatus = batchStatus;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(String modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
    
}
