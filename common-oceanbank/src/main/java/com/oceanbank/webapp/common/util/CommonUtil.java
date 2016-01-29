/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.common.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.oceanbank.webapp.common.model.AuthenticationUserDetails;

/**
 * The Class CommonUtil.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
public class CommonUtil {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtil.class);
	
	
	/**
	 * Gets the authenticated user details.
	 *
	 * @return the authenticated user details
	 */
	public static AuthenticationUserDetails getAuthenticatedUserDetails(){
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final AuthenticationUserDetails details = (AuthenticationUserDetails) authentication.getPrincipal();
		return details;
	}
	
	/**
	 * Checks if is null or empty.
	 *
	 * @param value the value
	 * @return the boolean
	 */
	public static Boolean isNullOrEmpty(String value){
		if(value == null || value.trim().length() < 1){
			return true;
		}
		
		return false;
	}
	
	/**
	 * Convert from decimal.
	 *
	 * @param decimal the decimal
	 * @return the string
	 */
	public static String convertFromDecimal(String decimal){
		String result = null;
		
		if(decimal.trim().equalsIgnoreCase("0.00")){
			result = "0";
		}else{
			result = decimal;
		}
		
		
		return result;
	}
	
	/**
	 * Creates the http entity.
	 *
	 * @param <T> the generic type
	 * @param accessToken the access token
	 * @return the http entity
	 */
	public static <T> HttpEntity<T> createHttpEntity(String accessToken) {

		final HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		final HttpEntity<T> entity = new HttpEntity<T>(headers);

		return entity;
	}
	
	/**
	 * Creates the http entity json.
	 *
	 * @param <T> the generic type
	 * @param accessToken the access token
	 * @return the http entity
	 */
	public static <T> HttpEntity<T> createHttpEntityJson(String accessToken) {

		final HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		final List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		headers.setAccept(acceptableMediaTypes);
		final HttpEntity<T> entity = new HttpEntity<T>(headers);

		return entity;
	}

	/**
	 * Creates the http entity with parameters.
	 *
	 * @param <A> the generic type
	 * @param accessToken the access token
	 * @param userResponse the user response
	 * @return the http entity
	 */
	public static <A> HttpEntity<A> createHttpEntityWithParameters(String accessToken, A userResponse) {

		final HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		
		final List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		headers.setAccept(acceptableMediaTypes);
		
		final HttpEntity<A> entity = new HttpEntity<A>(userResponse, headers);

		return entity;
	}
	
	/**
	 * Determine value.
	 *
	 * @param requestParams the request params
	 * @param keyChosen the key chosen
	 * @return the string
	 */
	public static String determineValue(Map<String, String> requestParams, String keyChosen) {
		
		String pickedValue = null;

		for (Map.Entry<String, String> entry : requestParams.entrySet()) {
			final String key = entry.getKey(); 
			final String value = entry.getValue();
			
			if(keyChosen.trim().toLowerCase().equalsIgnoreCase(key.trim().toLowerCase())){
				pickedValue = value;
				break;
			}
			
			//System.out.println("***** " + key + " - " + valueString);
		}
		
		return pickedValue;
	}
	
	/**
	 * Prints the parameter values.
	 *
	 * @param requestParams the request params
	 */
	public static void printParameterValues(Map<String, String> requestParams){
		
		for (Map.Entry<String, String> entry : requestParams.entrySet()) {
			final String key = entry.getKey(); 
			final String value = entry.getValue();
			
			System.out.println("***** " + key + " - " + value);
		}
	}
	
	
	/**
	 * Prints the heap sizes.
	 */
	public static void printHeapSizes(){
		// Get current size of heap in bytes
		final long heapSize = Runtime.getRuntime().totalMemory(); 

		// Get maximum size of heap in bytes. The heap cannot grow beyond this size.// Any attempt will result in an OutOfMemoryException.
		final long heapMaxSize = Runtime.getRuntime().maxMemory();

		 // Get amount of free memory within the heap in bytes. This size will increase // after garbage collection and decrease as new objects are created.
		final long heapFreeSize = Runtime.getRuntime().freeMemory(); 
		
		LOGGER.info("heapSize " + heapSize/1000);
		LOGGER.info("heapMaxSize " + heapMaxSize/1000);
		LOGGER.info("heapFreeSize " + heapFreeSize/1000);
	}
	
	public static void openFile(String fullFilePath){
		Process p;
		try {
			p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + fullFilePath);
			p.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
