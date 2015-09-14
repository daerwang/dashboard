package com.oceanbank.webapp.restoauth.test;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.oceanbank.webapp.common.exception.DashboardException;
import com.oceanbank.webapp.common.model.IrsFormSelected;
import com.oceanbank.webapp.restoauth.model.BankDetail;
import com.oceanbank.webapp.restoauth.model.IrsFormCustomer;
import com.oceanbank.webapp.restoauth.model.MailCodeDetail;
import com.oceanbank.webapp.restoauth.service.IrsFormServiceImpl;
import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;

/**
 * Sample Oauth2 request and refresh token	
 * 
 * request a token
 * http://localhost:8085/restoauth-webapp-oceanbank/oauth/token?grant_type=password&client_id=devaccess&client_secret=oceanbank&username=oceandev&password=ocean@123
 * 
 * refresh a token
 * http://localhost:8085/restoauth-webapp-oceanbank/oauth/token?grant_type=refresh_token&client_id=devaccess&client_secret=oceanbank&refresh_token=7ac7940a-d29d-4a4c-9a47-25a2167c8c49
 * 
 */

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"file:src/main/webapp/WEB-INF/mvc-servlet.xml", "file:src/main/resources/springcontext/local-jdbc-security-spring.xml",
		"file:src/main/resources/springcontext/as400-datajpa-spring.xml"})
public class IrsFormIntegrationTest {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IrsFormServiceImpl irsFormCustomer;
	
	@Autowired
    private SessionFactory sessionFactory;
	
	private Session session;
	
	@Before
	public void before() {
        session = sessionFactory.openSession();

        session.beginTransaction();

    }
	
	@After
    public void after() {
        session.getTransaction().commit();
        session.close();
    }
	
	@Test
	public void test_mail_code_detail(){
		List<MailCodeDetail> list = irsFormCustomer.getAllMailCodeDetails();
		for(MailCodeDetail b: list){
			LOGGER.info("It is " + b.getMailDescription());
		}
	}
	
	@Test
	public void test_find_mail_code_detail(){
		String code = "";
		MailCodeDetail bean = irsFormCustomer.getMailCodeByCode(code);

		LOGGER.info("It is " + bean.getMailDescription());
	}
	
	
	@Test
	public void test_mail_code_by_code(){
		
		List<String> codes = new ArrayList<String>();
		codes.add("Z");
		codes.add("");
		
		List<IrsFormCustomer> list = irsFormCustomer.findByMailCode(codes);
		LOGGER.info("count is " + list.size());
	}
	
	@Test
	public void test_distinct_mail_code(){
		List<String> list = irsFormCustomer.findByMailCodeDistinct();
		for(String s: list){
			LOGGER.info(s);
		}
	}
	
	@Test
	public void test_date_of_birth() throws DashboardException{

		IrsFormSelected selected = new IrsFormSelected();
		selected.setSelected(new String[]{"GIUSEPPINA ALLOCCA ANNUNZIATA,526494408,592237280,239.84"});
		List<IrsFormCustomer> clist = irsFormCustomer.getIrsFormCustomerBySelected(selected);
		IrsFormCustomer entity = clist.get(0);
		String dob = entity.getFld_20();
		
		StringBuilder b = new StringBuilder();
		String mon = dob.length() > 5 ? dob.substring(0, 2) : dob.substring(0, 1);
		b.append(mon);
		b.append("/");
		b.append(dob.substring(dob.length() - 4, dob.length() - 2));
		b.append("/");
		b.append(dob.substring(dob.length() - 2, dob.length()));
		
		LOGGER.info("Number is " + b.toString());
	}
	
	@Test
	public void test_parse_zip_code(){
		String phone = irsFormCustomer.findBankDetailByName("Ocean Bank").getFld_14ef_3();
		
		StringBuilder b = new StringBuilder();
		b.append(phone.substring(0, 5));
		b.append("-");
		b.append(phone.substring(5, 9));
		
		LOGGER.info("Number is " + b.toString());
	}
	
	@Test
	public void test_parse_phone_number(){
		String phone = irsFormCustomer.findBankDetailByName("Ocean Bank").getFld_13a_2();
		
		StringBuilder b = new StringBuilder();
		b.append("(");
		b.append(phone.substring(0, 3));
		b.append(") ");
		b.append(phone.substring(3, 6));
		b.append("-");
		b.append(phone.substring(6, 10));
		
		LOGGER.info("Number is " + b.toString());
	}
	
	@Test
	public void test_bank_detail(){
		List<BankDetail> list = irsFormCustomer.getAllBankDetails();
		for(BankDetail b: list){
			LOGGER.info("It is " + b.getFld_14e());
		}
	}
	
	@Test
	public void test_get_irs_customer_by_pagerequest(){
		List<IrsFormCustomer> customer = irsFormCustomer.getIrsCustomersByPage(new PageRequest(10, 20));
		
		LOGGER.info("It is " + customer.size());
	}
	
	@Test
	public void test_limit_paging_of_results_2(){
		Pageable pageable = new PageRequest(1, 50);
		List<IrsFormCustomer> list = irsFormCustomer.findCustomersByNameAndPageable("%ALICIA AAGAARD CELIS%", pageable);
		//List<IrsFormCustomer> list = irsFormCustomer.findCustomersByNameAndPageable("%ALICIA AAGAARD CELIS%");
		//List<IrsFormCustomer> list = irsFormCustomer.findByShortName("AAGAARDCELIS ALICI");
		//List<IrsFormCustomer> list = irsFormCustomer.getAllIrsFormCustomer();
		LOGGER.info("It is " + list.size());
	}
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void test_hibernate_native_query(){
		Query q = session.createQuery("select e from IrsFormCustomer e")
	            .setFirstResult(800)
	            .setMaxResults(850);
		
		List<IrsFormCustomer> list = (List<IrsFormCustomer>)q.list();
		LOGGER.info("It is " + list.size());
	}
	
}
