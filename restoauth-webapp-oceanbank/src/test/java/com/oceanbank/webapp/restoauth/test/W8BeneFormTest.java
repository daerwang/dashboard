package com.oceanbank.webapp.restoauth.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oceanbank.webapp.common.util.CommonUtil;
import com.oceanbank.webapp.restoauth.model.W8BeneForm;
import com.oceanbank.webapp.restoauth.service.PdfFormWriter;
import com.oceanbank.webapp.restoauth.service.W8BeneFormPdfWriter;

@Ignore
public class W8BeneFormTest {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	private static String W8BENEFORM_CLASSPATH = "pdf/W8BeneForm_Empty.pdf";
	private static String W8BENEFORM_INDIVIDUAL_DIRECTORY = "C://dashboard//w8beneform//individual";
	private static String W8BENEFORM_MERGE_DIRECTORY = "C://dashboard//w8beneform//merge";
	private static String W8BENEFORM_TEMP_DIRECTORY = "C://dashboard//w8beneform//temp";
	private static String MERGE_PDF_NAME = "W8BeneForm_merged.pdf";
	
	@Test
	public void test_get_pdf_from_classpath(){
		
		String mergedFilePath = W8BENEFORM_MERGE_DIRECTORY + "//" + MERGE_PDF_NAME;
		PdfFormWriter pdfFormWriter = new W8BeneFormPdfWriter();
		List<W8BeneForm> forms = new ArrayList<W8BeneForm>();
		
		W8BeneForm form1 = new W8BeneForm();
		form1.setId(1);
		form1.setCif("VAA6181");
		form1.setName("VIAJES Y TURISMO ESMERALDA TOURS C A");
		W8BeneForm form2 = new W8BeneForm();
		form2.setId(2);
		form2.setCif("BAA0638");
		form2.setName("Jose Dela Castro");
		forms.add(form1);
		//forms.add(form2);
		
		pdfFormWriter.writeToTemplate(W8BENEFORM_CLASSPATH, W8BENEFORM_INDIVIDUAL_DIRECTORY, forms);
		pdfFormWriter.mergePdf(W8BENEFORM_INDIVIDUAL_DIRECTORY, W8BENEFORM_MERGE_DIRECTORY, W8BENEFORM_TEMP_DIRECTORY, MERGE_PDF_NAME);
		
		CommonUtil.openFile(mergedFilePath);
		
	}
	
	
}
