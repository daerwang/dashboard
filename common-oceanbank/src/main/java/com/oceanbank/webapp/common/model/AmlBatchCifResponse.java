/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.common.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


/**
 * The Class DashboardAmlBatchContainerResponse.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AmlBatchCifResponse {
	
	/** The id. */
	private Integer id;
    
    /** The request id. */
    private String requestId;
    
    /** The transaction type. */
    private String transactionType;
    
    /** The cif reference. */
    private String cifReference;
    
    /** The audit description. */
    private String auditDescription;
    
    /** The status. */
    private String status;
    private String iseriesname;
    
    private String createdby;
	private String modifiedby;
    
    /**
     * Instantiates a new dashboard aml batch container response.
     */
    public AmlBatchCifResponse(){}
    
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * Gets the request id.
	 *
	 * @return the request id
	 */
	public String getRequestId() {
		return requestId;
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
	 * Gets the cif reference.
	 *
	 * @return the cif reference
	 */
	public String getCifReference() {
		return cifReference;
	}
	
	/**
	 * Gets the audit description.
	 *
	 * @return the audit description
	 */
	public String getAuditDescription() {
		return auditDescription;
	}
	
	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Integer id) {
		this.id = id;
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
	 * Sets the transaction type.
	 *
	 * @param transactionType the new transaction type
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	
	/**
	 * Sets the cif reference.
	 *
	 * @param cifReference the new cif reference
	 */
	public void setCifReference(String cifReference) {
		this.cifReference = cifReference;
	}
	
	/**
	 * Sets the audit description.
	 *
	 * @param auditDescription the new audit description
	 */
	public void setAuditDescription(String auditDescription) {
		this.auditDescription = auditDescription;
	}
	
	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public String getModifiedby() {
		return modifiedby;
	}

	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
	}

	public String getIseriesname() {
		return iseriesname;
	}

	public void setIseriesname(String iseriesname) {
		this.iseriesname = iseriesname;
	}
	
}
