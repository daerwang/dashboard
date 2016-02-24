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
	
	public void test_template_repo(){
		LOGGER.info("start testing here...");

		// use dashboardupload table to monitor saved files on disk
	}

	@Test
	public void test_longer_address(){
		//String address = "CALLE CARIBBEAN C/C AV ANDRES ELOY BLANCO CC GRAVINA";
		String address = "CTERA PETARE SANTA LUCIA KM 01 STOR LA CORTADA MARICHE FTE AL CTRAL MADEIRENSE";
		//String address = "CALLE CARIBBEAN C/C AV ANDRES ELOY BLANCO CC GRAVINA II LOCAL 13 PLATA BAJA";
		//longest is 45
		int limit = 48;
		String[] arr = address.split(" ");
		StringBuilder line1 = new StringBuilder("");
		StringBuilder line2 = new StringBuilder("");
		Boolean isSecondNow = false;
		for(int i = 0; i < arr.length; i++){
			String next = line1.toString() + " " + arr[i]; 
			if(next.length() <= limit && !isSecondNow){
				line1.append(arr[i] + " ");
			}else{
				line2.append(arr[i] + " ");
				isSecondNow = true;
			}
		}
		System.out.println("length is - " + address.length());
		System.out.println("first line - " + line1.toString());
		System.out.println("second line - " + line2.toString());
	}
	
	@Test
	public void test_get_pdf_from_classpath(){
		
		String mergedFilePath = W8BENEFORM_MERGE_DIRECTORY + "//" + MERGE_PDF_NAME;
		PdfFormWriter pdfFormWriter = new W8BeneFormPdfWriter();
		List<W8BeneForm> forms = new ArrayList<W8BeneForm>();
		
		W8BeneForm form1 = new W8BeneForm();
		form1.setId(1);
		form1.setCif("VAA6181");
		form1.setName("VIAJES Y TURISMO ESMERALDA TOURS C A");
		form1.setPhysicalCountryInc("VENEZUELA");
		form1.setPhysicalAddress("AV 5 DE JULIO EDF DON JOSE LOCAL 1 ");
		form1.setPhysicalCity("PUERTO LA CRUZ EDO ANZOATEGUI");
		form1.setAltCountry("VENEZUELA");
		form1.setAltAddress("AV 5 DE JULIO EDF DON JOSE LOCAL 1 ");
		form1.setAltCity("PUERTO LA CRUZ EDO ANZOATEGUI");
		form1.setAccount("90230609");
		form1.setLabelName("VIAJES Y TURISMO ESMERALDA TOURS C A");
		form1.setOfficer("GA1");
		form1.setAltAddressLabel("AV 5 DE JULIO EDF DON JOSE LOCAL 1");
		form1.setAltCityLabel("PUERTO LA CRUZ EDO ANZOATEGUI");
		form1.setAltCountryLabel("VENEZUELA");
		
		W8BeneForm form2 = new W8BeneForm();
		form2.setId(2);
		form2.setCif("BAA0638");
		form2.setName("Jose Dela Castro");
		forms.add(form1);
		//forms.add(form2);
		
		//pdfFormWriter.writeToTemplate(W8BENEFORM_CLASSPATH, W8BENEFORM_INDIVIDUAL_DIRECTORY, forms);
		pdfFormWriter.writeToClearPaper(W8BENEFORM_CLASSPATH, W8BENEFORM_INDIVIDUAL_DIRECTORY, forms);
		pdfFormWriter.mergePdf(W8BENEFORM_INDIVIDUAL_DIRECTORY, W8BENEFORM_MERGE_DIRECTORY, W8BENEFORM_TEMP_DIRECTORY, MERGE_PDF_NAME);
		
		CommonUtil.openFile(mergedFilePath);
		
	}
	
	
}
