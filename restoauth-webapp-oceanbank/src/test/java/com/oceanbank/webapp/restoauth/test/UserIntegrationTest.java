package com.oceanbank.webapp.restoauth.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.oceanbank.webapp.common.model.UserResponse;
import com.oceanbank.webapp.restoauth.converter.UserConverter;
import com.oceanbank.webapp.restoauth.model.DashboardRole;
import com.oceanbank.webapp.restoauth.model.DashboardUser;
import com.oceanbank.webapp.restoauth.service.RoleServiceImpl;
import com.oceanbank.webapp.restoauth.service.UserServiceImpl;


@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"file:src/main/webapp/WEB-INF/mvc-servlet.xml", "file:src/main/resources/springcontext/test-jdbc-security-spring.xml",
		"file:src/main/resources/springcontext/as400-datajpa-spring.xml" })
public class UserIntegrationTest {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserServiceImpl userservice;
	
	@Autowired
	private RoleServiceImpl roleservice;
	
	@Test
	public void test_the_one(){
		LOGGER.info("Start service here ...");
		
		List<DashboardUser> userList = userservice.findAllUsers();
		
		Integer size = userList.size();
		
		LOGGER.info("The number of users are " + size);
	}
	
	@Test
	public void test_update_user(){
		LOGGER.info("Create User ...");
		
		// get a User entity
		// convert entity to bean
		DashboardUser entity = userservice.findAllUsers().get(0);
		List<DashboardRole> entityRoles = new ArrayList<DashboardRole>();
		entityRoles = roleservice.getRoleByUserId(entity.getUserId());
		entity.setRoleses(entityRoles);
		
		// update entity to have List<Roles> because its no picked up or Lazily loaded
		UserResponse response = UserConverter.convertFromEntity(entity);
		response.setFirstname("Marinella");
		
		DashboardUser u = userservice.updateUser(response);
		LOGGER.info(u.getRoleses().get(0).getRoleName());
		LOGGER.info("The User saved is " + u.getFirstname());
	}
	
	@Test
	public void test_create_user(){
		LOGGER.info("Create User ...");
		
		UserResponse request = new UserResponse();
		request.setFirstname("Marinell");
		request.setLastname("Medina");
		request.setUsername("nell");
		request.setPassword("medina");
		request.setEmail("medinama@ph.ibm.com");
		request.setRoleNames(new String[]{"Tester", "Administrator"});
		
		request = userservice.createUser(request);
		
		LOGGER.info("The User saved is " + request.getFirstname());
	}

}
