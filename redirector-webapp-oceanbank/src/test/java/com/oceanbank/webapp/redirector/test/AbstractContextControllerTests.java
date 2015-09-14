package com.oceanbank.webapp.redirector.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

@WebAppConfiguration
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/mvc-servlet.xml", "classpath*:springcontext/mvc-spring.xml"})
public class AbstractContextControllerTests {

	@Autowired
	protected WebApplicationContext wac;

}
