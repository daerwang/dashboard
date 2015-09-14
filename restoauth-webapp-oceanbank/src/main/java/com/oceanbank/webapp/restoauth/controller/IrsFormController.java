/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */

package com.oceanbank.webapp.restoauth.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.oceanbank.webapp.common.exception.DashboardException;
import com.oceanbank.webapp.common.model.DashboardConstant;
import com.oceanbank.webapp.common.model.DataTablesRequest;
import com.oceanbank.webapp.common.model.IrsFormCustomerResponse;
import com.oceanbank.webapp.common.model.IrsFormSelected;
import com.oceanbank.webapp.common.model.MailCodeResponse;
import com.oceanbank.webapp.restoauth.model.IrsFormCustomer;
import com.oceanbank.webapp.restoauth.model.MailCodeDetail;
import com.oceanbank.webapp.common.model.RestWebServiceUrl;
import com.oceanbank.webapp.restoauth.service.IrsFormServiceImpl;


/**
 * The REST controller class for IRS 1042-S Form for 2014.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@RestController
public class IrsFormController{

	/** The irs form service. */
	@Autowired
	private IrsFormServiceImpl irsFormService;
	
	/** The logger. */
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	

	/**
	 * Instantiates a new {@link IrsFormController}.
	 */
	public IrsFormController(){}
	
	/**
	 * Creates PDF file saved on server disk for selected mail code records of {@link IrsFormCustomer}.
	 *
	 * @param the {@link IrsFormSelected} containing the keys in a String array
	 * @return returns a String value if successful
	 * @throws DashboardException which encapsulates any error handled by Spring RestTemplate
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@RequestMapping(value = RestWebServiceUrl.GENERATE_SELECTED_PDF_MAIL_CODE, method = RequestMethod.POST)
	public String generateSelectedMailCode(@RequestBody IrsFormSelected selected) throws DashboardException, IOException{

		final Date startTime = new Date();
		LOGGER.info("Executing generateSelectedMailCode() Start time: " + startTime.toString());
		
		final List<IrsFormCustomer> list = irsFormService.getIrsFormCustomerBySelectedMailCode(selected);
		
		irsFormService.generateSelectedPdf(list);
		
		final Date endTime = new Date();
		LOGGER.info("Process Completed generateSelectedMailCode(): " + endTime);
		final long timeTakenInSec = endTime.getTime() - startTime.getTime();
		LOGGER.info("Time taken: " + (timeTakenInSec / 1000) + " secs " + (timeTakenInSec % 1000) + " ms");
		
		return "OK!";
	}

	/**
	 * Return a list of {@link MailCodeResponse} that has distinct mail code.
	 * 
	 */
	@RequestMapping(value = RestWebServiceUrl.GET_IRS_MAIL_CODE_DISTINCT, method = RequestMethod.POST)
	public List<MailCodeResponse> getIrsMailCodeDistinct(){
		final List<MailCodeResponse> list = new ArrayList<MailCodeResponse>();
		final List<String> codes = irsFormService.findByMailCodeDistinct();
		final List<MailCodeDetail> mailCodes = irsFormService.getAllMailCodeDetails();
		
		for(String s: codes){
			StringBuilder b = new StringBuilder();
			b.append(s.trim().length() > 0 ? s: DashboardConstant.MAIL_CODE_BLANK);
			// get equivalent description
			for(MailCodeDetail d: mailCodes){
				if(d.getMailCode().trim().equalsIgnoreCase(s.trim())){
					b.append(", " + d.getMailDescription() + " - " + d.getCount());
					break;
				}
			}
			
			final MailCodeResponse response = new MailCodeResponse();
			response.setCode(s);
			response.setDescription(b.toString());
			list.add(response);
		}
		
		return list;
	}
	
	/**
	 * Creates PDF file saved on server disk for selected records of {@link IrsFormCustomer}.
	 *
	 * @param the {@link IrsFormSelected} containing the keys in a String array
	 * @return returns a String value if successful
	 * @throws DashboardException which encapsulates any error handled by Spring RestTemplate
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@RequestMapping(value = RestWebServiceUrl.GENERATE_SELECTED_PDF, method = RequestMethod.POST)
	public String generateSelectedPdf(@RequestBody IrsFormSelected selected) throws IOException, DashboardException{
		
		final Date startTime = new Date();
		LOGGER.info("Executing generateSelectedPdf() Start time: " + startTime.toString());
		
		final List<IrsFormCustomer> list = irsFormService.getIrsFormCustomerBySelected(selected);
		
		irsFormService.generateSelectedPdf(list);
		
		final Date endTime = new Date();
		LOGGER.info("Process Completed generateSelectedPdf(): " + endTime);
		final long timeTakenInSec = endTime.getTime() - startTime.getTime();
		LOGGER.info("Time taken: " + (timeTakenInSec / 1000) + " secs " + (timeTakenInSec % 1000) + " ms");
		
		return "OK!";
	}
	
	/**
	 * Creates PDF file saved on server disk for all records of {@link IrsFormCustomer}.
	 *
	 * @return returns a String value if successful
	 * @throws DashboardException which encapsulates any error handled by Spring RestTemplate
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@RequestMapping(value = RestWebServiceUrl.GENERATE_SELECTED_PDF_ALL, method = RequestMethod.POST)
	public String generateSelectedPdfAll() throws IOException, DashboardException{
		
		final Date startTime = new Date();
		LOGGER.info("Executing generateSelectedPdfAll() Start time: " + startTime.toString());
		
		final List<IrsFormCustomer> list = irsFormService.getAllIrsFormCustomer();
		
		irsFormService.generateSelectedPdf(list);
		
		final Date endTime = new Date();
		LOGGER.info("Process Completed: " + endTime);
		final long timeTakenInSec = endTime.getTime() - startTime.getTime();
		LOGGER.info("Time taken: " + (timeTakenInSec / 1000) + " secs " + (timeTakenInSec % 1000) + " ms");
		
		return "OK!";
	}
	
	/**
	 * Gets the datatable object for IRS 1042-S Form for 2014.
	 *
	 * @param pass {@link DataTablesRequest} for the criteria of search
	 * @return the list of object to create the datatable 
	 * @throws Exception 
	 */
	@RequestMapping(value = RestWebServiceUrl.GET_IRS_FORM_SEARCH_BY_DATATABLE, method = RequestMethod.POST)
	public List<IrsFormCustomerResponse> getIrsBySearchOnDatatables(@RequestBody DataTablesRequest datatableRequest) throws Exception{
	
		Integer pageLength = 0;
    	Integer pageNumber = 0;
		
    	final String searchParameter = datatableRequest.getValue();
		pageLength = datatableRequest.getLength();
    	pageNumber = datatableRequest.getStart();
    	final String mailCode = datatableRequest.getMailCode();
		
		LOGGER.info("Getting Datatable request for page length " + pageLength + " and under page number " + pageNumber + ".");
		
		final List<IrsFormCustomerResponse> irsSearchList = irsFormService.findByDatatableSearch(searchParameter, mailCode);
		
		
		return irsSearchList;
	}
	
	
}
