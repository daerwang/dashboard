/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.restoauth.model;


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

}
