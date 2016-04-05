/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.common.model;

import java.util.List;

/**
 * The Class RestOauthAccessToken.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
public class RestOauthAccessToken {

	/** The access token. */
	private String accessToken;
	
	/** The refresh token. */
	private String refreshToken;
	
	/** The rest api. */
	private String restApi;
	
	/** The ob dashboard roles list. */
	private List<ObDashboardRoles> obDashboardRolesList; 
	
	private String environment;

	private String userName;

	/**
	 * Gets the ob dashboard roles list.
	 *
	 * @return the ob dashboard roles list
	 */
	public List<ObDashboardRoles> getObDashboardRolesList() {
		return obDashboardRolesList;
	}

	/**
	 * Sets the ob dashboard roles list.
	 *
	 * @param obDashboardRolesList the new ob dashboard roles list
	 */
	public void setObDashboardRolesList(List<ObDashboardRoles> obDashboardRolesList) {
		this.obDashboardRolesList = obDashboardRolesList;
	}

	/**
	 * Gets the access token.
	 *
	 * @return the access token
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * Sets the access token.
	 *
	 * @param accessToken the new access token
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * Gets the refresh token.
	 *
	 * @return the refresh token
	 */
	public String getRefreshToken() {
		return refreshToken;
	}

	/**
	 * Sets the refresh token.
	 *
	 * @param refreshToken the new refresh token
	 */
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	/**
	 * Gets the rest api.
	 *
	 * @return the rest api
	 */
	public String getRestApi() {
		return restApi;
	}

	/**
	 * Sets the rest api.
	 *
	 * @param restApi the new rest api
	 */
	public void setRestApi(String restApi) {
		this.restApi = restApi;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
