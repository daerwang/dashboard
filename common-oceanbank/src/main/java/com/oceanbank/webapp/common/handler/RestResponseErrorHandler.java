/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.common.handler;


import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;

/**
 * The Class RestResponseErrorHandler.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
public class RestResponseErrorHandler implements ResponseErrorHandler{
	
	/* (non-Javadoc)
	 * @see org.springframework.web.client.ResponseErrorHandler#handleError(org.springframework.http.client.ClientHttpResponse)
	 */
	public void handleError(ClientHttpResponse response) throws IOException {
		
		String theString = IOUtils.toString(response.getBody());
		
		if(HttpStatus.UNAUTHORIZED.equals(response.getStatusCode())){
			throw new HttpClientErrorException(response.getStatusCode());
		}
		
		throw new RestClientException(theString);
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.client.ResponseErrorHandler#hasError(org.springframework.http.client.ClientHttpResponse)
	 */
	public boolean hasError(ClientHttpResponse response) throws IOException {
		
		return RestUtil.isError(response.getStatusCode());
	}

}
