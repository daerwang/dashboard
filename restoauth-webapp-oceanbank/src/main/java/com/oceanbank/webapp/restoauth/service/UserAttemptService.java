package com.oceanbank.webapp.restoauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Service;

import com.oceanbank.webapp.restoauth.dao.UserAttemptDao;
import com.oceanbank.webapp.restoauth.dao.UserRepository;
import com.oceanbank.webapp.restoauth.model.DashboardUser;
import com.oceanbank.webapp.restoauth.model.UserAttempt;

@Service
public class UserAttemptService {
	
	@Autowired
	private UserAttemptDao userAttemptDao;
	
	@Autowired
	private UserRepository userDao;
	
	private static final Integer ALLOWED_FAILED_ATTEMPTS = 3; 
	
	public void updateFailedAttempt(String username){
		
		UserAttempt userAttempt = userAttemptDao.findByUsername(username.trim());
		DashboardUser user = userDao.findByUsername(username.trim());
		
		if(userAttempt != null){
			Integer attempts = userAttempt.getAttempt();
			userAttempt.setAttempt(attempts + 1);
			userAttempt.setModifiedBy(user.getUsername());
			if(attempts + 1 > ALLOWED_FAILED_ATTEMPTS){
				if(user.getAccountNonLocked() != 0){
					user.setAccountNonLocked(0);
					userDao.save(user);
				}
				userAttemptDao.save(userAttempt);
				
				throw new LockedException("User Account is locked!");
				
			}else{

				userAttemptDao.save(userAttempt);
			}
		}
		
		if(userAttempt == null){
			UserAttempt newUserAttempt = new UserAttempt();
			newUserAttempt.setModifiedBy(user.getUsername());
			newUserAttempt.setCreatedBy(user.getUsername());
			newUserAttempt.setAttempt(1);
			newUserAttempt.setUsername(user.getUsername());
			userAttemptDao.save(newUserAttempt);
		}
		
	}
	
	public void resetFailedAttempt(String username){
		UserAttempt userAttempt = userAttemptDao.findByUsername(username.trim());
		DashboardUser user = userDao.findByUsername(username.trim());
		
		if(userAttempt != null && user != null){
			userAttempt.setAttempt(0);
			userAttempt.setModifiedBy(user.getUsername());
			userAttemptDao.save(userAttempt);
			
			user.setAccountNonLocked(1);
			userDao.save(user);
		}
	}
}
