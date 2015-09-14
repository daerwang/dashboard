/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.common.model;

import java.util.List;



/**
 * The Class ObDashboardRoles.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
public class ObDashboardRoles {

	/** The category. */
	private String category;
	
	/** The description. */
	private String description;
	
	/** The role names. */
	private List<String> roleNames;
	
	
	/**
	 * Gets the category.
	 *
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}
	
	/**
	 * Sets the category.
	 *
	 * @param category the new category
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	
	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Gets the role names.
	 *
	 * @return the role names
	 */
	public List<String> getRoleNames() {
		return roleNames;
	}
	
	/**
	 * Sets the role names.
	 *
	 * @param roleNames the new role names
	 */
	public void setRoleNames(List<String> roleNames) {
		this.roleNames = roleNames;
	}
}
