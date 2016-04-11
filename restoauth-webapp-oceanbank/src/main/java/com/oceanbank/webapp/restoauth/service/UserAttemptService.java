package com.oceanbank.webapp.restoauth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.oceanbank.webapp.restoauth.dao.UserAttemptDao;
import com.oceanbank.webapp.restoauth.dao.UserPasswordDao;
import com.oceanbank.webapp.restoauth.dao.UserRepository;
import com.oceanbank.webapp.restoauth.model.DashboardUser;
import com.oceanbank.webapp.restoauth.model.UserAttempt;
import com.oceanbank.webapp.restoauth.model.UserPassword;

@Service
public class UserAttemptService {
	
	@Autowired
	private UserAttemptDao userAttemptDao;
	
	@Autowired
	private UserRepository userDao;
	
	@Autowired
	private UserPasswordDao userPasswordDao;
	
	private static final Integer ALLOWED_FAILED_ATTEMPTS = 3; 
	
	public Boolean isPasswordDuplicate(String username, String rawPassword){
		Boolean isDuplicate = false;
		BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
		List<UserPassword> list = userPasswordDao.findByUsernameOrderByCreatedonDesc(username);
		if(list != null && list.size() > 0){
			int i = 0;
			for(UserPassword u : list){
				if(i < 5){
					isDuplicate = enc.matches(rawPassword, u.getPassword());
					if(isDuplicate)
						break;
				}
				i++;
			}
		}
		
		return isDuplicate;
	}
	
	public UserPassword saveUserPassword(String createdBy, String username, String password){
		
		UserPassword userPassword = new UserPassword();
		userPassword.setCreatedBy(createdBy);
		userPassword.setUsername(username);
		userPassword.setPassword(password);
		
		UserPassword up = userPasswordDao.save(userPassword);
		
		return up;
	}
	
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
