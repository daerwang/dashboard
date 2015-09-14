/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.restoauth.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;



/**
 * The Class ObRestUserDetails.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
public class ObRestUserDetails implements UserDetails{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new ob rest user details.
	 */
	public ObRestUserDetails(){}
	
	
	/** The username. */
	private String username;
	
	/** The password. */
	private String password;
	
	/** The authorities. */
	private Collection<? extends GrantedAuthority> authorities;
	
	/** The user. */
	private DashboardUser user;
	
	/** The is account non expired. */
	private Boolean isAccountNonExpired;
	
	/** The is account non locked. */
	private Boolean isAccountNonLocked;
	
	/** The is credentials non expired. */
	private Boolean isCredentialsNonExpired;
	
	/** The is enabled. */
	private Boolean isEnabled;
	
	/**
	 * Instantiates a new ob rest user details.
	 *
	 * @param user the user
	 * @param authorities the authorities
	 * @param isEnabled the is enabled
	 */
	public ObRestUserDetails(DashboardUser user, Collection<? extends GrantedAuthority> authorities, Boolean isEnabled){
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.setUser(user);
		
		this.authorities = authorities;
		this.isEnabled = isEnabled;
		
		this.isAccountNonExpired = true;
		this.isAccountNonLocked = true;
		this.isCredentialsNonExpired = true;
		
	}
	
	/**
	 * Instantiates a new ob rest user details.
	 *
	 * @param user the user
	 * @param authorities the authorities
	 * @param isEnabled the is enabled
	 * @param isAccountNonExpired the is account non expired
	 * @param isAccountNonLocked the is account non locked
	 * @param isCredentialsNonExpired the is credentials non expired
	 */
	public ObRestUserDetails(DashboardUser user, Collection<? extends GrantedAuthority> authorities
			, Boolean isEnabled
			, Boolean isAccountNonExpired
			, Boolean isAccountNonLocked
			, Boolean isCredentialsNonExpired){
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.setUser(user);
		
		this.authorities = authorities;
		this.isEnabled = isEnabled;
		this.isAccountNonExpired = isAccountNonExpired;
		this.isAccountNonLocked = isAccountNonLocked;
		this.isCredentialsNonExpired = isCredentialsNonExpired;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities()
	 */
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return authorities;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#getPassword()
	 */
	public String getPassword() {

		return password;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#getUsername()
	 */
	public String getUsername() {

		return username;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
	 */
	public boolean isAccountNonExpired() {

		return isAccountNonExpired;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
	 */
	public boolean isAccountNonLocked() {

		return isAccountNonLocked;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
	 */
	public boolean isCredentialsNonExpired() {

		return isCredentialsNonExpired;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isEnabled()
	 */
	public boolean isEnabled() {

		return isEnabled;
	}

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public DashboardUser getUser() {
		return user;
	}

	/**
	 * Sets the user.
	 *
	 * @param user the new user
	 */
	public void setUser(DashboardUser user) {
		this.user = user;
	}

}
