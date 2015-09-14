/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.restoauth.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import com.oceanbank.webapp.restoauth.listener.AbstractAuditEntityTimestamp;


/**
 * The Class DashboardAmlBatchCif.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@Entity
@Table(name="amlbatchcif")
@NamedQueries({
	  @NamedQuery(name="AmlBatchCif.findAmlBatchCifById",
	              query = "from AmlBatchCif where id = :id"),
	  @NamedQuery(name="AmlBatchCif.findByDatatableSearch",
	              query = "from AmlBatchCif where requestId = :requestId AND (cifReference LIKE :search OR auditDescription LIKE :search)")
	})
public class AmlBatchCif extends AbstractAuditEntityTimestamp implements java.io.Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The id. */
	private Integer id;
    
    /** The dashboardamlbatchrequest. */
    private AmlBatchRequest dashboardamlbatchrequest;
    
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
    
    /** The createdby. */
	private String createdby;
	
	/** The modifiedby. */
	private String modifiedby;
	
	private String iseriesname;
	
	/**
	 * Instantiates a new dashboard aml batch container.
	 */
	public AmlBatchCif() {
    }

	
    /**
     * Instantiates a new dashboard aml batch container.
     *
     * @param dashboardamlbatchrequest the dashboardamlbatchrequest
     * @param requestId the request id
     * @param transactionType the transaction type
     * @param cifReference the cif reference
     * @param auditDescription the audit description
     */
    public AmlBatchCif(AmlBatchRequest dashboardamlbatchrequest, String requestId, String transactionType, String cifReference, String auditDescription) {
        this.dashboardamlbatchrequest = dashboardamlbatchrequest;
        this.requestId = requestId;
        this.transactionType = transactionType;
        this.cifReference = cifReference;
        this.auditDescription = auditDescription;
    }
    
    /**
     * Instantiates a new dashboard aml batch container.
     *
     * @param dashboardamlbatchrequest the dashboardamlbatchrequest
     * @param requestId the request id
     * @param transactionType the transaction type
     * @param cifReference the cif reference
     * @param auditDescription the audit description
     * @param status the status
     */
    public AmlBatchCif(AmlBatchRequest dashboardamlbatchrequest, String requestId, String transactionType, String cifReference, String auditDescription, String status) {
       this.dashboardamlbatchrequest = dashboardamlbatchrequest;
       this.requestId = requestId;
       this.transactionType = transactionType;
       this.cifReference = cifReference;
       this.auditDescription = auditDescription;
       this.status = status;
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
     * Gets the dashboardamlbatchrequest.
     *
     * @return the dashboardamlbatchrequest
     */
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="amlBatchRequest_id", nullable=false)
    public AmlBatchRequest getDashboardamlbatchrequest() {
        return this.dashboardamlbatchrequest;
    }
    
    /**
     * Sets the dashboardamlbatchrequest.
     *
     * @param dashboardamlbatchrequest the new dashboardamlbatchrequest
     */
    public void setDashboardamlbatchrequest(AmlBatchRequest dashboardamlbatchrequest) {
        this.dashboardamlbatchrequest = dashboardamlbatchrequest;
    }

    
    /**
     * Gets the request id.
     *
     * @return the request id
     */
    @Column(name="requestId", nullable=false, length=45)
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
     * Gets the cif reference.
     *
     * @return the cif reference
     */
    @Column(name="cifReference", nullable=false, length=45)
    public String getCifReference() {
        return this.cifReference;
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
     * Gets the audit description.
     *
     * @return the audit description
     */
    @Column(name="auditDescription", nullable=false, length=200)
    public String getAuditDescription() {
        return this.auditDescription;
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
     * Gets the status.
     *
     * @return the status
     */
    @Column(name="status", length=45)
    public String getStatus() {
        return this.status;
    }
    
    /**
     * Sets the status.
     *
     * @param status the new status
     */
    public void setStatus(String status) {
        this.status = status;
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
	
	@Column(name = "ISERIESNAME", length = 45)
	public String getIseriesname() {
		return iseriesname;
	}

	public void setIseriesname(String iseriesname) {
		this.iseriesname = iseriesname;
	}
    
}
