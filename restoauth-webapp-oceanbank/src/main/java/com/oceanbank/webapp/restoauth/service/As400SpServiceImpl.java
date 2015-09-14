package com.oceanbank.webapp.restoauth.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.stereotype.Service;

@Service
public class As400SpServiceImpl implements As400SpService{

	
	@PersistenceContext
	EntityManager entityManagerFactory;
	
	@Override
	public String testLocalStoredProcedure(String schema) {
		StoredProcedureQuery proc = entityManagerFactory.createStoredProcedureQuery(schema + ".test_out_parameter");
		//proc.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
		proc.registerStoredProcedureParameter(2, String.class, ParameterMode.OUT);

		//proc.setParameter(1, 2);
		proc.execute();
		String result = proc.getOutputParameterValue(2) + "";
		
		return result;
	}

	@Override
	public List<String> testResultSetStoredProcedure() {


		
		return null;
	}

	

}
