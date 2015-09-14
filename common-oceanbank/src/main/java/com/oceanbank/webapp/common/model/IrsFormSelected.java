/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.common.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * The Class IrsFormSelected.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IrsFormSelected {
	
	/**
	 * Instantiates a new irs form selected.
	 */
	public IrsFormSelected(){}
	
	/** The selected. */
	private String[] selected;
	
	/** The status. */
	private String status;

	/**
	 * Gets the selected.
	 *
	 * @return the selected
	 */
	public String[] getSelected() {
		return selected;
	}

	/**
	 * Sets the selected.
	 *
	 * @param selected the new selected
	 */
	public void setSelected(String[] selected) {
		this.selected = selected;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}
