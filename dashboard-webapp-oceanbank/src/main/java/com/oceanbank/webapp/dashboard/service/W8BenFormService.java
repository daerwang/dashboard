/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.dashboard.service;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.oceanbank.webapp.common.exception.DashboardException;
import com.oceanbank.webapp.common.model.DashboardConstant;
import com.oceanbank.webapp.common.model.DashboardUploadResponse;
import com.oceanbank.webapp.common.model.DataTablesRequest;
import com.oceanbank.webapp.common.model.IrsFormSelected;
import com.oceanbank.webapp.common.model.MailCodeResponse;
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
	
	public List<W8BeneFormResponse> searchFormDataTableDirect(DataTablesRequest datatableRequest) {
		
		HttpEntity<DataTablesRequest> entity = CommonUtil.createHttpEntityWithParameters(getAccessToken(), datatableRequest);
		String url = getRestApi() + "/api/w8beneform/dataTableDirect";
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

	public String createPdfToDiskDirect(IrsFormSelected selected){
		final HttpEntity<IrsFormSelected> entity = CommonUtil.createHttpEntityWithParameters(getAccessToken(), selected);
		
		final ResponseEntity<String> response = restTemplate.exchange(getRestApi() + "/api/w8beneform/createPdfToDiskDirect", HttpMethod.POST, entity, String.class);
		final String result = response.getBody();

		return result;
	}
	
	public String createPdfToDiskDirectFromFilter(IrsFormSelected selected){
		final HttpEntity<IrsFormSelected> entity = CommonUtil.createHttpEntityWithParameters(getAccessToken(), selected);
		
		final ResponseEntity<String> response = restTemplate.exchange(getRestApi() + "/api/w8beneform/createPdfToDiskDirectFromFilter", HttpMethod.POST, entity, String.class);
		final String result = response.getBody();

		return result;
	}
	
	public String createPdfToDiskDirectFromFilterCif(IrsFormSelected selected){
		final HttpEntity<IrsFormSelected> entity = CommonUtil.createHttpEntityWithParameters(getAccessToken(), selected);
		
		final ResponseEntity<String> response = restTemplate.exchange(getRestApi() + "/api/w8beneform/createPdfToDiskDirectFromFilterCif", HttpMethod.POST, entity, String.class);
		final String result = response.getBody();

		return result;
	}
	
	public String createPdfToDiskDirectAll(IrsFormSelected selected){
		final HttpEntity<IrsFormSelected> entity = CommonUtil.createHttpEntityWithParameters(getAccessToken(), selected);
		
		final ResponseEntity<String> response = restTemplate.exchange(getRestApi() + "/api/w8beneform/createPdfToDiskDirectAll", HttpMethod.POST, entity, String.class);
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
	
	public void savePdfToDiskDirect(MultipartFile mpf, String createdBy) throws DashboardException, IOException{

		// 1. save pdf details to DB
		DashboardUploadResponse response = new DashboardUploadResponse();
    	response.setFilename(mpf.getOriginalFilename());
    	response.setCreatedby(createdBy);
    	response.setDescription("active");
    	response = createDashboardUploadDirect(response);

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
	
	public DashboardUploadResponse createDashboardUploadDirect(DashboardUploadResponse response) {

		final HttpEntity<DashboardUploadResponse> entity = CommonUtil.createHttpEntityWithParameters(getAccessToken(), response);
		final ResponseEntity<DashboardUploadResponse> request = restTemplate.exchange(getRestApi() + "/api/w8beneform/pdfUploadDirect", HttpMethod.POST, entity, DashboardUploadResponse.class);

		return request.getBody();
	}
	
	public List<MailCodeResponse> getOfficerCodes(){

		final HttpEntity<String> entity = CommonUtil.createHttpEntity(getAccessToken());

		ResponseEntity<MailCodeResponse[]> response = restTemplate.exchange(getRestApi() + "/api/w8beneform/officerCodes", HttpMethod.POST, entity,MailCodeResponse[].class);

		List<MailCodeResponse> list = Arrays.asList(response.getBody());

		return list;
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
	
	public List<DashboardUploadResponse> getDashboardUploadDataTableDirect(DataTablesRequest datatableRequest) {

		final HttpEntity<DataTablesRequest> entity = CommonUtil.createHttpEntityWithParameters(getAccessToken(), datatableRequest);

		final ResponseEntity<DashboardUploadResponse[]> response = restTemplate.exchange(getRestApi() + "/api/w8beneform/uploadDataTableDirect", HttpMethod.POST, entity, DashboardUploadResponse[].class);

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

	public List<W8BeneFormResponse> createW8BeneFormFromExcel(MultipartHttpServletRequest request) throws DashboardException{

		Iterator<String> itr =  request.getFileNames();
        MultipartFile mpf = null;
        //ExcelFileMeta fileMeta = null;

        String fileName1 = itr.next();
    	mpf = request.getFile(fileName1);
    	if(mpf.getSize()/1024 > 5000){
        	throw new DashboardException("The upload should be less than 5MB in size.");
        }
    	String ext = FilenameUtils.getExtension(mpf.getOriginalFilename());
    	if(!(ext.equalsIgnoreCase("xls") || ext.equalsIgnoreCase("xlsx"))){
    		throw new DashboardException("The upload should be an Excel file.");
    	}

		List<W8BeneFormResponse> list = new ArrayList<W8BeneFormResponse>();

		try {
			InputStream input = new ByteArrayInputStream(mpf.getBytes());

			String fileName = mpf.getOriginalFilename();
			Workbook workbook = null;
			if(fileName.toLowerCase().endsWith("xlsx")){
                workbook = new XSSFWorkbook(input);
            }else if(fileName.toLowerCase().endsWith("xls")){
            	try {
            		workbook = new HSSFWorkbook(input);
				} catch (Exception e) {
					e.printStackTrace();
				}

            }

			Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();


            while (rowIterator.hasNext()) {
            	W8BeneFormResponse bean = new W8BeneFormResponse();
                Row row = rowIterator.next();

                if(row.getRowNum() == 0){
                	continue;
                }

                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext())
                {
                    Cell cell = cellIterator.next();

                    if(cell.getColumnIndex() == 50 && row.getRowNum() != 0){
                    	// do nothing
                    }else{
                    	cell.setCellType(Cell.CELL_TYPE_STRING);
                    }
                    //System.out.println("starting cell " + cell.getColumnIndex() + " at row " + row.getRowNum());
                    switch (cell.getCellType())
                    {
                        case Cell.CELL_TYPE_NUMERIC:
                        	if(cell.getColumnIndex() < 100){
                            	throw new DashboardException("The Excel file cannot have numeric cell.");
                            }
                            break;
                        case Cell.CELL_TYPE_STRING:
                        	if(cell.getColumnIndex() == 0){
                        		bean.setCif(cell.getStringCellValue());
                        	}
                        	if(cell.getColumnIndex() == 1){
                        		bean.setName(cell.getStringCellValue());
                        	}
                        	if(cell.getColumnIndex() == 2){
                        		bean.setPhysicalCountryInc(cell.getStringCellValue());
                        	}
                        	if(cell.getColumnIndex() == 3){
                        		bean.setPhysicalAddress(cell.getStringCellValue());
                        	}
                        	if(cell.getColumnIndex() == 4){
                        		bean.setPhysicalCity(cell.getStringCellValue());
                        	}
                        	if(cell.getColumnIndex() == 5){
                        		bean.setPhysicalCountry(cell.getStringCellValue());
                        	}
                        	if(cell.getColumnIndex() == 6){
                        		bean.setAltAddress(cell.getStringCellValue());
                        	}
                        	if(cell.getColumnIndex() == 7){
                        		bean.setAltCity(cell.getStringCellValue());
                        	}
                        	if(cell.getColumnIndex() == 8){
                        		bean.setAltCountry(cell.getStringCellValue());
                        	}
                        	if(cell.getColumnIndex() == 9){
                        		bean.setAccount(cell.getStringCellValue());
                        	}
                        	if(cell.getColumnIndex() == 10){
                        		bean.setLabelName(cell.getStringCellValue());
                        	}
                        	if(cell.getColumnIndex() == 11){
                        		bean.setOfficer(cell.getStringCellValue());
                        	}
                        	if(cell.getColumnIndex() == 12){
                        		bean.setBranch(cell.getStringCellValue());
                        	}
                        	if(cell.getColumnIndex() == 13){
                        		bean.setAltAddressLabel(cell.getStringCellValue());
                        	}
                        	if(cell.getColumnIndex() == 14){
                        		bean.setAltCityLabel(cell.getStringCellValue());
                        	}
                        	if(cell.getColumnIndex() == 15){
                        		bean.setAltCountryLabel(cell.getStringCellValue());
                        	}

                            break;
                    }
                }

                list.add(bean);
            }

            input.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return list;
	}

	public String insertW8BeneForms(List<W8BeneFormResponse> w8BeneForms) {

		final HttpEntity<List<W8BeneFormResponse>> entity = CommonUtil.createHttpEntityWithParameters(getAccessToken(), w8BeneForms);
		final ResponseEntity<String> request = restTemplate.exchange(getRestApi() + "/api/w8beneform/insertW8BeneForms", HttpMethod.POST, entity, String.class);

		return request.getBody();
	}

	public String delete(Integer id) {

		final HttpEntity<String> entity = CommonUtil.createHttpEntity(getAccessToken());
		final ResponseEntity<String> request = restTemplate.exchange(getRestApi() + "/api/w8beneform/" + id, HttpMethod.DELETE, entity, String.class);

		return request.getBody();
	}

	public String deleteAll() {

		final HttpEntity<String> entity = CommonUtil.createHttpEntity(getAccessToken());
		final ResponseEntity<String> request = restTemplate.exchange(getRestApi() + "/api/w8beneform/deleteAll", HttpMethod.DELETE, entity, String.class);

		return request.getBody();
	}
}
