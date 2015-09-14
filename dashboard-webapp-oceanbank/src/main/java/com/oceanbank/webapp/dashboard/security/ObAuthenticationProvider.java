/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.dashboard.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oceanbank.webapp.common.model.AuthenticationUserDetails;
import com.oceanbank.webapp.common.model.DashboardConstant;
import com.oceanbank.webapp.common.model.RoleResponse;
import com.oceanbank.webapp.common.model.UserResponse;
import com.oceanbank.webapp.common.model.Users;
import com.oceanbank.webapp.dashboard.service.UserServiceImpl;

/**
 * The Class ObAuthenticationProvider.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@Service
@Transactional
public class ObAuthenticationProvider implements UserDetailsService{
	
	/** The userservice. */
	@Autowired
	private UserServiceImpl userservice;
	
	/**
	 * Instantiates a new ob authentication provider.
	 */
	public ObAuthenticationProvider(){}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserResponse userResponse = userservice.findUserByUsername(username);
		final Users user = new Users();
		
		AuthenticationUserDetails userdetail = null;
		final boolean enabled = true;
		final boolean accountNonExpired = true;
		final boolean accountNonLocked = true;
		final boolean credentialsNonExpired = true;
        
		
		if(userResponse != null){
			
			user.setUserId(userResponse.getUserId());
			user.setPassword(userResponse.getPassword());
			user.setUsername(userResponse.getUsername());
			user.setFirstname(userResponse.getFirstname());
			user.setLastname(userResponse.getLastname());
			user.setEmail(userResponse.getEmail());
			
			userdetail = new AuthenticationUserDetails(
					user,
					getAuthorities(userResponse),
					enabled,
					accountNonExpired,
					accountNonLocked,
					credentialsNonExpired);
		}else{
			userResponse = new UserResponse();
			userdetail = new AuthenticationUserDetails(user, getAuthorities(userResponse), true);
		}

		return userdetail;
	}
	
	
	/**
	 * Gets the authorities.
	 *
	 * @param userResponse the user response
	 * @return the authorities
	 */
	private Collection<GrantedAuthority> getAuthorities(UserResponse userResponse) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		if(userResponse == null){
			authorities = null;
			return authorities;
		}
		
		
		final List<RoleResponse> roleResponseList = userResponse.getRoleses();
		
		if(roleResponseList != null){
			for(RoleResponse roleResponse : roleResponseList){
				authorities.add(new SimpleGrantedAuthority(roleResponse.getRoleName()));
			}
		}else{
			
			authorities.add(new SimpleGrantedAuthority(DashboardConstant.ROLE_GUEST));
		}
		
		
        return authorities;
    }


}
