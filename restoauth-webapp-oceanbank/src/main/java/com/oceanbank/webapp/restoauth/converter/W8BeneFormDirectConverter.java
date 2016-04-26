package com.oceanbank.webapp.restoauth.converter;

import com.oceanbank.webapp.common.model.W8BeneFormResponse;
import com.oceanbank.webapp.restoauth.model.W8BeneFormDirect;
import com.oceanbank.webapp.restoauth.model.W8BeneFormDirectPK;


public class W8BeneFormDirectConverter implements DashboardConverter<W8BeneFormDirect, W8BeneFormResponse>{


	@Override
	public W8BeneFormDirect convertFromBean(W8BeneFormResponse response) {
		W8BeneFormDirect c = new W8BeneFormDirect();
		
		W8BeneFormDirectPK pk = new W8BeneFormDirectPK(response.getCif(), response.getName());
		c.setPkId(pk);
		
		c.setPhysicalAddress(response.getPhysicalAddress());
		c.setPhysicalCity(response.getPhysicalCity());
		c.setPhysicalCountryInc(response.getPhysicalCountryInc());
		c.setPhysicalCountry(response.getPhysicalCountry());
		
		c.setAltAddress(response.getAltAddress());
		c.setAltCity(response.getAltCity());
		c.setAltCountryInc(response.getAltCountryInc());
		c.setAltCountry(response.getAltCountry());
		
		c.setOfficer(response.getOfficer());
		c.setBranch(response.getBranch());

		return c;
	}

	@Override
	public W8BeneFormResponse convertFromEntity(W8BeneFormDirect entity) {

		final W8BeneFormResponse res = new W8BeneFormResponse();
		
		res.setCif(entity.getPkId().getCif());
		res.setName(entity.getPkId().getName());
		res.setPhysicalAddress(entity.getPhysicalAddress());
		res.setPhysicalCity(entity.getPhysicalCity());
		res.setPhysicalCountryInc(entity.getPhysicalCountryInc());
		res.setPhysicalCountry(entity.getPhysicalCountry());
		
		res.setAltAddress(entity.getAltAddress());
		res.setAltCity(entity.getAltCity());
		res.setAltCountryInc(entity.getAltCountryInc());
		res.setAltCountry(entity.getAltCountry());

		res.setOfficer(entity.getOfficer());
		res.setBranch(entity.getBranch());
		
		return res;
	}

}
