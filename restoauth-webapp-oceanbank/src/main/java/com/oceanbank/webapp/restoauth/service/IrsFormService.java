/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.restoauth.service;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.oceanbank.webapp.common.exception.DashboardException;
import com.oceanbank.webapp.common.model.IrsFormCustomerResponse;
import com.oceanbank.webapp.common.model.IrsFormSelected;
import com.oceanbank.webapp.restoauth.model.BankDetail;
import com.oceanbank.webapp.restoauth.model.IrsFormCustomer;
import com.oceanbank.webapp.restoauth.model.MailCodeDetail;

/**
 * The Interface for the implementation of service for IRS 1042-S Form for 2014.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
public interface IrsFormService {
	
	/**
	 * Gets a list of all {@link IrsFormCustomer}.
	 */
	List<IrsFormCustomer> getAllIrsFormCustomer();
	
	/**
	 * Gets a list {@link IrsFormCustomer} with paging support.
	 *
	 * @param the Spring JPA pageable object
	 */
	List<IrsFormCustomer> getIrsCustomersByPage(Pageable pageable);

	/**
	 * Finds a {@link IrsFormCustomer} by name and paging support.
	 *
	 * @param the name of customer
	 * @param the Spring JPA pageable object
	 */
	List<IrsFormCustomer> findCustomersByNameAndPageable(String name, Pageable pageable);
	
	/**
	 * Finds a {@link IrsFormCustomer} by name.
	 *
	 * @param the name of customer
	 * @return the list
	 */
	List<IrsFormCustomer> findByShortName(String name);
	
	/**
	 * Finds a list of {@link IrsFormCustomer} by name.
	 *
	 * @param the name of customer
	 * @param the mail code of customer
	 */
	List<IrsFormCustomerResponse> findByDatatableSearch(String search, String mailCode)  throws Exception;

	/**
	 * Creates PDF file saved on server disk for selected records of {@link IrsFormCustomer}.
	 *
	 * @param the list of {@link IrsFormCustomer} for processing
	 * @return returns a String value if successful
	 * @throws DashboardException which encapsulates any error handled by Spring RestTemplate
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	void generateSelectedPdf(List<IrsFormCustomer> list) throws IOException, DashboardException;
	
	/**
	 * Gets a list of {@link IrsFormCustomer} by {@link IrsFormSelected} or keys in an array.
	 *
	 * @param the {@link IrsFormSelected} containing the keys in a String array
	 * @throws DashboardException which encapsulates any error handled by Spring RestTemplate
	 */
	List<IrsFormCustomer> getIrsFormCustomerBySelected(IrsFormSelected selected)  throws DashboardException;
	
	/**
	 * Gets a list of all {@link BankDetail}.
	 *
	 */
	List<BankDetail> getAllBankDetails();
	
	/**
	 * Gets a list of {@link BankDetail} by bank name.
	 *
	 * @param the bank name
	 */
	BankDetail findBankDetailByName(String bankName);
	
	/**
	 * Gets a list of all {@link MailCodeDetail}.
	 *
	 */
	List<MailCodeDetail> getAllMailCodeDetails();
	
	/**
	 * Gets a list of {@link MailCodeDetail} by mail code.
	 *
	 * @param the mail code
	 */
	MailCodeDetail getMailCodeByCode(String mailCode);
	
	/**
	 * Find a list of distinct mail code.
	 *
	 * @return the list
	 */
	List<String> findByMailCodeDistinct();
	
	/**
	 * Gets a list of {@link IrsFormCustomer} by a list of mail code.
	 *
	 * @param list of mail code
	 */
	List<IrsFormCustomer> findByMailCode(List<String> codes);
	
	/**
	 * Gets a list of {@link IrsFormCustomer} by {@link IrsFormSelected} or keys in an array.
	 *
	 * @param the {@link IrsFormSelected} containing the keys in a String array
	 * @throws DashboardException which encapsulates any error handled by Spring RestTemplate
	 */
	List<IrsFormCustomer> getIrsFormCustomerBySelectedMailCode(IrsFormSelected selected)  throws DashboardException;
}
