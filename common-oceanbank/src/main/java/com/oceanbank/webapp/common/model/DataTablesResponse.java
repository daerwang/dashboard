/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.common.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * The Class DataTablesResponse.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataTablesResponse <E>{
	
	/**
	 * Instantiates a new data tables response.
	 */
	public DataTablesResponse(){}
	
	/** The draw. */
	private Integer draw;
	
	/** The records total. */
	private Integer recordsTotal;
	
	/** The records filtered. */
	private Integer recordsFiltered;
	
	/** The data. */
	private List<E> data;
	
	/**
	 * Gets the draw.
	 *
	 * @return the draw
	 */
	public Integer getDraw() {
		return draw;
	}
	
	/**
	 * Sets the draw.
	 *
	 * @param draw the new draw
	 */
	public void setDraw(Integer draw) {
		this.draw = draw;
	}
	
	/**
	 * Gets the records total.
	 *
	 * @return the records total
	 */
	public Integer getRecordsTotal() {
		return recordsTotal;
	}
	
	/**
	 * Sets the records total.
	 *
	 * @param recordsTotal the new records total
	 */
	public void setRecordsTotal(Integer recordsTotal) {
		this.recordsTotal = recordsTotal;
	}
	
	/**
	 * Gets the records filtered.
	 *
	 * @return the records filtered
	 */
	public Integer getRecordsFiltered() {
		return recordsFiltered;
	}
	
	/**
	 * Sets the records filtered.
	 *
	 * @param recordsFiltered the new records filtered
	 */
	public void setRecordsFiltered(Integer recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}
	
	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public List<E> getData() {
		return data;
	}
	
	/**
	 * Sets the data.
	 *
	 * @param data the new data
	 */
	public void setData(List<E> data) {
		this.data = data;
	}
}
