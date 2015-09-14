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

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * The Class AbstractAuditEntityTimestamp.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@MappedSuperclass
@EntityListeners(value = { AuditingEntityListener.class })
public class AbstractAuditEntityTimestamp {


	/** The createdon. */
	@CreatedDate
	private Date createdon;

	/** The modifiedon. */
	@LastModifiedDate
	private Date modifiedon;


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
