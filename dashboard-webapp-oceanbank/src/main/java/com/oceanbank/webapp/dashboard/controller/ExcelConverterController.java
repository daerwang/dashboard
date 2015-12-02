/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.dashboard.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import ch.qos.logback.classic.Logger;

import com.oceanbank.webapp.common.exception.DashboardException;
import com.oceanbank.webapp.common.model.DashboardConstant;
import com.oceanbank.webapp.common.model.ExcelFileMeta;
import com.oceanbank.webapp.dashboard.service.AmlBatchServiceImpl;


@Controller
public class ExcelConverterController {

	@Autowired
	private AmlBatchServiceImpl amlBatchService;
	
	@RequestMapping(value = DashboardConstant.SHOW_EXCEL_CONVERTER_PAGE, method = RequestMethod.GET)
	public String showExcelConverterPage(Model model) {

		final String pageTitle = " Advisor File Converter";

		model.addAttribute("title1",pageTitle);
		model.addAttribute("uploadExcelModal", DashboardConstant.SHOW_UPLOAD_EXCEL_CONVERTER_MODAL);
		model.addAttribute("openTextFileNewWindow", DashboardConstant.OPEN_ADVISOR_TEXT_FILE_NEW_WINDOW);

		return DashboardConstant.TILES_EXCEL_CONVERTER_TEMPLATE;
	}
	
	@RequestMapping(value = DashboardConstant.SHOW_UPLOAD_EXCEL_CONVERTER_MODAL, method = RequestMethod.GET)
	public String showExcelConverterUploadModal(Model model) {
		
		model.addAttribute("title1", "Upload Excel File");
		model.addAttribute("executeUploadExcel", DashboardConstant.EXECUTE_UPLOAD_EXCEL_CONVERTER_MODAL);
		model.addAttribute("openTextFileNewWindow", DashboardConstant.OPEN_ADVISOR_TEXT_FILE_NEW_WINDOW);
		
		return DashboardConstant.SHOW_UPLOAD_EXCEL_CONVERTER_MODAL_TILES;
	}
	
	@RequestMapping(value = DashboardConstant.EXECUTE_UPLOAD_EXCEL_CONVERTER_MODAL, method = RequestMethod.POST)
	public @ResponseBody ExcelFileMeta executeExcelConverterUpload(MultipartHttpServletRequest request, @RequestParam Map<String, String> allRequestParams) throws DashboardException, IOException {

        Iterator<String> itr =  request.getFileNames();
        MultipartFile mpf = null;
        ExcelFileMeta fileMeta = null;
        
        final String fileName = itr.next();

    	mpf = request.getFile(fileName); 
    	// allow only less than 4MB excel file
    	if(mpf.getSize()/1024 > 4000){
        	throw new DashboardException("The upload should be less than 4MB in size.");
        }
    	// allow only xls or xlsx file name
    	String ext = FilenameUtils.getExtension(mpf.getOriginalFilename());
    	if(ext.equalsIgnoreCase("xls") || ext.equalsIgnoreCase("xlsx")){
    		// do nothing
    	}else{
    		throw new DashboardException("The upload should be an Excel file.");
    	}
    	
    	synchronized (this) {
    		amlBatchService.createExcelIntoTextFileToDisk(mpf);
		}
    	
        fileMeta = new ExcelFileMeta();
        fileMeta.setFileName(mpf.getOriginalFilename());
        fileMeta.setFileSize(mpf.getSize()/1024+" Kb - OK");
        fileMeta.setFileType(mpf.getContentType());

		return fileMeta;
	}
	
	@RequestMapping(value = DashboardConstant.OPEN_ADVISOR_TEXT_FILE_NEW_WINDOW, method = RequestMethod.GET)
	public void openAdvisoryTextFileFromServer(HttpServletResponse response) throws IOException, DashboardException{
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(new File(DashboardConstant.TXT_MERGING_INDIVIDUAL_FILE_LOCATION_FULL));
			 byte[] byteArr = null;
			 final ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		        int nRead;
		        final byte[] data = new byte[5000];
		        while ((nRead = is.read(data, 0, data.length)) != -1) {
		          buffer.write(data, 0, nRead);
		        }
		        buffer.flush();
		        byteArr = buffer.toByteArray();
		        
		        response.setContentType("text/plain; charset=UTF-8");
		        response.setContentLength(byteArr.length);
		        response.setHeader("Content-Disposition","attachment;filename=\"advisorConverted.txt\"");
		        
		        os = response.getOutputStream();
		        os.write(byteArr);
		    	
			} catch (Exception e) {
				throw new DashboardException("openAdvisoryTextFileFromServer error occured", e);
			}finally{
				os.flush();
		        os.close(); 
				if(is != null)
					is.close();
			}
	}
	
	private static Integer randInt(int min, int max) {

	    // Usually this can be a field rather than a method variable
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	}
