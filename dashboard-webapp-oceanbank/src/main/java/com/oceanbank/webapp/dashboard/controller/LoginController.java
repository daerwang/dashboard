/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.dashboard.controller;

import java.util.Locale;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oceanbank.webapp.common.model.DashboardConstant;
import com.oceanbank.webapp.common.model.OauthTokenBean;
import com.oceanbank.webapp.common.model.RestOauthAccessToken;
import com.oceanbank.webapp.common.model.UserResponse;
import com.oceanbank.webapp.dashboard.service.EmailService;
import com.oceanbank.webapp.dashboard.service.UserServiceImpl;


/**
 * The Class LoginController.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@Controller
public class LoginController extends OauthTokenBean{

	@Autowired
	private UserServiceImpl userservice;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private RestOauthAccessToken oauthAccessToken;
	
	@RequestMapping(value = "/login/getApiToken", method = RequestMethod.GET)
	public @ResponseBody RestOauthAccessToken getApiToken() {

		return oauthAccessToken;
	}
	
	@RequestMapping(value = "/login/sendActivationLinkEmail/{emailAddress}", method = RequestMethod.GET)
	public @ResponseBody String sendActivationLinkEmail(HttpServletRequest request, @PathVariable String emailAddress) throws MessagingException {
		
		// send token to User via email
		String from = "mmedina@oceanbank.com";
		String to = emailAddress;
		String subject = "Forgot Password Activation Link";
		
		String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/changeForgotPassword";
		String message = appUrl;

		EmailService emailService = new EmailService(from, to, subject, message);
//		email.sendEmail();
		
		return "Email has been sent successfully.";	
	}
	
	@RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
	public String showForgotPassword(Model model) {
		
		model.addAttribute("title1", "Forgot Password");
		
		return "tiles_forgotUserPassword";	
	}
	
	@RequestMapping({ "/", "/login" })
	public String showLoginPage(Model model, Locale locale) {

		String changePassword1 = messageSource.getMessage("Page.title.changepassword", null, locale);
		
		model.addAttribute("title1", "Login");
		model.addAttribute("changePassword1",changePassword1);
		model.addAttribute("changePasswordUrl", DashboardConstant.SHOW_CHANGE_PASSWORD_PAGE);
		model.addAttribute("username1", "Username");
		model.addAttribute("password1", "Password");
		
		
		return DashboardConstant.TILES_LOGIN_TEMPLATE;
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String showHomePage(Model model, Locale locale) {
		
		model.addAttribute("title1","Dashboard");
		
		model.addAttribute("administrationUrl", DashboardConstant.SHOW_ADMIN_PAGE);
		model.addAttribute("kycBatchApprovalUrl", DashboardConstant.SHOW_AML_BATCH_PAGE);
		model.addAttribute("irs1042sFormUrl", DashboardConstant.SHOW_IRS_FORM_PAGE);
		model.addAttribute("excelConverterUrl", DashboardConstant.SHOW_EXCEL_CONVERTER_PAGE);
		model.addAttribute("w8BeneFormUrl", "/w8beneform");

		return DashboardConstant.TILES_HOME_TEMPLATE;	
	}
	
	@RequestMapping(value = DashboardConstant.SHOW_403_ERROR_PAGE, method = RequestMethod.GET)
	public String show403ErrorPage(Model model) {
		
		final String pageTitle = "403 Not Authorized";
		model.addAttribute("title1",pageTitle);
		
		model.addAttribute("homeUrl", DashboardConstant.SHOW_HOME_PAGE);
		
		return DashboardConstant.TILES_403_ERROR_TEMPLATE;	
	}
	
	
	
}
