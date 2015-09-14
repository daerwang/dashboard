/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.common.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.oceanbank.webapp.common.model.FieldErrorResource;


/**
 * The Class ErrorResource.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResource {
    
    /** The code. */
    private String code;
    
    /** The message. */
    private String message;
    

    /**
     * Instantiates a new error resource.
     */
    public ErrorResource() { }

    /**
     * Instantiates a new error resource.
     *
     * @param code the code
     * @param message the message
     */
    public ErrorResource(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Gets the code.
     *
     * @return the code
     */
    public String getCode() { return code; }

    /**
     * Sets the code.
     *
     * @param code the new code
     */
    public void setCode(String code) { this.code = code; }

    /**
     * Gets the message.
     *
     * @return the message
     */
    public String getMessage() { return message; }

    /**
     * Sets the message.
     *
     * @param message the new message
     */
    public void setMessage(String message) { this.message = message; }

}