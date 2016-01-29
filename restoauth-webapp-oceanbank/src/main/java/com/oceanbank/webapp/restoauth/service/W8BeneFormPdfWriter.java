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
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.util.PDFMergerUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oceanbank.webapp.restoauth.model.IrsFormCoordinate;
import com.oceanbank.webapp.restoauth.model.W8BeneForm;

public class W8BeneFormPdfWriter implements PdfFormWriter {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	private W8BeneFormService w8BeneFormService;
	
	public W8BeneFormPdfWriter(){
		this.w8BeneFormService = new W8BeneFormService();
	}

	@Override
	public void writeToTemplate(String templateFilePath, String individualDirectory, List<W8BeneForm> forms) {
		
		PDDocument document = null;
		PDPage page = null;
		String individualFilePath = null;
		File pdfFile = w8BeneFormService.getFile(templateFilePath);
		w8BeneFormService.clearDirectory(individualDirectory);
		
		for(W8BeneForm f : forms){
			individualFilePath = individualDirectory + "//W8BeneForm_" + f.getId() + ".pdf";
			
			try {
				
				document = PDDocument.load(pdfFile);
				page = (PDPage) document.getDocumentCatalog().getAllPages().get(0);
				
				PDFont font = PDType1Font.HELVETICA_BOLD;
				PDPageContentStream contentStream = new PDPageContentStream(document, page, true, true);
				page.getContents().getStream();

				List<IrsFormCoordinate> coordinates = setupFormCoordinates(f);
				draw(contentStream, coordinates, font);

				contentStream.close();
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
	public void writeToClearPaper() {

	}

	@Override
	public List<IrsFormCoordinate> setupFormCoordinates(W8BeneForm entity) {

		List<IrsFormCoordinate> coordinates = new ArrayList<IrsFormCoordinate>();
		
		IrsFormCoordinate cif = new IrsFormCoordinate("cif", 495, 748, 10, entity.getCif());
		IrsFormCoordinate name = new IrsFormCoordinate("name", 32, 555, 10, entity.getName());
		
		
		coordinates.add(cif);
		coordinates.add(name);
		
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
	public void draw(PDPageContentStream contentStream, List<IrsFormCoordinate> coordinates, PDFont font) throws IOException {
		final Integer moveX = 0;
		final Integer moveYForm1 = 0;
		final Integer moveYForm2 = 243 + 1;
		final Integer moveYForm3 = 486 + 1;

		// 1st Form
		for (IrsFormCoordinate p : coordinates) {
			contentStream.beginText();
			contentStream.setFont(font, Float.floatToRawIntBits(p.getFontSize()) != 0 ? p.getFontSize() : 7);
			contentStream.setNonStrokingColor(Color.BLUE);
			contentStream.moveTextPositionByAmount(p.getX() + moveX, p.getY() - moveYForm1);
			contentStream.drawString(p.getText() != null ? p.getText() : "");
			contentStream.endText();
		}

//		// 2nd Form
//		for (IrsFormCoordinate p : coordinates) {
//
//			if (p.getName() != null
//					&& p.getName().equalsIgnoreCase("thirdForm"))
//				continue;
//
//			contentStream.beginText();
//			contentStream.setFont(font, Float.floatToRawIntBits(p.getFontSize()) != 0 ? p.getFontSize()
//					: 7);
////			contentStream.setNonStrokingColor(Color.BLUE);
//			contentStream.moveTextPositionByAmount(p.getX() + moveX, p.getY()
//					- moveYForm2);
//			contentStream.drawString(p.getText() != null ? p.getText() : "");
//			contentStream.endText();
//		}
//
//		// 3rd Form
//		for (IrsFormCoordinate p : coordinates) {
//			if (p.getName() != null && p.getName().equalsIgnoreCase("header")) {
//				p.setY(p.getY() + 3);
//			}
//			contentStream.beginText();
//			contentStream.setFont(font, Float.floatToRawIntBits(p.getFontSize()) != 0 ? p.getFontSize()
//					: 7);
////			contentStream.setNonStrokingColor(Color.BLUE);
//			contentStream.moveTextPositionByAmount(p.getX() + moveX, p.getY()
//					- moveYForm3);
//			contentStream.drawString(p.getText() != null ? p.getText() : "");
//			contentStream.endText();
//		}
	}

}
