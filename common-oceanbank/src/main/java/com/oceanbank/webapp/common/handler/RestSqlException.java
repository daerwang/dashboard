/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.common.handler;


/**
 * The Class RestSqlException.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@SuppressWarnings("serial")
public class RestSqlException extends RuntimeException{

	
	/**
	 * Instantiates a new rest sql exception.
	 *
	 * @param message the message
	 */
	public RestSqlException(String message) {
        super(message);
    }
}
