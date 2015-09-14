/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.restoauth.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.oceanbank.webapp.common.exception.DashboardException;
import com.oceanbank.webapp.common.model.DashboardConstant;
import com.oceanbank.webapp.common.model.IrsFormCustomerResponse;
import com.oceanbank.webapp.common.model.IrsFormSelected;
import com.oceanbank.webapp.common.util.RestUtil;
import com.oceanbank.webapp.restoauth.converter.DashboardConverter;
import com.oceanbank.webapp.restoauth.converter.IrsFormConverter;
import com.oceanbank.webapp.restoauth.dao.BankDetailRepository;
import com.oceanbank.webapp.restoauth.dao.IrsFormRepository;
import com.oceanbank.webapp.restoauth.dao.MailCodeRepository;
import com.oceanbank.webapp.restoauth.model.BankDetail;
import com.oceanbank.webapp.restoauth.model.IrsFormCoordinate;
import com.oceanbank.webapp.restoauth.model.IrsFormCustomer;
import com.oceanbank.webapp.restoauth.model.IrsFormPosition;
import com.oceanbank.webapp.restoauth.model.MailCodeDetail;

/**
 * The service implementation for IRS 1042-S Form for 2014.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
@Service
public class IrsFormServiceImpl implements IrsFormService {

	/** The IrsFormCustomer dao by Spring Data. */
	@Autowired
	private IrsFormRepository irsFormCustomer;
	
	/** The BankDetail dao by Spring Data. */
	@Autowired
	private BankDetailRepository bankdetailRepository;
	
	/** The MailCodeDetail dao by Spring Data. */
	@Autowired
	private MailCodeRepository mailCodeRepository;

	/** The logger. */
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	/** The entity and bean converter. */
	private DashboardConverter<IrsFormCustomer, IrsFormCustomerResponse> converter = new IrsFormConverter();


	/**
	 * Instantiates a new service.
	 */
	public IrsFormServiceImpl() {
	}

	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.restoauth.service.IrsFormService#getAllIrsFormCustomerEntity()
	 */
	@Override
	public List<IrsFormCustomer> getAllIrsFormCustomer() {

		return irsFormCustomer.findAll();
	}

	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.restoauth.service.IrsFormService#findCustomersByNameAndPageable(java.lang.String, org.springframework.data.domain.Pageable)
	 */
	@Override
	public List<IrsFormCustomer> findCustomersByNameAndPageable(String name,
			Pageable pageable) {

		final List<IrsFormCustomer> list = irsFormCustomer.findByCustomerByNameLike(name, pageable);

		return list;
	}

	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.restoauth.service.IrsFormService#findByShortName(java.lang.String)
	 */
	@Override
	public List<IrsFormCustomer> findByShortName(String name) {
		final List<IrsFormCustomer> list = irsFormCustomer.findByShortNameContaining(name);

		return list;
	}

	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.restoauth.service.IrsFormService#getIrsCustomersByPage(org.springframework.data.domain.Pageable)
	 */
	@Override
	public List<IrsFormCustomer> getIrsCustomersByPage(Pageable pageable) {
		final Page<IrsFormCustomer> list = irsFormCustomer.findAll(pageable);

		final List<IrsFormCustomer> listResult = list.getContent();

		return listResult;
	}

	
	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.restoauth.service.IrsFormService#findByDatatableSearch(java.lang.String, java.lang.String)
	 */
	@Override
	public List<IrsFormCustomerResponse> findByDatatableSearch(String search, String mailCode) throws Exception {
		final List<IrsFormCustomerResponse> list = new ArrayList<IrsFormCustomerResponse>();
		List<IrsFormCustomer> entityList = new ArrayList<IrsFormCustomer>();

		// check mailCode
		if(!RestUtil.isNullOrEmpty(mailCode)){
			List<String> codes = new ArrayList<String>();
			codes = RestUtil.parseMailCodeFromDatatable(mailCode);
			entityList = irsFormCustomer.findByMailCode(codes);
		}else{
			
			if (!RestUtil.isNullOrEmpty(search) && RestUtil.isNullOrEmpty(mailCode)) {
				entityList = irsFormCustomer.findByDatatableSearch("%" + search + "%");
			} else {
				final Page<IrsFormCustomer> data = irsFormCustomer.findAll(new PageRequest(0, 200));
				entityList = data.getContent();
			}
		}
		
		final List<MailCodeDetail> mailCodes = mailCodeRepository.findAll();
		for (IrsFormCustomer entity : entityList) {
			final StringBuilder b = new StringBuilder();
			b.append(entity.getMailCode().trim().length() > 0 ? entity.getMailCode(): DashboardConstant.MAIL_CODE_BLANK);
			// get equivalent description
			for(MailCodeDetail d: mailCodes){
				if(d.getMailCode().trim().equalsIgnoreCase(entity.getMailCode().trim())){
					b.append(", " + d.getMailDescription());
					break;
				}
			}
			entity.setMailCode(b.toString());
			
			final IrsFormCustomerResponse response = converter.convertFromEntity(entity);
			list.add(response);
		}

		return list;
	}

	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.restoauth.service.IrsFormService#generateSelectedPdf(java.util.List)
	 */
	@Override
	public void generateSelectedPdf(List<IrsFormCustomer> list) throws IOException, DashboardException{
		
		synchronized (this) {
			LOGGER.info("Part 1: Start execute of generateSelectedPdf()");
			RestUtil.printHeapSizes();
			final BankDetail detail = findBankDetailByName(DashboardConstant.BANK_NAME);
			// create pdf file one by one to save server memory
			createIndividualPdfToDisk(list, DashboardConstant.PDF_MERGING_INDIVIDUAL_FILE_LOCATION, detail);
			LOGGER.info("Part 2: After execute of getBankDetailByName() and createIndividualPdfToDisk()");
			RestUtil.printHeapSizes();
			// apply the PDFBox technique of merging documents to avoid java.lang.outofmemoryerror
			createMergedPdfFromIndividual(DashboardConstant.PDF_MERGING_INDIVIDUAL_FILE_LOCATION, DashboardConstant.PDF_MERGING_FILE_LOCATION, DashboardConstant.PDF_FILE_LOCATION);
			LOGGER.info("Part 3: After execute of createMergedPdfFromIndividual()");
			RestUtil.printHeapSizes();
		}
		
		
	}

	
	
	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.restoauth.service.IrsFormService#getListFromSelected(com.oceanbank.webapp.restoauth.api.IrsFormSelected)
	 */
	@Override
	public List<IrsFormCustomer> getIrsFormCustomerBySelected(IrsFormSelected selected) throws DashboardException{
		final String[] selectedId = selected.getSelected();
		final List<String> ids = Arrays.asList(selectedId);
		final List<IrsFormCustomer> list = new ArrayList<IrsFormCustomer>();
		for (String s : ids) {
			final String[] result = s.split(",");
			final String name = result[0];
			final String accountNo = result[1];
			final String agentTin = result[2];
			final String gross = result[3];
			final IrsFormCustomer c = irsFormCustomer.findByCustomerPk(name, accountNo, agentTin, gross);
			if (c == null) {
				throw new DashboardException(
						"The IrsCustomer object cannot be null.", new NullPointerException());
			}
			list.add(c);
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.restoauth.service.IrsFormService#getListFromSelectedMailCode(com.oceanbank.webapp.restoauth.api.IrsFormSelected)
	 */
	@Override
	public List<IrsFormCustomer> getIrsFormCustomerBySelectedMailCode(IrsFormSelected selected) throws DashboardException{
		final String[] selectedId = selected.getSelected();
		final List<String> ids = Arrays.asList(selectedId);
		List<IrsFormCustomer> list = null;
		try {
			list = irsFormCustomer.findByMailCode(ids);
		} catch (Exception e) {
			throw new DashboardException(
					"The getListFromSelectedMailCode() failed with an Exception.", e);
		}
		

		return list;
	}


	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.restoauth.service.IrsFormService#getAllBankDetails()
	 */
	@Override
	public List<BankDetail> getAllBankDetails() {
		return bankdetailRepository.findAll();
	}

	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.restoauth.service.IrsFormService#getBankDetailByName(java.lang.String)
	 */
	@Override
	public BankDetail findBankDetailByName(String bankName) {
		
		return bankdetailRepository.findByFld14eIs(bankName);
	}

	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.restoauth.service.IrsFormService#findByMailCodeDistinct()
	 */
	@Override
	public List<String> findByMailCodeDistinct() {
		return irsFormCustomer.findByMailCodeDistinct();
	}

	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.restoauth.service.IrsFormService#findByMailCode(java.util.List)
	 */
	@Override
	public List<IrsFormCustomer> findByMailCode(List<String> codes) {
		return irsFormCustomer.findByMailCode(codes);
	}
	
	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.restoauth.service.IrsFormService#getAllMailCodeDetails()
	 */
	@Override
	public List<MailCodeDetail> getAllMailCodeDetails(){
		return mailCodeRepository.findAll();
	}
	
	/* (non-Javadoc)
	 * @see com.oceanbank.webapp.restoauth.service.IrsFormService#getMailCodeByCode(java.lang.String)
	 */
	@Override
	public MailCodeDetail getMailCodeByCode(String mailCode){
		return mailCodeRepository.findByMailCode(mailCode);
	}
	

	/**
	 * Draw the text to PDF based on the positions passed.
	 *
	 * @param the stream to save the results
	 * @param the positions or where to place the text
	 * @param the font to use
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void draw1042sPdf(PDPageContentStream contentStream, List<IrsFormCoordinate> coordinates, PDFont font) throws IOException {
		final Integer moveX = 7;
		final Integer moveYForm1 = 0 + 1;
		final Integer moveYForm2 = 243 + 1;
		final Integer moveYForm3 = 486 + 1;

		// 1st Form
		for (IrsFormCoordinate p : coordinates) {

			if (p.getName() != null
					&& p.getName().equalsIgnoreCase("thirdForm"))
				continue;

			contentStream.beginText();
			contentStream.setFont(font, Float.floatToRawIntBits(p.getFontSize()) != 0 ? p.getFontSize()
					: 7);
//			 contentStream.setNonStrokingColor(Color.BLUE);
			contentStream.moveTextPositionByAmount(p.getX() + moveX, p.getY()
					- moveYForm1);
			contentStream.drawString(p.getText() != null ? p.getText() : "");
			contentStream.endText();
		}

		// 2nd Form
		for (IrsFormCoordinate p : coordinates) {

			if (p.getName() != null
					&& p.getName().equalsIgnoreCase("thirdForm"))
				continue;

			contentStream.beginText();
			contentStream.setFont(font, Float.floatToRawIntBits(p.getFontSize()) != 0 ? p.getFontSize()
					: 7);
//			contentStream.setNonStrokingColor(Color.BLUE);
			contentStream.moveTextPositionByAmount(p.getX() + moveX, p.getY()
					- moveYForm2);
			contentStream.drawString(p.getText() != null ? p.getText() : "");
			contentStream.endText();
		}

		// 3rd Form
		for (IrsFormCoordinate p : coordinates) {
			if (p.getName() != null && p.getName().equalsIgnoreCase("header")) {
				p.setY(p.getY() + 3);
			}
			contentStream.beginText();
			contentStream.setFont(font, Float.floatToRawIntBits(p.getFontSize()) != 0 ? p.getFontSize()
					: 7);
//			contentStream.setNonStrokingColor(Color.BLUE);
			contentStream.moveTextPositionByAmount(p.getX() + moveX, p.getY()
					- moveYForm3);
			contentStream.drawString(p.getText() != null ? p.getText() : "");
			contentStream.endText();
		}
	}
	

	/**
	 * Saves the PDF to disk given a list of {@link IrsFormCustomer}.
	 *
	 * @param list of {@link IrsFormCustomer}
	 * @param the file location
	 * @param the bank detail
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws DashboardException which encapsulates any error handled by Spring RestTemplate
	 */
	private void createIndividualPdfToDisk(List<IrsFormCustomer> list, String fileIndLocation, BankDetail detail)
			throws IOException, DashboardException {
		
		// check file location if exist
		final File pdfIndividualDir = new File(fileIndLocation);

		if (pdfIndividualDir.isDirectory()) {
			
			// delete files inside directory
			FileUtils.cleanDirectory(pdfIndividualDir);

		} else {
			throw new DashboardException(
					"Error in createIndividualPdfToDisk() method",
					new IllegalArgumentException(
							"Pdf individual directory does not exist"));
		}

		Integer id = 0;
		String fileLocation = null;
		for (IrsFormCustomer entity : list) {
			id++;
			fileLocation = fileIndLocation + "//1042sForm_" + id + ".pdf";

			// set the Pdf positions given the values from Entity
			final IrsFormPosition pdf = new IrsFormPosition();
			
			try {
				pdf.drawPdfFromEntity(entity, detail);
			} catch (Exception e) {
				LOGGER.error("Entity in mistake is " + entity.getIrsFormId().getFld_14acd_1());
				
				throw new DashboardException("Error in createIndividualPdfToDisk() method", e);
			}

			final List<IrsFormCoordinate> coordinates = pdf.getCoordinates();

			PDDocument document = null;
			PDPage page = null;

			try {
				
				// for testing of template during adjustment
//				File f = new File(PDF_TESTING_TEMPLATE_FILE);
//				document = PDDocument.load(f);
//				page = (PDPage) document.getDocumentCatalog().getAllPages().get(0);

				// using a blank legal size pdf
				document = new PDDocument();
				final PDRectangle rec = new PDRectangle(612, 1008);
				page = new PDPage(rec);
				document.addPage(page);

				final PDFont font = PDType1Font.HELVETICA_BOLD;
				final PDPageContentStream contentStream = new PDPageContentStream(
						document, page, true, true);
				page.getContents().getStream();

				draw1042sPdf(contentStream, coordinates, font);

				contentStream.close();
				document.save(fileLocation);
				document.close();

			} catch (Exception e) {
				e.printStackTrace();
			}

			fileLocation = null;
		}
	}

	/**
	 * Merge the PDF and save to target location.
	 *
	 * @param the source file location
	 * @param the target file location
	 * @param the temporary file location
	 */
	private void createMergedPdfFromIndividual(String sourceFileLocation, String targetFileName, String tempFileLocation) {

		final int maxPdf = 100000;

		final File pdfDir = new File(sourceFileLocation);
		if (pdfDir.isDirectory()) {

			// proceed to crawl thru the folder and merge the pdf according to
			// last mod date
			final File[] pdfs = pdfDir.listFiles();
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
				final File tempFile = new File(tempFileLocation + "//" + tempFileName);

				// proceed to merge
				PDDocument desPDDoc = null;
				final PDFMergerUtility pdfMerger = new PDFMergerUtility();
				try {
					// traverse the files
					boolean hasCloneFirstDoc = false;
					for (int i = 0; i < cnt; i++) {
						final File file = pdfs[i];
						PDDocument doc = null;
						try {
							if (hasCloneFirstDoc) {
								doc = PDDocument.load(file);
								pdfMerger.appendDocument(desPDDoc, doc);
							} else {
								desPDDoc = PDDocument.load(file, new RandomAccessFile(tempFile, "rw"));
								hasCloneFirstDoc = true;
							}
						} catch (IOException ioe) {
							LOGGER.error("Invalid PDF detected: " + file.getName());
							ioe.printStackTrace();
						} finally {
							if (doc != null) {
								doc.close();
							}
						}
					}

					
					desPDDoc.save(targetFileName);


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
			LOGGER.error("Target is not a directory (" + sourceFileLocation+ ").");
		}
	}

	

}
