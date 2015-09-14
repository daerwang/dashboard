/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.dashboard.service;

import java.util.List;

import com.oceanbank.webapp.common.model.DataTablesRequest;
import com.oceanbank.webapp.common.model.IrsFormCustomerResponse;
import com.oceanbank.webapp.common.model.IrsFormSelected;
import com.oceanbank.webapp.common.model.MailCodeResponse;

/**
 * The Interface IrsFormService.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
public interface IrsFormService {
	
	/**
	 * Creates the pdf to disk.
	 *
	 * @param selected the selected
	 * @return the string
	 */
	String createPdfToDisk(IrsFormSelected selected);
	
	/**
	 * Creates the pdf to disk all.
	 *
	 * @return the string
	 */
	String createPdfToDiskAll();
	
	/**
	 * Creates the pdf mail code to disk.
	 *
	 * @param selected the selected
	 * @return the string
	 */
	String createPdfMailCodeToDisk(IrsFormSelected selected);
	
	/**
	 * Search irs form data table.
	 *
	 * @param datatableRequest the datatable request
	 * @return the list
	 */
	List<IrsFormCustomerResponse> searchIrsFormDataTable(DataTablesRequest datatableRequest);
	
	/**
	 * Gets the mail codes.
	 *
	 * @return the mail codes
	 */
	List<MailCodeResponse> getMailCodes();
}
