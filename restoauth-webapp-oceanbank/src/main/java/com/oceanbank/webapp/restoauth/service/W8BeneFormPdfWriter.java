package com.oceanbank.webapp.restoauth.service;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.util.PDFMergerUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oceanbank.webapp.restoauth.model.IrsFormCoordinate;
import com.oceanbank.webapp.restoauth.model.W8BeneForm;
import com.oceanbank.webapp.restoauth.model.W8BeneFormAddress;
import com.oceanbank.webapp.restoauth.model.W8BeneFormDirect;

public class W8BeneFormPdfWriter implements PdfFormWriter {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	private W8BeneFormService w8BeneFormService;
	
	public W8BeneFormPdfWriter(){
		this.w8BeneFormService = new W8BeneFormService();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void writeToTemplate(String templateFilePath, String individualDirectory, List<W8BeneForm> forms) {
		
		PDDocument document = null;
		String individualFilePath = null;
		PDFont font = PDType1Font.HELVETICA_BOLD;
		//File pdfFile = w8BeneFormService.getFile(templateFilePath);
		File pdfFile = new File(templateFilePath);
		w8BeneFormService.clearDirectory(individualDirectory);
		
		
		for(W8BeneForm f : forms){
			individualFilePath = individualDirectory + "//W8BeneForm_" + f.getId() + ".pdf";
			List<IrsFormCoordinate> coordinates = setupFormCoordinates(f);
			try {
				document = PDDocument.load(pdfFile);
				List<PDPage> pages = document.getDocumentCatalog().getAllPages();
				int i = 0;
				for(PDPage p : pages){
					PDPageContentStream contentStream = new PDPageContentStream(document, p, true, true);
					
					draw(contentStream, coordinates, font, ++i);
					contentStream.close();
				}

				document.save(individualFilePath);
				document.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (COSVisitorException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void writeToClearPaper(String templateFilePath, String individualDirectory, List<W8BeneForm> forms) {
		PDDocument document = null;
		String individualFilePath = null;
		PDFont font = PDType1Font.HELVETICA_BOLD;
		//File pdfFile = w8BeneFormService.getFile(templateFilePath);
		w8BeneFormService.clearDirectory(individualDirectory);
		
		
		
		for(W8BeneForm f : forms){
			individualFilePath = individualDirectory + "//W8BeneForm_" + f.getId() + ".pdf";
			List<IrsFormCoordinate> coordinates = setupFormCoordinates(f);
			try {
				document = new PDDocument();
				//List<PDPage> pages = document.getDocumentCatalog().getAllPages();
				for(int i = 1; i <= 10; i++){
					PDRectangle rec = new PDRectangle(615, 790);
					PDPage clearPage = new PDPage(rec);
					document.addPage(clearPage);
					
					PDPageContentStream contentStream = new PDPageContentStream(document, clearPage, true, true);
					
					draw(contentStream, coordinates, font, i);
					contentStream.close();
				}

				document.save(individualFilePath);
				document.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (COSVisitorException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<IrsFormCoordinate> setupFormCoordinates(W8BeneForm entity) {

		List<IrsFormCoordinate> coordinates = new ArrayList<IrsFormCoordinate>();
		
		int indent = 3;
		int indent2 = 25;
		
		// for Page 1
		IrsFormCoordinate cif = new IrsFormCoordinate("cif", 495 + 1, 748 - 3, 10, entity.getCif(), 1);
		IrsFormCoordinate name = new IrsFormCoordinate("name", 34 + indent, 555, 10, entity.getName(), 1);
		String address1 = entity.getPhysicalAddress().trim();
		String address2 = entity.getPhysicalCity().trim();
		String address3 = entity.getPhysicalCountry().trim();
		IrsFormCoordinate physicalAddress = new IrsFormCoordinate("physicalAddress", 34 + indent, 146 + indent2, 10, address1, 1);
		IrsFormCoordinate physicalCity = new IrsFormCoordinate("physicalCity", 34 + indent, 122 + indent2, 10, address2, 1);
		IrsFormCoordinate physicalCountryInc = new IrsFormCoordinate("physicalCountryInc", 440 + indent, 122 + indent2, 10, address3, 1);
		int a = 48;
		String altAddress1 = "";
		String altAddress2 = "";
		String altAddress3 = "";
		if(!entity.getPhysicalAddress().equalsIgnoreCase(entity.getAltAddress())){
			altAddress1 = entity.getAltAddress().trim();
			altAddress2 = entity.getAltCity().trim();
			altAddress3 = entity.getAltCountry().trim();
		}
		
		IrsFormCoordinate altAddress = new IrsFormCoordinate("altAddress", 34 + indent, 146 - a + indent2, 10, altAddress1, 1);
		IrsFormCoordinate altCity = new IrsFormCoordinate("altCity", 34 + indent, 122 - a + indent2, 10, altAddress2, 1);
		IrsFormCoordinate altCountry = new IrsFormCoordinate("altCountry", 440 + indent, 122 - a + indent2, 10, altAddress3, 1);
		IrsFormCoordinate account = new IrsFormCoordinate("account", 34 + indent, 122 - a - 48 + indent2, 10, entity.getAccount(), 1);
		
		// for Page 10
		int left = 105;
		IrsFormCoordinate labelName = new IrsFormCoordinate("labelName", left, 640, 8, entity.getLabelName(), 10);
		IrsFormCoordinate officer = new IrsFormCoordinate("officer", 350, 640, 8, entity.getOfficer() + "    " + entity.getBranch(), 10);
		W8BeneFormAddress trimAddress = trimAddress(entity.getAltAddressLabel());
		IrsFormCoordinate altAddressLabel = new IrsFormCoordinate("altAddressLabel", left, 630, 8, trimAddress.getFirstLine(), 10);
		IrsFormCoordinate altAddressLabel2 = new IrsFormCoordinate();
		if(trimAddress.getSecondLine().trim().length() > 0){
			altAddressLabel2 = new IrsFormCoordinate("altAddressLabel2", left, 620, 8, trimAddress.getSecondLine(), 10);
		}
		IrsFormCoordinate altCityLabel = new IrsFormCoordinate("altCityLabel", left, 608, 8, entity.getAltCityLabel(), 10);
		IrsFormCoordinate altCountryLabel = new IrsFormCoordinate("altCountryLabel", left, 598, 8, entity.getAltCountryLabel(), 10);
		
		
		coordinates.add(cif);
		coordinates.add(name);
		coordinates.add(physicalCountryInc);
		coordinates.add(physicalAddress);
		coordinates.add(physicalCity);
		coordinates.add(altAddress);
		coordinates.add(altCity);
		coordinates.add(altCountry);
		coordinates.add(account);
		coordinates.add(labelName);
		coordinates.add(officer);
		coordinates.add(altAddressLabel);
		coordinates.add(altAddressLabel2);
		coordinates.add(altCityLabel);
		coordinates.add(altCountryLabel);
		
		return coordinates;
	}
	
	
    @SuppressWarnings("unchecked")
	public void writeToTemplateDirect(String templateFilePath, String individualDirectory, List<W8BeneFormDirect> forms) {
		
		PDDocument document = null;
		String individualFilePath = null;
		PDFont font = PDType1Font.HELVETICA_BOLD;
		//File pdfFile = w8BeneFormService.getFile(templateFilePath);
		File pdfFile = new File(templateFilePath);
		w8BeneFormService.clearDirectory(individualDirectory);
		
		
		for(W8BeneFormDirect f : forms){
			String fileName = null;
			if(f.getAltAddress().trim().equalsIgnoreCase("OCEAN BANK HOLD MAIL")){
				fileName = "W8_BEN_E_" + f.getOfficer().trim() + "_" + f.getPkId().getCif().trim() + "_HOLD";
			}else{
				fileName = "W8_BEN_E_" + f.getOfficer().trim() + "_" + f.getPkId().getCif().trim();
			}
			individualFilePath = individualDirectory + "//" + fileName + ".pdf";
			List<IrsFormCoordinate> coordinates = setupFormCoordinatesDirect(f);
			try {
				document = PDDocument.load(pdfFile);
				List<PDPage> pages = document.getDocumentCatalog().getAllPages();
				int i = 0;
				for(PDPage p : pages){
					PDPageContentStream contentStream = new PDPageContentStream(document, p, true, true);
					
					draw(contentStream, coordinates, font, ++i);
					contentStream.close();
				}

				document.save(individualFilePath);
				document.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (COSVisitorException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public List<IrsFormCoordinate> setupFormCoordinatesDirect(W8BeneFormDirect entity) {

		List<IrsFormCoordinate> coordinates = new ArrayList<IrsFormCoordinate>();
		
		int indent = 3;
		int indent2 = 25;
		
		// for Page 1
		IrsFormCoordinate cif = new IrsFormCoordinate("cif", 495 + 1, 748 - 3, 10, entity.getPkId().getCif(), 1);
		IrsFormCoordinate name = new IrsFormCoordinate("name", 34 + indent, 555, 10, entity.getPkId().getName(), 1);
		String address1 = entity.getPhysicalAddress().trim() + ", " + entity.getPhysicalCity().trim();
		if(entity.getPhysicalCity() != null && entity.getPhysicalCity().trim().length() == 0 || entity.getPhysicalCity() == null){
			address1 = address1.substring(0, address1.length() - 2);
		}
		String address2 = entity.getPhysicalCountryInc().trim();
		String address3 = entity.getPhysicalCountry().trim();
		IrsFormCoordinate physicalAddress = new IrsFormCoordinate("physicalAddress", 34 + indent, 146 + indent2, 10, address1, 1);
		IrsFormCoordinate physicalCity = new IrsFormCoordinate("physicalCity", 34 + indent, 122 + indent2, 10, address2, 1);
		IrsFormCoordinate physicalCountryInc = new IrsFormCoordinate("physicalCountryInc", 440 + indent, 122 + indent2, 10, address3, 1);
		int a = 48;
		
		String altAddress1 = "";
		String altAddress2 = "";
		String altAddress3 = "";
		if(!entity.getPhysicalAddress().equalsIgnoreCase(entity.getAltAddress()) && entity.getAltAddress().trim().length() > 0){
			address1 = entity.getAltAddress().trim() + ", " + entity.getAltCity().trim();
			if(entity.getAltCity() != null && entity.getAltCity().trim().length() == 0 || entity.getAltCity() == null){
				address1 = address1.substring(0, address1.length() - 2);
			}
			address2 = entity.getAltCountryInc().trim();
			address3 = entity.getAltCountry().trim();
			altAddress1 = address1;
			altAddress2 = address2;
			altAddress3 = address3;
		}
		
		IrsFormCoordinate altAddress = new IrsFormCoordinate("altAddress", 34 + indent, 146 - a  + indent2, 10, altAddress1, 1);
		IrsFormCoordinate altCity = new IrsFormCoordinate("altCity", 34 + indent, 122 - a  + indent2, 10, altAddress2, 1);
		IrsFormCoordinate altCountry = new IrsFormCoordinate("altCountry", 440 + indent, 122 - a + indent2, 10, altAddress3, 1);
		IrsFormCoordinate giin = new IrsFormCoordinate("giin", 238 + indent, 122 - a - 25 + indent2, 10, entity.getGiin(), 1);
		IrsFormCoordinate tin = new IrsFormCoordinate("tin", 440 + indent, 122 - a - 25 + indent2, 10, entity.getTin(), 1);
		
		// for Page 10	
		int left = 105;
		IrsFormCoordinate labelName = new IrsFormCoordinate("labelName", left, 640, 8, entity.getPkId().getName(), 10);
		IrsFormCoordinate officer = new IrsFormCoordinate("officer", 350, 640, 8, entity.getOfficer().trim() + "    " + entity.getBranch().trim(), 10);
		
		W8BeneFormAddress trimAddress = trimAddress(address1);
		IrsFormCoordinate altAddressLabel = new IrsFormCoordinate("altAddressLabel", left, 630, 8, trimAddress.getFirstLine().trim(), 10);
		IrsFormCoordinate altAddressLabel2 = new IrsFormCoordinate();
		if(trimAddress.getSecondLine().trim().length() > 0){
			altAddressLabel2 = new IrsFormCoordinate("altAddressLabel2", left, 620, 8, trimAddress.getSecondLine().trim(), 10);
		}
		IrsFormCoordinate altCityLabel = new IrsFormCoordinate("altCityLabel", left, 608, 8, address2, 10);
		IrsFormCoordinate altCountryLabel = new IrsFormCoordinate("altCountryLabel", left, 598, 8, address3, 10);
		
		
		coordinates.add(cif);
		coordinates.add(name);
		coordinates.add(physicalCountryInc);
		coordinates.add(physicalAddress);
		coordinates.add(physicalCity);
		coordinates.add(altAddress);
		coordinates.add(altCity);
		coordinates.add(altCountry);
		coordinates.add(giin);
		coordinates.add(tin);
		coordinates.add(labelName);
		coordinates.add(officer);
		coordinates.add(altAddressLabel);
		coordinates.add(altAddressLabel2);
		coordinates.add(altCityLabel);
		coordinates.add(altCountryLabel);
		
		return coordinates;
	}

	@Override
	public void mergePdf(String individualDirectory, String mergeDirectory, String tempDirectory, String mergeFileName) {
		int maxPdf = 100000;
		String mergeFilePath = mergeDirectory + "//" + mergeFileName;
		
		final File file = new File(individualDirectory);
		if (file.isDirectory()) {

			// proceed to crawl thru the folder and merge the pdf according to last mod date
			final File[] pdfs = file.listFiles();
			int cnt = pdfs.length;

			if (cnt > 0) {
				// sort the pdfs by last mod date in desc order
				Arrays.sort(pdfs, new Comparator<File>() {

					@Override
					public int compare(File f1, File f2) {
						return Long.compare(f2.lastModified(),
								f1.lastModified());
					}
				});

				if (maxPdf != 0 && maxPdf < cnt) {
					cnt = maxPdf;
				}

				// create a temp file for temp pdf stream storage
				final String tempFileName = (new Date()).getTime() + "_temp";
				final File tempFile = new File(tempDirectory + "//" + tempFileName);

				// proceed to merge
				PDDocument desPDDoc = null;
				final PDFMergerUtility pdfMerger = new PDFMergerUtility();
				try {
					// traverse the files
					boolean hasCloneFirstDoc = false;
					for (int i = 0; i < cnt; i++) {
						final File pdfFile = pdfs[i];
						PDDocument doc = null;
						try {
							if (hasCloneFirstDoc) {
								doc = PDDocument.load(pdfFile);
								pdfMerger.appendDocument(desPDDoc, doc);
							} else {
								desPDDoc = PDDocument.load(pdfFile, new RandomAccessFile(tempFile, "rw"));
								hasCloneFirstDoc = true;
							}
						} catch (IOException ioe) {
							LOGGER.error("Invalid PDF detected: " + pdfFile.getName());
							ioe.printStackTrace();
						} finally {
							if (doc != null) {
								doc.close();
							}
						}
					}

					
					desPDDoc.save(mergeFilePath);


				} catch (IOException | COSVisitorException e) {
					e.printStackTrace();
				} finally {
					try {
						if (desPDDoc != null) {
							desPDDoc.close();
						}
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
					try {
						tempFile.delete();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					
				}
			} else {
				LOGGER.error("Target directory is empty.");
			}
		} else {
			LOGGER.error("Target is not a directory (" + mergeDirectory+ ").");
		}
		
	}

	@Override
	public void draw(PDPageContentStream contentStream, List<IrsFormCoordinate> coordinates, PDFont font, Integer pageNumber) throws IOException {
		final Integer moveX = 0;
		final Integer moveYForm1 = 0;

		// 1st Form
		for (IrsFormCoordinate p : coordinates) {
			if(p.getPageNumber() == pageNumber){
				contentStream.beginText();
				contentStream.setFont(font, Float.floatToRawIntBits(p.getFontSize()) != 0 ? p.getFontSize() : 7);
				if(pageNumber == 10){
					contentStream.setNonStrokingColor(Color.BLACK);
				}else{
					contentStream.setNonStrokingColor(Color.BLUE);
				}
				contentStream.moveTextPositionByAmount(p.getX() + moveX, p.getY() - moveYForm1);
				contentStream.drawString(p.getText() != null ? p.getText() : "");
				contentStream.endText();
			}
			
		}

	}
	
	private W8BeneFormAddress trimAddress(String address){
		W8BeneFormAddress add = new W8BeneFormAddress();
		
		int limit = 48;
		String[] arr = address.split(" ");
		StringBuilder line1 = new StringBuilder();
		StringBuilder line2 = new StringBuilder();
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
		add.setFirstLine(line1.toString().trim());
		add.setSecondLine(line2.toString().trim());
		return add;
		
	}

}
