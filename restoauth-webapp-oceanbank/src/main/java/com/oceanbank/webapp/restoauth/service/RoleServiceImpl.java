/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.restoauth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oceanbank.webapp.restoauth.dao.RoleRepository;
import com.oceanbank.webapp.restoauth.model.DashboardRole;

/**
 * The Class RoleServiceImpl.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@Service
public class RoleServiceImpl implements RoleService {

	/**
	 * Instantiates a new {@link RoleServiceImpl}.
	 */
	public RoleServiceImpl(){}
	
	/** The role repository. */
	@Autowired
	private RoleRepository roleRepository; 
	
	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.restoauth.service.RoleService#getRoleByUserId(java.lang.Integer)
	 */
	@Override
	public List<DashboardRole> getRoleByUserId(Integer user_id) {
		
		return roleRepository.findByUseridIs(user_id);
	}

}
