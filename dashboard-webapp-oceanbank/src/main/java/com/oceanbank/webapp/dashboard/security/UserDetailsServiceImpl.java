/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.dashboard.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oceanbank.webapp.common.model.DashboardConstant;
import com.oceanbank.webapp.common.model.RoleResponse;
import com.oceanbank.webapp.common.model.UserResponse;
import com.oceanbank.webapp.dashboard.service.UserServiceImpl;

/**
 * The Class ObAuthenticationProvider.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private UserServiceImpl userservice;
	

	public UserDetailsServiceImpl(){}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserResponse userResponse = userservice.findUserByUsername(username);
		
		userservice.validateAccountExpiry(userResponse);

		if(userResponse == null){
			throw new UsernameNotFoundException("Username not found"); 
		} 
		
		boolean enabled = true;
		boolean accountNonExpired = userResponse.getAccountNonExpired() == 0 ? false : true;
		boolean accountNonLocked = userResponse.getAccountNonLocked() == 0 ? false : true;
		boolean credentialsNonExpired = true;
		
		User springUser = new User(userResponse.getUsername(), userResponse.getPassword(), 
                enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, getGrantedAuthorities(userResponse));

		
		return springUser;
	}
	
	
	private List<GrantedAuthority> getGrantedAuthorities(UserResponse userResponse){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        List<RoleResponse> roleResponseList = userResponse.getRoleses();
        
        if(roleResponseList != null && roleResponseList.size() > 0){
        	for(RoleResponse roleResponse : roleResponseList){
				authorities.add(new SimpleGrantedAuthority(roleResponse.getRoleName()));
			}
        }else{
        	authorities.add(new SimpleGrantedAuthority(DashboardConstant.ROLE_GUEST));
        }
         
        return authorities;
    }



}
