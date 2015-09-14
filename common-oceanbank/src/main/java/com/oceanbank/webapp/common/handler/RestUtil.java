/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.common.handler;

import org.springframework.http.HttpStatus;


/**
 * The Class RestUtil.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
public class RestUtil {
	
	/**
	 * Checks if is error.
	 *
	 * @param status the status
	 * @return true, if is error
	 */
	public static boolean isError(HttpStatus status) {
        final HttpStatus.Series series = status.series();
        return (HttpStatus.Series.CLIENT_ERROR.equals(series)
                || HttpStatus.Series.SERVER_ERROR.equals(series));
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
}
