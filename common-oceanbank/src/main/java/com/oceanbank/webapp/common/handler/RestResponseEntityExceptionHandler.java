/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.common.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.oceanbank.webapp.common.exception.DashboardException;
import com.oceanbank.webapp.common.exception.RestInvalidRequestException;
import com.oceanbank.webapp.common.model.DashboardConstant;
import com.oceanbank.webapp.common.model.ErrorResource;
import com.oceanbank.webapp.common.model.FieldErrorResource;


/**
 * The Class RestResponseEntityExceptionHandler.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{
	
	/** The logger. */
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	/**
	 * Handle invalid request.
	 *
	 * @param exception the exception
	 * @param request the request
	 * @return the response entity
	 */
	@ExceptionHandler({RestInvalidRequestException.class })
    protected ResponseEntity<Object> handleInvalidRequest(RuntimeException exception, WebRequest request) {
		final RestInvalidRequestException ire = (RestInvalidRequestException) exception;
		
		LOGGER.error(exception.getMessage());
		
		final List<FieldErrorResource> fieldErrorResources = new ArrayList<FieldErrorResource>();

		final List<FieldError> fieldErrors = ire.getErrors().getFieldErrors();
		FieldErrorResource fieldErrorResource = new FieldErrorResource();
        for (FieldError fieldError : fieldErrors) {
            fieldErrorResource.setResource(fieldError.getObjectName());
            fieldErrorResource.setField(fieldError.getField());
            fieldErrorResource.setCode(fieldError.getCode());
            fieldErrorResource.setMessage(fieldError.getDefaultMessage());
            fieldErrorResources.add(fieldErrorResource);
        }

        final ErrorResource error = new ErrorResource("InvalidRequest", ire.getMessage());

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return handleExceptionInternal(exception, error, headers, HttpStatus.UNPROCESSABLE_ENTITY, request);
    }
	
	/**
	 * Handle invalid sql.
	 *
	 * @param exception the exception
	 * @param request the request
	 * @return the response entity
	 */
	@ExceptionHandler({RestSqlException.class })
    protected ResponseEntity<Object> handleInvalidSql(RuntimeException exception, WebRequest request) {
		final RestSqlException ire = (RestSqlException) exception;
		
		LOGGER.error(exception.getMessage());
		
		final List<FieldErrorResource> fieldErrorResources = new ArrayList<FieldErrorResource>();

		final ErrorResource error = new ErrorResource("InvalidSQLRequest", ire.getMessage());

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return handleExceptionInternal(exception, error, headers, HttpStatus.UNPROCESSABLE_ENTITY, request);
    }
	
	/**
	 * Handle invalid authority.
	 *
	 * @param exception the exception
	 * @param request the request
	 * @return the response entity
	 */
	@ExceptionHandler({HttpClientErrorException.class })
    protected ResponseEntity<Object> handleInvalidAuthority(RuntimeException exception, WebRequest request) {
		final HttpClientErrorException ire = (HttpClientErrorException) exception;
		
		LOGGER.error(exception.getMessage());
		
		final List<FieldErrorResource> fieldErrorResources = new ArrayList<FieldErrorResource>();

		final ErrorResource error = new ErrorResource("InvalidAuthorityRequest", ire.getMessage());

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return handleExceptionInternal(exception, error, headers, HttpStatus.UNPROCESSABLE_ENTITY, request);
    }
	
	/**
	 * Handle invalid sql.
	 *
	 * @param exception the exception
	 * @param request the request
	 * @return the response entity
	 */
	@ExceptionHandler({DashboardException.class })
    protected ResponseEntity<Object> handleDashboardException(Exception exception, WebRequest request) {
		final String errorMsg = exception.getMessage();
		String code = "No Error Code Specified";
		
		if(errorMsg.contains(DashboardConstant.DB_CONSTRAINT_EXCEPTION)){
			code = "Duplicate CIF is not allowed.";
		}
		
		final ErrorResource body = new ErrorResource(code, errorMsg);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

        return new ResponseEntity<Object>(body, headers, status);
    }
}
