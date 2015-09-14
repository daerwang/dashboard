/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.restoauth.model;

import java.util.ArrayList;
import java.util.List;

import com.oceanbank.webapp.common.model.IrsFormCustomerResponse;
import com.oceanbank.webapp.common.util.RestUtil;

/**
 * The Class that contains the positions of text for PDF printing.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
public class IrsFormPosition {
	
	/** The id. */
	private Integer id;
	
	/** The name. */
	private String name;
	
	/** The description. */
	private String description;
	
	/** The coordinates. */
	private List<IrsFormCoordinate> coordinates = new ArrayList<IrsFormCoordinate>();
	
	/** The response. */
	private IrsFormCustomerResponse response;
	
	/** The response list. */
	private List<IrsFormCustomerResponse> responseList = new ArrayList<IrsFormCustomerResponse>();
	
	/**
	 * Instantiates a new position.
	 */
	public IrsFormPosition(){}
	
	
	/**
	 * Creates the PDF based on passed Customer and Bank Detail which already has the fixed positions.
	 *
	 * @param the database entity
	 * @param the bank detail
	 * @throws Exception 
	 */
	public void drawPdfFromEntity(IrsFormCustomer entity, BankDetail bank) throws Exception{
		
		final String value = "";
		final String value2 = "";
		
		final List<IrsFormCoordinate> list = new ArrayList<IrsFormCoordinate>();
		
		// static values
		final IrsFormCoordinate header = new IrsFormCoordinate("header", 195, 989, 7, "The information on the form is being furnished to the US IRS");
		
		final String whAgentsNameTxt = bank.getFld_14e().trim() + "       " + RestUtil.parseIrsFormPhoneNumber(bank.getFld_13a_2());
		final IrsFormCoordinate loc_13a = new IrsFormCoordinate("whAgentsName", 155, 922, 7, whAgentsNameTxt); // 13a
		final IrsFormCoordinate whAddress_1 = new IrsFormCoordinate("whAddress_1", 46, 872, 7, bank.getFld_14ef_1().trim()); // 13ef_1
		final IrsFormCoordinate whAddress_2 = new IrsFormCoordinate("whAddress_2", 46, 866, 7,  bank.getFld_14ef_2().trim() +", "+ (bank.getFld_13c().equalsIgnoreCase("FL") ? "Florida" : "")); // 13ef_2
		final IrsFormCoordinate whAddress_3 = new IrsFormCoordinate("whAddress_3", 46, 860, 7, RestUtil.parseIrsZipCode(bank.getFld_14ef_3())); // 13ef_3
		
		// the dummies
		String checked3 = "";
		String checked4 = "";
		final Integer num = RestUtil.parseStringToInteger(entity.getFld_3());
		if(num == 3){
			checked3 = "x";
			checked4 = "";
		}else if(num == 4){
			checked3 = "";
			checked4 = "x";
		}else{
			// default it to checked3 as x since most of it are in there
			checked3 = "x";
			checked4 = "";
		}
		final IrsFormCoordinate loc_3 = new IrsFormCoordinate(160, 967, checked3);
		final IrsFormCoordinate loc_4 = new IrsFormCoordinate(160, 957, checked4);
		final IrsFormCoordinate loc_7a = new IrsFormCoordinate(547, 948, value2);
		

		// dynamic values
		final IrsFormCoordinate loc_1 = new IrsFormCoordinate(46, 959, entity.getFld_1());
		final IrsFormCoordinate loc_2 = new IrsFormCoordinate(78, 959, entity.getIrsFormId().getFld_2());
        
		final IrsFormCoordinate loc_3a = new IrsFormCoordinate(208, 967, entity.getFld_3a());
		final IrsFormCoordinate loc_4a = new IrsFormCoordinate(208, 957, entity.getFld_4a());
		final IrsFormCoordinate loc_3b = new IrsFormCoordinate(255, 967, RestUtil.convertFromDecimal2(entity.getFld_3b()));
		final IrsFormCoordinate loc_4b = new IrsFormCoordinate(255, 957, RestUtil.convertFromDecimal2(entity.getFld_4b()));
		final IrsFormCoordinate loc_5 = new IrsFormCoordinate(350, 967, RestUtil.convertFromDecimal(entity.getFld_5()));
		final IrsFormCoordinate loc_6 = new IrsFormCoordinate(350, 957, RestUtil.convertFromDecimal(entity.getFld_6()));
		final IrsFormCoordinate loc_8 = new IrsFormCoordinate(100, 948, RestUtil.convertFromDecimal(entity.getFld_8()));
        // TNFFWH + TNFWBO
		final Float totalWhTax = Float.parseFloat(entity.getFld_7()) + Float.parseFloat(entity.getFld_8());
		final IrsFormCoordinate loc_10 = new IrsFormCoordinate(100, 939, RestUtil.parseIrsWhTax(totalWhTax + ""));
		final IrsFormCoordinate loc_12a = new IrsFormCoordinate(100, 931, entity.getIrsFormId().getFld_12a());
        
        // 12c is equal to 12b as per William
		final String code = RestUtil.parseStringToNumberZeroFirst(bank.getFld_12b());
		final IrsFormCoordinate loc_12b = new IrsFormCoordinate(274, 931, code);
		final IrsFormCoordinate loc_12c = new IrsFormCoordinate(81, 922, code);
        
        
		final IrsFormCoordinate loc_7 = new IrsFormCoordinate(350, 948, RestUtil.convertFromDecimal(entity.getFld_7()));
        
		final IrsFormCoordinate loc_9 = new IrsFormCoordinate(370, 939, RestUtil.convertFromDecimal(entity.getFld_9()));
		final IrsFormCoordinate loc_11 = new IrsFormCoordinate(360, 931, RestUtil.convertFromDecimal(entity.getFld_11()));
		final IrsFormCoordinate loc_14f = new IrsFormCoordinate(380, 922, bank.getFld_14f());
		final IrsFormCoordinate loc_13b = new IrsFormCoordinate(46, 905, value);
		final IrsFormCoordinate loc_13c = new IrsFormCoordinate(46, 886, bank.getFld_13c());
		final IrsFormCoordinate loc_13d = new IrsFormCoordinate(125, 886, bank.getFld_13d());
        //PdfCoordinates loc_13ef_1 = new PdfCoordinates(46, 872, entity.getFld_13ef());
        //PdfCoordinates loc_13ef_2 = new PdfCoordinates(46, 866, "*");
        //PdfCoordinates loc_13ef_3 = new PdfCoordinates(46, 860, "*");
		final IrsFormCoordinate loc_13g = new IrsFormCoordinate(46, 841, value);
		final IrsFormCoordinate loc_13h = new IrsFormCoordinate(83, 831, entity.getFld_13h());
		final IrsFormCoordinate loc_13i = new IrsFormCoordinate(147, 831, entity.getFld_13i());
		final IrsFormCoordinate loc_14b = new IrsFormCoordinate(225, 831, entity.getFld_14b());
        
        
		final String space = RestUtil.isNullOrEmpty(entity.getFld_14acd_4()) ? "" : " ";
		IrsFormCoordinate loc_14acd_3 = new IrsFormCoordinate();
		IrsFormCoordinate loc_14acd_4 = null;
		final IrsFormCoordinate loc_14acd_1 = new IrsFormCoordinate(46, 814, entity.getIrsFormId().getFld_14acd_1());
		final IrsFormCoordinate loc_14acd_2 = new IrsFormCoordinate(46, 806, entity.getFld_14acd_2());
        if(RestUtil.isNullOrEmpty(entity.getFld_14acd_3())){
        	loc_14acd_4 = new IrsFormCoordinate(46, 798, entity.getFld_14acd_4().trim() + space + entity.getFld_14acd_5());
        }else{
        	loc_14acd_3 = new IrsFormCoordinate(46, 798, entity.getFld_14acd_3());
            loc_14acd_4 = new IrsFormCoordinate(46, 790, entity.getFld_14acd_4().trim() + space + entity.getFld_14acd_5());
        }
        
        final IrsFormCoordinate loc_14e = new IrsFormCoordinate(46, 770, bank.getFld_14e());
        final IrsFormCoordinate loc_15a = new IrsFormCoordinate(320, 905, value);
        final IrsFormCoordinate loc_15b = new IrsFormCoordinate(460, 905, entity.getFld_15b());
        final IrsFormCoordinate loc_15c = new IrsFormCoordinate(520, 905, entity.getFld_15c());
        final IrsFormCoordinate loc_16a = new IrsFormCoordinate(390, 895, value);
        final IrsFormCoordinate loc_16b = new IrsFormCoordinate(390, 886, entity.getFld_16b());
        final IrsFormCoordinate loc_16c = new IrsFormCoordinate(320, 870, value);
        final IrsFormCoordinate loc_16d = new IrsFormCoordinate(395, 870, entity.getFld_16d());
        final IrsFormCoordinate loc_16ef_1 = new IrsFormCoordinate(310, 854, value);
        final IrsFormCoordinate loc_16ef_2 = new IrsFormCoordinate(310, 848, value);
        final IrsFormCoordinate loc_16ef_3 = new IrsFormCoordinate(310, 842, value);
        final IrsFormCoordinate loc_17 = new IrsFormCoordinate(320, 823, RestUtil.isNullOrEmpty(entity.getFld_17()) ? value : entity.getFld_17());
        final IrsFormCoordinate loc_18 = new IrsFormCoordinate(420, 823, "");
        final IrsFormCoordinate loc_19 = new IrsFormCoordinate(320, 805, entity.getIrsFormId().getFld_19() + " " + entity.getAccountType());
        final IrsFormCoordinate loc_20 = new IrsFormCoordinate(470, 805, RestUtil.parseIrsDateOfBirth(entity.getFld_20()));
        final IrsFormCoordinate loc_21 = new IrsFormCoordinate(320, 788, RestUtil.isNullOrEmpty(entity.getFld_21()) ? value : entity.getFld_21());
        final IrsFormCoordinate loc_22 = new IrsFormCoordinate(420, 788, entity.getFld_22());
        final IrsFormCoordinate loc_23 = new IrsFormCoordinate(495, 788, value);
        final IrsFormCoordinate loc_24 = new IrsFormCoordinate(320, 770, RestUtil.convertFromDecimal(entity.getFld_24()));
        final IrsFormCoordinate loc_25 = new IrsFormCoordinate(420, 770, value);
        final IrsFormCoordinate loc_26 = new IrsFormCoordinate(495, 770, value);
        
        final IrsFormCoordinate loc_from_1 = new IrsFormCoordinate("thirdForm", 90, 722, 9, bank.getFld_14e().trim()); // 13a
        final IrsFormCoordinate loc_from_2 = new IrsFormCoordinate("thirdForm", 90, 712, 9, bank.getFld_14ef_1().trim()); // 13ef_1
        final IrsFormCoordinate loc_from_3 = new IrsFormCoordinate("thirdForm", 90, 702, 9, bank.getFld_14ef_2().trim() +", "+ bank.getFld_13c().trim() + " " + RestUtil.parseIrsZipCode(bank.getFld_14ef_3())); // 13ef_2
		
        final IrsFormCoordinate loc_to_1 = new IrsFormCoordinate("thirdForm",110, 610, 8, entity.getFld_to_1()); 
        final IrsFormCoordinate loc_to_2 = new IrsFormCoordinate("thirdForm",110, 600, 8, entity.getFld_to_2()); 
        final IrsFormCoordinate loc_to_3 = new IrsFormCoordinate("thirdForm",110, 590, 8, entity.getFld_to_3()); 
        final IrsFormCoordinate loc_to_4 = new IrsFormCoordinate("thirdForm",110, 580, 8, entity.getFld_to_4()); 
        final IrsFormCoordinate loc_to_5 = new IrsFormCoordinate("thirdForm",110, 570, 8, entity.getFld_to_5()); 
        final IrsFormCoordinate loc_to_6 = new IrsFormCoordinate("thirdForm",110, 560, 8, entity.getFld_to_6() + " " + entity.getFld_to_7());
		 
		
		
		list.add(loc_from_1);
        list.add(loc_from_2);
		list.add(loc_from_3);
		list.add(loc_to_1);
		list.add(loc_to_2);
		list.add(loc_to_3);
		list.add(loc_to_4);
        list.add(loc_to_5);
		list.add(loc_to_6);

        list.add(header);
        //list.add(whAgentsName);
		list.add(whAddress_1);
		list.add(whAddress_2);
		list.add(whAddress_3);
		//list.add(whCountryCode);
        
        list.add(loc_1);
        list.add(loc_2);
        list.add(loc_3);
        list.add(loc_4);
        list.add(loc_3a);
        list.add(loc_4a);
        list.add(loc_3b);
        list.add(loc_4b);
        list.add(loc_5);
        list.add(loc_6);
        list.add(loc_8);
        list.add(loc_10);
        list.add(loc_12a);
        list.add(loc_12b);
        list.add(loc_12c);
        list.add(loc_13a);
        list.add(loc_7);
        list.add(loc_7a);
        list.add(loc_9);
        list.add(loc_11);
        list.add(loc_14f);
        list.add(loc_13b);
        list.add(loc_13c);
        list.add(loc_13d);
//        list.add(loc_13ef_1);
//        list.add(loc_13ef_2);
//        list.add(loc_13ef_3);
        list.add(loc_13g);
        list.add(loc_13h);
        list.add(loc_13i);
        list.add(loc_14b);
        list.add(loc_14acd_1);
        list.add(loc_14acd_2);
        list.add(loc_14acd_3);
        list.add(loc_14acd_4);
        list.add(loc_14e);
        list.add(loc_15a);
        list.add(loc_15b);
        list.add(loc_15c);
        list.add(loc_16a);
        list.add(loc_16b);
        list.add(loc_16c);
        list.add(loc_16d);
        list.add(loc_16ef_1);
        list.add(loc_16ef_2);
        list.add(loc_16ef_3);
        list.add(loc_17);
        list.add(loc_18);
        list.add(loc_19);
        list.add(loc_20);
        list.add(loc_21);
        list.add(loc_22);
        list.add(loc_23);
        list.add(loc_24);
        list.add(loc_25);
        list.add(loc_26);
        
        setCoordinates(list);
        
	}
	

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}


	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
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
	 * Gets the coordinates.
	 *
	 * @return the coordinates
	 */
	public List<IrsFormCoordinate> getCoordinates() {
		return coordinates;
	}


	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Integer id) {
		this.id = id;
	}


	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
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
	 * Sets the coordinates.
	 *
	 * @param coordinates the new coordinates
	 */
	public void setCoordinates(List<IrsFormCoordinate> coordinates) {
		this.coordinates = coordinates;
	}

	/**
	 * Gets the response.
	 *
	 * @return the response
	 */
	public IrsFormCustomerResponse getResponse() {
		return response;
	}

	/**
	 * Sets the response.
	 *
	 * @param response the new response
	 */
	public void setResponse(IrsFormCustomerResponse response) {
		this.response = response;
	}


	/**
	 * Gets the response list.
	 *
	 * @return the response list
	 */
	public List<IrsFormCustomerResponse> getResponseList() {
		return responseList;
	}


	/**
	 * Sets the response list.
	 *
	 * @param responseList the new response list
	 */
	public void setResponseList(List<IrsFormCustomerResponse> responseList) {
		this.responseList = responseList;
	}
}
