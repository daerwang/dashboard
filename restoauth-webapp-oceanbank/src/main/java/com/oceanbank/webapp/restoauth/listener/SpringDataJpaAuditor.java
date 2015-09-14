/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.restoauth.listener;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.oceanbank.webapp.common.model.DashboardConstant;
import com.oceanbank.webapp.restoauth.model.ObRestUserDetails;

/**
 * The Class SpringDataJpaAuditor.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@Component(value = "springDataJpaAuditor")
public class SpringDataJpaAuditor implements AuditorAware<String> {

	/* (non-Javadoc)
	 * @see org.springframework.data.domain.AuditorAware#getCurrentAuditor()
	 */
	@Override
	public String getCurrentAuditor() {
		final Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		
		if (authentication == null || !authentication.isAuthenticated()) {
			return null;
		}
		
		String username = null;
		
		// if role is ROLE_ANONYMOUS then auditor will be defaulted to sysadmin
		for (GrantedAuthority auth : authentication.getAuthorities()) {
            if (DashboardConstant.ROLE_ANONYMOUS.equals(auth.getAuthority())){
            	username = "sysadmin";
    			return username;
            }
            if(DashboardConstant.ROLE_DEV.equals(auth.getAuthority())){
            	username = "testadmin";
    			return username;
            }
            // otherwise, just return the the authenticated name
            return auth.getAuthority();
        }
		
		final UserDetails userdetail = (ObRestUserDetails) authentication.getPrincipal();
		
		return userdetail.getUsername();
	}

}
