/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.restoauth.converter;

import com.oceanbank.webapp.common.model.IrsFormCustomerResponse;
import com.oceanbank.webapp.common.model.W8BeneFormResponse;
import com.oceanbank.webapp.restoauth.model.IrsFormCustomer;
import com.oceanbank.webapp.restoauth.model.W8BeneForm;

/**
 * The Class IrsFormConverter.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
public class W8BeneFormConverter implements DashboardConverter<W8BeneForm, W8BeneFormResponse>{


	@Override
	public W8BeneForm convertFromBean(W8BeneFormResponse response) {
		W8BeneForm c = new W8BeneForm();
		
		c.setId(response.getId());
		c.setCif(response.getCif());
		c.setName(response.getName());
		c.setPhysicalCountryInc(response.getPhysicalCountryInc());

		return c;
	}

	@Override
	public W8BeneFormResponse convertFromEntity(W8BeneForm entity) {

		final W8BeneFormResponse res = new W8BeneFormResponse();
		
		res.setId(entity.getId());
		res.setCif(entity.getCif());
		res.setName(entity.getName());
		res.setPhysicalCountryInc(entity.getPhysicalCountryInc());

		
		return res;
	}

}
