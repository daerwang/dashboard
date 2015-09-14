/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.dashboard.controller;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.ComparisonChain;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oceanbank.webapp.common.exception.DashboardException;
import com.oceanbank.webapp.common.handler.GsonDataTableTypeAdapter;
import com.oceanbank.webapp.common.model.DataTablesRequest;
import com.oceanbank.webapp.common.model.DataTablesResponse;
import com.oceanbank.webapp.common.model.IrsFormCustomerResponse;
import com.oceanbank.webapp.common.model.IrsFormDatatableResponse;
import com.oceanbank.webapp.common.model.IrsFormSelected;
import com.oceanbank.webapp.common.model.MailCodeResponse;
import com.oceanbank.webapp.common.model.DashboardConstant;
import com.oceanbank.webapp.common.util.CommonUtil;
import com.oceanbank.webapp.dashboard.service.IrsFormServiceImpl;

/**
 * The Class IrsFormController.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@Controller
public class IrsFormController {

	/** The logger. */
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	/** The irs form service. */
	@Autowired
	private IrsFormServiceImpl irsFormService;
	
	
	/**
	 * Show irs mail code search modal.
	 *
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = DashboardConstant.SHOW_IRS_MAIL_CODE_MODAL, method = RequestMethod.GET)
	public String showIrsMailCodeSearchModal(Model model) {

		final List<MailCodeResponse> list = irsFormService.getMailCodes();
		model.addAttribute("codeList", list);
		
		model.addAttribute("selectedIrsMailCodeUrl", DashboardConstant.SHOW_SELECTED_MAIL_CODE_IRS_FORM);
		model.addAttribute("mergingPdfFromDiskDirectUrl", DashboardConstant.OPEN_NEW_WINDOW_IRS_FORM_MERGING_PDF_DIRECT);
		
		
		return DashboardConstant.SHOW_IRS_MAIL_CODE_MODAL_PAGE;
	}
	
	/**
	 * Show irs form page.
	 *
	 * @param model the model
	 * @param locale the locale
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@RequestMapping(value = DashboardConstant.SHOW_IRS_FORM_PAGE, method = RequestMethod.GET)
	public String showIrsFormPage(Model model) throws IOException{
		
		final String pageTitle = "IRS 1042-S Form";

		model.addAttribute("title1",pageTitle);
		model.addAttribute("irsFormDatatableUrl", DashboardConstant.GET_IRS_FORM_BY_DATATABLE_JSON);
		model.addAttribute("irsMailCodeSearchUrl", DashboardConstant.SHOW_IRS_MAIL_CODE_MODAL);
		
		
		model.addAttribute("mergingPdfFromDiskDirectUrl", DashboardConstant.OPEN_NEW_WINDOW_IRS_FORM_MERGING_PDF_DIRECT);
		model.addAttribute("selectedIrsFormUrl", DashboardConstant.SHOW_SELECTED_IRS_FORM);
		model.addAttribute("selectedIrsFormAllUrl", DashboardConstant.SHOW_SELECTED_IRS_FORM_ALL);
		

		return DashboardConstant.TILES_IRS_FORM_1042S_TEMPLATE;
								
	}
	
	/**
	 * Creates the selected mail code pdf.
	 *
	 * @param selected the selected
	 * @return the irs form selected
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws DashboardException the dashboard exception
	 */
	@RequestMapping(value = DashboardConstant.SHOW_SELECTED_MAIL_CODE_IRS_FORM, method = RequestMethod.POST)
	public @ResponseBody IrsFormSelected createSelectedMailCodePdf(@RequestBody IrsFormSelected selected) throws IOException, DashboardException{
		LOGGER.info("Executing createSelectedMailCodePdf() controller..");

		final String result = irsFormService.createPdfMailCodeToDisk(selected);
		final IrsFormSelected sel = new IrsFormSelected();
		sel.setStatus(result);
		sel.setSelected(new String[]{""});
		return sel;				
	}
	
	/**
	 * Creates the selected pdf.
	 *
	 * @param selected the selected
	 * @return the irs form selected
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws DashboardException the dashboard exception
	 */
	@RequestMapping(value = DashboardConstant.SHOW_SELECTED_IRS_FORM, method = RequestMethod.POST)
	public @ResponseBody IrsFormSelected createSelectedPdf(@RequestBody IrsFormSelected selected) throws IOException, DashboardException{
		LOGGER.info("Executing createSelectedPdf() controller..");

		final String result = irsFormService.createPdfToDisk(selected);
		final IrsFormSelected sel = new IrsFormSelected();
		sel.setStatus(result);
		sel.setSelected(new String[]{""});
		return sel;				
	}
	
	/**
	 * Creates the selected pdf all.
	 *
	 * @return the irs form selected
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws DashboardException the dashboard exception
	 */
	@RequestMapping(value = DashboardConstant.SHOW_SELECTED_IRS_FORM_ALL, method = RequestMethod.POST)
	public @ResponseBody IrsFormSelected createSelectedPdfAll() throws IOException, DashboardException{
		LOGGER.info("Executing createSelectedPdf() controller..");

		final String result = irsFormService.createPdfToDiskAll();
		final IrsFormSelected sel = new IrsFormSelected();
		sel.setStatus(result);
		sel.setSelected(new String[]{""});
		return sel;				
	}
	

	/**
	 * Open pdf on server.
	 *
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@RequestMapping(value = DashboardConstant.OPEN_NEW_WINDOW_IRS_FORM_MERGING_PDF_DIRECT, method = RequestMethod.GET)
	public void openPdfOnServer(HttpServletResponse response) throws IOException{
		InputStream is = null;
		try {
			is = new FileInputStream(new File(DashboardConstant.PDF_MERGING_FILE_LOCATION));
			 byte[] byteArr = null;
			 final ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		        int nRead;
		        final byte[] data = new byte[5000];
		        while ((nRead = is.read(data, 0, data.length)) != -1) {
		          buffer.write(data, 0, nRead);
		        }
		        buffer.flush();
		        byteArr = buffer.toByteArray();
		        
		        response.setContentType("application/pdf");
		        response.setContentLength(byteArr.length);
		        
		        final OutputStream os = response.getOutputStream();
		        os.write(byteArr);
		        os.flush();
		        os.close(); 
		    	
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(is != null)
				is.close();
		}
		
		
		
        
        	
	}
	
	
	/**
	 * Show irs form data table.
	 *
	 * @param allRequestParams the all request params
	 * @return the string
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	@RequestMapping(value = DashboardConstant.GET_IRS_FORM_BY_DATATABLE_JSON, method = RequestMethod.GET)
	public @ResponseBody String showIrsFormDataTable(@RequestParam Map<String, String> allRequestParams)
			throws UnsupportedEncodingException {
		
		// print for testing 
		//CommonUtil.printParameterValues(allRequestParams);

		
		Integer pageNumber = 0;
		Integer pageLength = 0;
		Integer draw = 0;
		Integer column = 0;
		String searchParameter = null;
		String mailCode = null;
		String sort = null;

		searchParameter = CommonUtil.determineValue(allRequestParams, "search[value]");
		pageNumber = Integer.valueOf(CommonUtil.determineValue(allRequestParams, "start"));
		pageLength = Integer.valueOf(CommonUtil.determineValue(allRequestParams, "length"));
		draw = Integer.valueOf(CommonUtil.determineValue(allRequestParams, "draw"));
		mailCode = CommonUtil.determineValue(allRequestParams, "mailCode");
		sort = CommonUtil.determineValue(allRequestParams, "order[0][dir]");
		column = Integer.valueOf(CommonUtil.determineValue(allRequestParams, "order[0][column]"));

		final DataTablesRequest datatableRequest = new DataTablesRequest();
		datatableRequest.setValue(searchParameter);
		datatableRequest.setStart(pageNumber);
		datatableRequest.setLength(pageLength);
		datatableRequest.setMailCode(mailCode);

		// retrieve the Users from parameters given but only 50 rows for blank search for quickness....
		final List<IrsFormCustomerResponse> responseList = irsFormService.searchIrsFormDataTable(datatableRequest);
		Integer recordsTotal = 0;
		recordsTotal = responseList.size();
		
		// transform to Json Object
		List<IrsFormDatatableResponse> responseDatatableList = new ArrayList<IrsFormDatatableResponse>();
		
		for (IrsFormCustomerResponse u : responseList) {
			final IrsFormDatatableResponse dt = new IrsFormDatatableResponse();
			
			dt.setIrsId(u.getFld_14acd_1() + "," +u.getFld_19() + "," + u.getFld_12a() + "," + u.getFld_2());
			dt.setRecipientName(u.getFld_14acd_1());
			dt.setMailCityStateZip(u.getFld_14acd_4());
			dt.setForeignCountry(u.getFld_14acd_5());
			dt.setMailCode(u.getMailCode());
			responseDatatableList.add(dt);
		}

		// create the Datatable Response
		final DataTablesResponse<IrsFormDatatableResponse> response = new DataTablesResponse<IrsFormDatatableResponse>();
		response.setDraw(draw);
		response.setRecordsFiltered(recordsTotal);
		response.setRecordsTotal(pageLength);
	
		Integer second = 0;
		Integer total = 0;
		total = pageNumber + pageLength;
		second = total < recordsTotal ? total: recordsTotal;
		
		LOGGER.debug("first " + pageNumber + " and second " + second + ".");
		
		responseDatatableList = responseDatatableList.subList(pageNumber, second);
		LOGGER.debug("column is " + column + " and sort is " + sort);
		// apply sort on column[0] only
		if(column == 0 && sort.equalsIgnoreCase("desc")){
			Collections.sort(responseDatatableList, new Comparator<IrsFormDatatableResponse>() {

				@Override
				public int compare(IrsFormDatatableResponse o1, IrsFormDatatableResponse o2) {
					return ComparisonChain.start()
							.compare(o2.getMailCode(), o1.getMailCode())
							.result();
				}
			});
		}else{
			Collections.sort(responseDatatableList, new Comparator<IrsFormDatatableResponse>() {

				@Override
				public int compare(IrsFormDatatableResponse o1, IrsFormDatatableResponse o2) {
					return ComparisonChain.start()
							.compare(o1.getMailCode(), o2.getMailCode())
							.result();
				}
			});
		}

		
		
		response.setData(responseDatatableList);

		// clear the List container
		responseDatatableList = null;

		// parse to Json Format
		final GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(DataTablesResponse.class, new GsonDataTableTypeAdapter<IrsFormDatatableResponse>());
		
		// needs to be removed to improve performance
		//gsonBuilder.setPrettyPrinting(); 
		
		final Gson gson = gsonBuilder.create();
		final String json = gson.toJson(response);
		
		LOGGER.debug(json);
		
		return json;

	}
	
	
	
	
	
	
	
	
	
}
