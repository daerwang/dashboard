package com.oceanbank.webapp.restoauth.test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.ComparisonChain;
import com.oceanbank.webapp.common.model.AmlBatchCifResponse;
import com.oceanbank.webapp.common.model.AmlBatchRequestResponse;
import com.oceanbank.webapp.common.model.DashboardLogResponse;
import com.oceanbank.webapp.common.model.DataTablesRequest;
import com.oceanbank.webapp.common.model.RestWebServiceUrl;
import com.oceanbank.webapp.common.util.CommonUtil;
import com.oceanbank.webapp.restoauth.model.AmlBatchTransactionType;
import com.oceanbank.webapp.restoauth.model.RestOauthAccessToken;

/**
 * Sample Oauth2 request and refresh token	
 * 
 * request a token with refresh token
 * http://localhost:8085/restoauth-webapp-oceanbank/oauth/token?grant_type=password&client_id=devaccess&client_secret=oceanbank&username=oceandev&password=ocean@123
 * 
 * request a token only
 * http://localhost:8085/restoauth-webapp-oceanbank/oauth/token?grant_type=password&client_id=oceandev&client_secret=ocean@123&username=oceandev&password=ocean@123
 * 
 * refresh a token
 * http://localhost:8085/restoauth-webapp-oceanbank/oauth/token?grant_type=refresh_token&client_id=oceandev&client_secret=ocean@123&refresh_token=a6a8207c-40ed-4a5d-b9e9-1951397d76ff
 * 
 */

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"file:src/main/resources/springcontext/test-context-spring.xml"})
public class AmlBatchClientTest {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	public static final String TEST_REST_URI = "http://localhost:8080/restoauth-webapp-oceanbank";
	
	private String accessToken;
	private String restApi;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private RestOauthAccessToken oauthToken;
	
	@Before
	public void setup_oauth(){
		this.accessToken = oauthToken.getAccessToken();
		this.restApi = oauthToken.getRestApi();
	}
	
	@Test
	public void test_read_from_excel2() throws Exception{
		String filePath = "C://Users//IBM_ADMIN//Documents//doc-sachi//testExcel2.xls";
		String textfilePath = "C://Users//IBM_ADMIN//Documents//doc-sachi//testText.txt";
		
		File file = new File(textfilePath);
		FileWriter fw = null;
		if (!file.exists()) {
			file.createNewFile();
			fw = new FileWriter(file.getAbsoluteFile());
		}else{
			fw = new FileWriter(file.getAbsoluteFile(), false);
		}
		BufferedWriter writer = new BufferedWriter(fw);
		
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filePath);
            // Using XSSF for xlsx format, for xls use HSSF
			Workbook workbook = null;
			if(filePath.toLowerCase().endsWith("xlsx")){
                workbook = new XSSFWorkbook(fis);
            }else if(filePath.toLowerCase().endsWith("xls")){
            	try {
            		workbook = new HSSFWorkbook(fis);
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
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext())
                {
                    Cell cell = cellIterator.next();
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    switch (cell.getCellType())
                    {
                        case Cell.CELL_TYPE_NUMERIC:
                            System.out.print(cell.getNumericCellValue() + "|");
                            writer.write(cell.getStringCellValue() + "|");
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

                            System.out.print(value + "|");
                            writer.write(value + "|");
                            escape = true;
                            break;
                    }
                }
                if(escape){
                	System.out.println("");
                	writer.write(System.lineSeparator());
                }
                	
            }
		
            fis.close();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	private void validateFirstRow(Iterator<Row> rowIterator) throws Exception{
        
        //iterating over each row
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
            					throw new Exception("Incorrect Column Name for row " + cell.getRowIndex() + " and cell " + columnIndex);
            				}
            				break;
            			case 1:
            				if(!cell.getStringCellValue().trim().equalsIgnoreCase("full name")){
            					throw new Exception("Incorrect Column Name for row " + cell.getRowIndex() + " and cell " + columnIndex);
            				}
            				break;
            			case 2:
            				if(!cell.getStringCellValue().trim().equalsIgnoreCase("current balance date")){
            					throw new Exception("Incorrect Column Name for row " + cell.getRowIndex() + " and cell " + columnIndex);
            				}
            				break;
            			case 3:
            				if(!cell.getStringCellValue().trim().equalsIgnoreCase("current balance")){
            					throw new Exception("Incorrect Column Name for row " + cell.getRowIndex() + " and cell " + columnIndex);
            				}
            				break;
            			case 4:
            				if(!cell.getStringCellValue().trim().equalsIgnoreCase("account number")){
            					throw new Exception("Incorrect Column Name for row " + cell.getRowIndex() + " and cell " + columnIndex);
            				}
            				break;
            			}
            		}
            	}
            }
	}
	
	@Test
	public void test_read_from_excel(){
		String EXCEL_PATH = "C://Users//IBM_ADMIN//Documents//Sachi//model//cif_for_approval_1.xlsx";
		
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(EXCEL_PATH);

            // Using XSSF for xlsx format, for xls use HSSF
            Workbook workbook = new XSSFWorkbook(fis);

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            //iterating over each row
            while (rowIterator.hasNext()) {

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
                    //Check the cell type and format accordingly
                    switch (cell.getCellType())
                    {
                        case Cell.CELL_TYPE_NUMERIC:
                            System.out.print(cell.getNumericCellValue() + " numeric");
                            break;
                        case Cell.CELL_TYPE_STRING:
                            System.out.print(cell.getStringCellValue() + " ");
                            break;
                    }
                }
                System.out.println("");
            }

            fis.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	@Test
	public void test_execute_approval_button(){
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		headers.setAccept(acceptableMediaTypes);
		
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		String requestId = "AML-2015-0002";
        ResponseEntity<AmlBatchRequestResponse> response = restTemplate.exchange(TEST_REST_URI + RestWebServiceUrl.EXECUTE_BATCH_APPROVAL_REQUEST_BY_REQUEST_ID, HttpMethod.GET, entity, AmlBatchRequestResponse.class, requestId);
        AmlBatchRequestResponse result = response.getBody();
        
        LOGGER.info("Request Id is " + result.getRequestId() + " and Batch Status is " + result.getStatus());
        assertNotNull("Should not be null", result);
        
        
	}
	
	@Test
	public void test_execute_reversal_button(){
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		headers.setAccept(acceptableMediaTypes);
		
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		String requestId = "AML-2015-0056";
        ResponseEntity<AmlBatchRequestResponse> response = restTemplate.exchange(TEST_REST_URI + RestWebServiceUrl.EXECUTE_BATCH_REVERSAL_REQUEST_BY_REQUEST_ID, HttpMethod.GET, entity, AmlBatchRequestResponse.class, requestId);
        AmlBatchRequestResponse result = response.getBody();
        
        LOGGER.info("Request Id is " + result.getRequestId() + " and Batch Status is " + result.getStatus());
        assertNotNull("Should not be null", result);
        
        
	}
	
	
	
	@Test
	public void test_exception_on_a_string(){
		String text = "could not execute statement; SQL [n/a]; constraint [cif_requestId_unique]; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement";
		Boolean isExisting = false;

		if(text.contains("org.hibernate.exception.ConstraintViolationException")){
			isExisting = true;
		}
		String result = isExisting ? "It is existing." : "It is NOT existing.";
		LOGGER.info(result);
	}
	
	@Test
	public void test_string_to_integer_parse(){
		String one = "0.00";
		String two = "0.00";

		Float result = Float.parseFloat(one) + Float.parseFloat(two);

		LOGGER.info("it is " + result);
	}
	
	@Test
	public void test_delete_aml_batch_by_requestId(){
		
		String requestId = "AML-2015-0039";
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		headers.setAccept(acceptableMediaTypes);
		
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		
        ResponseEntity<String> response = restTemplate.exchange(TEST_REST_URI + RestWebServiceUrl.DELETE_AML_BATCH_REQUEST_BY_REQUESTID, HttpMethod.DELETE, entity, String.class, requestId);
        String result = response.getBody();
        
        LOGGER.info(result);
        assertNotNull("Should not be null", result);
        
        
	}
	
	@Test
	public void test_get_aml_batch_datatable() {
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		headers.setAccept(acceptableMediaTypes);

		DataTablesRequest datatableRequest = new DataTablesRequest();
		datatableRequest.setValue(null);
		datatableRequest.setStart(1);
		datatableRequest.setLength(10);

		HttpEntity<DataTablesRequest> entity = new HttpEntity<DataTablesRequest>(
				datatableRequest, headers);
		ResponseEntity<AmlBatchRequestResponse[]> response = restTemplate
				.exchange(restApi + RestWebServiceUrl.GET_AML_BATCH_DATATABLE, HttpMethod.POST, entity, AmlBatchRequestResponse[].class);
		List<AmlBatchRequestResponse> resultList = Arrays.asList(response.getBody());

		Integer count = resultList.size();
		// sort
		Collections.sort(resultList,new Comparator<AmlBatchRequestResponse>() {

			@Override
			public int compare(AmlBatchRequestResponse o1,
					AmlBatchRequestResponse o2) {
				return ComparisonChain.start()
						.compare(o1.getId(), o2.getId())
						.result();
			}
		});
		
		for (AmlBatchRequestResponse r : resultList) {
			LOGGER.info("Id: " + r.getId() + " requestId: " + r.getRequestId());
		}
		LOGGER.info("Number of users is " + count);

		assertThat(count, is(not(0)));
	}
	
	@Test
	public void test_get_dashboard_log_datatable() {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		headers.setAccept(acceptableMediaTypes);

		DataTablesRequest datatableRequest = new DataTablesRequest();
		datatableRequest.setValue(null);
		datatableRequest.setStart(1);
		datatableRequest.setLength(10);
		datatableRequest.setAmlRequestId(47);

		HttpEntity<DataTablesRequest> entity = new HttpEntity<DataTablesRequest>(datatableRequest, headers);
		ResponseEntity<DashboardLogResponse[]> response = restTemplate.exchange(restApi + RestWebServiceUrl.GET_DASHBOARDLOG_DATATABLE, HttpMethod.POST, entity, DashboardLogResponse[].class);
		List<DashboardLogResponse> resultList = Arrays.asList(response.getBody());

		Integer count = resultList.size();
		
		
		for (DashboardLogResponse r : resultList) {
			LOGGER.info("Table name: " + r.getTableName() + " Description: " + r.getDescription());
		}
		
		LOGGER.info("Number of users is " + count);

		assertThat(count, is(not(0)));
	}
	
	@Test
	public void test_get_aml_batch_cif_datatable() {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		headers.setAccept(acceptableMediaTypes);

		DataTablesRequest datatableRequest = new DataTablesRequest();
		datatableRequest.setValue("test");
		datatableRequest.setStart(1);
		datatableRequest.setLength(10);
		datatableRequest.setAmlBatchRequestId("AML-2015-0056");

		HttpEntity<DataTablesRequest> entity = new HttpEntity<DataTablesRequest>(
				datatableRequest, headers);
		ResponseEntity<AmlBatchCifResponse[]> response = restTemplate
				.exchange(restApi + RestWebServiceUrl.GET_AML_BATCH_CIF_DATATABLE, HttpMethod.POST, entity, AmlBatchCifResponse[].class);
		List<AmlBatchCifResponse> resultList = Arrays.asList(response.getBody());

		Integer count = resultList.size();
		
		
		for (AmlBatchCifResponse r : resultList) {
			LOGGER.info("Id: " + r.getId() + " requestId: " + r.getRequestId());
		}
		LOGGER.info("Number of users is " + count);

		assertThat(count, is(not(0)));
	}
	
	@Test
	public void test_get_aml_batch_request_by_Id(){
		Integer id = 47;
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		ResponseEntity<AmlBatchRequestResponse> response = restTemplate.exchange(TEST_REST_URI + RestWebServiceUrl.GET_AML_BATCH_REQUEST_BY_ID, HttpMethod.POST, entity, AmlBatchRequestResponse.class, id);
		
		AmlBatchRequestResponse result = response.getBody();
		String dateString = new SimpleDateFormat("EEE, d MMM yyyy h:mm a").format(result.getCreatedOn());
		
		assertNotNull(response);
		
		LOGGER.info("The date is " + dateString);
		LOGGER.info("Request Id is " + result.getRequestId());
	}
	
	@Test
	public void test_get_aml_batch_request_by_requestId(){
		String requestId = "AML-2015-0023";
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		ResponseEntity<AmlBatchRequestResponse> response = restTemplate.exchange(TEST_REST_URI + RestWebServiceUrl.GET_AML_BATCH_REQUEST_BY_REQUEST_ID, HttpMethod.POST, entity, AmlBatchRequestResponse.class, requestId);
		
		AmlBatchRequestResponse result = response.getBody();
		String dateString = new SimpleDateFormat("EEE, d MMM yyyy h:mm a").format(result.getCreatedOn());
		
		assertNotNull(response);
		
		LOGGER.info("The date is " + dateString);
		LOGGER.info("Request Id is " + result.getRequestId());
	}
	
	@Test
	public void test_create_aml_batch_cif(){
		String requestId = "AML-2015-0002";

		// then upload a list of CIF to datatable
		AmlBatchCifResponse con = new AmlBatchCifResponse();
		con.setRequestId(requestId);
		con.setCifReference("VA12348");
		con.setAuditDescription("Batch Approved as per BSA Procedure. Only 30% of Medium Risk needs KYC Supervisor Review");
		con.setStatus("Pending");
		con.setCreatedby("NELL MEDINA");

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);

		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		headers.setAccept(acceptableMediaTypes);
		
		// Pass the new User and Header
		HttpEntity<AmlBatchCifResponse> entity = new HttpEntity<AmlBatchCifResponse>(con, headers);
		ResponseEntity<AmlBatchCifResponse> response = null;
		try {
			response = restTemplate.exchange(TEST_REST_URI + RestWebServiceUrl.CREATE_AML_BATCH_CIF, HttpMethod.POST, entity, AmlBatchCifResponse.class);
		} catch (RestClientException e) {
			LOGGER.info(e.getMessage());
		}

		AmlBatchCifResponse result = response.getBody();
		
		assertNotNull(response);
		
		LOGGER.info("Request Id is " + result.getRequestId() + " and CIF is " + result.getCifReference());
	}
	
	@Test
	public void test_create_many_aml_batch_cif(){
		String requestId = "AML-2015-0047";

		List<AmlBatchCifResponse> list = new ArrayList<AmlBatchCifResponse>();
		// then upload a list of CIF to datatable
		AmlBatchCifResponse con1 = new AmlBatchCifResponse();
		con1.setRequestId(requestId);
		con1.setCifReference("BAA6164");
		con1.setAuditDescription("Batch Approved as per BSA Procedure. Only 30% of Medium Risk needs KYC Supervisor Review");
		con1.setCreatedby("Nell Medina");
		
		AmlBatchCifResponse con2 = new AmlBatchCifResponse();
		con2.setRequestId(requestId);
		con2.setCifReference("CIF123");
		con2.setAuditDescription("Batch Approved as per BSA Procedure. Only 30% of Medium Risk needs KYC Supervisor Review");
		con2.setCreatedby("Nell Medina");
		
		AmlBatchCifResponse con3 = new AmlBatchCifResponse();
		con3.setRequestId(requestId);
		con3.setCifReference("CIF123f");
		con3.setAuditDescription("Batch Approved as per BSA Procedure. Only 30% of Medium Risk needs KYC Supervisor Review");
		con3.setCreatedby("Nell Medina");
		
		AmlBatchCifResponse con4 = new AmlBatchCifResponse();
		con4.setRequestId(requestId);
		con4.setCifReference("AAA6303");
		con4.setAuditDescription("Batch Approved as per BSA Procedure. Only 30% of Medium Risk needs KYC Supervisor Review");
		con4.setCreatedby("Nell Medina");
		
		list.add(con1);
		list.add(con3);
		list.add(con2);
		list.add(con4);
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);

		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		headers.setAccept(acceptableMediaTypes);
		
		// Pass the new User and Header
		HttpEntity<List<AmlBatchCifResponse>> entity = new HttpEntity<List<AmlBatchCifResponse>>(list, headers);
		ResponseEntity<String> response = null;
		try {
			response = restTemplate.exchange(TEST_REST_URI + RestWebServiceUrl.CREATE_MANY_AML_BATCH_CIF, HttpMethod.POST, entity, String.class);
		} catch (RestClientException e) {
			LOGGER.info(e.getMessage());
		}

		String result = response.getBody();
		
		assertNotNull(response);
		
		LOGGER.info("The result of many CIF saves is " + result);
	}
	
	@Test
	public void test_delete_aml_batch_cif_by_id(){
		
		Integer id = 6;
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		headers.setAccept(acceptableMediaTypes);
		
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		
        ResponseEntity<String> response = restTemplate.exchange(TEST_REST_URI + RestWebServiceUrl.DELETE_AML_BATCH_CIF_BY_ID, HttpMethod.DELETE, entity, String.class, id);
        String result = response.getBody();
        
        LOGGER.info(result);
        assertNotNull("Should not be null", result);
        
        
	}
	
	@Test
	public void test_find_aml_batch_cif_by_id(){
		
		Integer id = 28;
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		headers.setAccept(acceptableMediaTypes);
		
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		
        ResponseEntity<AmlBatchCifResponse> response = restTemplate.exchange(TEST_REST_URI + RestWebServiceUrl.FIND_AML_BATCH_CIF_BY_ID, HttpMethod.GET, entity, AmlBatchCifResponse.class, id);
        AmlBatchCifResponse result = response.getBody();
        
        LOGGER.info("It is " + result.getCifReference());
        assertNotNull("Should not be null", result);
        
        
	}
	
	@Test
	public void test_update_dashboard_cif_by_id(){
		
		Integer id = 33;

		// fill out the Request Form
		AmlBatchCifResponse req = new AmlBatchCifResponse();
		req.setId(id);
		req.setRequestId("AML-2015-0047");
		req.setStatus("DONE");
		req.setCifReference("CHOW123");
		req.setTransactionType(AmlBatchTransactionType.APPROVAL.getValue());
		req.setAuditDescription("Testing only");
		req.setModifiedby("Nell Medina");
		
		// submit the object to REST service
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		headers.setAccept(acceptableMediaTypes);
		
		HttpEntity<AmlBatchCifResponse> entity = new HttpEntity<AmlBatchCifResponse>(req, headers);
		ResponseEntity<AmlBatchCifResponse> response = restTemplate.exchange(TEST_REST_URI + RestWebServiceUrl.UPDATE_AML_BATCH_CIF, HttpMethod.POST, entity, AmlBatchCifResponse.class);
		
		AmlBatchCifResponse result = response.getBody();
		
		assertNotNull(response);
		
		LOGGER.info("Request Id is " + result.getRequestId() + " and CIF is " + result.getCifReference());
		
	}
	
	@Test
	public void test_update_dashboard_request_only(){
		
		String requestId = "AML-2015-0053";

		// fill out the Request Form
		AmlBatchRequestResponse req = new AmlBatchRequestResponse();
		req.setRequestId(requestId);
		req.setName("Sample Approval Request 1");
		req.setDescription("This is first testing of saving a AML Batch request.");
		req.setTransactionType(AmlBatchTransactionType.APPROVAL.getValue());
		req.setModifiedby("Nell Medina");
		
		// submit the object to REST service
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		headers.setAccept(acceptableMediaTypes);
		
		HttpEntity<AmlBatchRequestResponse> entity = new HttpEntity<AmlBatchRequestResponse>(req, headers);
		ResponseEntity<AmlBatchRequestResponse> response = restTemplate.exchange(TEST_REST_URI + RestWebServiceUrl.UPDATE_AML_BATCH_REQUEST, HttpMethod.POST, entity, AmlBatchRequestResponse.class);
		
		AmlBatchRequestResponse result = response.getBody();
		
		assertNotNull(response);
		
		LOGGER.info("Request Id is " + result.getRequestId() + " and name is " + result.getName() + " and status is " + result.getStatus());
		
	}
	
	@Test
	public void test_create_dashboard_request_initial(){
		
		
		// submit the object to REST service
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		headers.setAccept(acceptableMediaTypes);
		
		AmlBatchRequestResponse bean = new AmlBatchRequestResponse();
		//bean.setTransactionType("placeholder");
		bean.setCreatedby("Nell Medina");
		
		HttpEntity<AmlBatchRequestResponse> entity = new HttpEntity<AmlBatchRequestResponse>(bean, headers);
		ResponseEntity<AmlBatchRequestResponse> response = restTemplate.exchange(TEST_REST_URI + RestWebServiceUrl.CREATE_AML_BATCH_REQUEST, HttpMethod.POST, entity, AmlBatchRequestResponse.class);
		
		AmlBatchRequestResponse result = response.getBody();
		
		assertNotNull(response);
		
		//LOGGER.info("Request Id is " + result.getRequestId() + " and CIF is " + result.getDashboardamlbatchcontainers().get(0).getCifReference());
		
		LOGGER.info("Request Id is " + result.getRequestId());
	}
	
	@Test
	public void test_generated_requestId(){

		String result = null;
		
		String accessToken = "a0a56bf5-8694-4634-b106-c174aedd4984";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		
		HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<String> response = restTemplate.exchange(TEST_REST_URI + RestWebServiceUrl.GET_AML_BATCH_REQUEST_ID, HttpMethod.GET, entity, String.class);
        result = response.getBody();
        
        LOGGER.info("The Generated RequestId is " + result);
        
        assertNotNull(result);
	}
	
}
