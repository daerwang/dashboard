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
import com.oceanbank.webapp.common.model.IrsFormSelected;
import com.oceanbank.webapp.common.model.OauthTokenBean;
import com.oceanbank.webapp.common.model.W8BeneFormResponse;
import com.oceanbank.webapp.common.util.CommonUtil;


@Service
public class W8BenFormService extends OauthTokenBean {

	@Autowired
	private RestTemplate restTemplate;
	

	public W8BenFormService(){}
	
	
	public List<W8BeneFormResponse> searchFormDataTable(DataTablesRequest datatableRequest) {
		
		HttpEntity<DataTablesRequest> entity = CommonUtil.createHttpEntityWithParameters(getAccessToken(), datatableRequest);
		String url = getRestApi() + "/api/w8beneform/dataTable";
		ResponseEntity<W8BeneFormResponse[]> response = restTemplate.exchange(url, HttpMethod.POST, entity, W8BeneFormResponse[].class);

		List<W8BeneFormResponse> list = Arrays.asList(response.getBody());

		return list;
	}
	
	public String createPdfToDisk(IrsFormSelected selected){
		final HttpEntity<IrsFormSelected> entity = CommonUtil.createHttpEntityWithParameters(getAccessToken(), selected);
		
		final ResponseEntity<String> response = restTemplate.exchange(getRestApi() + "/api/w8beneform/createPdfToDisk", HttpMethod.POST, entity, String.class);
		final String result = response.getBody();

		return result;
	}
	

}
