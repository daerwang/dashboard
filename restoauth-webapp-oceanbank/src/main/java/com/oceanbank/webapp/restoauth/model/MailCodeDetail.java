/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.restoauth.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



/**
 * The Class MailCodeDetail.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@Entity
@Table(name="CIS7349F2")
public class MailCodeDetail {
	
	/**
	 * Instantiates a new {@link MailCodeDetail}.
	 */
	public MailCodeDetail(){}
	
	@Id
	@Column(name="F2MAILC")
	private String mailCode;
	@Column(name="F2MAILD")
	private String mailDescription;
	@Column(name="F2COUNT")
	private String count;
	
	public String getMailCode() {
		return mailCode;
	}
	public void setMailCode(String mailCode) {
		this.mailCode = mailCode;
	}
	public String getMailDescription() {
		return mailDescription;
	}
	public void setMailDescription(String mailDescription) {
		this.mailDescription = mailDescription;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	
	
	
}
