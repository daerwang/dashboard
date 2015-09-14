/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.restoauth.converter;

/**
 * The Interface DashboardConverter.
 *
 * @author Marinell Medina
 * @since 03.10.2015
 * @param <E> the element type
 * @param <O> the generic type
 */
public interface DashboardConverter<E, O> {
	
	/**
	 * Convert from bean.
	 *
	 * @param response the response
	 * @return the e
	 */
	E convertFromBean(O response);
	
	/**
	 * Convert from entity.
	 *
	 * @param entity the entity
	 * @return the o
	 */
	O convertFromEntity(E entity);
}
