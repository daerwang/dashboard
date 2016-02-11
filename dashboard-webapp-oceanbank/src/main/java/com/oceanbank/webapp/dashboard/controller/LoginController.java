/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.dashboard.controller;

import com.oceanbank.webapp.common.handler.AjaxResponseHandler;
import com.oceanbank.webapp.common.model.ChangePassword;
import com.oceanbank.webapp.common.model.OauthTokenBean;
import com.oceanbank.webapp.common.model.DashboardConstant;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oceanbank.webapp.dashboard.service.UserServiceImpl;


/**
 * The Class LoginController.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@Controller
public class LoginController extends OauthTokenBean{
	
	/** The logger. */
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	/** The userservice. */
	@Autowired
	private UserServiceImpl userservice;
	
	/** The message source. */
	@Autowired
	private MessageSource messageSource;
	
	/**
	 * Show change password page.
	 *
	 * @param model the model
	 * @param locale the locale
	 * @return the string
	 */
	@RequestMapping(value = DashboardConstant.SHOW_CHANGE_PASSWORD_PAGE, method = RequestMethod.GET)
	public String showChangePasswordPage(Model model, Locale locale) {

        final String pageTitle = messageSource.getMessage("Page.title.changepassword", null, locale);
        final String matchPassword1 = messageSource.getMessage("label.user.confirmPass", null, locale);
        
        final String oldPassword1 = "Old Password";
        final String newPassword1 = "New Password";
        
        
		model.addAttribute("title1",pageTitle);
		model.addAttribute("username1", "Username");
		model.addAttribute("oldPassword1",oldPassword1);
		model.addAttribute("newPassword1",newPassword1);
		model.addAttribute("matchPassword1",matchPassword1);
		model.addAttribute("changePasswordUrl", DashboardConstant.EXECUTE_CHANGE_PASSWORD);
		model.addAttribute("loginPageUrl", DashboardConstant.SHOW_LOGIN_PAGE);

		final ChangePassword password = new ChangePassword();
		model.addAttribute("passwordObj",password);

		return DashboardConstant.TILES_CHANGE_PASSWORD_TEMPLATE;
	}
	
	/**
	 * Execute change password.
	 *
	 * @param password the password
	 * @return the ajax response handler
	 */
	@RequestMapping(value = DashboardConstant.EXECUTE_CHANGE_PASSWORD, method = RequestMethod.POST)
	public @ResponseBody AjaxResponseHandler executeChangePassword(@RequestBody ChangePassword password) {
		
		String messageResult = null;
		AjaxResponseHandler handler = userservice.changeUserPassword(password);
		
		// if not match, return error;
		if(handler == null){
			messageResult = "The username or password does not match.";
			handler = new AjaxResponseHandler(); 
			handler.setCode("ERROR");
			handler.setMessage(messageResult);
		}
		
		
		return handler;
	}
	
	
	/**
	 * Show login page.
	 *
	 * @param model the model
	 * @param locale the locale
	 * @return the string
	 */
	@RequestMapping({ "/", DashboardConstant.SHOW_LOGIN_PAGE })
	public String showLoginPage(Model model, Locale locale) {
		
		LOGGER.info("Showing the Dashboard Login page.");
		LOGGER.info("REST service: " + getRestApi());
		
		final String pageTitle = messageSource.getMessage("Page.title.login", null, locale);
		final String changePassword1 = messageSource.getMessage("Page.title.changepassword", null, locale);
		
		model.addAttribute("title1",pageTitle);
		model.addAttribute("changePassword1",changePassword1);
		model.addAttribute("changePasswordUrl", DashboardConstant.SHOW_CHANGE_PASSWORD_PAGE);
		model.addAttribute("username1", "Username");
		model.addAttribute("password1", "Password");
		
		
		return DashboardConstant.TILES_LOGIN_TEMPLATE;
	}
	
	
	/**
	 * Show home page.
	 *
	 * @param model the model
	 * @param locale the locale
	 * @return the string
	 */
	@RequestMapping(value = DashboardConstant.SHOW_HOME_PAGE, method = RequestMethod.GET)
	public String showHomePage(Model model, Locale locale) {
		
		final String pageTitle = messageSource.getMessage("Page.title.home", null, locale);
		model.addAttribute("title1",pageTitle);
		
		model.addAttribute("administrationUrl", DashboardConstant.SHOW_ADMIN_PAGE);
		model.addAttribute("kycBatchApprovalUrl", DashboardConstant.SHOW_AML_BATCH_PAGE);
		model.addAttribute("irs1042sFormUrl", DashboardConstant.SHOW_IRS_FORM_PAGE);
		model.addAttribute("excelConverterUrl", DashboardConstant.SHOW_EXCEL_CONVERTER_PAGE);
		model.addAttribute("w8BeneFormUrl", "/w8beneform");

		return DashboardConstant.TILES_HOME_TEMPLATE;	
	}
	
	/**
	 * Show403 error page.
	 *
	 * @param model the model
	 * @param locale the locale
	 * @return the string
	 */
	@RequestMapping(value = DashboardConstant.SHOW_403_ERROR_PAGE, method = RequestMethod.GET)
	public String show403ErrorPage(Model model) {
		
		final String pageTitle = "403 Not Authorized";
		model.addAttribute("title1",pageTitle);
		
		model.addAttribute("homeUrl", DashboardConstant.SHOW_HOME_PAGE);
		
		return DashboardConstant.TILES_403_ERROR_TEMPLATE;	
	}
	
	
	
}
