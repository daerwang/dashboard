package com.oceanbank.webapp.dashboard.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.common.collect.ComparisonChain;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oceanbank.webapp.common.exception.DashboardException;
import com.oceanbank.webapp.common.handler.GsonDataTableTypeAdapter;
import com.oceanbank.webapp.common.model.DashboardConstant;
import com.oceanbank.webapp.common.model.DashboardUploadCheckboxDatatable;
import com.oceanbank.webapp.common.model.DashboardUploadDatatable;
import com.oceanbank.webapp.common.model.DashboardUploadResponse;
import com.oceanbank.webapp.common.model.DataTablesRequest;
import com.oceanbank.webapp.common.model.DataTablesResponse;
import com.oceanbank.webapp.common.model.ExcelFileMeta;
import com.oceanbank.webapp.common.model.IrsFormSelected;
import com.oceanbank.webapp.common.model.W8BeneFormDatatableResponse;
import com.oceanbank.webapp.common.model.W8BeneFormResponse;
import com.oceanbank.webapp.common.util.CommonUtil;
import com.oceanbank.webapp.dashboard.service.AmlBatchServiceImpl;
import com.oceanbank.webapp.dashboard.service.W8BenFormService;


@Controller
@RequestMapping("/w8beneform")
public class W8BeneFormController {
	
	@Autowired
	private W8BenFormService w8BenFormService;
	
	@Autowired
	private AmlBatchServiceImpl amlBatchService;

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	private static String W8BENEFORM_MERGE_DIRECTORY = "C://dashboard//w8beneform//merge";
	private static String MERGE_PDF_NAME = "W8BeneForm_merged.pdf";
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String mainPage(Model model) throws IOException{
		
		final String pageTitle = "W-8BEN-E Form";

		model.addAttribute("title1",pageTitle);
		model.addAttribute("irsFormDatatableUrl", DashboardConstant.GET_IRS_FORM_BY_DATATABLE_JSON);
		model.addAttribute("irsMailCodeSearchUrl", DashboardConstant.SHOW_IRS_MAIL_CODE_MODAL);
		
		
		model.addAttribute("mergingPdfFromDiskDirectUrl", DashboardConstant.OPEN_NEW_WINDOW_IRS_FORM_MERGING_PDF_DIRECT);
		model.addAttribute("selectedIrsFormUrl", DashboardConstant.SHOW_SELECTED_IRS_FORM);
		model.addAttribute("selectedIrsFormAllUrl", DashboardConstant.SHOW_SELECTED_IRS_FORM_ALL);
		

		return "tiles_w8BeneForm";
								
	}
	
	@RequestMapping(value = "/dataTable", method = RequestMethod.GET)
	public @ResponseBody String dataTable(@RequestParam Map<String, String> allRequestParams)
			throws UnsupportedEncodingException {
		
		// print for testing 
		//CommonUtil.printParameterValues(allRequestParams);

		
		Integer pageNumber = 0;
		Integer pageLength = 0;
		Integer draw = 0;
		String searchParameter = null;

		searchParameter = CommonUtil.determineValue(allRequestParams, "search[value]");
		pageNumber = Integer.valueOf(CommonUtil.determineValue(allRequestParams, "start"));
		pageLength = Integer.valueOf(CommonUtil.determineValue(allRequestParams, "length"));
		draw = Integer.valueOf(CommonUtil.determineValue(allRequestParams, "draw"));

		final DataTablesRequest datatableRequest = new DataTablesRequest();
		datatableRequest.setValue(searchParameter);
		datatableRequest.setStart(pageNumber);
		datatableRequest.setLength(pageLength);

		// retrieve the Users from parameters given but only 50 rows for blank search for quickness....
		final List<W8BeneFormResponse> responseList = w8BenFormService.searchFormDataTable(datatableRequest);
		Integer recordsTotal = 0;
		recordsTotal = responseList.size();
		
		// transform to Json Object
		List<W8BeneFormDatatableResponse> responseDatatableList = new ArrayList<W8BeneFormDatatableResponse>();
		
		for (W8BeneFormResponse u : responseList) {
			final W8BeneFormDatatableResponse dt = new W8BeneFormDatatableResponse();
			dt.setW8BeneFormId(u.getId() + "");
			dt.setCif(u.getCif());
			dt.setName(u.getName());
			dt.setPhysicalCountryInc(u.getPhysicalCountryInc());
			
			responseDatatableList.add(dt);
		}

		// create the Datatable Response
		final DataTablesResponse<W8BeneFormDatatableResponse> response = new DataTablesResponse<W8BeneFormDatatableResponse>();
		response.setDraw(draw);
		response.setRecordsFiltered(recordsTotal);
		response.setRecordsTotal(pageLength);
	
		Integer second = 0;
		Integer total = 0;
		total = pageNumber + pageLength;
		second = total < recordsTotal ? total: recordsTotal;
		
		LOGGER.debug("first " + pageNumber + " and second " + second + ".");
		
		responseDatatableList = responseDatatableList.subList(pageNumber, second);
		
		response.setData(responseDatatableList);

		// clear the List container
		responseDatatableList = null;

		// parse to Json Format
		final GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(DataTablesResponse.class, new GsonDataTableTypeAdapter<W8BeneFormDatatableResponse>());
		
		// needs to be removed to improve performance
		//gsonBuilder.setPrettyPrinting(); 
		
		final Gson gson = gsonBuilder.create();
		final String json = gson.toJson(response);
		
		LOGGER.debug(json);
		
		return json;

	}
	
	@RequestMapping(value = "/createPdfToDisk", method = RequestMethod.POST)
	public @ResponseBody IrsFormSelected createPdfToDisk(@RequestBody IrsFormSelected selected) throws IOException, DashboardException{
		IrsFormSelected sel = new IrsFormSelected();
		String result = null;
		try {
			result = w8BenFormService.createPdfToDisk(selected);
		} catch (RestClientException e) {
			throw new DashboardException(e.getMessage(), e.getCause());
		}


		sel.setStatus(result);
		sel.setSelected(new String[]{""});
		return sel;			
	}
	
	@RequestMapping(value = "/openPdf", method = RequestMethod.GET)
	public void openPdf(HttpServletResponse response) throws IOException{
		InputStream is = null;
		try {
			String mergedFilePath = W8BENEFORM_MERGE_DIRECTORY + "//" + MERGE_PDF_NAME;
			is = new FileInputStream(new File(mergedFilePath));
			 byte[] byteArr = null;
			 final ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		        int nRead;
		        final byte[] data = new byte[100000];
		        while ((nRead = is.read(data, 0, data.length)) != -1) {
		          buffer.write(data, 0, nRead);
		        }
		        buffer.flush();
		        byteArr = buffer.toByteArray();
		        
		        response.setContentType("application/pdf");
		        response.setContentLength(byteArr.length);
		        
		        final OutputStream os = response.getOutputStream();
		        os.write(byteArr);
		        os.flush();
		        os.close(); 
		    	
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(is != null)
				is.close();
		}

	}

	@RequestMapping(value = "/uploadExcelData", method = RequestMethod.POST)
	public void uploadExcelData(MultipartHttpServletRequest request, @RequestParam Map<String, String> allRequestParams) throws DashboardException, IOException {

		List<W8BeneFormResponse> list = new ArrayList<W8BeneFormResponse>();
		list = w8BenFormService.createW8BeneFormFromExcel(request);
		w8BenFormService.insertW8BeneForms(list);
	}


	@RequestMapping(value = "/uploadPdfTemplate", method = RequestMethod.POST)
	public void uploadPdfTemplate(MultipartHttpServletRequest request, @RequestParam Map<String, String> allRequestParams) throws DashboardException, IOException {
		//@ResponseBody ExcelFileMeta
		
        Iterator<String> itr =  request.getFileNames();
        MultipartFile mpf = null;
        ExcelFileMeta fileMeta = null;

        final String fileName = itr.next();

    	mpf = request.getFile(fileName);
    	String ext = FilenameUtils.getExtension(mpf.getOriginalFilename());
    	if(mpf.getSize()/1024 > 5000){
        	throw new DashboardException("The upload should be less than 5MB in size.");
        }
    	if(!ext.equalsIgnoreCase("pdf")){
    		throw new DashboardException("Sorry, this Upload accepts PDF files only.");
    	}

    	String createdBy = CommonUtil.getAuthenticatedUserDetails().getUsername();

    	synchronized (this) {
    		w8BenFormService.savePdfToDisk(mpf, createdBy);
		}

        fileMeta = new ExcelFileMeta();
        fileMeta.setFileName(mpf.getOriginalFilename());
        fileMeta.setFileSize(mpf.getSize()/1024+" Kb - OK");
        fileMeta.setFileType(mpf.getContentType());

		//return fileMeta;
	}


	@RequestMapping(value = "/uploadDataTable", method = RequestMethod.GET)
	public @ResponseBody String getDashboardUploadDataTables(@RequestParam Map<String, String> allRequestParams)
			throws UnsupportedEncodingException {

		Integer pageNumber = 0;
		Integer pageLength = 0;
		Integer draw = 0;

		final String searchParameter = CommonUtil.determineValue(allRequestParams, "search[value]");
		pageNumber = Integer.valueOf(CommonUtil.determineValue(allRequestParams, "start"));
		pageLength = Integer.valueOf(CommonUtil.determineValue(allRequestParams, "length"));
		draw = Integer.valueOf(CommonUtil.determineValue(allRequestParams, "draw"));

		final DataTablesRequest datatableRequest = new DataTablesRequest();
		datatableRequest.setValue(searchParameter);
		datatableRequest.setStart(pageNumber);
		datatableRequest.setLength(pageLength);


		// retrieve the Users from parameters given but only 50 rows for blank search for quickness....
		final List<DashboardUploadResponse> responseList = w8BenFormService.getDashboardUploadDataTable(datatableRequest);
		Integer recordsTotal = 0;
		recordsTotal = responseList.size();

		// transform to Json Object
		List<DashboardUploadCheckboxDatatable> responseDatatableList = new ArrayList<DashboardUploadCheckboxDatatable>();

		for (DashboardUploadResponse u : responseList) {
			final DashboardUploadCheckboxDatatable dt = new DashboardUploadCheckboxDatatable();
			dt.setCreatedby(u.getCreatedby());
			dt.setFilename(u.getFilename());
			final String createdOn = new SimpleDateFormat(DashboardConstant.AML_BATCH_DATE_FORMAT, new Locale("en")).format(u.getCreatedOn());
			dt.setCreatedon(createdOn);
			dt.setUploadId(u.getId() + "");
			Boolean enable = u.getDescription() != null && u.getDescription().trim().length() > 0 ? true : false;
			dt.setEnable(enable);
			responseDatatableList.add(dt);
		}

		// create the Datatable Response
		final DataTablesResponse<DashboardUploadCheckboxDatatable> response = new DataTablesResponse<DashboardUploadCheckboxDatatable>();
		response.setDraw(draw);
		response.setRecordsFiltered(recordsTotal);
		response.setRecordsTotal(pageLength);

		Integer second = 0;
		Integer total = 0;
		total = pageNumber + pageLength;
		second = total < recordsTotal ? total: recordsTotal;

		if(!responseDatatableList.isEmpty()){
			responseDatatableList = responseDatatableList.subList(pageNumber, second);

			// apply sort on column[0] only
			Collections.sort(responseDatatableList, new Comparator<DashboardUploadCheckboxDatatable>() {

				@Override
				public int compare(DashboardUploadCheckboxDatatable o1, DashboardUploadCheckboxDatatable o2) {
					return ComparisonChain.start()
							.compare(o2.getCreatedon(), o1.getCreatedon())
							.result();
				}
			});
		}


		response.setData(responseDatatableList);

		// clear the List container
		responseDatatableList = null;

		// parse to Json Format
		final GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(DataTablesResponse.class, new GsonDataTableTypeAdapter<DashboardUploadDatatable>());

		final Gson gson = gsonBuilder.create();
		final String json = gson.toJson(response);

		return json;

	}

	@RequestMapping(value = "/showUploadPage", method = RequestMethod.GET)
	public String showUploadPage(Model model) {

		model.addAttribute("title1", "Upload PDF File");

		return "/w8beneforms/w8BeneFormUpload";
	}

	@RequestMapping(value = "/showUploadExcelPage", method = RequestMethod.GET)
	public String showUploadExcelPage(Model model) {

		model.addAttribute("title1", "Upload PDF File");

		return "/w8beneforms/w8BeneFormDataUpload";
	}

	@RequestMapping(value = "/{id}/deletePdfUpload", method = RequestMethod.DELETE)
	public @ResponseBody String deleteDashboardUpload(@PathVariable("id") Long id)throws DashboardException{
		String result = null;

		// 1. delete in the file system
		String fullLocation = DashboardConstant.W8BENEFORM_TEMPLATE_UPLOAD_DIRECTORY + id;
		File file = new File(fullLocation);

		boolean isSuccess = amlBatchService.removeDirectory(file);

		// 2. delete in the database
		if(isSuccess)
			result = amlBatchService.deleteDashboardUpload(id);

		return result;
	}

	@RequestMapping(value = "/{id}/openPdfTemplate", method = RequestMethod.GET)
	public void openDashboardUpload(HttpServletResponse response, @PathVariable("id") Long id) throws IOException{
		InputStream is = null;
		DashboardUploadResponse bean = amlBatchService.findDashboardUploadById(id);
		String fullLocation = DashboardConstant.W8BENEFORM_TEMPLATE_UPLOAD_DIRECTORY + id + "//" + bean.getFilename();
		try {
			is = new FileInputStream(new File(fullLocation));
			 byte[] byteArr = null;
			 final ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		        int nRead;
		        final byte[] data = new byte[5000];
		        while ((nRead = is.read(data, 0, data.length)) != -1) {
		          buffer.write(data, 0, nRead);
		        }
		        buffer.flush();
		        byteArr = buffer.toByteArray();

		        response.setContentType("application/pdf");
		        response.setContentLength(byteArr.length);
		        response.setHeader("Content-Disposition","attachment;filename=" + bean.getFilename());

		        final OutputStream os = response.getOutputStream();
		        os.write(byteArr);
		        os.flush();
		        os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(is != null)
				is.close();
		}

	}


	@RequestMapping(value = "/updateCheckbox", method = RequestMethod.PUT)
	public @ResponseBody DashboardUploadResponse updateCheckbox(@RequestBody DashboardUploadResponse response){

		DashboardUploadResponse result = w8BenFormService.activateCheckbox(response);
		return result;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody String delete(@PathVariable("id") Integer id){

		String result = w8BenFormService.delete(id);

		return result;
	}

	@RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
	public @ResponseBody String deleteAll(){

		String result = w8BenFormService.deleteAll();

		return result;
	}

}
