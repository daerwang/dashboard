/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.common.exception;

/**
 * The Class UsernameExistException.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@SuppressWarnings("serial")
public class UsernameExistException extends RuntimeException{
	
	/**
	 * Instantiates a new username exist exception.
	 */
	public UsernameExistException() { super(); }
	
	/**
	 * Instantiates a new username exist exception.
	 *
	 * @param message the message
	 */
	public UsernameExistException(String message) { 
		super(message); 
	}
	
	/**
	 * Instantiates a new username exist exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public UsernameExistException(String message, Throwable cause) { 
		super(message, cause); 
	}
	
	/**
	 * Instantiates a new username exist exception.
	 *
	 * @param cause the cause
	 */
	public UsernameExistException(Throwable cause) { super(cause); }
}
