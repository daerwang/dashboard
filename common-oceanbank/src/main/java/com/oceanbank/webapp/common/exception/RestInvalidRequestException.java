/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.common.exception;

import org.springframework.validation.Errors;

/**
 * The Class RestInvalidRequestException.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@SuppressWarnings("serial")
public class RestInvalidRequestException extends RuntimeException{

	/** The errors. */
	private Errors errors;

    /**
     * Instantiates a new rest invalid request exception.
     *
     * @param message the message
     * @param errors the errors
     */
    public RestInvalidRequestException(String message, Errors errors) {
        super(message);
        this.errors = errors;
    }

    /**
     * Gets the errors.
     *
     * @return the errors
     */
    public Errors getErrors() { return errors; }
}
