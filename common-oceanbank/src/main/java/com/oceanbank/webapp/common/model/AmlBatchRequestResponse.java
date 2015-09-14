/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.common.model;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.oceanbank.webapp.common.handler.JsonDateSerializer;

/**
 * The Class DashboardAmlBatchRequestResponse.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AmlBatchRequestResponse {
	
	/** The id. */
	private Integer id;
    
    /** The name. */
    private String name;
    
    /** The description. */
    private String description;
    
    /** The request id. */
    private String requestId;
    
    /** The transaction type. */
    private String transactionType;
    private String status;
    
    /** The createdby. */
    private String createdby;
	
	/** The modifiedby. */
	private String modifiedby;
    
    /** The created on. */
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date createdOn;
    
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date modifiedOn;
    
    private String[] selectableTypes;
    
    
    /**
     * Instantiates a new dashboard aml batch request response.
     */
    public AmlBatchRequestResponse(){}
    
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Integer getId() {
		return id;
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
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
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
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Integer id) {
		this.id = id;
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
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * Gets the created on.
	 *
	 * @return the created on
	 */
	public Date getCreatedOn() {
		return createdOn;
	}
	
	/**
	 * Sets the created on.
	 *
	 * @param createdOn the new created on
	 */
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String[] getSelectableTypes() {
		return selectableTypes;
	}

	public void setSelectableTypes(String[] selectableTypes) {
		this.selectableTypes = selectableTypes;
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

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
    
}
