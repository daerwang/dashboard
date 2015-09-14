/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.restoauth.model;

/**
 * The Enum DashboardTransactionType.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
public enum AmlBatchTransactionType {
	
	/** The approval. */
	APPROVAL("Approval"), 
	/** The disapproval. */
	DISAPPROVAL("Disapproval"), 
	/** The reversal. */
	REVERSAL("Reversal");
	
	/** The value. */
	private String value;
	
	/**
	 * Instantiates a new dashboard transaction type.
	 *
	 * @param value the value
	 */
	private AmlBatchTransactionType(String value){
		this.setValue(value);
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
}
