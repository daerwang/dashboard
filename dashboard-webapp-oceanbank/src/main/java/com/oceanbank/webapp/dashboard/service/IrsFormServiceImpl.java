/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.dashboard.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.oceanbank.webapp.common.model.DataTablesRequest;
import com.oceanbank.webapp.common.model.IrsFormCustomerResponse;
import com.oceanbank.webapp.common.model.IrsFormSelected;
import com.oceanbank.webapp.common.model.MailCodeResponse;
import com.oceanbank.webapp.common.model.OauthTokenBean;
import com.oceanbank.webapp.common.model.RestWebServiceUrl;
import com.oceanbank.webapp.common.util.CommonUtil;



/**
 * The Class IrsFormServiceImpl.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@Service
public class IrsFormServiceImpl extends OauthTokenBean implements IrsFormService{

	/** The rest template. */
	@Autowired
	private RestTemplate restTemplate;
	
	/**
	 * Instantiates a new irs form service impl.
	 */
	public IrsFormServiceImpl(){}
	
	
	
	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.dashboard.service.IrsFormService#getMailCodes()
	 */
	@Override
	public List<MailCodeResponse> getMailCodes(){

		final HttpEntity<String> entity = CommonUtil.createHttpEntityJson(getAccessToken());

		final ResponseEntity<MailCodeResponse[]> response = restTemplate.exchange(getRestApi()
				+ RestWebServiceUrl.GET_IRS_MAIL_CODE_DISTINCT, HttpMethod.POST, entity,
				MailCodeResponse[].class);

		final List<MailCodeResponse> list = Arrays.asList(response.getBody());

		return list;
	}
	
	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.dashboard.service.IrsFormService#searchIrsFormDataTable(com.oceanbank.webapp.common.model.DataTablesRequest)
	 */
	@Override
	public List<IrsFormCustomerResponse> searchIrsFormDataTable(DataTablesRequest datatableRequest) {
		
		final HttpEntity<DataTablesRequest> entity = CommonUtil.createHttpEntityWithParameters(getAccessToken(), datatableRequest);

		final ResponseEntity<IrsFormCustomerResponse[]> response = restTemplate.exchange(getRestApi()
				+ RestWebServiceUrl.GET_IRS_FORM_SEARCH_BY_DATATABLE, HttpMethod.POST, entity,
				IrsFormCustomerResponse[].class);

		final List<IrsFormCustomerResponse> list = Arrays.asList(response.getBody());

		return list;
	}
	
	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.dashboard.service.IrsFormService#createPdfMailCodeToDisk(com.oceanbank.webapp.common.model.IrsFormSelected)
	 */
	@Override
	public String createPdfMailCodeToDisk(IrsFormSelected selected){
		final HttpEntity<IrsFormSelected> entity = CommonUtil.createHttpEntityWithParameters(getAccessToken(), selected);
		
		final ResponseEntity<String> response = restTemplate.exchange(getRestApi() + RestWebServiceUrl.GENERATE_SELECTED_PDF_MAIL_CODE, HttpMethod.POST, entity, String.class);
		final String result = response.getBody();

		return result;
	}

	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.dashboard.service.IrsFormService#createPdfToDisk(com.oceanbank.webapp.common.model.IrsFormSelected)
	 */
	@Override
	public String createPdfToDisk(IrsFormSelected selected){
		final HttpEntity<IrsFormSelected> entity = CommonUtil.createHttpEntityWithParameters(getAccessToken(), selected);
		
		final ResponseEntity<String> response = restTemplate.exchange(getRestApi() + RestWebServiceUrl.GENERATE_SELECTED_PDF, HttpMethod.POST, entity, String.class);
		final String result = response.getBody();

		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.dashboard.service.IrsFormService#createPdfToDiskAll()
	 */
	@Override
	public String createPdfToDiskAll(){
		final HttpEntity<String> entity = CommonUtil.createHttpEntity(getAccessToken());
		
		final ResponseEntity<String> response = restTemplate.exchange(getRestApi() + RestWebServiceUrl.GENERATE_SELECTED_PDF_ALL, HttpMethod.POST, entity, String.class);
		final String result = response.getBody();

		return result;
	}

	
	
	
	

	
	
	
	
	
	

}
