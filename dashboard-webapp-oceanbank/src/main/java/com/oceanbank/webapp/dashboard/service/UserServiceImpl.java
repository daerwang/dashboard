/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.dashboard.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oceanbank.webapp.common.handler.AjaxResponseHandler;
import com.oceanbank.webapp.common.model.BootstrapValidatorResponse;
import com.oceanbank.webapp.common.model.ChangePassword;
import com.oceanbank.webapp.common.model.DataTablesRequest;
import com.oceanbank.webapp.common.model.OauthTokenBean;
import com.oceanbank.webapp.common.model.ObDashboardRoles;
import com.oceanbank.webapp.common.model.RestWebServiceUrl;
import com.oceanbank.webapp.common.model.UserResponse;
import com.oceanbank.webapp.common.util.CommonUtil;



@Service
public class UserServiceImpl extends OauthTokenBean implements UserService {


	@Autowired
	private RestTemplate restTemplate;

	@Autowired
    private PasswordEncoder passwordEncoder;
	
	private static Integer ALLOWED_DAYS_BEFORE_EXPIRATION = 60;

	public UserServiceImpl(){}
	
	public Integer daysBeforeExpiry(Date modifiedOn){
		DateTime start = new DateTime(modifiedOn);
        DateTime end = new DateTime(new Date());

		Integer daysDiff = Days.daysBetween(start.toLocalDate(), end.toLocalDate()).getDays();
		
		return daysDiff;
	}
	
	public void validateAccountExpiry(UserResponse userResponse){
		if(userResponse.getAccountNonExpired() == 1){
			Integer daysDiff = daysBeforeExpiry(userResponse.getModifiedon());
			if(daysDiff > ALLOWED_DAYS_BEFORE_EXPIRATION){
				
				userResponse.setAccountNonExpired(0);
				
				updateUser(userResponse);
			}
		}
	}

	private void checkRestException(String exceptionMessage) throws LockedException, BadCredentialsException{
		ObjectMapper mapper = new ObjectMapper();
		JsonNode mainNode;
		try {
			mainNode = mapper.readTree(exceptionMessage);
			if(mainNode.has("name")){
				String name = mainNode.path("name").asText();
				String[] arr = name.split("\\.");
				name = arr[arr.length - 1];
				if(name.trim().contains("LockedException")){
					throw new LockedException("The User is locked out.");
				}
				if(name.trim().contains("BadCredentialsException")){
					throw new BadCredentialsException("The User name and password is not correct.");
				}
				if(name.trim().contains("JpaSystemException")){
					throw new BadCredentialsException("The User name and password is not correct. - JpaSystemException");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new InternalAuthenticationServiceException("The DB or Server may not be responding.");
		} catch (Exception e) {
			e.printStackTrace();
			throw new InternalAuthenticationServiceException("The DB or Server may not be responding.");
		}


	}

	public String updateFailedAttempt(String username) throws LockedException, BadCredentialsException{
		final HttpEntity<String> entity = CommonUtil.createHttpEntity(getAccessToken());
		ResponseEntity<String> response = null;

		try {
			response = restTemplate.exchange(getRestApi() + "/api/user/updateFailedAttempt/{username}", HttpMethod.POST, entity,
					String.class, username);
		} catch (Exception e) {
			e.printStackTrace();
			String msg = e.getMessage();
			checkRestException(msg);
		}


        String result = response.getBody();

		return result;
	}

	public String resetFailedAttempt(String username){
		final HttpEntity<String> entity = CommonUtil.createHttpEntity(getAccessToken());
		ResponseEntity<String> response = null;

		try {
			response = restTemplate.exchange(getRestApi() + "/api/user/resetFailedAttempt/{username}", HttpMethod.POST, entity,
					String.class, username);
		} catch (Exception e) {
			e.printStackTrace();
		}


        String result = response.getBody();

		return result;
	}


	@Override
	public UserResponse findUserByUsername(String username) {

		final HttpEntity<String> entity = CommonUtil.createHttpEntity(getAccessToken());
		ResponseEntity<UserResponse> response = null;

		try {
			response = restTemplate.exchange(getRestApi() + "/api/user/username/{username}", HttpMethod.GET, entity,
					UserResponse.class, username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(response == null){
			return null;
		}
		
		final UserResponse result = response.getBody();

		return result;
	}

	@Override
	public UserResponse createUser(UserResponse request) {
		final HttpEntity<UserResponse> entity = CommonUtil.createHttpEntityWithParameters(getAccessToken(), request);

		request.setPassword((passwordEncoder.encode(request.getPassword())));

		final ResponseEntity<UserResponse> response = restTemplate.exchange(getRestApi()
				+ RestWebServiceUrl.CREATE_USER, HttpMethod.POST, entity,
				UserResponse.class);

		final UserResponse result = response.getBody();

		return result;
	}


	@Override
	public BootstrapValidatorResponse findUserByUsernameWithBootstrapValidator(String username) {

		final HttpEntity<UserResponse> entity = CommonUtil.createHttpEntityWithParameters(getAccessToken(), new UserResponse(username));

		final ResponseEntity<BootstrapValidatorResponse> response = restTemplate.exchange(getRestApi()
				+ RestWebServiceUrl.GET_USER_USERNAME_BY_BOOTSTRAP_VALIDATOR, HttpMethod.POST, entity,
				BootstrapValidatorResponse.class);

		final BootstrapValidatorResponse result = response.getBody();

		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.dashboard.service.UserService#findUserByUsernameWithBootstrapValidatorOriginal(java.lang.String, java.lang.String)
	 */
	@Override
	public BootstrapValidatorResponse findUserByUsernameWithBootstrapValidatorOriginal(String originalUsername, String newUsername) {

		final HttpEntity<UserResponse> entity = CommonUtil.createHttpEntityWithParameters(getAccessToken(), new UserResponse(newUsername));

		final ResponseEntity<BootstrapValidatorResponse> response = restTemplate.exchange(getRestApi()
				+ RestWebServiceUrl.GET_USER_USERNAME_BY_BOOTSTRAP_VALIDATOR_ORIGINAL, HttpMethod.POST, entity,
				BootstrapValidatorResponse.class, originalUsername);

		final BootstrapValidatorResponse result = response.getBody();

		return result;
	}
	
	public Boolean isPasswordCorrectFormat(String password){
		String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{7,20})";

		Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
		Matcher matcher = pattern.matcher(password);
		Boolean isCorrect =  matcher.matches();

		return isCorrect;
	}

	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.dashboard.service.UserService#searchUserDataTable(com.oceanbank.webapp.common.model.DataTablesRequest)
	 */
	@Override
	public List<UserResponse> searchUserDataTable(DataTablesRequest datatableRequest) {
		final HttpEntity<DataTablesRequest> entity = CommonUtil.createHttpEntityWithParameters(getAccessToken(), datatableRequest);

		final ResponseEntity<UserResponse[]> response = restTemplate.exchange(getRestApi()
				+ RestWebServiceUrl.GET_USER_SEARCH_BY_DATATABLE, HttpMethod.POST, entity,
				UserResponse[].class);

		final List<UserResponse> userResponseList = Arrays.asList(response.getBody());

		return userResponseList;
	}
	

	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.dashboard.service.UserService#findUserByUserid(java.lang.Integer)
	 */
	@Override
	public UserResponse findUserByUserid(Integer user_id) {
		
		final HttpEntity<String> entity = CommonUtil.createHttpEntity(getAccessToken());

		final ResponseEntity<UserResponse> response = restTemplate.exchange(getRestApi() + RestWebServiceUrl.GET_USER, HttpMethod.GET, entity, UserResponse.class, user_id);

		final UserResponse result = response.getBody();

		return result;
	}

	public UserResponse changePassword(Integer userId, String password){
		UserResponse oldUser = findUserByUserid(userId);
		oldUser.setPassword(password);
		UserResponse newUser = updateUser(oldUser);
		return newUser;
	}

	@Override
	public UserResponse updateUser(UserResponse response) {
		// check if old is different from new
		UserResponse old = findUserByUserid(response.getUserId());

		if(response.getPassword() != null && response.getPassword().trim().length() > 0
				&& !old.getPassword().trim().equals(response.getPassword().trim())){
			response.setPassword((passwordEncoder.encode(response.getPassword())));
		}

		final HttpEntity<UserResponse> entity = CommonUtil.createHttpEntityWithParameters(getAccessToken(), response);

		final ResponseEntity<UserResponse> updatedResponse = restTemplate.exchange(getRestApi()
				+ RestWebServiceUrl.UPDATE_USER, HttpMethod.PUT, entity, UserResponse.class);

		final UserResponse result = updatedResponse.getBody();

		return result;
	}

	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.dashboard.service.UserService#getObDashboardRoles(java.lang.String)
	 */
	@Override
	public ObDashboardRoles getObDashboardRoles(String category){
		
		final List<ObDashboardRoles> list = getOauthAccessToken().getObDashboardRolesList();
		
		for(ObDashboardRoles r : list){
			if(category.trim().equalsIgnoreCase(r.getCategory().trim())){
				return r;
			}
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.dashboard.service.UserService#deleteUser(java.lang.Integer)
	 */
	@Override
	public String deleteUser(Integer user_id) {

		final HttpEntity<String> entity = CommonUtil.createHttpEntity(getAccessToken());
		
		final ResponseEntity<String> response = restTemplate.exchange(getRestApi()
				+ RestWebServiceUrl.DELETE_USER, HttpMethod.DELETE, entity, String.class, user_id);
		final String result = response.getBody();
		
        return result;
	}

	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.dashboard.service.UserService#changeUserPassword(com.oceanbank.webapp.common.model.ChangePassword)
	 */
	@Override
	public AjaxResponseHandler changeUserPassword(ChangePassword request) {

		final HttpEntity<ChangePassword> entity = CommonUtil.createHttpEntityWithParameters(getAccessToken(), request);

		final ResponseEntity<UserResponse> updatedResponse = restTemplate.exchange(getRestApi()
				+ RestWebServiceUrl.EXECUTE_CHANGE_PASSWORD, HttpMethod.PUT, entity, UserResponse.class);

		final UserResponse result = updatedResponse.getBody();
		
		final AjaxResponseHandler handler = new AjaxResponseHandler();
		if(result.getUsername() == null){
			return null;
		}else{
			handler.setCode("OK");
			handler.setMessage("Your password is changed successfully.");
		}

		return handler;
	}

	
}
