package com.oceanbank.webapp.dashboard.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.oceanbank.webapp.dashboard.service.UserServiceImpl;

public class LimitLoginAuthenticationProvider extends DaoAuthenticationProvider {
	
	@Autowired
	private UserServiceImpl userServiceImpl;


	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		try {

			Authentication auth = super.authenticate(authentication);

			userServiceImpl.resetFailedAttempt(authentication.getName());

			return auth;

		} catch (BadCredentialsException e) {

			userServiceImpl.updateFailedAttempt(authentication.getName());
			e.printStackTrace();
			throw e;

		} catch (LockedException e) {
			
			userServiceImpl.updateFailedAttempt(authentication.getName());
			
			e.printStackTrace();
			throw new LockedException("The User " + authentication.getName() + " is locked out.");
			
		}catch(InternalAuthenticationServiceException e){
			e.printStackTrace();
			throw e;
		}catch(AccountExpiredException e){
			e.printStackTrace();
			throw e;
		}catch(CredentialsExpiredException e){
			e.printStackTrace();
			throw e;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}

	}
}
