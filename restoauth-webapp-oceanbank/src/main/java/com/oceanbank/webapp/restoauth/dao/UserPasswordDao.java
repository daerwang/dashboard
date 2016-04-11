package com.oceanbank.webapp.restoauth.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oceanbank.webapp.restoauth.model.UserPassword;

public interface UserPasswordDao extends JpaRepository<UserPassword, Long>{

	
	List<UserPassword> findByUsernameOrderByCreatedonDesc(String username);
}
