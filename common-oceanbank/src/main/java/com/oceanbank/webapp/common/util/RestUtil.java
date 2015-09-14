/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.common.util;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

/**
 * The Class RestUtil.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
public class RestUtil {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(RestUtil.class);
	
	/**
	 * Parses the string to number zero first.
	 *
	 * @param number the number
	 * @return the string
	 * @throws Exception 
	 */
	public static String parseStringToNumberZeroFirst(String number) throws Exception{
		
		final Integer num = parseStringToInteger(number);
		
		final String result = String.format("%02d", num);
		
		return result;
	}
	
	/**
	 * Parses the string to integer.
	 *
	 * @param number the number
	 * @return the integer
	 * @throws Exception 
	 */
	public static Integer parseStringToInteger(String number) throws Exception{
		Integer result = null;
		try {
			result = Integer.parseInt(number);
		} catch (Exception e) {
			LOGGER.info("Error in parseStringToInteger");
			throw e;
		}

		return result;
	}
	
	/**
	 * Parses the mail code from datatable.
	 *
	 * @param code the code
	 * @return the list
	 * @throws Exception 
	 */
	public static List<String> parseMailCodeFromDatatable(String code) throws Exception{
		List<String> codes = null;
		try {
			final String[] arr = code.split(",");
			codes = Arrays.asList(arr);
		} catch (Exception e) {
			LOGGER.info("Error in parseMailCodeFromDatatable");
			throw e;
		}

		return codes;
	}
	
	/**
	 * Parses the irs wh tax.
	 *
	 * @param number the number
	 * @return the string
	 * @throws Exception 
	 */
	public static String parseIrsWhTax(String number) throws Exception{
		try {
			if(number.equalsIgnoreCase("0.0") || number.equalsIgnoreCase("0.00")){
				number = "0";
			}
		} catch (Exception e) {
			LOGGER.info("Error in parseIrsWhTax");
			throw e;
		}
		
		
		return number;
	}
	
	/**
	 * Parses the irs date of birth.
	 *
	 * @param dob the dob
	 * @return the string
	 * @throws Exception 
	 */
	public static String parseIrsDateOfBirth(String dob) throws Exception{
		StringBuilder b = new StringBuilder();
		
		try {
			
			final String mon = dob.length() > 5 ? dob.substring(0, 2) : dob.substring(0, 1);
			b.append(mon);
			b.append("/");
			b.append(dob.substring(dob.length() - 4, dob.length() - 2));
			b.append("/");
			b.append(dob.substring(dob.length() - 2, dob.length()));
		}catch(StringIndexOutOfBoundsException s){
			// the dob is not declared properly
			b = new StringBuilder("");
		}catch (Exception e) {
			LOGGER.info("Error in parseIrsDateOfBirth");
			throw e;
		}
		
		
		return b.toString();
	}
	
	/**
	 * Parses the irs zip code.
	 *
	 * @param phone the phone
	 * @return the string
	 * @throws Exception 
	 */
	public static String parseIrsZipCode(String phone) throws Exception{
		final StringBuilder b = new StringBuilder();
		try {
			
			b.append(phone.substring(0, 5));
			b.append("-");
			b.append(phone.substring(5, 9));
		} catch (Exception e) {
			LOGGER.info("Error in parseIrsZipCode");
			throw e;
		}
		
		
		return b.toString();
	}

	/**
	 * Parses the irs form phone number.
	 *
	 * @param phone the phone
	 * @return the string
	 * @throws Exception 
	 */
	public static String parseIrsFormPhoneNumber(String phone) throws Exception{
		final StringBuilder b = new StringBuilder();
		try {
			
			b.append("(");
			b.append(phone.substring(0, 3));
			b.append(") ");
			b.append(phone.substring(3, 6));
			b.append("-");
			b.append(phone.substring(6, 10));
		} catch (Exception e) {
			LOGGER.info("Error in parseIrsFormPhoneNumber");
			throw e;
		}
		
		
		return b.toString();
	}
	
	/**
	 * Convert from decimal2.
	 *
	 * @param decimal the decimal
	 * @return the string
	 */
	public static String convertFromDecimal2(String decimal){
		final StringBuilder b =  new StringBuilder();
		b.append("0").append(decimal);
		
		return b.toString();
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
}
