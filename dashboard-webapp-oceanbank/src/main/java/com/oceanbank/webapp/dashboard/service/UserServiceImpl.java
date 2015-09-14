/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.dashboard.service;

import com.oceanbank.webapp.common.model.DashboardConstant;
import com.oceanbank.webapp.common.model.RestWebServiceUrl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.oceanbank.webapp.common.handler.AjaxResponseHandler;
import com.oceanbank.webapp.common.model.BootstrapValidatorResponse;
import com.oceanbank.webapp.common.model.ChangePassword;
import com.oceanbank.webapp.common.model.DataTablesRequest;
import com.oceanbank.webapp.common.model.OauthTokenBean;
import com.oceanbank.webapp.common.model.ObDashboardRoles;
import com.oceanbank.webapp.common.model.UserResponse;
import com.oceanbank.webapp.common.util.CommonUtil;



/**
 * The Class UserServiceImpl.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@Service
public class UserServiceImpl extends OauthTokenBean implements UserService {

	/** The rest template. */
	@Autowired
	private RestTemplate restTemplate;

	/**
	 * Instantiates a new user service impl.
	 */
	public UserServiceImpl(){}
	
	
	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.dashboard.service.UserService#findUserByUsername(java.lang.String)
	 */
	@Override
	public UserResponse findUserByUsername(String username) {

		final HttpEntity<String> entity = CommonUtil.createHttpEntity(getAccessToken());
		ResponseEntity<UserResponse> response = null;

		try {
			response = restTemplate.exchange(getRestApi() + RestWebServiceUrl.GET_USER_USERNAME, HttpMethod.GET, entity,
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
	
	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.dashboard.service.UserService#createUser(com.oceanbank.webapp.common.model.UserResponse)
	 */
	@Override
	public UserResponse createUser(UserResponse request) {
		final HttpEntity<UserResponse> entity = CommonUtil.createHttpEntityWithParameters(getAccessToken(), request);

		final ResponseEntity<UserResponse> response = restTemplate.exchange(getRestApi()
				+ RestWebServiceUrl.CREATE_USER, HttpMethod.POST, entity,
				UserResponse.class);

		final UserResponse result = response.getBody();

		return result;
	}

	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.dashboard.service.UserService#findUserByUsernameWithBootstrapValidator(java.lang.String)
	 */
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

	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.dashboard.service.UserService#updateUser(com.oceanbank.webapp.common.model.UserResponse)
	 */
	@Override
	public UserResponse updateUser(UserResponse response) {
		
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
