package com.oceanbank.webapp.restoauth.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.oceanbank.webapp.common.exception.DashboardException;
import com.oceanbank.webapp.common.model.IrsFormSelected;
import com.oceanbank.webapp.common.model.W8BeneFormResponse;
import com.oceanbank.webapp.common.util.RestUtil;
import com.oceanbank.webapp.restoauth.converter.DashboardConverter;
import com.oceanbank.webapp.restoauth.converter.W8BeneFormConverter;
import com.oceanbank.webapp.restoauth.dao.W8BeneFormDao;
import com.oceanbank.webapp.restoauth.model.W8BeneForm;

@Service
public class W8BeneFormService {
	
	@Autowired
	private W8BeneFormDao w8BeneFormDao;
	
	private DashboardConverter<W8BeneForm, W8BeneFormResponse> converter = new W8BeneFormConverter();
	
	//private static String W8BENEFORM_CLASSPATH = "pdf/W8BeneForm_Empty.pdf";
	private static String W8BENEFORM_INDIVIDUAL_DIRECTORY = "C://dashboard//w8beneform//individual";
	private static String W8BENEFORM_MERGE_DIRECTORY = "C://dashboard//w8beneform//merge";
	private static String W8BENEFORM_TEMP_DIRECTORY = "C://dashboard//w8beneform//temp";
	private static String MERGE_PDF_NAME = "W8BeneForm_merged.pdf";
	
	public void createPdfToDisk(List<W8BeneForm> forms, String templateFilePath){
		PdfFormWriter pdfFormWriter = new W8BeneFormPdfWriter();
		//pdfFormWriter.writeToTemplate(W8BENEFORM_CLASSPATH, W8BENEFORM_INDIVIDUAL_DIRECTORY, forms);
		pdfFormWriter.writeToTemplate(templateFilePath, W8BENEFORM_INDIVIDUAL_DIRECTORY, forms);
		//pdfFormWriter.writeToClearPaper(W8BENEFORM_CLASSPATH, W8BENEFORM_INDIVIDUAL_DIRECTORY, forms);
		pdfFormWriter.mergePdf(W8BENEFORM_INDIVIDUAL_DIRECTORY, W8BENEFORM_MERGE_DIRECTORY, W8BENEFORM_TEMP_DIRECTORY, MERGE_PDF_NAME);
	}
	
	public List<W8BeneForm> findByIds(IrsFormSelected selected) throws DashboardException{
		final String[] selectedId = selected.getSelected();
		final List<String> ids = Arrays.asList(selectedId);
		final List<Integer> intIds = new ArrayList<Integer>(); 
		for(String s : ids){
			try {
				intIds.add(Integer.parseInt(s));
			} catch (Exception e) {
			}
		}
		List<W8BeneForm> list = null;
		try {
			list = w8BeneFormDao.findByEntityIds(intIds);
		} catch (Exception e) {
			throw new DashboardException(
					"The findByIds() failed with an Exception.", e);
		}
		

		return list;
	}
	
	public List<W8BeneFormResponse> findByDatatableSearch(String search){
		final List<W8BeneFormResponse> list = new ArrayList<W8BeneFormResponse>();
		List<W8BeneForm> entityList = new ArrayList<W8BeneForm>();

		if (!RestUtil.isNullOrEmpty(search)){
			entityList = w8BeneFormDao.findByDatatableSearch("%" + search + "%");
		} else {
			final Page<W8BeneForm> data = w8BeneFormDao.findAll(new PageRequest(0, 200));
			entityList = data.getContent();
		}
			
		for (W8BeneForm entity : entityList) {
			
			final W8BeneFormResponse response = converter.convertFromEntity(entity);
			list.add(response);
		}


		return list;
	}

	public File getFile(String filePath){
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(filePath).getFile());
		
		return file;
	}
	
	public void clearDirectory(String directory){
		final File file = new File(directory);

		if (file.isDirectory()) {
			try {
				FileUtils.cleanDirectory(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void insertW8BeneForms(List<W8BeneFormResponse> responseList){
		List<W8BeneForm> entityList = new ArrayList<W8BeneForm>();
		for (W8BeneFormResponse response : responseList) {			
			W8BeneForm entity = converter.convertFromBean(response);
			entityList.add(entity);
		}
		w8BeneFormDao.save(entityList);

	}
	
	public void delete(Integer id){
		w8BeneFormDao.delete(id);
	}
	
	public void deleteAll(){
		w8BeneFormDao.deleteAll();
	}
}
