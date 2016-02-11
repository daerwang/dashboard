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
import org.springframework.test.context.web.WebAppConfiguration;

import com.oceanbank.webapp.restoauth.dao.W8BeneFormDao;
import com.oceanbank.webapp.restoauth.model.W8BeneForm;
import com.oceanbank.webapp.restoauth.service.PdfFormWriter;
import com.oceanbank.webapp.restoauth.service.W8BeneFormPdfWriter;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { 
		"file:src/main/webapp/WEB-INF/mvc-servlet.xml", "file:src/main/resources/springcontext/mysql-jdbc-security-spring.xml",
		"file:src/main/resources/springcontext/datajpa-spring.xml"})
public class W8BeneFormIntegrationTest {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	private static String W8BENEFORM_CLASSPATH = "pdf/W8BeneForm_Empty.pdf";
	private static String W8BENEFORM_INDIVIDUAL_DIRECTORY = "C://dashboard//w8beneform//individual";
	private static String W8BENEFORM_MERGE_DIRECTORY = "C://dashboard//w8beneform//merge";
	private static String W8BENEFORM_TEMP_DIRECTORY = "C://dashboard//w8beneform//temp";
	private static String MERGE_PDF_NAME = "W8BeneForm_merged.pdf";
	
	@Autowired
	private W8BeneFormDao w8BeneFormDao;
	
	
	
	@Test
	public void test_w8beneform_db_connection(){
		long count = w8BeneFormDao.count();

		LOGGER.info("It is " + count);
	}
	
	@Test
	public void test_w8beneform_create_individual_pdf(){
		//long count = w8BeneFormDao.count();
		List<W8BeneForm> forms = new ArrayList<W8BeneForm>();
		forms = w8BeneFormDao.findAll();
		PdfFormWriter pdfFormWriter = new W8BeneFormPdfWriter();
		pdfFormWriter.writeToTemplate(W8BENEFORM_CLASSPATH, W8BENEFORM_INDIVIDUAL_DIRECTORY, forms);
		//pdfFormWriter.writeToClearPaper(W8BENEFORM_CLASSPATH, W8BENEFORM_INDIVIDUAL_DIRECTORY, forms);
		//pdfFormWriter.mergePdf(W8BENEFORM_INDIVIDUAL_DIRECTORY, W8BENEFORM_MERGE_DIRECTORY, W8BENEFORM_TEMP_DIRECTORY, MERGE_PDF_NAME);
	}
	
	@Test
	public void test_w8beneform_create_individual_pdf_single(){
		//long count = w8BeneFormDao.count();
		W8BeneForm form = new W8BeneForm();
		form = w8BeneFormDao.findOne(3);
		List<W8BeneForm> forms = new ArrayList<W8BeneForm>();
		forms.add(form);
		PdfFormWriter pdfFormWriter = new W8BeneFormPdfWriter();
		pdfFormWriter.writeToTemplate(W8BENEFORM_CLASSPATH, W8BENEFORM_INDIVIDUAL_DIRECTORY, forms);
		//pdfFormWriter.writeToClearPaper(W8BENEFORM_CLASSPATH, W8BENEFORM_INDIVIDUAL_DIRECTORY, forms);
		//pdfFormWriter.mergePdf(W8BENEFORM_INDIVIDUAL_DIRECTORY, W8BENEFORM_MERGE_DIRECTORY, W8BENEFORM_TEMP_DIRECTORY, MERGE_PDF_NAME);
	}
}
