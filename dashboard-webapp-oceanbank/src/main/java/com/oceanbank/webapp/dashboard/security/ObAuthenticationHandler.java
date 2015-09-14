/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.dashboard.security;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.oceanbank.webapp.common.model.DashboardConstant;

/**
 * The Class ObAuthenticationHandler.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
public class ObAuthenticationHandler implements AuthenticationSuccessHandler{
	
	/** The logger. */
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	/** The redirect strategy. */
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	/* (non-Javadoc)
	 * @see org.springframework.security.web.authentication.AuthenticationSuccessHandler#onAuthenticationSuccess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

		handle(request, response, authentication);
		final HttpSession session = request.getSession(false);
        if (session != null) {
            session.setMaxInactiveInterval(30*105);
        }
        clearAuthenticationAttributes(request);
		
	}
	
	
	/**
	 * Handle.
	 *
	 * @param request the request
	 * @param response the response
	 * @param authentication the authentication
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
		final String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {
            LOGGER.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }
	
	/**
	 * Determine target url.
	 *
	 * @param authentication the authentication
	 * @return the string
	 */
	protected String determineTargetUrl(Authentication authentication) {

		final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals(DashboardConstant.ROLE_DEV) 
            		|| grantedAuthority.getAuthority().equals(DashboardConstant.USER_ROLE)) {
            	
            	//return HOME_PAGE;
            	
            } else if (grantedAuthority.getAuthority().equals(DashboardConstant.ROLE_SUPER_USER)) {
            	//return SHOW_ADMIN_PAGE;
            }
        }
        
        return DashboardConstant.SHOW_HOME_PAGE;
        
    }
	
	/**
	 * Clear authentication attributes.
	 *
	 * @param request the request
	 */
	protected void clearAuthenticationAttributes(HttpServletRequest request) {
		final HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    /**
     * Sets the redirect strategy.
     *
     * @param redirectStrategy the new redirect strategy
     */
    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    /**
     * Gets the redirect strategy.
     *
     * @return the redirect strategy
     */
    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

}
