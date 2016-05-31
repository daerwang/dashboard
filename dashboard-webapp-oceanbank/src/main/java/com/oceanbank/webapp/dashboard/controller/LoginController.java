/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.dashboard.controller;

import java.util.Locale;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oceanbank.webapp.common.model.DashboardConstant;
import com.oceanbank.webapp.common.model.OauthTokenBean;
import com.oceanbank.webapp.common.model.RestOauthAccessToken;
import com.oceanbank.webapp.common.model.UserResponse;
import com.oceanbank.webapp.dashboard.service.EmailService;
import com.oceanbank.webapp.dashboard.service.UserServiceImpl;


@Controller
public class LoginController extends OauthTokenBean{
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private RestOauthAccessToken oauthAccessToken;
	
	@Autowired
	private UserServiceImpl userService;
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@RequestMapping(value = "/login/keep-alive", method = RequestMethod.POST)
	public @ResponseBody String keepAlive() {
		LOGGER.info("session keep alive");
		return "alive";	
	}
	
	
	
	
	@RequestMapping(value = "/login/changeForgotPassword/{activation}", method = RequestMethod.GET)
	public String changeForgotPassword(Model model, @PathVariable String activation) {
		
		model.addAttribute("title1", "Change Forgot Password");
		model.addAttribute("activation", activation);
		
		return "tiles_changeForgotPassword";	
	}
	
	@RequestMapping(value = "/login/getApiToken", method = RequestMethod.GET)
	public @ResponseBody RestOauthAccessToken getApiToken() {

		return oauthAccessToken;
	}
	
	@RequestMapping(value = "/login/sendActivationLinkEmail/{username}", method = RequestMethod.GET)
	public @ResponseBody String sendActivationLinkEmail(HttpServletRequest request, @PathVariable String username) throws MessagingException {
		
		UserResponse user = userService.findUserByUsername(username);

		String from = "dashboard@oceanbank.com";
		String to = user.getEmail();
		String subject = "Dashboard - Password Activation Link";
		
		String activationUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() 
				+ "/login/changeForgotPassword/" + user.getResetToken();
		String message = activationUrl;
		System.out.println("it is " + activationUrl);
		EmailService emailService = new EmailService(from, to, subject, message);
		emailService.sendEmail();
		
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
