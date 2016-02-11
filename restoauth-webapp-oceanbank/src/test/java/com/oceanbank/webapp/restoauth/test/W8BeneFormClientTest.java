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
import com.oceanbank.webapp.common.model.W8BeneFormResponse;
import com.oceanbank.webapp.common.util.CommonUtil;
import com.oceanbank.webapp.restoauth.model.RestOauthAccessToken;


@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/resources/springcontext/test-context-spring.xml" })
public class W8BeneFormClientTest {

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
	public void test_api() {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		//63e4a86c-d62d-48a6-8594-5e512d2de11c
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		headers.setAccept(acceptableMediaTypes);

		DataTablesRequest datatableRequest = new DataTablesRequest();
		datatableRequest.setValue(null);
		datatableRequest.setStart(1);
		datatableRequest.setLength(10);

		HttpEntity<DataTablesRequest> entity = new HttpEntity<DataTablesRequest>(datatableRequest, headers);
		String url = "http://localhost:8085/restoauth-webapp-oceanbank" + "/api/w8beneform/dataTable";
		ResponseEntity<W8BeneFormResponse[]> response = restTemplate.exchange(url, HttpMethod.POST, entity, W8BeneFormResponse[].class);
		List<W8BeneFormResponse> resultList = Arrays.asList(response.getBody());

		Integer count = resultList.size();

		LOGGER.info("Number of Customer is " + count);
	}
	
	@Test
	public void test_api_selections() throws IOException, InterruptedException{

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON); 
		headers.setAccept(acceptableMediaTypes);

		IrsFormSelected selected = new IrsFormSelected();
		selected.setSelected(new String[]{"2"});
		HttpEntity<IrsFormSelected> entity = new HttpEntity<IrsFormSelected>(selected,headers);
		String url = "http://localhost:8080/restoauth-webapp-oceanbank" + "/api/w8beneform/createPdfToDisk";
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		String result = response.getBody();
		
		LOGGER.info("The response is " + result);

		String mergedFilePath = W8BENEFORM_MERGE_DIRECTORY + "//" + MERGE_PDF_NAME;
		CommonUtil.openFile(mergedFilePath);
	}
	
	private static String W8BENEFORM_MERGE_DIRECTORY = "C://dashboard//w8beneform//merge";
	private static String MERGE_PDF_NAME = "W8BeneForm_merged.pdf";
}
