package com.oceanbank.webapp.restoauth.service;

import java.util.List;

public interface As400SpService {
	
	String testLocalStoredProcedure(String schema);
	List<String> testResultSetStoredProcedure();
}
