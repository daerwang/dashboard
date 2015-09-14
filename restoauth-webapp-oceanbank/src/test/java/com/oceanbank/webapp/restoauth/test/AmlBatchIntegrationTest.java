package com.oceanbank.webapp.restoauth.test;

import static com.oceanbank.webapp.common.model.RestWebServiceUrl.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

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
import org.springframework.web.client.RestTemplate;

import com.oceanbank.webapp.common.model.AmlBatchCifResponse;
import com.oceanbank.webapp.common.model.AmlBatchRequestResponse;
import com.oceanbank.webapp.restoauth.model.AmlBatchTransactionType;

/**
 * Sample Oauth2 request and refresh token	
 * 
 * request a token
 * http://localhost:8085/restoauth-webapp-oceanbank/oauth/token?grant_type=password&client_id=devaccess&client_secret=oceanbank&username=oceandev&password=ocean@123
 * 
 * refresh a token
 * http://localhost:8085/restoauth-webapp-oceanbank/oauth/token?grant_type=refresh_token&client_id=devaccess&client_secret=oceanbank&refresh_token=7ac7940a-d29d-4a4c-9a47-25a2167c8c49
 * 
 */

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"file:src/main/resources/springcontext/local-as400-datajpa-spring.xml"
		, "file:src/main/webapp/WEB-INF/mvc-servlet.xml", "file:src/main/resources/springcontext/test-context-spring.xml"})
public class AmlBatchIntegrationTest {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	public static final String TEST_REST_URI = "http://localhost:8080/restoauth-webapp-oceanbank";
	
	@Autowired
	private RestTemplate restTemplate;
	
	@PersistenceContext
	EntityManager em;
	
	
	@Test
	public void test_get_aml_logs_by_cif_reference(){
		
		LOGGER.info("Starting the SP here...");
		try {
			
			StoredProcedureQuery proc = em.createNamedStoredProcedureQuery("SPBSAGETLOGBYTYPE");

			proc.setParameter("I_AMLCBANKNU", 1);
			proc.setParameter("I_AMLCTYPLOG", "%");
			proc.setParameter("I_AMLCCIFCOD", "OAA3515");
			proc.execute();
			
			
			List<Object[]> resultList = proc.getResultList();
			for (Object[] element : resultList){
				Date date = (Date) element[6];
				Time time = (Time) element[7];
				String username = (String) element[5];
				BigDecimal logDescription = (BigDecimal) element[4];
				String logNote = (String) element[11];
				
				LOGGER.info(""+date);
				LOGGER.info(""+time);
				LOGGER.info(username);
				LOGGER.info(""+logDescription);
				LOGGER.info(logNote);
			}
			  
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		
	}
	
	
	@Test
	public void test_generated_requestId(){

		String result = null;
		
		String accessToken = "a0a56bf5-8694-4634-b106-c174aedd4984";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		
		HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<String> response = restTemplate.exchange(TEST_REST_URI + GET_AML_BATCH_REQUEST_ID, HttpMethod.GET, entity, String.class);
        result = response.getBody();
        
        LOGGER.info("The Generated RequestId is " + result);
        
        assertNotNull(result);
	}
	
	@Test
	public void test_create_dashboard_request_initial(){
		LOGGER.info("Create Dashboard AML Batch Request ... ");
		
		// fill out the Request Form
//		DashboardAmlBatchRequestResponse req = new DashboardAmlBatchRequestResponse();
//		req.setName("Sample Approval Request 1");
//		req.setDescription("This is first testing of saving a AML Batch request.");
//		req.setTransactionType(DashboardTransactionType.APPROVAL.getValue());
		
		// then upload a list of CIF to datatable
//		List<DashboardAmlBatchContainerResponse> list = new ArrayList<DashboardAmlBatchContainerResponse>();
//		DashboardAmlBatchContainerResponse con = new DashboardAmlBatchContainerResponse();
//		con.setCifReference("VAA5394");
//		con.setAuditDescription("Batch Approved as per BSA Procedure. Only 30% of Medium Risk needs KYC Supervisor Review");
//		con.setStatus("Pending");
//		list.add(con);
		//req.setDashboardamlbatchcontainers(list);
		
		// submit the object to REST service
		String accessToken = "2eb87285-55b9-458c-83e0-6a5a4dcb5512";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		ResponseEntity<AmlBatchRequestResponse> response = restTemplate.exchange(TEST_REST_URI + CREATE_AML_BATCH_REQUEST, HttpMethod.POST, entity, AmlBatchRequestResponse.class);
		
		AmlBatchRequestResponse result = response.getBody();
		
		assertNotNull(response);
		
		//LOGGER.info("Request Id is " + result.getRequestId() + " and CIF is " + result.getDashboardamlbatchcontainers().get(0).getCifReference());
		
		LOGGER.info("Request Id is " + result.getRequestId());
	}
	
	@Test
	public void test_create_dashboard_container(){
		LOGGER.info("Create Dashboard AML Batch Container ... ");
		
		// create a Container object
		AmlBatchCifResponse con = new AmlBatchCifResponse();
		con.setTransactionType(AmlBatchTransactionType.APPROVAL.getValue());
		con.setCifReference("VAA5394");
		con.setAuditDescription("Batch Approved as per BSA Procedure. Only 30% of Medium Risk needs KYC Supervisor Review");
		con.setStatus("Pending");
		
		String accessToken = "45b4a94b-00d9-45af-b31c-6abda7578114";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);

		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		headers.setAccept(acceptableMediaTypes);
		
		// Pass the new User and Header
		HttpEntity<AmlBatchCifResponse> entity = new HttpEntity<AmlBatchCifResponse>(con, headers);
		ResponseEntity<AmlBatchCifResponse> response = restTemplate.exchange(TEST_REST_URI + CREATE_AML_BATCH_CIF, HttpMethod.POST, entity, AmlBatchCifResponse.class);
		
		AmlBatchCifResponse result = response.getBody();
		
		assertNotNull(response);
		
		LOGGER.info("Request Id is " + result.getRequestId() + " and CIF is " + result.getCifReference());
		
	}
}
