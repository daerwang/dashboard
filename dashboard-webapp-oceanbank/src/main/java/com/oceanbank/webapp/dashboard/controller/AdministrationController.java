/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.dashboard.controller;

import com.oceanbank.webapp.common.handler.GsonDataTableTypeAdapter;
import com.oceanbank.webapp.common.model.BootstrapValidatorResponse;
import com.oceanbank.webapp.common.model.DataTablesRequest;
import com.oceanbank.webapp.common.model.DataTablesResponse;
import com.oceanbank.webapp.common.model.ObDashboardRoles;
import com.oceanbank.webapp.common.model.DashboardConstant;
import com.oceanbank.webapp.common.model.UserDataTableResponse;
import com.oceanbank.webapp.common.model.UserResponse;
import com.oceanbank.webapp.common.util.CommonUtil;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oceanbank.webapp.dashboard.service.UserServiceImpl;

/**
 * The Class AdministrationController.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@Controller
public class AdministrationController {

	/** The logger. */
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	/** The userservice. */
	@Autowired
	private UserServiceImpl userservice;

	/** The message source. */
	@Autowired
	private MessageSource messageSource;

	
	
	/**
	 * Show administration page.
	 *
	 * @param model the model
	 * @param locale the locale
	 * @return the string
	 */
	@RequestMapping(value = DashboardConstant.SHOW_ADMIN_PAGE, method = RequestMethod.GET)
	public String showAdministrationPage(Model model) {

        final String pageTitle = "Administration";

		model.addAttribute("title1",pageTitle);
		model.addAttribute("userDatatableUrl", DashboardConstant.GET_USER_BY_DATATABLE_JSON);
		model.addAttribute("updateModalPageUrl", DashboardConstant.SHOW_UPDATE_MODAL_PAGE);
		model.addAttribute("newModalPageUrl", DashboardConstant.SHOW_NEW_MODAL_PAGE);
		model.addAttribute("deleteUserUrl", DashboardConstant.DELETE_USER_DATATABLE);

		return DashboardConstant.TILES_ADMINISTRATION_TEMPLATE;
	}
	
	/**
	 * Show user create modal page.
	 *
	 * @param model the model
	 * @param locale the locale
	 * @return the string
	 */
	@RequestMapping(value = DashboardConstant.SHOW_NEW_MODAL_PAGE, method = RequestMethod.GET)
	public String showUserCreateModalPage(Model model, Locale locale) {
		
		// setup label locale
		final String pageTitle = messageSource.getMessage("Page.title.register", null, locale);
		final String email1 = messageSource.getMessage("label.user.email", null, locale);
		final String password1 = messageSource.getMessage("label.user.password", null, locale);
		final String matchPassword1 = messageSource.getMessage("label.user.confirmPass", null, locale);
		final String role1 = messageSource.getMessage("label.user.role", null, locale);
		
		model.addAttribute("title1", pageTitle);
		model.addAttribute("firstname1", "First Name");
		model.addAttribute("lastname1", "Last Name");
		model.addAttribute("username1", "Username");
		model.addAttribute("email1", email1);
		model.addAttribute("password1", password1);
		model.addAttribute("matchPassword1", matchPassword1);
		model.addAttribute("role1", role1);
		model.addAttribute("userBootstrapValidatorUrl", DashboardConstant.GET_USER_BY_BOOTSTRAP_VALIDATOR);
		model.addAttribute("userCreateDatatableUrl", DashboardConstant.CREATE_USER_DATATABLE);
		
		// pass the object by query on row_id
		final UserResponse userResponse = new UserResponse();
		model.addAttribute("user",userResponse);
		
		// pass an object with Roles List
		final ObDashboardRoles obRoles = userservice.getObDashboardRoles("test");
		model.addAttribute("obRoles",obRoles);
		
		LOGGER.info("Invoking Create User Modal Form");
		
		return DashboardConstant.SHOW_ADMIN_USER_CREATE_FORM;
	}
	
	/**
	 * Execute user create.
	 *
	 * @param response the response
	 * @return the user response
	 */
	@RequestMapping(value = DashboardConstant.CREATE_USER_DATATABLE, method = RequestMethod.POST)
	public @ResponseBody UserResponse executeUserCreate(@RequestBody UserResponse response) {
		// manage auditing
		response.setCreatedby(CommonUtil.getAuthenticatedUserDetails().getUsername());
		response.setModifiedby(CommonUtil.getAuthenticatedUserDetails().getUsername());
		
		// Assumed that the request has been validated by BootstrapValidator
		final UserResponse createdResponse = userservice.createUser(response);
		
		LOGGER.info("Returning Created User of " + createdResponse.getUsername());
		
		return createdResponse;
	}
	
	/**
	 * Execute user delete.
	 *
	 * @param rowId the row id
	 * @return the string
	 */
	@RequestMapping(value = DashboardConstant.DELETE_USER_DATATABLE, method = RequestMethod.DELETE)
	public @ResponseBody String executeUserDelete(@PathVariable("row_id") String rowId) {
		
		final String[] rowArr = rowId.split("_");
		final Integer user_id = Integer.parseInt(rowArr[1]);
		
		String result = null; 
		result = userservice.deleteUser(user_id);
		
		LOGGER.info(result);
		
		return result;
	}

	/**
	 * Show user update modal page.
	 *
	 * @param model the model
	 * @param locale the locale
	 * @param rowId the row id
	 * @return the string
	 */
	@RequestMapping(value = DashboardConstant.SHOW_UPDATE_MODAL_PAGE, method = RequestMethod.GET)
	public String showUserUpdateModalPage(Model model, Locale locale, @PathVariable("row_id") String rowId) {

		final String pageTitle = messageSource.getMessage("Page.title.register", null, locale);
		final String email1 = messageSource.getMessage("label.user.email", null, locale);
		final String password1 = messageSource.getMessage("label.user.password", null, locale);
		final String role1 = messageSource.getMessage("label.user.role", null, locale);
		
		// pass the Labels
		model.addAttribute("title1",pageTitle);
		model.addAttribute("firstname1", "First Name");
		model.addAttribute("lastname1", "Last Name");
		model.addAttribute("username1", "Username");
		model.addAttribute("email1",email1);
		model.addAttribute("password1",password1);
		model.addAttribute("role1", role1);
		model.addAttribute("userBootstrapValidatorUrl", DashboardConstant.GET_USER_BY_BOOTSTRAP_VALIDATOR_ORIGINAL);
		model.addAttribute("userUpdateDatatableUrl", DashboardConstant.UPDATE_USER_DATATABLE);
		
		
		// convert the rowid to user_id and associate User
		final String[] rowArr = rowId.split("_");
		final Integer user_id = Integer.parseInt(rowArr[1]);
		final UserResponse userResponse = userservice.findUserByUserid(user_id);
		model.addAttribute("user",userResponse);
		
		// pass an object with Roles List
		final ObDashboardRoles obRoles = userservice.getObDashboardRoles("test");
		model.addAttribute("obRoles",obRoles);
		
		
		return DashboardConstant.SHOW_ADMIN_USER_EDIT_FORM;
	}
	
	/**
	 * Execute user update.
	 *
	 * @param response the response
	 * @return the user response
	 */
	@RequestMapping(value = DashboardConstant.UPDATE_USER_DATATABLE, method = RequestMethod.POST)
	public @ResponseBody UserResponse executeUserUpdate(@RequestBody UserResponse response) {
		
		// manage auditing
		response.setModifiedby(CommonUtil.getAuthenticatedUserDetails().getUsername());
				
		final UserResponse updatedResponse = userservice.updateUser(response);
		
		LOGGER.info("Returning updated User of " + updatedResponse.getUsername());
		
		return updatedResponse;
	}
	
	/**
	 * Gets the user bootstrap validator exist.
	 *
	 * @param originalUsername the original username
	 * @param username the username
	 * @return the user bootstrap validator exist
	 */
	@RequestMapping(value= DashboardConstant.GET_USER_BY_BOOTSTRAP_VALIDATOR_ORIGINAL, method = RequestMethod.POST)
	public @ResponseBody BootstrapValidatorResponse getUserBootstrapValidatorExist(@PathVariable("originalUsername") String originalUsername, @RequestParam("username") String username) {
 
    	LOGGER.info("Searching for: " + username);
    	
    	final BootstrapValidatorResponse response = userservice.findUserByUsernameWithBootstrapValidatorOriginal(originalUsername, username);
    	
		return response;
	}

	/**
	 * Gets the user admin data tables.
	 *
	 * @param allRequestParams the all request params
	 * @return the user admin data tables
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	@RequestMapping(value = DashboardConstant.GET_USER_BY_DATATABLE_JSON, method = RequestMethod.GET)
	public @ResponseBody String getUserAdminDataTables(@RequestParam Map<String, String> allRequestParams)
			throws UnsupportedEncodingException {

		//CommonUtil.printParameterValues(allRequestParams);
		
		Integer pageNumber = 0;
		Integer pageLength = 0;
		Integer draw = 0;
		
		final String searchParameter = CommonUtil.determineValue(allRequestParams, "search[value]");
		pageNumber = Integer.valueOf(CommonUtil.determineValue(allRequestParams, "start"));
		pageLength = Integer.valueOf(CommonUtil.determineValue(allRequestParams, "length"));
		draw = Integer.valueOf(CommonUtil.determineValue(allRequestParams, "draw"));

		final DataTablesRequest datatableRequest = new DataTablesRequest();
		datatableRequest.setValue(searchParameter);
		datatableRequest.setStart(pageNumber);
		datatableRequest.setLength(pageLength);

		// retrieve the Users from parameters given
		final List<UserResponse> userResponseList = userservice.searchUserDataTable(datatableRequest);
		
		Integer recordsTotal = 0;
		recordsTotal = userResponseList.size();
		
		// transform to Json Object
		List<UserDataTableResponse> userDatatableList = new ArrayList<UserDataTableResponse>();
		
		for (UserResponse u : userResponseList) {
			final UserDataTableResponse dataTable = new UserDataTableResponse();
			dataTable.setUserId("row_" + u.getUserId());
			dataTable.setFirstname(u.getFirstname() == null ? "" : u.getFirstname());
			dataTable.setLastname(u.getLastname() == null ? "" : u.getLastname());
			dataTable.setUsername(u.getUsername());
			dataTable.setPassword(u.getPassword());
			dataTable.setEmail(u.getEmail());
			userDatatableList.add(dataTable);
		}

		// create the Datatable Response
		final DataTablesResponse<UserDataTableResponse> response = new DataTablesResponse<UserDataTableResponse>();
		response.setDraw(draw);
		response.setRecordsFiltered(recordsTotal);
		response.setRecordsTotal(pageLength);
		
		Integer second = 0;
		Integer total = 0;
		total = pageNumber + pageLength;
		second = total < recordsTotal ? total: recordsTotal;
		
		LOGGER.info("first " + pageNumber + " and second " + second + ".");
		
		userDatatableList = userDatatableList.subList(pageNumber, second);
		
		
		
		response.setData(userDatatableList);

		// clear the List container
		userDatatableList = null;

		// parse to Json Format
		final GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(DataTablesResponse.class, new GsonDataTableTypeAdapter<UserDataTableResponse>());
		
		// needs to be removed to improve performance
		//gsonBuilder.setPrettyPrinting(); 
		
		final Gson gson = gsonBuilder.create();
		final String json = gson.toJson(response);
		
		LOGGER.debug(json);
		
		return json;

	}
	
	
	
}
