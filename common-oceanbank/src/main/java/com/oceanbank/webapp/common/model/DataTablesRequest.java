/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.common.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * The Class DataTablesRequest.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataTablesRequest {
	
	/**
	 * Instantiates a new data tables request.
	 */
	public DataTablesRequest(){}
	
	/** The draw. */
	private Integer draw;
	
	/** The start. */
	private Integer start;
	
	/** The length. */
	private Integer length;
	
	/** The value. */
	private String value;
	
	/** The mail code. */
	private String mailCode;
	
	private String amlBatchRequestId;
	private Integer amlRequestId;

	/**
	 * Gets the draw.
	 *
	 * @return the draw
	 */
	public Integer getDraw() {
		return draw;
	}
	
	/**
	 * Sets the draw.
	 *
	 * @param draw the new draw
	 */
	public void setDraw(Integer draw) {
		this.draw = draw;
	}
	
	/**
	 * Gets the start.
	 *
	 * @return the start
	 */
	public Integer getStart() {
		return start;
	}
	
	/**
	 * Sets the start.
	 *
	 * @param start the new start
	 */
	public void setStart(Integer start) {
		this.start = start;
	}
	
	/**
	 * Gets the length.
	 *
	 * @return the length
	 */
	public Integer getLength() {
		return length;
	}
	
	/**
	 * Sets the length.
	 *
	 * @param length the new length
	 */
	public void setLength(Integer length) {
		this.length = length;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(String value) {
		this.value = value;
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

	public String getAmlBatchRequestId() {
		return amlBatchRequestId;
	}

	public void setAmlBatchRequestId(String amlBatchRequestId) {
		this.amlBatchRequestId = amlBatchRequestId;
	}

	public Integer getAmlRequestId() {
		return amlRequestId;
	}

	public void setAmlRequestId(Integer amlRequestId) {
		this.amlRequestId = amlRequestId;
	}
}
