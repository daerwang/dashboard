package com.oceanbank.webapp.restoauth.service;

import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

import com.oceanbank.webapp.restoauth.model.IrsFormCoordinate;
import com.oceanbank.webapp.restoauth.model.W8BeneForm;

public interface PdfFormWriter {
	
	public void writeToTemplate(String templateFilePath, String individualDirectory, List<W8BeneForm> forms);
	public void writeToClearPaper();
	public void mergePdf(String individualDirectory, String mergeDirectory, String tempDirectory, String mergeFileName);
	public void draw(PDPageContentStream contentStream, List<IrsFormCoordinate> coordinates, PDFont font) throws IOException;
	public List<IrsFormCoordinate> setupFormCoordinates(W8BeneForm entity);
}
