/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.common.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


/**
 * The Class MailCodeResponse.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MailCodeResponse {
	
	/**
	 * Instantiates a new mail code response.
	 */
	public MailCodeResponse(){}
	
	/** The code. */
	String code;
	
	/** The description. */
	String description;
	
	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * Sets the code.
	 *
	 * @param code the new code
	 */
	public void setCode(String code) {
		this.code = code;
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
}
