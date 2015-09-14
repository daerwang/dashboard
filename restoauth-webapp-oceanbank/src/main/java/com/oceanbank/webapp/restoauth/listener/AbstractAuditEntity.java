/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.restoauth.listener;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * The Class AbstractAuditEntity.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@MappedSuperclass
@EntityListeners(value = { AuditingEntityListener.class })
public class AbstractAuditEntity {

	/** The createdby. */
	@CreatedBy
	private String createdby;

	/** The createdon. */
	@CreatedDate
	private Date createdon;

	/** The modifiedby. */
	@LastModifiedBy
	private String modifiedby;

	/** The modifiedon. */
	@LastModifiedDate
	private Date modifiedon;

	/**
	 * Gets the createdby.
	 *
	 * @return the createdby
	 */
	@Column(name = "CREATEDBY", length = 45)
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
	 * Gets the createdon.
	 *
	 * @return the createdon
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDON", length = 10)
	public Date getCreatedon() {
		return this.createdon;
	}

	/**
	 * Sets the createdon.
	 *
	 * @param createdon the new createdon
	 */
	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	/**
	 * Gets the modifiedby.
	 *
	 * @return the modifiedby
	 */
	@Column(name = "MODIFIEDBY", length = 45)
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

	/**
	 * Gets the modifiedon.
	 *
	 * @return the modifiedon
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFIEDON", length = 10)
	public Date getModifiedon() {
		return this.modifiedon;
	}

	/**
	 * Sets the modifiedon.
	 *
	 * @param modifiedon the new modifiedon
	 */
	public void setModifiedon(Date modifiedon) {
		this.modifiedon = modifiedon;
	}

}
