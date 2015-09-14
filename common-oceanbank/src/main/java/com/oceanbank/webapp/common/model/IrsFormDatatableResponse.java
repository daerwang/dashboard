/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.common.model;

import com.google.gson.annotations.SerializedName;

/**
 * The Class IrsFormDatatableResponse.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
public class IrsFormDatatableResponse {
	
	/** The mail code. */
	@SerializedName("0")
	private String mailCode;
	
	/** The recipient name. */
	@SerializedName("1")
	private String recipientName;
	
	/** The mail city state zip. */
	@SerializedName("2")
	private String mailCityStateZip;
	
	/** The foreign country. */
	@SerializedName("3")
	private String foreignCountry;
	
	/** The irs id. */
	@SerializedName("DT_RowId")
	private String irsId;
	
    
    
	/**
	 * Gets the recipient name.
	 *
	 * @return the recipient name
	 */
	public String getRecipientName() {
		return recipientName;
	}
	
	/**
	 * Sets the recipient name.
	 *
	 * @param recipientName the new recipient name
	 */
	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}

	/**
	 * Gets the foreign country.
	 *
	 * @return the foreign country
	 */
	public String getForeignCountry() {
		return foreignCountry;
	}
	
	/**
	 * Sets the foreign country.
	 *
	 * @param foreignCountry the new foreign country
	 */
	public void setForeignCountry(String foreignCountry) {
		this.foreignCountry = foreignCountry;
	}
	
	/**
	 * Gets the irs id.
	 *
	 * @return the irs id
	 */
	public String getIrsId() {
		return irsId;
	}
	
	/**
	 * Sets the irs id.
	 *
	 * @param irsId the new irs id
	 */
	public void setIrsId(String irsId) {
		this.irsId = irsId;
	}
	
	/**
	 * Gets the mail city state zip.
	 *
	 * @return the mail city state zip
	 */
	public String getMailCityStateZip() {
		return mailCityStateZip;
	}
	
	/**
	 * Sets the mail city state zip.
	 *
	 * @param mailCityStateZip the new mail city state zip
	 */
	public void setMailCityStateZip(String mailCityStateZip) {
		this.mailCityStateZip = mailCityStateZip;
	}
	
	/**
	 * Gets the mail code.
	 *
	 * @return the mail code
	 */
	public String getMailCode() {
		return mailCode;
	}
	
	/**
	 * Sets the mail code.
	 *
	 * @param mailCode the new mail code
	 */
	public void setMailCode(String mailCode) {
		this.mailCode = mailCode;
	}

}
