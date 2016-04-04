package com.oceanbank.webapp.common.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.oceanbank.webapp.common.model.Users;


public class UserDetailsImpl implements UserDetails{

	private static final long serialVersionUID = 1L;
	
	public UserDetailsImpl(){}


	private String username;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	private Users user;
	
	private Boolean isAccountNonExpired;
	private Boolean isAccountNonLocked;
	private Boolean isCredentialsNonExpired;
	private Boolean isEnabled;
	

	public UserDetailsImpl(Users user, Collection<? extends GrantedAuthority> authorities, Boolean isEnabled){
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.setUser(user);
		
		this.authorities = authorities;
		this.isEnabled = isEnabled;
		
		this.isAccountNonExpired = true;
		this.isAccountNonLocked = true;
		this.isCredentialsNonExpired = true;
		
	}
	
	public UserDetailsImpl(Users user
			, Collection<? extends GrantedAuthority> authorities
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

	public Collection<? extends GrantedAuthority> getAuthorities() {

		return authorities;
	}

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
	public Users getUser() {
		return user;
	}

	/**
	 * Sets the user.
	 *
	 * @param user the new user
	 */
	public void setUser(Users user) {
		this.user = user;
	}

}
