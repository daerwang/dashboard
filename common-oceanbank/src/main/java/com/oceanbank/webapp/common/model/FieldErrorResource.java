/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.common.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


/**
 * The Class FieldErrorResource.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FieldErrorResource {
    
    /** The resource. */
    private String resource;
    
    /** The field. */
    private String field;
    
    /** The code. */
    private String code;
    
    /** The message. */
    private String message;

    /**
     * Gets the resource.
     *
     * @return the resource
     */
    public String getResource() { return resource; }

    /**
     * Sets the resource.
     *
     * @param resource the new resource
     */
    public void setResource(String resource) { this.resource = resource; }

    /**
     * Gets the field.
     *
     * @return the field
     */
    public String getField() { return field; }

    /**
     * Sets the field.
     *
     * @param field the new field
     */
    public void setField(String field) { this.field = field; }

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