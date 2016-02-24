/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.dashboard.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.oceanbank.webapp.common.exception.DashboardException;
import com.oceanbank.webapp.common.model.DashboardConstant;
import com.oceanbank.webapp.common.model.DashboardUploadResponse;
import com.oceanbank.webapp.common.model.DataTablesRequest;
import com.oceanbank.webapp.common.model.IrsFormSelected;
import com.oceanbank.webapp.common.model.OauthTokenBean;
import com.oceanbank.webapp.common.model.W8BeneFormResponse;
import com.oceanbank.webapp.common.util.CommonUtil;


@Service
public class W8BenFormService extends OauthTokenBean {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private AmlBatchServiceImpl amlBatchService;

	public W8BenFormService(){}
	
	//private final Logger LOGGER = LoggerFactory.getLogger(getClass());

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
	
	public void savePdfToDisk(MultipartFile mpf, String createdBy) throws DashboardException, IOException{

		// 1. save pdf details to DB
		DashboardUploadResponse response = new DashboardUploadResponse();
    	response.setFilename(mpf.getOriginalFilename());
    	response.setCreatedby(createdBy);
    	response.setDescription("active");
    	response = createDashboardUpload(response);

		// 2. copy pdf to disk
    	String permanentFileDirectory =  DashboardConstant.W8BENEFORM_TEMPLATE_UPLOAD_DIRECTORY + response.getId();
    	amlBatchService.createLocationDirectory(permanentFileDirectory, false);
		String fullFileLocation = permanentFileDirectory + "//" + mpf.getOriginalFilename();
		try {
			FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(fullFileLocation));
		} catch (IOException e) {
			throw new DashboardException("Error in savePdfToDisk() method", e.getCause());
		}

	}

	public DashboardUploadResponse createDashboardUpload(DashboardUploadResponse response) {

		final HttpEntity<DashboardUploadResponse> entity = CommonUtil.createHttpEntityWithParameters(getAccessToken(), response);
		final ResponseEntity<DashboardUploadResponse> request = restTemplate.exchange(getRestApi() + "/api/w8beneform/pdfUpload", HttpMethod.POST, entity, DashboardUploadResponse.class);

		return request.getBody();
	}

	public List<DashboardUploadResponse> getDashboardUploadDataTable(DataTablesRequest datatableRequest) {

		final HttpEntity<DataTablesRequest> entity = CommonUtil.createHttpEntityWithParameters(getAccessToken(), datatableRequest);

		final ResponseEntity<DashboardUploadResponse[]> response = restTemplate.exchange(getRestApi() + "/api/w8beneform/uploadDataTable", HttpMethod.POST, entity, DashboardUploadResponse[].class);

		List<DashboardUploadResponse> list = new ArrayList<DashboardUploadResponse>();
		if(response != null){
			list = Arrays.asList(response.getBody());
		}

		return list;
	}

	public DashboardUploadResponse activateCheckbox(DashboardUploadResponse response) {

		final HttpEntity<DashboardUploadResponse> entity = CommonUtil.createHttpEntityWithParameters(getAccessToken(), response);
		final ResponseEntity<DashboardUploadResponse> request = restTemplate.exchange(getRestApi() + "/api/w8beneform/updateCheckbox", HttpMethod.PUT, entity, DashboardUploadResponse.class);

		return request.getBody();
	}
}
