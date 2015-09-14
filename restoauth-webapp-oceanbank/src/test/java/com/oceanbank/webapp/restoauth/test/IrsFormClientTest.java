package com.oceanbank.webapp.restoauth.test;

import com.oceanbank.webapp.common.model.RestWebServiceUrl;
import com.oceanbank.webapp.common.model.DashboardConstant;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.oceanbank.webapp.common.model.DashboardConstant;
import com.oceanbank.webapp.common.model.DataTablesRequest;
import com.oceanbank.webapp.common.model.IrsFormCustomerResponse;
import com.oceanbank.webapp.common.model.IrsFormSelected;
import com.oceanbank.webapp.common.model.MailCodeResponse;
import com.oceanbank.webapp.restoauth.model.RestOauthAccessToken;

/**
 * Sample Oauth2 request and refresh token	
 * 
 * request a token
 * http://localhost:8085/restoauth-webapp-oceanbank/oauth/token?grant_type=password&client_id=devaccess&client_secret=oceanbank&username=oceandev&password=ocean@123
 * 
 * refresh a token
 * http://localhost:8085/restoauth-webapp-oceanbank/oauth/token?grant_type=refresh_token&client_id=devaccess&client_secret=oceanbank&refresh_token=7ac7940a-d29d-4a4c-9a47-25a2167c8c49
 * 
 * access token for jdbtokenstore
 * http://localhost:8080/restoauth-webapp-oceanbank/oauth/token?grant_type=password&client_id=oceantest&client_secret=ocean@123&username=oceantest&password=ocean@123
 * http://localhost:8080/restoauth-webapp-oceanbank/oauth/token?grant_type=password&client_id=oceandev&client_secret=ocean@123&username=oceandev&password=ocean@123
 * http://localhost:8080/restoauth-webapp-oceanbank/oauth/token?grant_type=refresh_token&client_id=oceandev&client_secret=ocean@123&refresh_token=738875ae-34f4-46f2-abde-b2612dfe2a51
 * 
 */

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/resources/springcontext/test-context-spring.xml" })
public class IrsFormClientTest {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	public static final String TEST_REST_URI = "http://localhost:8080/restoauth-webapp-oceanbank";

	
	private String accessToken;
	private String restApi;
	
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private RestOauthAccessToken oauthToken;

	@Autowired
	private ApplicationContext appContext;
	
	@Before
	public void setup_oauth(){
		this.accessToken = oauthToken.getAccessToken();
		this.restApi = oauthToken.getRestApi();
	}
	
	
	@Test
	public void test_number_to_requirement(){
		
		String res = String.format("%02d", 0.00);
		
		LOGGER.info("It is " + res);
	}
	
	@Test
	public void test_number_leading_zero(){
		String res = String.format("%02d", 2);
		
		LOGGER.info("It is " + res);
	}
	
	@Test
	public void test_mail_code_distinct() {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		headers.setAccept(acceptableMediaTypes);


		HttpEntity<String> entity = new HttpEntity<String>(headers);
		ResponseEntity<MailCodeResponse[]> response = restTemplate
				.exchange(restApi + RestWebServiceUrl.GET_IRS_MAIL_CODE_DISTINCT,
						HttpMethod.POST, entity,
						MailCodeResponse[].class);
		List<MailCodeResponse> list = Arrays.asList(response.getBody());

		for(MailCodeResponse r : list){
			LOGGER.info(r.getCode());
		}

	}
	
	@Test
	public void test_get_irs_search_datatable_mail_code() {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		headers.setAccept(acceptableMediaTypes);

		DataTablesRequest datatableRequest = new DataTablesRequest();
		datatableRequest.setValue(null);
		datatableRequest.setStart(1);
		datatableRequest.setLength(10);
		datatableRequest.setMailCode("Z,A,");

		HttpEntity<DataTablesRequest> entity = new HttpEntity<DataTablesRequest>(
				datatableRequest, headers);
		ResponseEntity<IrsFormCustomerResponse[]> response = restTemplate
				.exchange(restApi + RestWebServiceUrl.GET_IRS_FORM_SEARCH_BY_DATATABLE,
						HttpMethod.POST, entity,
						IrsFormCustomerResponse[].class);
		List<IrsFormCustomerResponse> resultList = Arrays.asList(response
				.getBody());

		Integer count = resultList.size();

		LOGGER.info("Number of Customer is " + count);

		assertThat(count, is(not(0)));
	}
	
	
	
	@Test
	public void test_mail_code_passed(){
		String code = "Z,O,";
		String[] arr = code.split(",");
		LOGGER.info("Length is : " + arr.length);
		for(String s : arr){
			LOGGER.info("It is: " + s);
		}
	}
	
	@Test
	public void test_individual_saving_to_disk_all() throws IOException, InterruptedException{

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);

		HttpEntity<String> entity = new HttpEntity<String>(headers);
		ResponseEntity<String> response = restTemplate.exchange(restApi + RestWebServiceUrl.GENERATE_SELECTED_PDF_ALL, HttpMethod.POST, entity, String.class);
		String result = response.getBody();
		
		LOGGER.info("The response is " + result);

		// then open the pdf from File location
		Process p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + DashboardConstant.PDF_MERGING_FILE_LOCATION);
		p.waitFor();
	}
	
	@Test
	public void test_individual_saving_to_disk() throws IOException, InterruptedException{

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON); 
		headers.setAccept(acceptableMediaTypes);

		IrsFormSelected selected = new IrsFormSelected();
		//selected.setSelected(new String[]{"MARIA LUISA ESPINOZA,140557808,592237280,334.35","ROSARIO FRANCO GUIDO,146963020,592237280,734.19","VINCENZO ABATE FALSETTI,141199320,592237280,513.00"});
		selected.setSelected(new String[]{"GIUSEPPINA ALLOCCA ANNUNZIATA,526494408,592237280,239.84"});

		HttpEntity<IrsFormSelected> entity = new HttpEntity<IrsFormSelected>(selected,headers);
		ResponseEntity<String> response = restTemplate.exchange(restApi + RestWebServiceUrl.GENERATE_SELECTED_PDF, HttpMethod.POST, entity, String.class);
		String result = response.getBody();
		
		LOGGER.info("The response is " + result);

		// then open the pdf from File location
		Process p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + DashboardConstant.PDF_MERGING_FILE_LOCATION);
		p.waitFor();
	}
	
	
	
	

	@Test
	public void test_get_user_by_search_datatable() {
		LOGGER.info("Test IRS Form Datatable Search");

		String accessToken = "3aba6911-2ff2-47bc-bac3-f882ae22b892";
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
		ResponseEntity<IrsFormCustomerResponse[]> response = restTemplate
				.exchange(TEST_REST_URI + RestWebServiceUrl.GET_IRS_FORM_SEARCH_BY_DATATABLE,
						HttpMethod.POST, entity,
						IrsFormCustomerResponse[].class);
		List<IrsFormCustomerResponse> resultList = Arrays.asList(response
				.getBody());

		Integer count = resultList.size();
		for (IrsFormCustomerResponse r : resultList) {
			LOGGER.info("It is " + r.getFld_14acd_1());
		}
		LOGGER.info("Number of users is " + count);

		assertThat(count, is(not(0)));
	}

	@Test
	public void test_if_file_is_existing() throws IOException {
		LOGGER.info("Checking...");

		Resource res = appContext
				.getResource("classpath:editpdf/1042sPdfTemplate.pdf");

		File f = res.getFile();

		// File f = new File(PDF_TEMPLATE_LOCATION);

		if (f.exists() && !f.isDirectory()) {
			LOGGER.info("There is a file...");
			LOGGER.info(f.getAbsolutePath());

		} else {
			LOGGER.info("File does not exist.." + f.getAbsolutePath());

		}

	}
	
	@Test
	public void test_heap_size(){
		// Get current size of heap in bytes
		long heapSize = Runtime.getRuntime().totalMemory(); 

		// Get maximum size of heap in bytes. The heap cannot grow beyond this size.// Any attempt will result in an OutOfMemoryException.
		long heapMaxSize = Runtime.getRuntime().maxMemory();

		 // Get amount of free memory within the heap in bytes. This size will increase // after garbage collection and decrease as new objects are created.
		long heapFreeSize = Runtime.getRuntime().freeMemory(); 
		
		LOGGER.info("heapSize " + heapSize/1000);
		LOGGER.info("heapMaxSize " + heapMaxSize/1000);
		LOGGER.info("heapFreeSize " + heapFreeSize/1000);
	}
	
	

	@Test
	public void test_sql_like_in_java() {
		if ("ALICIA AAGAARD CELIS ".matches(".*ALICIA.*")) {
			LOGGER.info("Matched");
		} else {
			LOGGER.info("NOT Matched");
		}
	}
	
}
