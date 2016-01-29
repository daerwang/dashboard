package com.oceanbank.webapp.restoauth.service;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.oceanbank.webapp.common.exception.DashboardException;

public class W8BeneFormService {

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
	
	
}
