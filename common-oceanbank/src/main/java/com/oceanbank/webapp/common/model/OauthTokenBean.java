/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.common.model;

import org.springframework.beans.factory.annotation.Autowired;

import com.oceanbank.webapp.common.model.RestOauthAccessToken;

/**
 * The Class OauthTokenBean.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
public class OauthTokenBean {

	/** The access token. */
	private String accessToken;
	
	/** The rest api. */
	private String restApi;
	
	/** The oauth access token. */
	@Autowired
	private RestOauthAccessToken oauthAccessToken;
	

	


	/**
	 * Gets the access token.
	 *
	 * @return the access token
	 */
	public String getAccessToken() {
		accessToken = oauthAccessToken.getAccessToken();
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
	 * Gets the rest api.
	 *
	 * @return the rest api
	 */
	public String getRestApi() {
		restApi = oauthAccessToken.getRestApi();
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


	/**
	 * Gets the oauth access token.
	 *
	 * @return the oauth access token
	 */
	public RestOauthAccessToken getOauthAccessToken() {
		return oauthAccessToken;
	}


	/**
	 * Sets the oauth access token.
	 *
	 * @param oauthAccessToken the new oauth access token
	 */
	public void setOauthAccessToken(RestOauthAccessToken oauthAccessToken) {
		this.oauthAccessToken = oauthAccessToken;
	}
}
