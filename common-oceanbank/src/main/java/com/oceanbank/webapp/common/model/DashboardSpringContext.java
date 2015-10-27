/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.common.model;

import java.util.List;



/**
 * The Class DashboardSpringContext.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
public class DashboardSpringContext {
	
	/** The aml batch transaction type. */
	private List<String> amlBatchTransactionType;
	private List<String> banks;

	/**
	 * Gets the aml batch transaction type.
	 *
	 * @return the aml batch transaction type
	 */
	public List<String> getAmlBatchTransactionType() {
		return amlBatchTransactionType;
	}

	/**
	 * Sets the aml batch transaction type.
	 *
	 * @param amlBatchTransactionType the new aml batch transaction type
	 */
	public void setAmlBatchTransactionType(List<String> amlBatchTransactionType) {
		this.amlBatchTransactionType = amlBatchTransactionType;
	}

	public List<String> getBanks() {
		return banks;
	}

	public void setBanks(List<String> banks) {
		this.banks = banks;
	}


	
	
}
