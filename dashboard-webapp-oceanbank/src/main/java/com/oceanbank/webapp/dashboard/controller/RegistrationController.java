/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.dashboard.controller;

import com.oceanbank.webapp.common.model.BootstrapValidatorResponse;
import com.oceanbank.webapp.common.model.DashboardConstant;
import com.oceanbank.webapp.common.model.UserRequest;
import com.oceanbank.webapp.common.model.UserResponse;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oceanbank.webapp.dashboard.service.UserServiceImpl;

/**
 * The Class RegistrationController.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@Controller
public class RegistrationController {
	
	/** The logger. */
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	
	/** The message source. */
	@Autowired
	private MessageSource messageSource;
	
	/** The userservice. */
	@Autowired
	private UserServiceImpl userservice;
	
	
	/**
	 * Show registration form.
	 *
	 * @param model the model
	 * @param locale the locale
	 * @return the string
	 */
	@RequestMapping(value = "/user/registration", method = RequestMethod.GET)
    public String showRegistrationForm(Model model, Locale locale) {
        
		LOGGER.info("Rendering registration page.");
        
        final UserRequest userRequest = new UserRequest();
        model.addAttribute("userRequest", userRequest);
        
        final String pageTitle = messageSource.getMessage("Page.title.register", null, locale);
        final String email1 = messageSource.getMessage("label.user.email", null, locale);
        final String password1 = messageSource.getMessage("label.user.password", null, locale);
        final String matchPassword1 = messageSource.getMessage("label.user.confirmPass", null, locale);
        final String welcome1 = messageSource.getMessage("Page.header.welcome", null, locale);
		
		model.addAttribute("title1",pageTitle);
		model.addAttribute("email1",email1);
		model.addAttribute("password1",password1);
		model.addAttribute("matchPassword1",matchPassword1);
		model.addAttribute("welcome1",welcome1);
		
		model.addAttribute("webServiceUrl", DashboardConstant.GET_USER_BY_BOOTSTRAP_VALIDATOR);
		
        return "tiles_registration";
    }

    /**
     * Register user account.
     *
     * @param request the request
     * @param result the result
     * @param locale the locale
     * @return the string
     */
    @RequestMapping(value = "/user/registration", method = RequestMethod.POST)
    public String registerUserAccount(@ModelAttribute("userRequest") UserResponse request, BindingResult result) {

        if (result.hasErrors()) {
        	return "Object has validation errors";
        } 
        
        userservice.createUser(request);
        
    	return "redirect:/successRegistration";
    }
    
    /**
     * Success registration page.
     *
     * @param model the model
     * @param locale the locale
     * @return the string
     */
    @RequestMapping(value = "/successRegistration", method = RequestMethod.GET)
    public String successRegistrationPage(Model model, Locale locale) {
    	
    	final String pageTitle = messageSource.getMessage("Page.title.sucessregister", null, locale);
		model.addAttribute("title1",pageTitle);
		
        return "tiles_successRegister";
    }
    
    /**
     * Check username exist.
     *
     * @param username the username
     * @return the bootstrap validator response
     */
    @RequestMapping(value= DashboardConstant.GET_USER_BY_BOOTSTRAP_VALIDATOR, method = RequestMethod.POST)
	public @ResponseBody BootstrapValidatorResponse checkUsernameExist(@RequestParam("username") String username) {
 
    	LOGGER.info("Searching for: " + username);
    	
    	final BootstrapValidatorResponse response = userservice.findUserByUsernameWithBootstrapValidator(username);
    	
    	
		return response;
 
	}

}
