package com.oceanbank.webapp.redirector.test;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
public class RedirectorTest extends AbstractContextControllerTests{
	private MockMvc mockMvc;

	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(this.wac).alwaysExpect(status().isOk()).build();
	}
	
	@Test
	public void imageView() throws Exception {
		this.mockMvc.perform(get("/img")
				.param("acc", "906054505")
				.param("ser", "34183")
				.param("amo", "642.00")
				.param("dat", "10/01/2014"))
			.andDo(print())	
			.andExpect(view().name(containsString("redirectError")));
	}
	
	@Test
	public void imageViewWithVpn() throws Exception {
		this.mockMvc.perform(get("/img")
				.param("acc", "906054505")
				.param("ser", "34183")
				.param("amo", "642.00")
				.param("dat", "10/01/2014"))
			.andDo(print())	
			.andExpect(view().name(containsString("redirectSuccess")));
	}
}
