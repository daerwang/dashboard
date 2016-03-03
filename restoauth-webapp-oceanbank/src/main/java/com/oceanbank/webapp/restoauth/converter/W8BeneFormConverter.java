package com.oceanbank.webapp.restoauth.converter;

import com.oceanbank.webapp.common.model.W8BeneFormResponse;
import com.oceanbank.webapp.restoauth.model.W8BeneForm;


public class W8BeneFormConverter implements DashboardConverter<W8BeneForm, W8BeneFormResponse>{


	@Override
	public W8BeneForm convertFromBean(W8BeneFormResponse response) {
		W8BeneForm c = new W8BeneForm();
		
		c.setId(response.getId());
		c.setCif(response.getCif());
		c.setName(response.getName());
		c.setPhysicalCountryInc(response.getPhysicalCountryInc());
		c.setAltAddress(response.getAltAddress());
		c.setAltCity(response.getAltCity());
		c.setAltCountry(response.getAltCountry());
		c.setAccount(response.getAccount());
		c.setLabelName(response.getLabelName());
		c.setOfficer(response.getOfficer());
		c.setBranch(response.getBranch());
		c.setAltAddressLabel(response.getAltAddressLabel());
		c.setAltCityLabel(response.getAltCityLabel());
		c.setAltCountryLabel(response.getAltCountryLabel());
		
		return c;
	}

	@Override
	public W8BeneFormResponse convertFromEntity(W8BeneForm entity) {

		final W8BeneFormResponse res = new W8BeneFormResponse();
		
		res.setId(entity.getId());
		res.setCif(entity.getCif());
		res.setName(entity.getName());
		res.setPhysicalCountryInc(entity.getPhysicalCountryInc());
		res.setAltAddress(entity.getAltAddress());
		res.setAltCity(entity.getAltCity());
		res.setAltCountry(entity.getAltCountry());
		res.setAccount(entity.getAccount());
		res.setLabelName(entity.getLabelName());
		res.setOfficer(entity.getOfficer());
		res.setBranch(entity.getBranch());
		res.setAltAddressLabel(entity.getAltAddressLabel());
		res.setAltCityLabel(entity.getAltCityLabel());
		res.setAltCountryLabel(entity.getAltCountryLabel());
		
		return res;
	}

}
