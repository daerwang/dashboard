/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.restoauth.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToMany;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import com.oceanbank.webapp.restoauth.listener.AbstractAuditEntityTimestamp;


/**
 * The Class DashboardAmlBatchRequest.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@Entity
@Table(name="amlbatchrequest")
@NamedStoredProcedureQuery(name = "SPBSAGETLOGBYTYPE", procedureName = "XPERTV700.DASHBOARDSPBSAGETLOGBYTYPE", parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "I_AMLCBANKNU", type = Integer.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "I_AMLCTYPLOG", type = String.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "I_AMLCCIFCOD", type = String.class)})
@NamedQueries({ 
	  @NamedQuery(name="AmlBatchRequest.findByDatatableSearch",
	              query = "from AmlBatchRequest where name LIKE :search OR requestId LIKE :search OR description LIKE :search OR transactionType LIKE :search")
	})
public class AmlBatchRequest extends AbstractAuditEntityTimestamp implements java.io.Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

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
    private String bankSchema;
    
    /** The dashboardamlbatchcontainers. */
    private List<AmlBatchCif> dashboardamlbatchcontainers = new ArrayList<AmlBatchCif>();
    
    /** The createdby. */
	private String createdby;
	
	/** The modifiedby. */
	private String modifiedby;

   /**
    * Instantiates a new dashboard aml batch request.
    */
   public AmlBatchRequest() {
   }
   
   /**
    * Instantiates a new dashboard aml batch request.
    *
    * @param id the id
    */
   public AmlBatchRequest(Integer id){
	   this.id = id;
   }

   /**
    * Instantiates a new dashboard aml batch request.
    *
    * @param requestId the request id
    */
   public AmlBatchRequest(String requestId){
	   this.requestId = requestId;
   }
	
   /**
    * Instantiates a new dashboard aml batch request.
    *
    * @param requestId the request id
    * @param transactionType the transaction type
    */
   public AmlBatchRequest(String requestId, String transactionType) {
       this.requestId = requestId;
       this.transactionType = transactionType;
   }
   
   /**
    * Instantiates a new dashboard aml batch request.
    *
    * @param name the name
    * @param description the description
    * @param requestId the request id
    * @param transactionType the transaction type
    * @param dashboardamlbatchcontainers the dashboardamlbatchcontainers
    */
   public AmlBatchRequest(String name, String description, String requestId, String transactionType, List<AmlBatchCif> dashboardamlbatchcontainers) {
      this.name = name;
      this.description = description;
      this.requestId = requestId;
      this.transactionType = transactionType;
      this.dashboardamlbatchcontainers = dashboardamlbatchcontainers;
   }
  
   /**
    * Gets the id.
    *
    * @return the id
    */
   @Id 
   @GeneratedValue(strategy=GenerationType.IDENTITY)
   @Column(name="id", unique=true, nullable=false)
   public Integer getId() {
       return this.id;
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
    * Gets the name.
    *
    * @return the name
    */
   @Column(name="name", length=70)
   public String getName() {
       return this.name;
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
   @Column(name="description", length=200)
   public String getDescription() {
       return this.description;
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
    * Gets the request id.
    *
    * @return the request id
    */
   @Column(name="requestId", unique=true, nullable=false, length=45)
   public String getRequestId() {
       return this.requestId;
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
   @Column(name="transactionType", nullable=false, length=45)
   public String getTransactionType() {
       return this.transactionType;
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
    * Gets the dashboardamlbatchcontainers.
    *
    * @return the dashboardamlbatchcontainers
    */
   @OneToMany(fetch=FetchType.LAZY, mappedBy="dashboardamlbatchrequest")
   public List<AmlBatchCif> getDashboardamlbatchcontainers() {
       return this.dashboardamlbatchcontainers;
   }
   
   /**
    * Sets the dashboardamlbatchcontainers.
    *
    * @param dashboardamlbatchcontainers the new dashboardamlbatchcontainers
    */
   public void setDashboardamlbatchcontainers(List<AmlBatchCif> dashboardamlbatchcontainers) {
       this.dashboardamlbatchcontainers = dashboardamlbatchcontainers;
   }
   
   /**
	 * Gets the createdby.
	 *
	 * @return the createdby
	 */
	@Column(name = "CREATEDBY")
	public String getCreatedby() {
		return this.createdby;
	}
	
	/**
	 * Sets the createdby.
	 *
	 * @param createdby the new createdby
	 */
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	
	/**
	 * Gets the modifiedby.
	 *
	 * @return the modifiedby
	 */
	@Column(name = "MODIFIEDBY")
	public String getModifiedby() {
		return this.modifiedby;
	}

	/**
	 * Sets the modifiedby.
	 *
	 * @param modifiedby the new modifiedby
	 */
	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
	}

	@Column(name="status", nullable=false, length=45)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name="bankSchema", nullable=false, length=45)
	public String getBankSchema() {
		return bankSchema;
	}

	public void setBankSchema(String bankSchema) {
		this.bankSchema = bankSchema;
	}
}
