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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.common.collect.ComparisonChain;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oceanbank.webapp.common.exception.DashboardException;
import com.oceanbank.webapp.common.handler.GsonDataTableTypeAdapter;
import com.oceanbank.webapp.common.model.AmlBatchCifDatatable;
import com.oceanbank.webapp.common.model.AmlBatchCifResponse;
import com.oceanbank.webapp.common.model.AmlBatchDatatable;
import com.oceanbank.webapp.common.model.AmlBatchRequestResponse;
import com.oceanbank.webapp.common.model.DashboardCommentDatatable;
import com.oceanbank.webapp.common.model.DashboardCommentResponse;
import com.oceanbank.webapp.common.model.DashboardConstant;
import com.oceanbank.webapp.common.model.DashboardLogDatatable;
import com.oceanbank.webapp.common.model.DashboardLogResponse;
import com.oceanbank.webapp.common.model.DashboardSpringContext;
import com.oceanbank.webapp.common.model.DashboardUploadDatatable;
import com.oceanbank.webapp.common.model.DashboardUploadResponse;
import com.oceanbank.webapp.common.model.DataTablesRequest;
import com.oceanbank.webapp.common.model.DataTablesResponse;
import com.oceanbank.webapp.common.model.ExcelFileMeta;
import com.oceanbank.webapp.common.util.CommonUtil;
import com.oceanbank.webapp.dashboard.service.AmlBatchServiceImpl;


/**
 * The Class AmlBatchController.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@Controller
public class AmlBatchController {
	
	/** The logger. */
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	/** The aml batch service. */
	@Autowired
	private AmlBatchServiceImpl amlBatchService;
	
	/** The dashboard spring context. */
	@Autowired
	private DashboardSpringContext dashboardSpringContext;

	/** The message source. */
	@Autowired
	private MessageSource messageSource;
	
	/**
	 * Gets the aml batch form data tables.
	 *
	 * @param allRequestParams the all request params
	 * @return the aml batch form data tables
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	@RequestMapping(value = DashboardConstant.GET_AML_BATCH_BY_DATATABLE, method = RequestMethod.GET)
	public @ResponseBody String getAmlBatchDataTables(@RequestParam Map<String, String> allRequestParams)
			throws UnsupportedEncodingException {

	 	//CommonUtil.printParameterValues(allRequestParams);
		
		Integer pageNumber = 0;
		Integer pageLength = 0;
		Integer draw = 0;
		Integer column = 0;

		final String searchParameter = CommonUtil.determineValue(allRequestParams, "search[value]");
		pageNumber = Integer.valueOf(CommonUtil.determineValue(allRequestParams, "start"));
		pageLength = Integer.valueOf(CommonUtil.determineValue(allRequestParams, "length"));
		draw = Integer.valueOf(CommonUtil.determineValue(allRequestParams, "draw"));
		
		column = Integer.valueOf(CommonUtil.determineValue(allRequestParams, "order[0][column]"));
		final String dir = CommonUtil.determineValue(allRequestParams, "order[0][dir]");

		final DataTablesRequest datatableRequest = new DataTablesRequest();
		datatableRequest.setValue(searchParameter);
		datatableRequest.setStart(pageNumber);
		datatableRequest.setLength(pageLength);
		
		LOGGER.debug("searchParameter = " + searchParameter + "; pageNumber/start = " + pageNumber + "; pageLength = " + pageLength + "; draw = " + draw);

		// retrieve the Users from parameters given but only 50 rows for blank search for quickness....
		final List<AmlBatchRequestResponse> responseList = amlBatchService.getAmlBatchDataTable(datatableRequest);
		Integer recordsTotal = 0;
		recordsTotal = responseList.size();
		
		// transform to Json Object
		List<AmlBatchDatatable> responseDatatableList = new ArrayList<AmlBatchDatatable>();
		
		for (AmlBatchRequestResponse u : responseList) {
			final AmlBatchDatatable dt = new AmlBatchDatatable();
			dt.setAmlBatchId("" + u.getRequestId());
			dt.setRequestId(u.getRequestId());
			dt.setTransactionType(CommonUtil.isNullOrEmpty(u.getTransactionType()) ? "" : u.getTransactionType());
			dt.setName(CommonUtil.isNullOrEmpty(u.getName()) ? "" : u.getName());
			dt.setDescription(CommonUtil.isNullOrEmpty(u.getDescription()) ? "": u.getDescription());
			dt.setBatchStatus(CommonUtil.isNullOrEmpty(u.getStatus()) ? "": u.getStatus());
			
			dt.setCreatedBy(u.getCreatedby());
			final String createdOn = new SimpleDateFormat(DashboardConstant.AML_BATCH_DATE_FORMAT, new Locale("en")).format(u.getCreatedOn());
			dt.setCreatedOn(createdOn);
			
			dt.setModifiedBy(CommonUtil.isNullOrEmpty(u.getModifiedby()) ? "" : u.getModifiedby());
			final String modifiedOn = new SimpleDateFormat(DashboardConstant.AML_BATCH_DATE_FORMAT, new Locale("en")).format(u.getModifiedOn());
			dt.setModifiedOn(modifiedOn);
			
			responseDatatableList.add(dt);
		}

		// create the Datatable Response
		final DataTablesResponse<AmlBatchDatatable> response = new DataTablesResponse<AmlBatchDatatable>();
		response.setDraw(draw);
		response.setRecordsFiltered(recordsTotal);
		response.setRecordsTotal(pageLength);
	
		Integer second = 0;
		Integer total = 0;
		total = pageNumber + pageLength;
		second = total < recordsTotal ? total: recordsTotal;
		
		LOGGER.debug("first " + pageNumber + " and second " + second + ".");
		
		responseDatatableList = responseDatatableList.subList(pageNumber, second);
		
		// apply sort on column[0] only
		if(column == 0 && dir.equalsIgnoreCase("desc")){
			Collections.sort(responseDatatableList, new Comparator<AmlBatchDatatable>() {

				@Override
				public int compare(AmlBatchDatatable o1, AmlBatchDatatable o2) {
					return ComparisonChain.start()
							.compare(o2.getRequestId(), o1.getRequestId())
							.result();
				}
			});
		}
		
		
		response.setData(responseDatatableList);

		// clear the List container
		responseDatatableList = null;

		// parse to Json Format
		final GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(DataTablesResponse.class, new GsonDataTableTypeAdapter<AmlBatchDatatable>());
		
		// needs to be removed to improve performance
		//gsonBuilder.setPrettyPrinting(); 
		
		final Gson gson = gsonBuilder.create();
		final String json = gson.toJson(response);
		
		LOGGER.debug(json);
		
		return json;

	}
	
	@RequestMapping(value = DashboardConstant.GET_AML_BATCH_CIF_BY_DATATABLE, method = RequestMethod.GET)
	public @ResponseBody String getAmlBatchCifDataTables(@RequestParam Map<String, String> allRequestParams)
			throws UnsupportedEncodingException {
		
		Integer pageNumber = 0;
		Integer pageLength = 0;
		Integer draw = 0;
		String amlBatchRequestId = null;

		final String searchParameter = CommonUtil.determineValue(allRequestParams, "search[value]");
		pageNumber = Integer.valueOf(CommonUtil.determineValue(allRequestParams, "start"));
		pageLength = Integer.valueOf(CommonUtil.determineValue(allRequestParams, "length"));
		draw = Integer.valueOf(CommonUtil.determineValue(allRequestParams, "draw"));
		amlBatchRequestId = CommonUtil.determineValue(allRequestParams, "amlBatchRequestId");

		final DataTablesRequest datatableRequest = new DataTablesRequest();
		datatableRequest.setValue(searchParameter);
		datatableRequest.setStart(pageNumber);
		datatableRequest.setLength(pageLength);
		datatableRequest.setAmlBatchRequestId(amlBatchRequestId);
		

		// retrieve the Users from parameters given but only 50 rows for blank search for quickness....
		final List<AmlBatchCifResponse> responseList = amlBatchService.getAmlBatchCifDataTable(datatableRequest);
		Integer recordsTotal = 0;
		recordsTotal = responseList.size();
		
		// transform to Json Object
		List<AmlBatchCifDatatable> responseDatatableList = new ArrayList<AmlBatchCifDatatable>();
		
		for (AmlBatchCifResponse u : responseList) {
			final AmlBatchCifDatatable dt = new AmlBatchCifDatatable();
			dt.setCifReference(u.getCifReference());
			dt.setAuditDescription(CommonUtil.isNullOrEmpty(u.getAuditDescription()) ? "" : u.getAuditDescription());
			dt.setStatus(CommonUtil.isNullOrEmpty(u.getStatus()) ? "" : u.getStatus());
			dt.setAmlBatchCifId(u.getId().toString());
			dt.setIseriesname(CommonUtil.isNullOrEmpty(u.getIseriesname()) ? "" : u.getIseriesname());
			
			responseDatatableList.add(dt);
		}

		// create the Datatable Response
		final DataTablesResponse<AmlBatchCifDatatable> response = new DataTablesResponse<AmlBatchCifDatatable>();
		response.setDraw(draw);
		response.setRecordsFiltered(recordsTotal);
		response.setRecordsTotal(pageLength);
	
		Integer second = 0;
		Integer total = 0;
		total = pageNumber + pageLength;
		second = total < recordsTotal ? total: recordsTotal;
		
		responseDatatableList = responseDatatableList.subList(pageNumber, second);
		response.setData(responseDatatableList);

		// clear the List container
		responseDatatableList = null;

		// parse to Json Format
		final GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(DataTablesResponse.class, new GsonDataTableTypeAdapter<AmlBatchCifDatatable>());
		
		final Gson gson = gsonBuilder.create();
		final String json = gson.toJson(response);

		return json;

	}
	
	@RequestMapping(value = DashboardConstant.GET_AML_BATCH_REQUEST_UPLOAD_BY_DATATABLE, method = RequestMethod.GET)
	public @ResponseBody String getDashboardUploadDataTables(@RequestParam Map<String, String> allRequestParams)
			throws UnsupportedEncodingException {
		
		Integer pageNumber = 0;
		Integer pageLength = 0;
		Integer draw = 0;
		Integer amlRequestId = 0;

		final String searchParameter = CommonUtil.determineValue(allRequestParams, "search[value]");
		pageNumber = Integer.valueOf(CommonUtil.determineValue(allRequestParams, "start"));
		pageLength = Integer.valueOf(CommonUtil.determineValue(allRequestParams, "length"));
		draw = Integer.valueOf(CommonUtil.determineValue(allRequestParams, "draw"));
		amlRequestId = Integer.valueOf(CommonUtil.determineValue(allRequestParams, "amlRequestId"));

		final DataTablesRequest datatableRequest = new DataTablesRequest();
		datatableRequest.setValue(searchParameter);
		datatableRequest.setStart(pageNumber);
		datatableRequest.setLength(pageLength);
		datatableRequest.setAmlRequestId(amlRequestId);
		

		// retrieve the Users from parameters given but only 50 rows for blank search for quickness....
		final List<DashboardUploadResponse> responseList = amlBatchService.getDashboardUploadDataTable(datatableRequest);
		Integer recordsTotal = 0;
		recordsTotal = responseList.size();
		
		// transform to Json Object
		List<DashboardUploadDatatable> responseDatatableList = new ArrayList<DashboardUploadDatatable>();
		
		for (DashboardUploadResponse u : responseList) {
			final DashboardUploadDatatable dt = new DashboardUploadDatatable();
			dt.setCreatedby(u.getCreatedby());
			dt.setFilename(u.getFilename());
			final String createdOn = new SimpleDateFormat(DashboardConstant.AML_BATCH_DATE_FORMAT, new Locale("en")).format(u.getCreatedOn());
			dt.setCreatedon(createdOn);
			dt.setUploadId(u.getId() + "");
			
			responseDatatableList.add(dt);
		}

		// create the Datatable Response
		final DataTablesResponse<DashboardUploadDatatable> response = new DataTablesResponse<DashboardUploadDatatable>();
		response.setDraw(draw);
		response.setRecordsFiltered(recordsTotal);
		response.setRecordsTotal(pageLength);
	
		Integer second = 0;
		Integer total = 0;
		total = pageNumber + pageLength;
		second = total < recordsTotal ? total: recordsTotal;
		
		if(!responseDatatableList.isEmpty()){
			responseDatatableList = responseDatatableList.subList(pageNumber, second);
			
			// apply sort on column[0] only
			Collections.sort(responseDatatableList, new Comparator<DashboardUploadDatatable>() {

				@Override
				public int compare(DashboardUploadDatatable o1, DashboardUploadDatatable o2) {
					return ComparisonChain.start()
							.compare(o2.getCreatedon(), o1.getCreatedon())
							.result();
				}
			});
		}
		
		
		response.setData(responseDatatableList);

		// clear the List container
		responseDatatableList = null;

		// parse to Json Format
		final GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(DataTablesResponse.class, new GsonDataTableTypeAdapter<DashboardUploadDatatable>());
		
		final Gson gson = gsonBuilder.create();
		final String json = gson.toJson(response);

		return json;

	}
	
	@RequestMapping(value = DashboardConstant.GET_DASHBOARD_COMMENT_BY_DATATABLE, method = RequestMethod.GET)
	public @ResponseBody String getDashboardCommentDataTables(@RequestParam Map<String, String> allRequestParams)
			throws UnsupportedEncodingException {
		
		Integer pageNumber = 0;
		Integer pageLength = 0;
		Integer draw = 0;
		Integer amlRequestId = 0;

		final String searchParameter = CommonUtil.determineValue(allRequestParams, "search[value]");
		pageNumber = Integer.valueOf(CommonUtil.determineValue(allRequestParams, "start"));
		pageLength = Integer.valueOf(CommonUtil.determineValue(allRequestParams, "length"));
		draw = Integer.valueOf(CommonUtil.determineValue(allRequestParams, "draw"));
		amlRequestId = Integer.valueOf(CommonUtil.determineValue(allRequestParams, "amlRequestId"));

		final DataTablesRequest datatableRequest = new DataTablesRequest();
		datatableRequest.setValue(searchParameter);
		datatableRequest.setStart(pageNumber);
		datatableRequest.setLength(pageLength);
		datatableRequest.setAmlRequestId(amlRequestId);
		

		// retrieve the Users from parameters given but only 50 rows for blank search for quickness....
		final List<DashboardCommentResponse> responseList = amlBatchService.getDashboardCommentDataTable(datatableRequest);
		Integer recordsTotal = 0;
		recordsTotal = responseList.size();
		
		// transform to Json Object
		List<DashboardCommentDatatable> responseDatatableList = new ArrayList<DashboardCommentDatatable>();
		
		for (DashboardCommentResponse u : responseList) {
			final DashboardCommentDatatable dt = new DashboardCommentDatatable();
			dt.setCreatedby(u.getCreatedby());
			dt.setMessage(u.getMessage());
			final String createdOn = new SimpleDateFormat(DashboardConstant.AML_BATCH_DATE_FORMAT, new Locale("en")).format(u.getCreatedOn());
			dt.setCreatedon(createdOn);
			dt.setUploadId(u.getId() + "");
			
			responseDatatableList.add(dt);
		}

		// create the Datatable Response
		final DataTablesResponse<DashboardCommentDatatable> response = new DataTablesResponse<DashboardCommentDatatable>();
		response.setDraw(draw);
		response.setRecordsFiltered(recordsTotal);
		response.setRecordsTotal(pageLength);
	
		Integer second = 0;
		Integer total = 0;
		total = pageNumber + pageLength;
		second = total < recordsTotal ? total: recordsTotal;
		
		if(!responseDatatableList.isEmpty()){
			responseDatatableList = responseDatatableList.subList(pageNumber, second);
			
			// apply sort on column[0] only
			Collections.sort(responseDatatableList, new Comparator<DashboardCommentDatatable>() {

				@Override
				public int compare(DashboardCommentDatatable o1, DashboardCommentDatatable o2) {
					return ComparisonChain.start()
							.compare(o2.getCreatedon(), o1.getCreatedon())
							.result();
				}
			});
		}
		
		
		response.setData(responseDatatableList);

		// clear the List container
		responseDatatableList = null;

		// parse to Json Format
		final GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(DataTablesResponse.class, new GsonDataTableTypeAdapter<DashboardCommentDatatable>());
		
		final Gson gson = gsonBuilder.create();
		final String json = gson.toJson(response);

		return json;

	}
	
	@RequestMapping(value = DashboardConstant.GET_DASHBOARD_LOG_BY_DATATABLE, method = RequestMethod.GET)
	public @ResponseBody String getDashboardLogDataTables(@RequestParam Map<String, String> allRequestParams)
			throws UnsupportedEncodingException {
		
		Integer pageNumber = 0;
		Integer pageLength = 0;
		Integer draw = 0;
		Integer amlRequestId = 0;

		final String searchParameter = CommonUtil.determineValue(allRequestParams, "search[value]");
		pageNumber = Integer.valueOf(CommonUtil.determineValue(allRequestParams, "start"));
		pageLength = Integer.valueOf(CommonUtil.determineValue(allRequestParams, "length"));
		draw = Integer.valueOf(CommonUtil.determineValue(allRequestParams, "draw"));
		amlRequestId = Integer.valueOf(CommonUtil.determineValue(allRequestParams, "amlRequestId"));

		final DataTablesRequest datatableRequest = new DataTablesRequest();
		datatableRequest.setValue(searchParameter);
		datatableRequest.setStart(pageNumber);
		datatableRequest.setLength(pageLength);
		datatableRequest.setAmlRequestId(amlRequestId);
		

		// retrieve the Users from parameters given but only 50 rows for blank search for quickness....
		final List<DashboardLogResponse> responseList = amlBatchService.getDashboardLogDataTable(datatableRequest);
		Integer recordsTotal = 0;
		recordsTotal = responseList.size();
		
		// transform to Json Object
		List<DashboardLogDatatable> responseDatatableList = new ArrayList<DashboardLogDatatable>();
		
		for (DashboardLogResponse u : responseList) {
			final DashboardLogDatatable dt = new DashboardLogDatatable();
			dt.setCreatedby(u.getCreatedby());
			dt.setDescription(u.getDescription());
			final String createdOn = new SimpleDateFormat(DashboardConstant.AML_BATCH_DATE_FORMAT, new Locale("en")).format(u.getCreatedOn());
			dt.setCreatedon(createdOn);
			dt.setLogId(u.getId() + "");
			
			responseDatatableList.add(dt);
		}

		// create the Datatable Response
		final DataTablesResponse<DashboardLogDatatable> response = new DataTablesResponse<DashboardLogDatatable>();
		response.setDraw(draw);
		response.setRecordsFiltered(recordsTotal);
		response.setRecordsTotal(pageLength);
	
		Integer second = 0;
		Integer total = 0;
		total = pageNumber + pageLength;
		second = total < recordsTotal ? total: recordsTotal;
		
		responseDatatableList = responseDatatableList.subList(pageNumber, second);
		
		// apply sort on column[0] only
		Collections.sort(responseDatatableList, new Comparator<DashboardLogDatatable>() {

			@Override
			public int compare(DashboardLogDatatable o1, DashboardLogDatatable o2) {
				return ComparisonChain.start()
						.compare(o2.getCreatedon(), o1.getCreatedon())
						.result();
			}
		});
		
		response.setData(responseDatatableList);

		// clear the List container
		responseDatatableList = null;

		// parse to Json Format
		final GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(DataTablesResponse.class, new GsonDataTableTypeAdapter<DashboardLogDatatable>());
		
		final Gson gson = gsonBuilder.create();
		final String json = gson.toJson(response);

		return json;

	}
	
	/**
	 * Creates the aml batch request id.
	 *
	 * @return the string
	 */
	@RequestMapping(value = DashboardConstant.GENERATE_AML_BATCH_REQUEST_ID, method = RequestMethod.GET)
	public String createAmlBatchRequest(){
		
		AmlBatchRequestResponse response = new AmlBatchRequestResponse();
		response.setCreatedby(CommonUtil.getAuthenticatedUserDetails().getUsername());
		
		response = amlBatchService.createAmlBatchRequest(response);
		
		final List<String> list = dashboardSpringContext.getAmlBatchTransactionType();		
		
		String[] selectable = new String[list.size()];
		selectable = list.toArray(selectable);		
		response.setSelectableTypes(selectable);
		response.setTransactionType("");
		
		final List<String> bankList = dashboardSpringContext.getBanks();
		String[] bankArr = new String[bankList.size()]; 
		bankArr = bankList.toArray(bankArr);
		response.setBankSchemas(bankArr);
		response.setBankSchema("");
		
		final String requestId = response.getRequestId();
		
		return "redirect:" + DashboardConstant.SHOW_AML_BATCH_REQUEST_PAGE_BY_REQUEST_ID + "?id=" + requestId;
	}
	
	/**
	 * Update aml batch request page.
	 *
	 * @param model the model
	 * @param response the response
	 * @return the dashboard aml batch request response
	 */
	@RequestMapping(value = DashboardConstant.SHOW_AML_BATCH_REQUEST_PAGE_BY_REQUEST_ID, method = RequestMethod.POST)
	public @ResponseBody AmlBatchRequestResponse updateAmlBatchRequest(Model model, @RequestBody AmlBatchRequestResponse response) {
		
		final String pageTitle = "AML Batch Request";
		model.addAttribute("title1",pageTitle);
		completeAmlBatchRequestModel(model);
		
		response.setModifiedby(CommonUtil.getAuthenticatedUserDetails().getUsername());
		
		// get Bean for update
		response = amlBatchService.updateAmlBatchRequest(response);
		

		final List<String> list = dashboardSpringContext.getAmlBatchTransactionType();
		String[] selectable = new String[list.size()];
		selectable = list.toArray(selectable);
		response.setSelectableTypes(selectable);
		
		final List<String> bankList = dashboardSpringContext.getBanks();
		String[] bankArr = new String[bankList.size()]; 
		bankArr = bankList.toArray(bankArr);
		response.setBankSchemas(bankArr);
		
		model.addAttribute("amlBatchRequest", response);
		

		return response;
	}

	@RequestMapping(value = DashboardConstant.DELETE_AML_BATCH_REQUEST_PAGE_BY_REQUEST_ID, method = RequestMethod.DELETE)
	public @ResponseBody String deleteAmlBatchRequestByRequestId(@PathVariable("requestId") String requestId) throws DashboardException {
		
		final AmlBatchRequestResponse response = amlBatchService.getAmlBatchRequestByRequestId(requestId);
		// check if user is the owner
		if(!isUserOwnerOfAmlBatchRequest(response)){
			throw new DashboardException("You are not allowed to delete " + requestId + ".", null);
		}
		if(response.getStatus().equalsIgnoreCase(DashboardConstant.AML_BATCH_STATUS_COMPLETE)){
			throw new DashboardException("You are not allowed to delete " + requestId + " because its already Completed.", null);
		}
		
		final String result = amlBatchService.deleteAmlBatchRequestByRequestId(requestId);
		
		return result;
	}
	
	private void completeAmlBatchRequestModel(Model model){
		
		model.addAttribute("amlBatchPage", DashboardConstant.SHOW_AML_BATCH_PAGE);
		model.addAttribute("amlBatchCifModal", DashboardConstant.SHOW_CREATE_AML_BATCH_CIF_BY_REQUEST_ID);
		model.addAttribute("amlBatchCifDatatable", DashboardConstant.GET_AML_BATCH_CIF_BY_DATATABLE);
		model.addAttribute("amlBatchUploadDatatable", DashboardConstant.GET_AML_BATCH_REQUEST_UPLOAD_BY_DATATABLE);
		model.addAttribute("updateAmlBatchCifModal", DashboardConstant.SHOW_UPDATE_AML_BATCH_CIF_BY_ID);
		model.addAttribute("deleteAmlBatchCif", DashboardConstant.DELETE_AML_BATCH_CIF_BY_ID);
		model.addAttribute("executeAmlBatchApproval", DashboardConstant.EXECUTE_AML_BATCH_APPROVAL_BY_REQUEST_ID);
		model.addAttribute("executeAmlBatchReversal", DashboardConstant.EXECUTE_AML_BATCH_REVERSAL_BY_REQUEST_ID);
		model.addAttribute("executeAmlBatchDisapproval", DashboardConstant.EXECUTE_AML_BATCH_DISAPPROVAL_BY_REQUEST_ID);
		model.addAttribute("uploadExcelModal", DashboardConstant.SHOW_UPLOAD_EXCEL_AML_BATCH_CIF_MODAL_BY_REQUEST_ID);
		model.addAttribute("amlBatchRequestLogPage", DashboardConstant.SHOW_AML_BATCH_REQUEST_LOG_PAGE_BY_REQUEST_ID);
		model.addAttribute("uploadAmlBatchRequest", DashboardConstant.SHOW_AML_BATCH_REQUEST_UPLOAD_BY_REQUEST_ID);
		model.addAttribute("openUploadAmlBatchRequest", DashboardConstant.OPEN_AML_BATCH_REQUEST_UPLOAD);
		model.addAttribute("deleteDashboardUpload", DashboardConstant.DELETE_DASHBOARDUPLOAD_BY_ID);
		
		model.addAttribute("dashboardCommentDatatable", DashboardConstant.GET_DASHBOARD_COMMENT_BY_DATATABLE);
		model.addAttribute("showCreateDashboardCommentModal", DashboardConstant.SHOW_CREATE_DASHBOARD_COMMENT_BY_ID);
		model.addAttribute("showUpdateDashboardCommentModal", DashboardConstant.SHOW_UPDATE_DASHBOARD_COMMENT_BY_ID);
		// SHOW_CREATE_DASHBOARD_COMMENT_BY_ID
	}
	
	@RequestMapping(value = DashboardConstant.SHOW_AML_BATCH_REQUEST_PAGE_BY_REQUEST_ID, method = RequestMethod.GET)
	public String showAmlBatchRequestByRequestId(Model model, @RequestParam("id") String requestId) {

		final String pageTitle = "AML Batch Request - " + requestId;

		model.addAttribute("title1",pageTitle);
		completeAmlBatchRequestModel(model);
		
		final AmlBatchRequestResponse response = amlBatchService.getAmlBatchRequestByRequestId(requestId);
		// if response is null then show error page
		if(response == null){
			return DashboardConstant.TILES_AML_BATCH_ERROR_TEMPLATE;
		}

		model.addAttribute("requestId1",response.getRequestId());
		model.addAttribute("amlRequestId",response.getId());
		
		final List<String> list = dashboardSpringContext.getAmlBatchTransactionType();
		String[] selectable = new String[list.size()];
		selectable = list.toArray(selectable);
		response.setSelectableTypes(selectable);
		
		final List<String> bankList = dashboardSpringContext.getBanks();
		String[] bankArr = new String[bankList.size()]; 
		bankArr = bankList.toArray(bankArr);
		response.setBankSchemas(bankArr);
		
		model.addAttribute("amlBatchRequest", response);
		
			
		
		model.addAttribute("isOwner", isUserOwnerOfAmlBatchRequest(response));

		return DashboardConstant.TILES_AML_BATCH_PAGE_REQUEST_TEMPLATE;
	}
	
	private Boolean isUserOwnerOfAmlBatchRequest(AmlBatchRequestResponse response){
		String owner = response.getCreatedby();
		String user = CommonUtil.getAuthenticatedUserDetails().getUsername();
		Boolean isOwner = owner.equalsIgnoreCase(user);
		
		return isOwner;
	}
	
	@RequestMapping(value = DashboardConstant.SHOW_AML_BATCH_REQUEST_LOG_PAGE_BY_REQUEST_ID, method = RequestMethod.GET)
	public String showAmlBatchRequestLogByRequestId(Model model, @RequestParam("id") String requestId) {

		final String pageTitle = "AML Batch Request - " + requestId;

		model.addAttribute("title1",pageTitle);
		completeAmlBatchRequestModel(model);
		model.addAttribute("amlBatchRequestPage", DashboardConstant.SHOW_AML_BATCH_REQUEST_PAGE_BY_REQUEST_ID);
		model.addAttribute("dashboardLogDatatable", DashboardConstant.GET_DASHBOARD_LOG_BY_DATATABLE);
		
		final AmlBatchRequestResponse response = amlBatchService.getAmlBatchRequestByRequestId(requestId);
		// if response is null then show error page
		if(response == null){
			return DashboardConstant.TILES_AML_BATCH_ERROR_TEMPLATE;
		}

		model.addAttribute("requestId1",response.getRequestId());
		model.addAttribute("amlRequestId",response.getId());
		
		model.addAttribute("amlBatchRequest", response);
		

		return DashboardConstant.TILES_AML_BATCH_LOG_PAGE_REQUEST_TEMPLATE;
	}
	
	/**
	 * Show create aml batch container modal.
	 *
	 * @param model the model
	 * @param transactionType the transaction type
	 * @return the string
	 */
	@RequestMapping(value = DashboardConstant.SHOW_CREATE_AML_BATCH_CIF_BY_REQUEST_ID, method = RequestMethod.GET)
	public String showCreateAmlBatchCif(Model model, @PathVariable("requestId") String requestId) {
		
		final String pageTitle = "New CIF for AML Batch";
		
		model.addAttribute("title1", pageTitle);
		model.addAttribute("cifReference1", "CIF Reference");
		model.addAttribute("description1", "Description");
		model.addAttribute("transaction1", "Transaction");
		model.addAttribute("createAmlBatchCif", DashboardConstant.CREATE_AML_BATCH_CIF);
		
		final AmlBatchRequestResponse req =  amlBatchService.getAmlBatchRequestByRequestId(requestId);
		
		// pass the object by query on row_id
		final AmlBatchCifResponse response = new AmlBatchCifResponse();
		response.setTransactionType(req.getTransactionType());
		response.setRequestId(req.getRequestId());
		model.addAttribute("amlBatchCif", response);
		
		
		return DashboardConstant.SHOW_CREATE_AML_BATCH_CIF_MODAL;
	}
	
	@RequestMapping(value = DashboardConstant.EXECUTE_AML_BATCH_APPROVAL_BY_REQUEST_ID, method = RequestMethod.GET)
	public @ResponseBody AmlBatchRequestResponse executeAmlBatchRequestApproval(@PathVariable("requestId") String requestId) throws DashboardException {
		
		AmlBatchRequestResponse result = null;
		try {
			result = amlBatchService.executeAmlBatchRequestApproval(requestId);
		} catch (RestClientException e) {
			throw new DashboardException(e.getMessage(), e.getCause());
		}
		
//		try {
//			Thread.sleep(15000L);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		
		return result;
	}
	
	@RequestMapping(value = DashboardConstant.EXECUTE_AML_BATCH_REVERSAL_BY_REQUEST_ID, method = RequestMethod.GET)
	public @ResponseBody AmlBatchRequestResponse executeAmlBatchRequestReversal(@PathVariable("requestId") String requestId) throws DashboardException {
		
		AmlBatchRequestResponse result = null;
		try {
			result = amlBatchService.executeAmlBatchRequestReversal(requestId);
		} catch (RestClientException e) {
			throw new DashboardException(e.getMessage(), e.getCause());
		}
		
		return result;
	}
	
	@RequestMapping(value = DashboardConstant.EXECUTE_AML_BATCH_DISAPPROVAL_BY_REQUEST_ID, method = RequestMethod.GET)
	public @ResponseBody AmlBatchRequestResponse executeAmlBatchRequestDisapproval(@PathVariable("requestId") String requestId) throws DashboardException {
		
		AmlBatchRequestResponse result = null;
		try {
			result = amlBatchService.executeAmlBatchRequestDisapproval(requestId);
		} catch (RestClientException e) {
			throw new DashboardException(e.getMessage(), e.getCause());
		}
		
		return result;
	}
	
	@RequestMapping(value = DashboardConstant.SHOW_UPDATE_AML_BATCH_CIF_BY_ID, method = RequestMethod.GET)
	public String showUpdateAmlBatchCif(Model model, @PathVariable("id") Integer id) {
		
		final String pageTitle = "Update CIF for AML Batch";
		
		model.addAttribute("title1", pageTitle);
		model.addAttribute("cifReference1", "CIF Reference");
		model.addAttribute("description1", "Description");
		model.addAttribute("transaction1", "Transaction");
		model.addAttribute("updateAmlBatchCif", DashboardConstant.UPDATE_AML_BATCH_CIF);
		
		AmlBatchCifResponse response = amlBatchService.findAmlBatchCif(id);
		model.addAttribute("amlBatchCif", response);
		
		
		return DashboardConstant.SHOW_UPDATE_AML_BATCH_CIF_MODAL;
	}
	
	@RequestMapping(value = DashboardConstant.CREATE_AML_BATCH_CIF, method = RequestMethod.POST)
	public @ResponseBody AmlBatchCifResponse createAmlBatchCif(@RequestBody AmlBatchCifResponse response) throws DashboardException{
		
		AmlBatchCifResponse result = null;
		try {
			response.setCreatedby(CommonUtil.getAuthenticatedUserDetails().getUsername());
			response.setStatus("new request");
			result = amlBatchService.createAmlBatchCif(response);
		} catch (RestClientException e) {
			throw new DashboardException(e.getMessage(), e.getCause());
		}
		
		return result;
	}
	
	@RequestMapping(value = DashboardConstant.UPDATE_AML_BATCH_CIF, method = RequestMethod.POST)
	public @ResponseBody AmlBatchCifResponse updateAmlBatchCif(@RequestBody AmlBatchCifResponse response) {
		response.setModifiedby(CommonUtil.getAuthenticatedUserDetails().getUsername());
		return amlBatchService.updateAmlBatchCif(response);
	}
	
	@RequestMapping(value = DashboardConstant.DELETE_AML_BATCH_CIF_BY_ID, method = RequestMethod.DELETE)
	public @ResponseBody String deleteAmlBatchCif(@PathVariable("id") Integer id) throws DashboardException{
		String result = null;
		AmlBatchCifResponse response = amlBatchService.findAmlBatchCif(id);
		String status = response.getStatus().trim();
		if(status.equalsIgnoreCase(DashboardConstant.AML_BATCH_STATUS_APPROVED)
				|| status.equalsIgnoreCase(DashboardConstant.AML_BATCH_STATUS_REVERSED)
				|| status.equalsIgnoreCase(DashboardConstant.AML_BATCH_STATUS_DISAPPROVED)){
			throw new DashboardException("You are not allowed to delete CIF " + response.getCifReference() + " because its been " + response.getStatus() + ".", null);
		}
		
		try {
			result = amlBatchService.deleteAmlBatchCif(id);
		} catch (Exception e) {
			throw new DashboardException(e.getMessage(), e.getCause());
		}
		
		
		return result;
	}
	
	/**
	 * Show aml batch page.
	 *
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = DashboardConstant.SHOW_AML_BATCH_PAGE, method = RequestMethod.GET)
	public String showAmlBatchPage(Model model) {

		final String pageTitle = "AML Batch Approval and Disapproval";

		model.addAttribute("title1",pageTitle);
		model.addAttribute("amlBatchRequestPage", DashboardConstant.SHOW_AML_BATCH_REQUEST_PAGE_BY_REQUEST_ID);
		model.addAttribute("generateRequestId", DashboardConstant.GENERATE_AML_BATCH_REQUEST_ID);
		model.addAttribute("amlBatchDatatable1", DashboardConstant.GET_AML_BATCH_BY_DATATABLE);
		model.addAttribute("deleteAmlBatchRequest", DashboardConstant.DELETE_AML_BATCH_REQUEST_PAGE_BY_REQUEST_ID);

		return DashboardConstant.TILES_AML_BATCH_PAGE_TEMPLATE;
	}
	
	@RequestMapping(value = DashboardConstant.SHOW_UPLOAD_EXCEL_AML_BATCH_CIF_MODAL_BY_REQUEST_ID, method = RequestMethod.GET)
	public String showUploadExcelModal(Model model, @PathVariable("requestId") String requestId) {
		
		model.addAttribute("title1", "Upload Excel File");
		model.addAttribute("cifReference1", "CIF Reference");
		model.addAttribute("executeUploadExcel", DashboardConstant.EXECUTE_UPLOAD_EXCEL_AML_BATCH_CIF_MODAL);
		model.addAttribute("requestId", requestId);
		
		return DashboardConstant.SHOW_UPLOAD_EXCEL_AML_BATCH_CIF_MODAL;
	}
	
	private ExecutorService executor = Executors.newFixedThreadPool(5);
	
	@RequestMapping(value = DashboardConstant.EXECUTE_UPLOAD_EXCEL_AML_BATCH_CIF_MODAL, method = RequestMethod.POST)
	public @ResponseBody ExcelFileMeta executeUploadExcelAmlBatchCif(MultipartHttpServletRequest request, @RequestParam Map<String, String> allRequestParams) throws DashboardException {
		
		final String requestId = CommonUtil.determineValue(allRequestParams, "requestId");

        Iterator<String> itr =  request.getFileNames();
        MultipartFile mpf = null;
        ExcelFileMeta fileMeta = null;
        
        final String fileName = itr.next();

    	mpf = request.getFile(fileName); 
    	// allow only less than 4MB excel file
    	if(mpf.getSize()/1024 > 4000){
        	throw new DashboardException("The upload should be less than 4MB in size.");
        }
    	// allow only xls or xlsx file name
    	String ext = FilenameUtils.getExtension(mpf.getOriginalFilename());
    	if(ext.equalsIgnoreCase("xls") || ext.equalsIgnoreCase("xlsx")){
    		// do nothing
    	}else{
    		throw new DashboardException("The upload should be an Excel file.");
    	}
    	
    	String createdBy = CommonUtil.getAuthenticatedUserDetails().getUsername();
    	
    	final List<AmlBatchCifResponse> cifList = amlBatchService.createAmlBatchCifFromExcel(requestId, mpf, createdBy);
    	String result = null;
    	
    	Long cifListCount = new Long(cifList.size());
    	request.getSession().setAttribute("cifListCount", cifListCount);
    	Future<String> future = executor.submit(new Callable<String>() {

			@Override
            public String call() throws Exception {
            	String message = null;

            	message = amlBatchService.createManyAmlBatchCif(cifList);

            	return message;
            }
        });
    	
    	while(!future.isDone()){
    		List<AmlBatchCifResponse> list = amlBatchService.getAmlBatchCifByRequestId(requestId);
    		Long currentCount = 1L;
    		if(!list.isEmpty()){
    			currentCount = new Long(list.size());
    		}
    		request.getSession().setAttribute("currentCount", currentCount);
    		try {
				Thread.sleep(2500L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    	
    	try {

        	result =  future.get();

        } catch (InterruptedException e) {
            result = "Exception caused by InterruptedException";
        } catch (ExecutionException e) {
        	result = "Exception caused by ExecutionException";
        } catch(Exception e){
        	result = "Exception caused by General Exception";
        }
    	
        fileMeta = new ExcelFileMeta();
        fileMeta.setFileName(mpf.getOriginalFilename());
        fileMeta.setFileSize(mpf.getSize()/1024+" Kb - " + result);
        fileMeta.setFileType(mpf.getContentType());
    
        
		return fileMeta;
	}
	
	@RequestMapping(value = DashboardConstant.SHOW_AML_BATCH_REQUEST_UPLOAD_BY_REQUEST_ID, method = RequestMethod.GET)
	public String showDashboardUploadModal(Model model, @PathVariable("requestId") String requestId) {
		
		model.addAttribute("title1", "Upload Excel File");
		model.addAttribute("executeAmlBatchRequestUpload", DashboardConstant.EXECUTE_AML_BATCH_REQUEST_UPLOAD);
		model.addAttribute("requestId", requestId);
		
		return DashboardConstant.SHOW_AML_BATCH_REQUEST_UPLOAD_MODAL;
	}
	
	@RequestMapping(value = DashboardConstant.EXECUTE_AML_BATCH_REQUEST_UPLOAD, method = RequestMethod.POST)
	@ResponseBody
	public ExcelFileMeta executeDashboardUpload(MultipartHttpServletRequest request, @RequestParam Map<String, String> allRequestParams) throws DashboardException, IOException {

		final String requestId = CommonUtil.determineValue(allRequestParams, "requestId");
		
        Iterator<String> itr =  request.getFileNames();
        MultipartFile mpf = null;
        ExcelFileMeta fileMeta = null;
        
        final String fileName = itr.next();

    	mpf = request.getFile(fileName); 
    	validateExcelFileForUpload(mpf);
    	
    	String createdBy = CommonUtil.getAuthenticatedUserDetails().getUsername();
    	
    	synchronized (this) {
    		amlBatchService.saveAmlBatchRequestUploadFileToDisk(mpf, createdBy, requestId);
		}
    	
        fileMeta = new ExcelFileMeta();
        fileMeta.setFileName(mpf.getOriginalFilename());
        fileMeta.setFileSize(mpf.getSize()/1024+" Kb - OK");
        fileMeta.setFileType(mpf.getContentType());

		return fileMeta;
	}
	
	@RequestMapping(value = DashboardConstant.OPEN_AML_BATCH_REQUEST_UPLOAD, method = RequestMethod.GET)
	public void openDashboardUpload(HttpServletResponse response, @PathVariable("id") Long id) throws IOException{
		InputStream is = null;
		DashboardUploadResponse bean = amlBatchService.findDashboardUploadById(id);
		String fullLocation = amlBatchService.getUploadPermanentFileLocation() + id + "//" + bean.getFilename();
		try {
			is = new FileInputStream(new File(fullLocation));
			 byte[] byteArr = null;
			 final ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		        int nRead;
		        final byte[] data = new byte[5000];
		        while ((nRead = is.read(data, 0, data.length)) != -1) {
		          buffer.write(data, 0, nRead);
		        }
		        buffer.flush();
		        byteArr = buffer.toByteArray();
		        
		        response.setContentType("application/vnd.ms-excel");
		        response.setContentLength(byteArr.length);
		        response.setHeader("Content-Disposition","attachment;filename=" + bean.getFilename());
		        
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
	
	private boolean removeDirectory(File directory) {
		
		  if (directory == null)
		    return false;
		  if (!directory.exists())
		    return true;
		  if (!directory.isDirectory())
		    return false;

		  String[] list = directory.list();
		  if (list != null) {
		    for (int i = 0; i < list.length; i++) {
		      File entry = new File(directory, list[i]);
		      if (entry.isDirectory()){
		    	  
		        if (!removeDirectory(entry))
		          return false;
		        
		      }else{
		    	  
		        if (!entry.delete())
		          return false;
		        
		      }
		    }
		  }

		  return directory.delete();
	}
	
	@RequestMapping(value = DashboardConstant.DELETE_DASHBOARDUPLOAD_BY_ID, method = RequestMethod.DELETE)
	public @ResponseBody String deleteDashboardUpload(@PathVariable("id") Long id)throws DashboardException{
		String result = null;
		
		// 1. delete in the file system
		String fullLocation = amlBatchService.getUploadPermanentFileLocation() + id;
		File file = new File(fullLocation);
		
		boolean isSuccess = removeDirectory(file);
		
		// 2. delete in the database
		if(isSuccess)
			result = amlBatchService.deleteDashboardUpload(id);
		
		return result;
	}
	
	@RequestMapping(value = DashboardConstant.SHOW_CREATE_DASHBOARD_COMMENT_BY_ID, method = RequestMethod.GET)
	public String showCreateCommentModal(Model model, @PathVariable("id") Long id) {
		
		final String pageTitle = "Create Comment";
		
		model.addAttribute("title1", pageTitle);
		model.addAttribute("message1", "Comment");
		model.addAttribute("createDashboardComment", DashboardConstant.CREATE_DASHBOARD_COMMENT);
		
		final DashboardCommentResponse response = new DashboardCommentResponse();
		response.setTableId(id.intValue());
		model.addAttribute("dashboardComment", response);
		
		
		return DashboardConstant.SHOW_CREATE_DASHBOARD_COMMENT_MODAL;
	}
	
	@RequestMapping(value = DashboardConstant.SHOW_UPDATE_DASHBOARD_COMMENT_BY_ID, method = RequestMethod.GET)
	public String showUpdateDashboardComment(Model model, @PathVariable("id") Long id) {
		
		final String pageTitle = "Update Comment";
		
		model.addAttribute("title1", pageTitle);
		model.addAttribute("message1", "Comment");
		model.addAttribute("updateDashboardComment", DashboardConstant.UPDATE_DASHBOARD_COMMENT);
		
		DashboardCommentResponse response = amlBatchService.findDashboardCommentById(id);
		model.addAttribute("dashboardComment", response);
		
		
		return DashboardConstant.SHOW_UPDATE_DASHBOARD_COMMENT_MODAL;
	}
	
	@RequestMapping(value = DashboardConstant.CREATE_DASHBOARD_COMMENT, method = RequestMethod.POST)
	public @ResponseBody DashboardCommentResponse createDashboardComment(@RequestBody DashboardCommentResponse response) throws DashboardException{
		
		DashboardCommentResponse result = null;
		try {
			response.setCreatedby(CommonUtil.getAuthenticatedUserDetails().getUsername());

			result = amlBatchService.createDashboardComment(response);
		} catch (RestClientException e) {
			throw new DashboardException(e.getMessage(), e.getCause());
		}
		
		return result;
	}
	
	@RequestMapping(value = DashboardConstant.UPDATE_DASHBOARD_COMMENT, method = RequestMethod.POST)
	public @ResponseBody DashboardCommentResponse updateDashboardComment(@RequestBody DashboardCommentResponse response) {
		response.setModifiedby(CommonUtil.getAuthenticatedUserDetails().getUsername());
		return amlBatchService.updateDashboardComment(response);
	}
	
//	@RequestMapping(value = DashboardConstant.DELETE_AML_BATCH_CIF_BY_ID, method = RequestMethod.DELETE)
//	public @ResponseBody String deleteAmlBatchCif(@PathVariable("id") Integer id) throws DashboardException{
//		String result = null;
//		AmlBatchCifResponse response = amlBatchService.findAmlBatchCif(id);
//		String status = response.getStatus().trim();
//		if(status.equalsIgnoreCase(DashboardConstant.AML_BATCH_STATUS_APPROVED)
//				|| status.equalsIgnoreCase(DashboardConstant.AML_BATCH_STATUS_REVERSED)
//				|| status.equalsIgnoreCase(DashboardConstant.AML_BATCH_STATUS_DISAPPROVED)){
//			throw new DashboardException("You are not allowed to delete CIF " + response.getCifReference() + " because its been " + response.getStatus() + ".", null);
//		}
//		
//		try {
//			result = amlBatchService.deleteAmlBatchCif(id);
//		} catch (Exception e) {
//			throw new DashboardException(e.getMessage(), e.getCause());
//		}
//		
//		
//		return result;
//	}
	
	private void validateExcelFileForUpload(MultipartFile mpf) throws DashboardException{
		// allow only less than 4MB excel file
    	if(mpf.getSize()/1024 > 4000){
        	throw new DashboardException("The upload should be less than 4MB in size.");
        }
    	
    	// allow only xls or xlsx file name
    	String ext = FilenameUtils.getExtension(mpf.getOriginalFilename());
    	if(ext.equalsIgnoreCase("xls") || ext.equalsIgnoreCase("xlsx")){
    		// do nothing
    	}else{
    		throw new DashboardException("Sorry, this Upload accepts Excel files only.");
    	}
	}
	
}
