package com.oceanbank.webapp.restoauth.test;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.oceanbank.webapp.restoauth.model.DashboardUser;
import com.oceanbank.webapp.restoauth.service.UserServiceImpl;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"file:src/main/resources/springcontext/as400-datajpa-spring.xml"
		, "file:src/main/webapp/WEB-INF/mvc-servlet.xml"})
public class ServiceIntegrationTest {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserServiceImpl userservice;
	
	@Test
	public void test_select_in_database(){
		
		LOGGER.info("Start service here ...");
		
		List<DashboardUser> userList = userservice.findAllUsers();
		
		Integer size = userList.size();
		
		LOGGER.info("The number of users are " + size);
	}
	
	
}
