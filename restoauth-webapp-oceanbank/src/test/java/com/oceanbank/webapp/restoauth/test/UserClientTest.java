package com.oceanbank.webapp.restoauth.test;

//import static org.hamcrest.CoreMatchers.equalTo;
import static com.oceanbank.webapp.common.model.RestWebServiceUrl.CREATE_USER;
import static com.oceanbank.webapp.common.model.RestWebServiceUrl.DELETE_USER;
import static com.oceanbank.webapp.common.model.RestWebServiceUrl.EXECUTE_CHANGE_PASSWORD;
import static com.oceanbank.webapp.common.model.RestWebServiceUrl.GET_USER;
import static com.oceanbank.webapp.common.model.RestWebServiceUrl.GET_USER_SEARCH_BY_DATATABLE;
import static com.oceanbank.webapp.common.model.RestWebServiceUrl.GET_USER_USERNAME;
import static com.oceanbank.webapp.common.model.RestWebServiceUrl.GET_USER_USERNAME_BY_BOOTSTRAP_VALIDATOR;
import static com.oceanbank.webapp.common.model.RestWebServiceUrl.GET_USER_USERNAME_BY_BOOTSTRAP_VALIDATOR_ORIGINAL;
import static com.oceanbank.webapp.common.model.RestWebServiceUrl.GET_USER_WITH_ROLES;
import static com.oceanbank.webapp.common.model.RestWebServiceUrl.UPDATE_USER;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oceanbank.webapp.common.handler.GsonDataTableTypeAdapter;
import com.oceanbank.webapp.common.model.BootstrapValidatorResponse;
import com.oceanbank.webapp.common.model.ChangePassword;
import com.oceanbank.webapp.common.model.DataTablesRequest;
import com.oceanbank.webapp.common.model.DataTablesResponse;
import com.oceanbank.webapp.common.model.UserDataTableResponse;
import com.oceanbank.webapp.common.model.UserResponse;
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
 */



@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"file:src/main/resources/springcontext/test-context-spring.xml"})
public class UserClientTest {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	public static final String TEST_REST_URI = "http://localhost:8080/restoauth-webapp-oceanbank";
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private RestOauthAccessToken oauthToken;
	
	private String accessToken;
	private String restApi;
	
	@Before
	public void setup_oauth(){
		this.accessToken = oauthToken.getAccessToken();
		this.restApi = oauthToken.getRestApi();
	}
	
	
	
	
	@Test
	public void test_change_password(){
		LOGGER.info("Start update a User username...");
		
        String accessToken = "9cb58648-6d19-413b-997a-dfd95c271061";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		
		// Prepare acceptable media type
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		headers.setAccept(acceptableMediaTypes);
		
		// Pass the new User and Header
		ChangePassword request = new ChangePassword("nina", "medina", "albano");
		HttpEntity<ChangePassword> entity = new HttpEntity<ChangePassword>(request, headers);
		
		ResponseEntity<UserResponse> responseUpdateUser = restTemplate.exchange(TEST_REST_URI + EXECUTE_CHANGE_PASSWORD, HttpMethod.PUT, entity, UserResponse.class);
		UserResponse updatedUser = responseUpdateUser.getBody();
		LOGGER.info("First Name: " + updatedUser.getFirstname()+ "; Password: "  + updatedUser.getPassword() + "; Email: "+ updatedUser.getEmail());
		
        assertNotNull("Should not be null", updatedUser);
        
	}
	
	@Test
	public void test_substring(){
		String url = "/user/bootstrap/validator/{originalUsername}";
		int index = url.indexOf("{");
		String result = url.substring(0, index); 
		LOGGER.info("It is " + index);
		LOGGER.info(result);
		assertNotNull(index);
	}
	
	@Test
	public void test_get_user_by_username_bootstrapvalidator_original(){
		LOGGER.info("Start retreiving User by username ...");
		
		String username = "nell";
		String newUsername = "nell";
		
        String accessToken = "bdb0ae57-c586-4cfe-9c0c-c97f995c4e85";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		
		// Prepare acceptable media type
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		headers.setAccept(acceptableMediaTypes);
				
		// Identify the user and change username
		UserResponse userResponse = new UserResponse();
		userResponse.setUsername(newUsername);
				
		// Pass the new User and Header
		HttpEntity<UserResponse> entity = new HttpEntity<UserResponse>(userResponse, headers);
        ResponseEntity<BootstrapValidatorResponse> response = restTemplate.exchange(TEST_REST_URI + GET_USER_USERNAME_BY_BOOTSTRAP_VALIDATOR_ORIGINAL, HttpMethod.POST, entity, BootstrapValidatorResponse.class, username);
        BootstrapValidatorResponse result = response.getBody();
        
        assertNotNull("Should not be null", result);
        String answer = result.getValid() ? "User does not exist." : "User exist.";
        LOGGER.info("Is it Valid?: " + result.getValid() + " OR " + answer);
	}
	
	
	@Test
	public void test_split_of_userid(){
		String userid = "row_2";
		String[] arr = userid.split("_");
		String arrayNumber = arr[1];
		Integer user_id = Integer.parseInt(arrayNumber.trim()); 
		
		assertNotNull(arrayNumber);
		
		LOGGER.info("Id is " + user_id);
		
	}
	
	@Test
	public void test_power_of_gson(){
		DataTablesResponse response = new DataTablesResponse();
		response.setDraw(1);
		response.setRecordsFiltered(50);
		response.setRecordsTotal(15);
		
		List<UserDataTableResponse> dataList = new ArrayList<UserDataTableResponse>();
		UserDataTableResponse userA = new UserDataTableResponse();
		UserDataTableResponse userB = new UserDataTableResponse();
		
		userA.setUserId("row_1");
		userA.setFirstname("Nell");
		userA.setFirstname("Nell");
		userA.setLastname("Medina");
		userA.setUsername("nellbryant");
		userA.setPassword("chow");
		userA.setEmail("chenes@yahoo.com");
		
		userB.setUserId("row_2");
		userB.setFirstname("Angelina");
		userB.setLastname("Jolie");
		userB.setUsername("brad");
		userB.setPassword("pwett");
		userB.setEmail("heaven@yahoo.com");
		
		dataList.add(userA);
		dataList.add(userB);
		response.setData(dataList);
		
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(DataTablesResponse.class, new GsonDataTableTypeAdapter());
		gsonBuilder.setPrettyPrinting();
		final Gson gson = gsonBuilder.create();
		
		String json = gson.toJson(response);

		LOGGER.info(json);
		
		assertNotNull(json);
		
	}
	
	@Test
	public void test_get_user_by_search_datatable(){
		LOGGER.info("Start retreiving Users by search parameter ...");
		
		DataTablesRequest datatableRequest = new DataTablesRequest();
		datatableRequest.setValue("");
		datatableRequest.setStart(0);
		datatableRequest.setLength(10);
		
        String accessToken = "60e2979d-7280-46b4-8b85-e0a3475d7d39";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		
		// Prepare acceptable media type
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		headers.setAccept(acceptableMediaTypes);

		// Pass the new User and Header
		HttpEntity<DataTablesRequest> entity = new HttpEntity<DataTablesRequest>(datatableRequest, headers);
        ResponseEntity<UserResponse[]> response = restTemplate.exchange(TEST_REST_URI + GET_USER_SEARCH_BY_DATATABLE, HttpMethod.POST, entity, UserResponse[].class);
        List<UserResponse> resultList = Arrays.asList(response.getBody());
        
        Integer count = resultList.size();
		for(UserResponse r : resultList){
			LOGGER.info("It is " + r.getUsername());
		}
		LOGGER.info("Number of users is " + count);
		
		assertThat(count, is(not(0)));
	}
	
	@Test
	public void test_get_user_by_username_bootstrapvalidator(){
		LOGGER.info("Start retreiving User by username ...");
		
		String username = "nelly";
		
        String accessToken = "35a692dc-ed82-4eac-8da0-2c46c129afef";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		
		// Prepare acceptable media type
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		headers.setAccept(acceptableMediaTypes);
				
		// Identify the user and change username
		UserResponse userResponse = new UserResponse();
		userResponse.setUsername(username);
				
		// Pass the new User and Header
		HttpEntity<UserResponse> entity = new HttpEntity<UserResponse>(userResponse, headers);
        ResponseEntity<BootstrapValidatorResponse> response = restTemplate.exchange(TEST_REST_URI + GET_USER_USERNAME_BY_BOOTSTRAP_VALIDATOR, HttpMethod.POST, entity, BootstrapValidatorResponse.class, username);
        BootstrapValidatorResponse result = response.getBody();
        
        assertNotNull("Should not be null", result);
        String answer = result.getValid() ? "User does not exist." : "User exist.";
        LOGGER.info("Is it Valid?: " + result.getValid() + " OR " + answer);
	}
	
	@Test
	public void test_get_user_by_username_with_roles(){
		LOGGER.info("Start retreiving User by username ...");
		
		String username = "nell";
		
        //String accessToken = "71a9c3de-14c6-47d7-a6cc-e5ac3dc10bd3";
		//String accessToken = "3b84e098-e87e-4068-8cd9-3d3d60073246";
        //String accessToken = "3aba6911-2ff2-47bc-bac3-f882ae22b892";
        String accessToken = "0c3ec78e-3c34-4dfe-a314-cc37ec49ec7f";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		
		HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<UserResponse> response = restTemplate.exchange(TEST_REST_URI + GET_USER_USERNAME, HttpMethod.GET, entity, UserResponse.class, username);
        UserResponse result = response.getBody();
        
        assertNotNull("Should not be null", result);
        
        LOGGER.info("Firstname: " + result.getFirstname()+ "; Password: "  + result.getPassword() + "; Email: "+ result.getEmail());
        LOGGER.info("Roles is " + result.getRoleses().get(0).getRoleName());
	}
	
	@Test
	public void test_get_user_by_userid_with_roles(){
		LOGGER.info("Start retreiving User by username ...");
		
		Integer userid = 2;
		
        String accessToken = "da582091-1e76-4d8e-b101-526b7a9861dc";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		
		HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<UserResponse> response = restTemplate.exchange(TEST_REST_URI + GET_USER_WITH_ROLES, HttpMethod.GET, entity, UserResponse.class, userid);
        UserResponse result = response.getBody();
        
        assertNotNull("Should not be null", result);
        
        LOGGER.info("Username: " + result.getUsername() + "; Password: "  + result.getPassword() + "; Email: "+ result.getEmail());
        LOGGER.info("Roles is " + result.getRoleses().get(0).getRoleName());
	}
	
	@Test
	public void test_get_user_by_username(){
		LOGGER.info("Start retreiving User by username ...");
		
		String username = "nell";

        //String accessToken = "6ba1e375-f0ff-498f-bd7a-1411eaea385d";
        String accessToken = "3aba6911-2ff2-47bc-bac3-f882ae22b892";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		
		HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<UserResponse> response = restTemplate.exchange(TEST_REST_URI + GET_USER_USERNAME, HttpMethod.GET, entity, UserResponse.class, username);
        UserResponse result = response.getBody();
        
        assertNotNull("Should not be null", result);
        
        LOGGER.info("Username: " + result.getUsername() + "; Password: "  + result.getPassword() + "; Email: "+ result.getEmail());
	}
	
	@Test
	public void test_get_user_by_id(){
		
		Integer user_id = 1;
		
		LOGGER.info("Start REST template retreiving user by id " + user_id);
		
		
		
        RestTemplate restTemplate = new RestTemplate();
        String accessToken = "36b78c63-9aea-48fb-a29d-4d7462063997";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		
		HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<UserResponse> response = restTemplate.exchange(TEST_REST_URI + GET_USER, HttpMethod.GET, entity, UserResponse.class, user_id);
        UserResponse result = response.getBody();
        
        assertNotNull("Should not be null", result);
        
        LOGGER.info("Username: " + result.getUsername() + "; Password: "  + result.getPassword() + "; Email: "+ result.getEmail());
	}
	
	@Test
	public void test_get_all_users(){
		LOGGER.info("Start get all Users ...");
		
		RestTemplate restTemplate = new RestTemplate();
        String accessToken = "abe561fd-7e1e-4270-9c08-da1dfd4e021b";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		ResponseEntity<UserResponse[]> response = restTemplate.exchange(TEST_REST_URI+"/api/user", HttpMethod.GET, entity, UserResponse[].class);
		
		List<UserResponse> userResponseList = Arrays.asList(response.getBody());
		
		Integer count = userResponseList.size();
		
		LOGGER.info("Number of users is " + count);
		
		assertThat(count, is(not(0)));
	}
	
	@Test
	public void test_create_user(){
		LOGGER.info("Start Create User ...");

        String accessToken = "0a0ad09c-1e75-40d9-8d66-267b9fdd6ab6";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		
		// Prepare acceptable media type
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		headers.setAccept(acceptableMediaTypes);
		
		// Create the user to save and a default ROLE_DEV will be assigned
		UserResponse request = new UserResponse();
		request.setFirstname("Marinell");
		request.setLastname("Medina");
		request.setUsername("nell");
		request.setPassword("medina");
		request.setEmail("medinama@ph.ibm.com");
		request.setRoleNames(new String[]{"Tester", "Administrator"});
		
		
		// Pass the new User and Header
		HttpEntity<UserResponse> entity = new HttpEntity<UserResponse>(request, headers);
		ResponseEntity<UserResponse> response = restTemplate.exchange(TEST_REST_URI + CREATE_USER, HttpMethod.POST, entity, UserResponse.class);
		
		UserResponse result = new UserResponse();
		result = response.getBody();
		
		LOGGER.info("Username: " + result.getUsername() + "; Password: "  + result.getPassword() + "; Email: "+ result.getEmail());
		
        assertNotNull("Should not be null", result);
        
	}
	
	@Test
	public void test_delete_user(){
		LOGGER.info("Start Delete of User");
		
		Integer user_id = 5;
		
        String accessToken = "a46f7ad3-3c94-4b80-8cd4-88d38b5bed79";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
        ResponseEntity<String> response = restTemplate.exchange(TEST_REST_URI + DELETE_USER, HttpMethod.DELETE, entity, String.class, user_id);
        String result = response.getBody();
        
        LOGGER.info("The result is: {}", result);
        assertNotNull("Should not be null", result);
        
        
	}
	
	@Test
	public void test_update_user(){
		LOGGER.info("Start update a User username...");
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		
		// Prepare acceptable media type
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		headers.setAccept(acceptableMediaTypes);
		
		// Get an existing user and change First Name
		Integer user_id = 2;
		HttpEntity<String> entity1 = new HttpEntity<String>(headers);
        ResponseEntity<UserResponse> responseExistingUser = restTemplate.exchange(restApi + GET_USER, HttpMethod.GET, entity1, UserResponse.class, user_id);
        UserResponse existingUser = responseExistingUser.getBody();
        existingUser.setFirstname("Marinells");
        //existingUser.setRoleNames(new String[]{"Administrator"});
		
		// Pass the new User and Header
        UserResponse updatedUser = new UserResponse();
		HttpEntity<UserResponse> entity = new HttpEntity<UserResponse>(existingUser, headers);
		ResponseEntity<UserResponse> responseUpdateUser = restTemplate.exchange(restApi + UPDATE_USER, HttpMethod.PUT, entity, UserResponse.class);
		updatedUser = responseUpdateUser.getBody();
		LOGGER.info("First Name: " + updatedUser.getFirstname()+ "; Password: "  + updatedUser.getPassword() + "; Email: "+ updatedUser.getEmail());
		LOGGER.info("It is " + updatedUser.getRoleNames());
        assertNotNull("Should not be null", updatedUser);
        
	}
}
