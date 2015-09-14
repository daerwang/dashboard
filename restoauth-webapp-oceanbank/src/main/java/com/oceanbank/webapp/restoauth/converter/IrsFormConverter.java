/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.restoauth.converter;

import com.oceanbank.webapp.common.model.IrsFormCustomerResponse;
import com.oceanbank.webapp.restoauth.model.IrsFormCustomer;

/**
 * The Class IrsFormConverter.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
public class IrsFormConverter implements DashboardConverter<IrsFormCustomer, IrsFormCustomerResponse>{

	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.restoauth.converter.DashboardConverter#convertFromBean(java.lang.Object)
	 */
	@Override
	public IrsFormCustomer convertFromBean(IrsFormCustomerResponse response) {
		final IrsFormCustomer c = new IrsFormCustomer();
		
		c.setMailCode(response.getMailCode());
		
		c.getIrsFormId().setFld_2(response.getFld_2());
		c.getIrsFormId().setFld_12a(response.getFld_12a());
		c.getIrsFormId().setFld_19(response.getFld_19());
		
		c.getIrsFormId().setFld_14acd_1(response.getFld_14acd_1());
		c.setFld_14acd_2(response.getFld_14acd_2());
		c.setFld_14acd_3(response.getFld_14acd_3());
		c.setFld_14acd_4(response.getFld_14acd_4());
		c.setFld_14acd_5(response.getFld_14acd_5());
		
		c.setAccountType(response.getAccountType());
		
		
		
		//c.setFld_12b(response.getFld_12b()); // TNESTS3
		//c.setFld_12c(response.getFld_12c()); // TNESTS4
		c.setFld_20(response.getFld_20()); // TNESTS4
		c.setFld_9(response.getFld_9()); // TNTXASM
		c.setFld_14b(response.getFld_14b()); // TNTXASM
		
		c.setFld_1(response.getFld_1()); // TNFINC
		c.getIrsFormId().setFld_2(response.getFld_2()); // TNFGRS
		c.setFld_3a(response.getFld_3a()); // TNEXMP
		c.setFld_3b(response.getFld_3b()); // TNTAXR
		c.setFld_4a(response.getFld_4a()); // TNEXMP4
		c.setFld_4b(response.getFld_4b()); // TNTAXR4
		c.setFld_5(response.getFld_5()); // TNFSTD
		c.setFld_6(response.getFld_6()); // TNFNET
		c.setFld_8(response.getFld_8()); // TNFWBO
		c.setFld_7(response.getFld_7()); // TNFFWH
		//c.setFld_12a("592237280"); // TNFEIN
		c.setFld_11(response.getFld_11()); // TNFARE

		c.setFld_16d(response.getFld_16d()); // TNFQUS
		c.setFld_13h(response.getFld_13h()); // TNFRCP
		c.setFld_13i(response.getFld_13i()); // TNRSTS4
		c.setFld_17(response.getFld_17()); // TNRGIIN
		c.setFld_18(response.getFld_18()); // TNRGIIN
		//c.setFld_19("108935820"); // TNACCT
		c.setFld_21(response.getFld_21()); // TNFPRN
		c.setFld_22(response.getFld_22()); // TNFPRT
		c.setFld_24(response.getFld_24()); // TNFSWH
		
		c.setFld_to_1(response.getFld_to_1());
		c.setFld_to_2(response.getFld_to_2());
		c.setFld_to_3(response.getFld_to_3());
		c.setFld_to_4(response.getFld_to_4());
		c.setFld_to_5(response.getFld_to_5());
		c.setFld_to_6(response.getFld_to_6());
		c.setFld_to_7(response.getFld_to_7());
		
		return c;
	}

	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.restoauth.converter.DashboardConverter#convertFromEntity(java.lang.Object)
	 */
	@Override
	public IrsFormCustomerResponse convertFromEntity(IrsFormCustomer entity) {

		final IrsFormCustomerResponse res = new IrsFormCustomerResponse();
		
		res.setMailCode(entity.getMailCode());
		
		res.setFld_2(entity.getIrsFormId().getFld_2());
		res.setFld_12a(entity.getIrsFormId().getFld_12a());
		res.setFld_19(entity.getIrsFormId().getFld_19());
		
		res.setFld_14acd_1(entity.getIrsFormId().getFld_14acd_1());
		res.setFld_14acd_2(entity.getFld_14acd_2());
		res.setFld_14acd_3(entity.getFld_14acd_3());
		res.setFld_14acd_4(entity.getFld_14acd_4());
		res.setFld_14acd_5(entity.getFld_14acd_5());
		
		res.setAccountType(entity.getAccountType());
		
		
		
		//res.setFld_12b(entity.getFld_12b()); // TNESTS3
		//res.setFld_12c(entity.getFld_12c()); // TNESTS4
		res.setFld_20(entity.getFld_20()); // TNESTS4
		res.setFld_9(entity.getFld_9()); // TNTXASM
		res.setFld_14b(entity.getFld_14b()); // TNTXASM
		
		res.setFld_1(entity.getFld_1()); // TNFINC
		res.setFld_2(entity.getIrsFormId().getFld_2()); // TNFGRS
		res.setFld_3a(entity.getFld_3a()); // TNEXMP
		res.setFld_3b(entity.getFld_3b()); // TNTAXR
		res.setFld_4a(entity.getFld_4a()); // TNEXMP4
		res.setFld_4b(entity.getFld_4b()); // TNTAXR4
		res.setFld_5(entity.getFld_5()); // TNFSTD
		res.setFld_6(entity.getFld_6()); // TNFNET
		res.setFld_8(entity.getFld_8()); // TNFWBO
		res.setFld_7(entity.getFld_7()); // TNFFWH
		//res.setFld_12a("592237280"); // TNFEIN
		res.setFld_11(entity.getFld_11()); // TNFARE

		res.setFld_16d(entity.getFld_16d()); // TNFQUS
		res.setFld_13h(entity.getFld_13h()); // TNFRCP
		res.setFld_13i(entity.getFld_13i()); // TNRSTS4
		res.setFld_17(entity.getFld_17()); // TNRGIIN
		res.setFld_18(entity.getFld_18()); // TNRGIIN
		//res.setFld_19("108935820"); // TNACCT
		res.setFld_21(entity.getFld_21()); // TNFPRN
		res.setFld_22(entity.getFld_22()); // TNFPRT
		res.setFld_24(entity.getFld_24()); // TNFSWH
		
		res.setFld_to_1(entity.getFld_to_1());
		res.setFld_to_2(entity.getFld_to_2());
		res.setFld_to_3(entity.getFld_to_3());
		res.setFld_to_4(entity.getFld_to_4());
		res.setFld_to_5(entity.getFld_to_5());
		res.setFld_to_6(entity.getFld_to_6());
		res.setFld_to_7(entity.getFld_to_7());
		
		return res;
	}

}
