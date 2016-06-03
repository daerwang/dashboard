package com.oceanbank.webapp.common.model;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.oceanbank.webapp.common.handler.JsonDateSerializer;


@JsonIgnoreProperties(ignoreUnknown = true)
public class AmlBatchRequestResponse {
	
	
	private Integer id;
    private String name;
    private String description;
    private String requestId;
    private String transactionType;
    private String status;
    private String bankSchema;
    private String createdby;
	private String modifiedby;
    
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date createdOn;
    
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date modifiedOn;
    
    private String[] selectableTypes;
    private String[] bankSchemas;
    
    
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

	public String[] getBankSchemas() {
		return bankSchemas;
	}

	public void setBankSchemas(String[] bankSchemas) {
		this.bankSchemas = bankSchemas;
	}

	public String getBankSchema() {
		return bankSchema;
	}

	public void setBankSchema(String bankSchema) {
		this.bankSchema = bankSchema;
	}
    
    
}
