package com.oceanbank.webapp.restoauth.controller;

import java.io.IOException;
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
import com.oceanbank.webapp.common.model.DataTablesRequest;
import com.oceanbank.webapp.common.model.IrsFormSelected;
import com.oceanbank.webapp.common.model.W8BeneFormResponse;
import com.oceanbank.webapp.restoauth.model.W8BeneForm;
import com.oceanbank.webapp.restoauth.service.W8BeneFormService;

@RestController
@RequestMapping("/api/w8beneform")
public class W8BeneFormController {

	@Autowired
	W8BeneFormService w8BeneFormService;
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@RequestMapping(value = "/createPdfToDisk", method = RequestMethod.POST)
	public String createPdfToDisk(@RequestBody IrsFormSelected selected) throws DashboardException, IOException{

		final Date startTime = new Date();
		LOGGER.info("Executing createPdfToDisk() Start time: " + startTime.toString());
		
		final List<W8BeneForm> list = w8BeneFormService.findByIds(selected);
		
		synchronized (this) {
			w8BeneFormService.createPdfToDisk(list);
		}
		
		final Date endTime = new Date();
		LOGGER.info("Process Completed generateSelectedMailCode(): " + endTime);
		final long timeTakenInSec = endTime.getTime() - startTime.getTime();
		LOGGER.info("Time taken: " + (timeTakenInSec / 1000) + " secs " + (timeTakenInSec % 1000) + " ms");
		
		return "OK!";
	}
	
	@RequestMapping(value = "/dataTable", method = RequestMethod.POST)
	public List<W8BeneFormResponse> getBySearchOnDatatables(@RequestBody DataTablesRequest datatableRequest){
	
		String searchParameter = datatableRequest.getValue();
    	List<W8BeneFormResponse> responseList = w8BeneFormService.findByDatatableSearch(searchParameter);
		
		
		return responseList;
	}
}
