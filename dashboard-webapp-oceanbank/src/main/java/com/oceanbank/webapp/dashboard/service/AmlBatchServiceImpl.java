/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.dashboard.service;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.oceanbank.webapp.common.exception.DashboardException;
import com.oceanbank.webapp.common.model.AmlBatchCifResponse;
import com.oceanbank.webapp.common.model.AmlBatchRequestResponse;
import com.oceanbank.webapp.common.model.DashboardCommentResponse;
import com.oceanbank.webapp.common.model.DashboardConstant;
import com.oceanbank.webapp.common.model.DashboardLogResponse;
import com.oceanbank.webapp.common.model.DashboardUploadResponse;
import com.oceanbank.webapp.common.model.DataTablesRequest;
import com.oceanbank.webapp.common.model.OauthTokenBean;
import com.oceanbank.webapp.common.model.RestWebServiceUrl;
import com.oceanbank.webapp.common.util.CommonUtil;


/**
 * The Class AmlBatchServiceImpl.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@Service
public class AmlBatchServiceImpl extends OauthTokenBean implements AmlBatchService{
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	/** The rest template. */
	@Autowired
	private RestTemplate restTemplate;

	
	/**
	 * Instantiates a new aml batch service impl.
	 */
	public AmlBatchServiceImpl(){}
	

	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.dashboard.service.AmlBatchService#createAmlBatchRequest()
	 */
	@Override
	public AmlBatchRequestResponse createAmlBatchRequest(AmlBatchRequestResponse response) {
	
		final HttpEntity<AmlBatchRequestResponse> entity = CommonUtil.createHttpEntityWithParameters(getAccessToken(), response);
		final ResponseEntity<AmlBatchRequestResponse> request = restTemplate.exchange(getRestApi() + RestWebServiceUrl.CREATE_AML_BATCH_REQUEST, HttpMethod.POST, entity, AmlBatchRequestResponse.class);

		return request.getBody();
	}
	
	@Override
	public AmlBatchCifResponse createAmlBatchCif(AmlBatchCifResponse response) {
	
		final HttpEntity<AmlBatchCifResponse> entity = CommonUtil.createHttpEntityWithParameters(getAccessToken(), response);
		final ResponseEntity<AmlBatchCifResponse> request = restTemplate.exchange(getRestApi() + RestWebServiceUrl.CREATE_AML_BATCH_CIF, HttpMethod.POST, entity, AmlBatchCifResponse.class);

		return request.getBody();
	}

	@Override
	public String deleteAmlBatchCif(Integer id) {
		final HttpEntity<String> entity = CommonUtil.createHttpEntity(getAccessToken());
		final ResponseEntity<String> response = restTemplate.exchange(getRestApi() + RestWebServiceUrl.DELETE_AML_BATCH_CIF_BY_ID, HttpMethod.DELETE, entity, String.class, id);
		
		return response.getBody();
	}
	
	@Override
	public AmlBatchCifResponse updateAmlBatchCif(AmlBatchCifResponse response){

		final HttpEntity<AmlBatchCifResponse> entity = CommonUtil.createHttpEntityWithParameters(getAccessToken(), response);
		final ResponseEntity<AmlBatchCifResponse> bean = restTemplate.exchange(getRestApi() + RestWebServiceUrl.UPDATE_AML_BATCH_CIF, HttpMethod.POST, entity, AmlBatchCifResponse.class);

		return bean.getBody();
	}
	
	@Override
	public AmlBatchCifResponse findAmlBatchCif(Integer id){

		final HttpEntity<String> entity = CommonUtil.createHttpEntity(getAccessToken());
		final ResponseEntity<AmlBatchCifResponse> response = restTemplate.exchange(getRestApi() + RestWebServiceUrl.FIND_AML_BATCH_CIF_BY_ID, HttpMethod.GET, entity, AmlBatchCifResponse.class, id);
		final AmlBatchCifResponse result = response.getBody();
		
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.dashboard.service.AmlBatchService#getAmlBatchRequestByRequestId(java.lang.String)
	 */
	@Override
	public AmlBatchRequestResponse getAmlBatchRequestByRequestId(String requestId) {
		
		final HttpEntity<String> entity = CommonUtil.createHttpEntity(getAccessToken());
		final ResponseEntity<AmlBatchRequestResponse> response = restTemplate.exchange(getRestApi() + RestWebServiceUrl.GET_AML_BATCH_REQUEST_BY_REQUEST_ID, HttpMethod.POST, entity, AmlBatchRequestResponse.class, requestId);
		final AmlBatchRequestResponse result = response.getBody();
		
		return result;
	}
	

	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.dashboard.service.AmlBatchService#getAmlBatchRequestById(java.lang.Integer)
	 */
	@Override
	public AmlBatchRequestResponse getAmlBatchRequestById(Integer id) {
		
		final HttpEntity<String> entity = CommonUtil.createHttpEntity(getAccessToken());
		final ResponseEntity<AmlBatchRequestResponse> response = restTemplate.exchange(getRestApi() + RestWebServiceUrl.GET_AML_BATCH_REQUEST_BY_ID, HttpMethod.POST, entity, AmlBatchRequestResponse.class, id);
		final AmlBatchRequestResponse result = response.getBody();
		
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.dashboard.service.AmlBatchService#updateAmlBatchRequest(com.oceanbank.webapp.common.model.DashboardAmlBatchRequestResponse)
	 */
	@Override
	public AmlBatchRequestResponse updateAmlBatchRequest(AmlBatchRequestResponse bean) {

		final HttpEntity<AmlBatchRequestResponse> entity = CommonUtil.createHttpEntityWithParameters(getAccessToken(), bean);
		final ResponseEntity<AmlBatchRequestResponse> response = restTemplate.exchange(getRestApi() + RestWebServiceUrl.UPDATE_AML_BATCH_REQUEST, HttpMethod.POST, entity, AmlBatchRequestResponse.class);
        bean = response.getBody();

		return bean;	
	}
	
	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.dashboard.service.AmlBatchService#searchAmlBatchFormDataTable(com.oceanbank.webapp.common.model.DataTablesRequest)
	 */
	@Override
	public List<AmlBatchRequestResponse> getAmlBatchDataTable(DataTablesRequest datatableRequest) {

		final HttpEntity<DataTablesRequest> entity = CommonUtil.createHttpEntityWithParameters(getAccessToken(), datatableRequest);
		
		final ResponseEntity<AmlBatchRequestResponse[]> response = restTemplate.exchange(getRestApi() + RestWebServiceUrl.GET_AML_BATCH_DATATABLE, HttpMethod.POST, entity, AmlBatchRequestResponse[].class);
		
		List<AmlBatchRequestResponse> list = new ArrayList<AmlBatchRequestResponse>();
		if(response != null){
			list = Arrays.asList(response.getBody());
		}
		
		return list;
	}

	@Override
	public List<AmlBatchCifResponse> getAmlBatchCifDataTable(DataTablesRequest datatableRequest) {

		final HttpEntity<DataTablesRequest> entity = CommonUtil.createHttpEntityWithParameters(getAccessToken(), datatableRequest);
		
		final ResponseEntity<AmlBatchCifResponse[]> response = restTemplate.exchange(getRestApi() + RestWebServiceUrl.GET_AML_BATCH_CIF_DATATABLE, HttpMethod.POST, entity, AmlBatchCifResponse[].class);
		
		List<AmlBatchCifResponse> list = new ArrayList<AmlBatchCifResponse>();
		if(response != null){
			list = Arrays.asList(response.getBody());
		}
		
		return list;
	}
	
	@Override
	public List<DashboardLogResponse> getDashboardLogDataTable(DataTablesRequest datatableRequest) {

		final HttpEntity<DataTablesRequest> entity = CommonUtil.createHttpEntityWithParameters(getAccessToken(), datatableRequest);
		
		final ResponseEntity<DashboardLogResponse[]> response = restTemplate.exchange(getRestApi() + RestWebServiceUrl.GET_DASHBOARDLOG_DATATABLE, HttpMethod.POST, entity, DashboardLogResponse[].class);
		
		List<DashboardLogResponse> list = new ArrayList<DashboardLogResponse>();
		if(response != null){
			list = Arrays.asList(response.getBody());
		}
		
		return list;
	}
	
	@Override
	public List<DashboardUploadResponse> getDashboardUploadDataTable(DataTablesRequest datatableRequest) {

		final HttpEntity<DataTablesRequest> entity = CommonUtil.createHttpEntityWithParameters(getAccessToken(), datatableRequest);
		
		final ResponseEntity<DashboardUploadResponse[]> response = restTemplate.exchange(getRestApi() + RestWebServiceUrl.GET_AML_BATCH_REQUEST_UPLOAD_DATATABLE, HttpMethod.POST, entity, DashboardUploadResponse[].class);
		
		List<DashboardUploadResponse> list = new ArrayList<DashboardUploadResponse>();
		if(response != null){
			list = Arrays.asList(response.getBody());
		}
		
		return list;
	}

	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.dashboard.service.AmlBatchService#deleteAmlBatchRequestByRequestId(java.lang.String)
	 */
	@Override
	public String deleteAmlBatchRequestByRequestId(String requestId) {
		final HttpEntity<String> entity = CommonUtil.createHttpEntity(getAccessToken());
		final ResponseEntity<String> response = restTemplate.exchange(getRestApi() + RestWebServiceUrl.DELETE_AML_BATCH_REQUEST_BY_REQUESTID, HttpMethod.DELETE, entity, String.class, requestId);
		final String result = response.getBody();
		
		return result;
	}


	@Override
	public AmlBatchRequestResponse executeAmlBatchRequestApproval(String requestId) {
		final HttpEntity<String> entity = CommonUtil.createHttpEntity(getAccessToken());
		final ResponseEntity<AmlBatchRequestResponse> response = restTemplate.exchange(getRestApi() + RestWebServiceUrl.EXECUTE_BATCH_APPROVAL_REQUEST_BY_REQUEST_ID, HttpMethod.GET, entity, AmlBatchRequestResponse.class, requestId);
		final AmlBatchRequestResponse result = response.getBody();
		
		return result;
	}

	@Override
	public AmlBatchRequestResponse executeAmlBatchRequestReversal(String requestId) {
		final HttpEntity<String> entity = CommonUtil.createHttpEntity(getAccessToken());
		final ResponseEntity<AmlBatchRequestResponse> response = restTemplate.exchange(getRestApi() + RestWebServiceUrl.EXECUTE_BATCH_REVERSAL_REQUEST_BY_REQUEST_ID, HttpMethod.GET, entity, AmlBatchRequestResponse.class, requestId);
		final AmlBatchRequestResponse result = response.getBody();
		
		return result;
	}
	
	@Override
	public AmlBatchRequestResponse executeAmlBatchRequestDisapproval(String requestId) {
		final HttpEntity<String> entity = CommonUtil.createHttpEntity(getAccessToken());
		final ResponseEntity<AmlBatchRequestResponse> response = restTemplate.exchange(getRestApi() + RestWebServiceUrl.EXECUTE_BATCH_DISAPPROVAL_REQUEST_BY_REQUEST_ID, HttpMethod.GET, entity, AmlBatchRequestResponse.class, requestId);
		final AmlBatchRequestResponse result = response.getBody();
		
		return result;
	}

	@Override
	public List<AmlBatchCifResponse> createAmlBatchCifFromExcel(String requestId, MultipartFile mpf, String createdBy) throws DashboardException{

		List<AmlBatchCifResponse> list = new ArrayList<AmlBatchCifResponse>();
		
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

            //iterating over each row
            while (rowIterator.hasNext()) {
            	AmlBatchCifResponse bean = new AmlBatchCifResponse();
            	bean.setRequestId(requestId);
            	bean.setCreatedby(createdBy);
            	
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                // skip the Excel header and start with second row
                if(row.getRowNum() == 0){
                	continue;
                }
                //Iterating over each cell (column wise)  in a particular row.
                while (cellIterator.hasNext())
                {
                    Cell cell = cellIterator.next();
                    // throw Exception when more than 2 column index found
                    if(cell.getColumnIndex() == 2){
                    	throw new DashboardException("The Excel file is not in proper format. There should be 2 rows only.");
                    }
                    
                    //Check the cell type and format accordingly
                    switch (cell.getCellType())
                    {
                        case Cell.CELL_TYPE_NUMERIC:
                        	if(cell.getColumnIndex() < 100){
                            	throw new DashboardException("The Excel file cannot have numeric cell.");
                            }
                            break;
                        case Cell.CELL_TYPE_STRING:
                        	if(cell.getColumnIndex() == 0){
                        		bean.setCifReference(cell.getStringCellValue());
                        	}
                        	if(cell.getColumnIndex() == 1){
                        		bean.setAuditDescription(cell.getStringCellValue());
                        	}
                            break;
                    }
                }
                bean.setStatus("new request");
                list.add(bean);
            }

            input.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	private void createLocationDirectory(String fileDirectory, Boolean isSingleDirectory) throws DashboardException, IOException{
		final File file = new File(fileDirectory);
		
		if(isSingleDirectory){
			if(!file.exists()){
				if (file.mkdir()) {
					LOGGER.info("Upload directory is created successfully.");
				} else {
					LOGGER.error("Failed to create directory!");
					throw new DashboardException("Failed to create Upload directory.");
				}
			}
		}else{
			if(!file.exists()){
				if (file.mkdirs()) {
					LOGGER.info("Multiple Upload directory is created successfully.");
				} else {
					LOGGER.error("Failed to create directory!");
					throw new DashboardException("Failed to create Upload directory.");
				}
			}
		}
		
		
		if (file.isDirectory()) {
			FileUtils.cleanDirectory(file);
		} else {
			throw new DashboardException("The File directory for upload does not exist.");
		}
	}
	
	private File saveFileToDiskCommonLocation(MultipartFile mpf) throws DashboardException{
		String fullFileLocation = null;
		try {
			createLocationDirectory(DashboardConstant.COMMON_FILE_LOCATION, true);
			
			fullFileLocation = DashboardConstant.COMMON_FILE_LOCATION + "//" + mpf.getOriginalFilename();
			
			FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(fullFileLocation));
			
		} catch (IOException e) {
			throw new DashboardException("Error in saveFileToDiskCommonLocation() method", e.getCause());
		}
		
		File file = new File(fullFileLocation);
		
		return file;
	}
	
	@Override
	public DashboardUploadResponse createDashboardUpload(DashboardUploadResponse response) {
	
		final HttpEntity<DashboardUploadResponse> entity = CommonUtil.createHttpEntityWithParameters(getAccessToken(), response);
		final ResponseEntity<DashboardUploadResponse> request = restTemplate.exchange(getRestApi() + RestWebServiceUrl.CREATE_AML_BATCH_REQUEST_UPLOAD, HttpMethod.POST, entity, DashboardUploadResponse.class);

		return request.getBody();
	}
	
	@Override
	public DashboardUploadResponse findDashboardUploadById(Long id){

		final HttpEntity<String> entity = CommonUtil.createHttpEntity(getAccessToken());
		final ResponseEntity<DashboardUploadResponse> response = restTemplate.exchange(getRestApi() + RestWebServiceUrl.FIND_AML_BATCH_UPLOAD_BY_ID, HttpMethod.GET, entity, DashboardUploadResponse.class, id);
		final DashboardUploadResponse result = response.getBody();
		
		return result;
	}
	
	@Override
	public String deleteDashboardUpload(Long id) {
		final HttpEntity<String> entity = CommonUtil.createHttpEntity(getAccessToken());
		final ResponseEntity<String> response = restTemplate.exchange(getRestApi() + RestWebServiceUrl.DELETE_AML_BATCH_UPLOAD_BY_ID, HttpMethod.DELETE, entity, String.class, id);
		
		return response.getBody();
	}
	
	@Override
	public String getUploadPermanentFileLocation(){
		final String uniqueUploadFileLocation = DashboardConstant.AML_BATCH_REQUEST_UPLOAD_FILE_LOCATION + "//upload_";
		
		return uniqueUploadFileLocation;
	}
	
	@Override
	public void saveAmlBatchRequestUploadFileToDisk(MultipartFile mpf, String createdBy, String requestId) throws DashboardException, IOException{
    	
    	// 1. save to common directory first to test if no error
    	File commonFile = saveFileToDiskCommonLocation(mpf);
    	
    	// 2. save object to database and create dashboardlog entry
    	DashboardUploadResponse response = new DashboardUploadResponse();
    	response.setFilename(mpf.getOriginalFilename());
    	response.setCreatedby(createdBy);
    	response.setRequestId(requestId);
    	response = createDashboardUpload(response);
    	
    	// 3. move file from common to permanent location
    	String permanentFileDirectory =  getUploadPermanentFileLocation() + response.getId();
    	createLocationDirectory(permanentFileDirectory, false);
    	final File permanentFile = new File(permanentFileDirectory + "//" + commonFile.getName());
		if(commonFile.renameTo(permanentFile)){
			LOGGER.info("File is moved successfully! - " + response.getFilename());
    	}else{
    		throw new DashboardException("Error in saveAmlBatchRequestUploadFileToDisk() method", new IllegalArgumentException("The File failed to move to permanent file location."));
    	}
		
	}
	
	@Override
	public void createExcelIntoTextFileToDisk(MultipartFile mpf) throws DashboardException, IOException {
		InputStream input = null;
		BufferedWriter writer = null;
		try {
			
			input = new ByteArrayInputStream(mpf.getBytes());

			// prepare file location
			final File fileLocation = new File(DashboardConstant.TXT_MERGING_INDIVIDUAL_FILE_LOCATION);

			if (fileLocation.isDirectory()) {
				FileUtils.cleanDirectory(fileLocation);
			} else {
				throw new DashboardException("Error in createExcelIntoTextFileToDisk() method", new IllegalArgumentException("Advisor text file directory does not exist"));
			}
			
			String fullFileLocation = DashboardConstant.TXT_MERGING_INDIVIDUAL_FILE_LOCATION + "//advisorConverted.txt";
			
			File file = new File(fullFileLocation);
			FileWriter fw = null;
			if (!file.exists()) {
				file.createNewFile();
				fw = new FileWriter(file.getAbsoluteFile());
			}else{
				fw = new FileWriter(file.getAbsoluteFile(), false);
			}
			writer = new BufferedWriter(fw);
			
			
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
	        Iterator<Row> rowIterator2 = sheet.iterator();
	        Boolean escape = true;
	        
	        validateFirstRow(rowIterator2);
	        
	        while (rowIterator.hasNext()) {
	        	
	        	Row row = rowIterator.next();
	        	
	        	if(row.getRowNum() > 0){
	        		
	        		Iterator<Cell> cellIterator = row.cellIterator();
	                while (cellIterator.hasNext())
	                {
	                    Cell cell = cellIterator.next();
	                    if(cell.getColumnIndex() == 2 && row.getRowNum() != 0){
	                    	// do nothing
	                    }else{
	                    	cell.setCellType(Cell.CELL_TYPE_STRING);
	                    }
	                    switch (cell.getCellType())
	                    {
	                        case Cell.CELL_TYPE_NUMERIC:
	                        	String value1 = null;
	                            if(cell.getColumnIndex() == 2 && row.getRowNum() != 0){
	                        		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	                        		try {
	                        			Date dateValue = cell.getDateCellValue();  
	                        			value1 = df.format(dateValue);
									} catch (Exception e) {
										e.printStackTrace();
									}
	                            }

	                            writer.write(value1 + "|");
	                            escape = true;
	                            break;
	                        case Cell.CELL_TYPE_STRING:
	                        	String value = cell.getStringCellValue();
	                        	if(value.trim().length() < 1){
	                        		escape = false;
	                        		continue;
	                            }
	                        	if(cell.getColumnIndex() == 3 && row.getRowNum() != 0){
	                        		//DecimalFormat df = new DecimalFormat("#.00"); 
	                        		double number = Double.parseDouble(value);
	                        		value = String.format( "%.2f", number );
	                            }
	                        	
	                            writer.write(value + "|");
	                            escape = true;
	                            break;
	                    }
	                }
	                if(escape){
	                	writer.write(System.lineSeparator());
	                }
	        		
	        	}
                
	        }
		}catch (FileNotFoundException e) {
            throw new DashboardException("FileNotFoundException occured", e);
        }catch (IOException e) {
        	throw new DashboardException("IOException occured", e);
        }finally{
        	
        	input.close();
        	writer.close();
        }
		
            
            
    }


	private void validateFirstRow(Iterator<Row> rowIterator) throws DashboardException{
		while (rowIterator.hasNext()) {

            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            
            if(row.getRowNum() == 0){
        		
            	while(cellIterator.hasNext()){
            		Cell cell = cellIterator.next();
            		cell.setCellType(Cell.CELL_TYPE_STRING);
            		
            		Integer columnIndex = cell.getColumnIndex();
            		switch(columnIndex){
            			case 0:
            				if(!cell.getStringCellValue().trim().equalsIgnoreCase("client number")){
            					throw new DashboardException("Incorrect Column Name for row " + cell.getRowIndex() + " and cell " + columnIndex);
            				}
            				break;
            			case 1:
            				if(!cell.getStringCellValue().trim().equalsIgnoreCase("full name")){
            					throw new DashboardException("Incorrect Column Name for row " + cell.getRowIndex() + " and cell " + columnIndex);
            				}
            				break;
            			case 2:
            				if(!cell.getStringCellValue().trim().equalsIgnoreCase("current balance date")){
            					throw new DashboardException("Incorrect Column Name for row " + cell.getRowIndex() + " and cell " + columnIndex);
            				}
            				break;
            			case 3:
            				if(!cell.getStringCellValue().trim().equalsIgnoreCase("current balance")){
            					throw new DashboardException("Incorrect Column Name for row " + cell.getRowIndex() + " and cell " + columnIndex);
            				}
            				break;
            			case 4:
            				if(!cell.getStringCellValue().trim().equalsIgnoreCase("account number")){
            					throw new DashboardException("Incorrect Column Name for row " + cell.getRowIndex() + " and cell " + columnIndex);
            				}
            				break;
	            		}
	            	}
	            }
            }
	}
	
	@Override
	public String createManyAmlBatchCif(List<AmlBatchCifResponse> list) {
		final HttpEntity<List<AmlBatchCifResponse>> entity = CommonUtil.createHttpEntityWithParameters(getAccessToken(), list);
		final ResponseEntity<String> request = restTemplate.exchange(getRestApi() + RestWebServiceUrl.CREATE_MANY_AML_BATCH_CIF, HttpMethod.POST, entity, String.class);

		return request.getBody();
	}


	@Override
	public List<DashboardCommentResponse> getDashboardCommentDataTable(
			DataTablesRequest datatableRequest) {
		final HttpEntity<DataTablesRequest> entity = CommonUtil.createHttpEntityWithParameters(getAccessToken(), datatableRequest);
		
		final ResponseEntity<DashboardCommentResponse[]> response = restTemplate.exchange(getRestApi() + RestWebServiceUrl.GET_DASHBOARD_COMMENT_DATATABLE, HttpMethod.POST, entity, DashboardCommentResponse[].class);
		
		List<DashboardCommentResponse> list = new ArrayList<DashboardCommentResponse>();
		if(response != null){
			list = Arrays.asList(response.getBody());
		}
		
		return list;
	}


	@Override
	public DashboardCommentResponse findDashboardCommentById(Long id) {
		final HttpEntity<String> entity = CommonUtil.createHttpEntity(getAccessToken());
		final ResponseEntity<DashboardCommentResponse> response = restTemplate.exchange(getRestApi() + RestWebServiceUrl.FIND_DASHBOARD_COMMENT_BY_ID, HttpMethod.GET, entity, DashboardCommentResponse.class, id.intValue());
		final DashboardCommentResponse result = response.getBody();
		
		return result;
	}


	@Override
	public String deleteDashboardComment(Long id) {
		final HttpEntity<String> entity = CommonUtil.createHttpEntity(getAccessToken());
		final ResponseEntity<String> response = restTemplate.exchange(getRestApi() + RestWebServiceUrl.DELETE_DASHBOARD_COMMENT_BY_ID, HttpMethod.DELETE, entity, String.class, id);
		
		return response.getBody();
	}


	@Override
	public DashboardCommentResponse createDashboardComment(
			DashboardCommentResponse response) {
		final HttpEntity<DashboardCommentResponse> entity = CommonUtil.createHttpEntityWithParameters(getAccessToken(), response);
		final ResponseEntity<DashboardCommentResponse> request = restTemplate.exchange(getRestApi() + RestWebServiceUrl.CREATE_DASHBOARD_COMMENT, HttpMethod.POST, entity, DashboardCommentResponse.class);

		return request.getBody();
	}


	@Override
	public DashboardCommentResponse updateDashboardComment(
			DashboardCommentResponse response) {
		final HttpEntity<DashboardCommentResponse> entity = CommonUtil.createHttpEntityWithParameters(getAccessToken(), response);
		final ResponseEntity<DashboardCommentResponse> bean = restTemplate.exchange(getRestApi() + RestWebServiceUrl.UPDATE_DASHBOARD_COMMENT, HttpMethod.POST, entity, DashboardCommentResponse.class);

		return bean.getBody();
	}


	@Override
	public List<AmlBatchCifResponse> getAmlBatchCifByRequestId(String requestId) {
		final HttpEntity<String> entity = CommonUtil.createHttpEntity(getAccessToken());
		final ResponseEntity<AmlBatchCifResponse[]> response = restTemplate.exchange(getRestApi() + "/amlbatchcif/{requestId}", HttpMethod.POST, entity, AmlBatchCifResponse[].class, requestId);
		
		List<AmlBatchCifResponse> list = new ArrayList<AmlBatchCifResponse>();
		list = Arrays.asList(response.getBody());
		
		return list;
	}


	@Override
	public AmlBatchRequestResponse executeAmlApprovalOrDisapproval(AmlBatchRequestResponse amlBatchRequest) {
		final HttpEntity<AmlBatchRequestResponse> entity = CommonUtil.createHttpEntityWithParameters(getAccessToken(), amlBatchRequest);
		final ResponseEntity<AmlBatchRequestResponse> response = restTemplate.exchange(getRestApi() + "/api/amlbatchrequest/approveOrDisapprove", HttpMethod.PUT, entity, AmlBatchRequestResponse.class);
		final AmlBatchRequestResponse result = response.getBody();
		
		return result;
	}

}
