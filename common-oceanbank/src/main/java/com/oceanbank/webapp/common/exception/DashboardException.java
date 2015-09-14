/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.common.exception;

/**
 * The Class DashboardRestException.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
public class DashboardException extends Exception{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new dashboard rest exception.
	 */
	public DashboardException() { super(); }
	
	/**
	 * Instantiates a new dashboard rest exception.
	 *
	 * @param message the message
	 */
	public DashboardException(String message) { 
		super(message); 
	}
	
	/**
	 * Instantiates a new dashboard rest exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public DashboardException(String message, Throwable cause) { 
		super(message, cause); 
	}
	
	/**
	 * Instantiates a new dashboard rest exception.
	 *
	 * @param cause the cause
	 */
	public DashboardException(Throwable cause) { super(cause); }
}
