package com.oceanbank.webapp.restoauth.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oceanbank.webapp.restoauth.model.UserAttempt;

public interface UserAttemptDao extends JpaRepository<UserAttempt, Long>{

	UserAttempt findByUsername(String username);
}
