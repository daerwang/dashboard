package com.oceanbank.webapp.restoauth.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.oceanbank.webapp.common.exception.DashboardException;
import com.oceanbank.webapp.common.model.DashboardConstant;
import com.oceanbank.webapp.common.model.DashboardUploadResponse;
import com.oceanbank.webapp.common.model.DataTablesRequest;
import com.oceanbank.webapp.common.model.IrsFormSelected;
import com.oceanbank.webapp.common.model.W8BeneFormResponse;
import com.oceanbank.webapp.restoauth.converter.DashboardConverter;
import com.oceanbank.webapp.restoauth.converter.DashboardUploadConverter;
import com.oceanbank.webapp.restoauth.dao.DashboardUploadRepository;
import com.oceanbank.webapp.restoauth.model.DashboardUpload;
import com.oceanbank.webapp.restoauth.model.W8BeneForm;
import com.oceanbank.webapp.restoauth.model.W8BeneFormDirect;
import com.oceanbank.webapp.restoauth.service.AmlBatchServiceImpl;
import com.oceanbank.webapp.restoauth.service.W8BeneFormService;

@RestController
@RequestMapping("/api/w8beneform")
public class W8BeneFormController {

	@Autowired
	W8BeneFormService w8BeneFormService;
	
	@Autowired
	private DashboardUploadRepository dashboardUploadDao;

	@Autowired
	private AmlBatchServiceImpl amlBatchServiceImpl;

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	private DashboardConverter<DashboardUpload, DashboardUploadResponse> dashboardUploadConverter = new DashboardUploadConverter();

	@RequestMapping(value = "/createPdfToDisk", method = RequestMethod.POST)
	public String createPdfToDisk(@RequestBody IrsFormSelected selected) throws DashboardException, IOException{

		final Date startTime = new Date();
		// find the active File to be used as template
		// throw error if no template yet
		List<DashboardUpload> uploads = amlBatchServiceImpl.findDashboardUploadByTableNameAndDescription("w8beneform", "active");
		if(uploads.isEmpty()){
			throw new DashboardException("There is no PDF template found", null);
		}else{
			if(uploads.size() > 1){
				throw new DashboardException("There are 1 or more template enabled. Please enable 1 only.", null);
			}
		}
		DashboardUpload activeTemplate = uploads.get(0);
		String fullLocation = DashboardConstant.W8BENEFORM_TEMPLATE_UPLOAD_DIRECTORY + activeTemplate.getId() + "//" + activeTemplate.getFileName();

		final List<W8BeneForm> list = w8BeneFormService.findByIds(selected);
		
		synchronized (this) {
			w8BeneFormService.createPdfToDisk(list, fullLocation);
		}
		
		final Date endTime = new Date();
		LOGGER.info("Process Completed generateSelectedMailCode(): " + endTime);
		final long timeTakenInSec = endTime.getTime() - startTime.getTime();
		LOGGER.info("Time taken: " + (timeTakenInSec / 1000) + " secs " + (timeTakenInSec % 1000) + " ms");
		
		return "OK!";
	}
	
	@RequestMapping(value = "/dataTable", method = RequestMethod.POST)
	public List<W8BeneFormResponse> getBySearchOnDatatables(@RequestBody DataTablesRequest datatableRequest){
	
		String searchParameter = datatableRequest.getValue();
    	List<W8BeneFormResponse> responseList = w8BeneFormService.findByDatatableSearch(searchParameter);
		
		
		return responseList;
	}
	
	@RequestMapping(value = "/dataTableDirect", method = RequestMethod.POST)
	public List<W8BeneFormResponse> getBySearchOnDatatablesDirect(@RequestBody DataTablesRequest datatableRequest){
	
		String searchParameter = datatableRequest.getValue();
    	List<W8BeneFormResponse> responseList = w8BeneFormService.findByDatatableSearchDirect(searchParameter);
		
		
		return responseList;
	}
	
	@RequestMapping(value = "/createPdfToDiskDirect", method = RequestMethod.POST)
	public String createPdfToDiskDirect(@RequestBody IrsFormSelected selected) throws DashboardException, IOException{

		List<DashboardUpload> uploads = amlBatchServiceImpl.findDashboardUploadByTableNameAndDescription("w8beneformDirect", "active");
		if(uploads.isEmpty()){

			throw new DashboardException("There is no PDF template found", null);
		
		}else{
			if(uploads.size() > 1){
				throw new DashboardException("There are 1 or more template enabled. Please enable 1 only.", null);
			}
		}
		DashboardUpload activeTemplate = uploads.get(0);
		String fullLocation = DashboardConstant.W8BENEFORM_TEMPLATE_UPLOAD_DIRECTORY + activeTemplate.getId() + "//" + activeTemplate.getFileName();

		List<W8BeneFormDirect> list = w8BeneFormService.findByPk(selected);
		
		synchronized (this) {
			w8BeneFormService.createPdfToDiskDirect(list, fullLocation);
		}
		
		
		return "OK!";
	}

	@RequestMapping(value = "/pdfUpload", method = RequestMethod.POST)
	public DashboardUploadResponse createDashboardUpload(@RequestBody DashboardUploadResponse response) throws DashboardException{

		DashboardUpload upload = dashboardUploadConverter.convertFromBean(response);
		upload.setTableName("W8BeneForm");
		upload = amlBatchServiceImpl.createDashboardUpload(upload);

		final DashboardUploadResponse result = dashboardUploadConverter.convertFromEntity(upload);

		return result;
	}
	
	@RequestMapping(value = "/pdfUploadDirect", method = RequestMethod.POST)
	public DashboardUploadResponse createDashboardUploadDirect(@RequestBody DashboardUploadResponse response) throws DashboardException{

		DashboardUpload upload = dashboardUploadConverter.convertFromBean(response);
		upload.setTableName("W8BeneFormDirect");
		upload = amlBatchServiceImpl.createDashboardUpload(upload);

		final DashboardUploadResponse result = dashboardUploadConverter.convertFromEntity(upload);

		return result;
	}

	@RequestMapping(value = "/uploadDataTable", method = RequestMethod.POST)
	public List<DashboardUploadResponse> getDashboardUploadDatatables(@RequestBody DataTablesRequest datatableRequest){
		List<DashboardUpload> uploadList = new ArrayList<DashboardUpload>();
		final List<DashboardUploadResponse> resultList = new ArrayList<DashboardUploadResponse>();

		uploadList = amlBatchServiceImpl.findDashboardUploadByTableName("W8BeneForm");

		if(!uploadList.isEmpty()){
			for (DashboardUpload entity : uploadList) {
				final DashboardUploadResponse response = dashboardUploadConverter.convertFromEntity(entity);
				resultList.add(response);
			}
		}

		return resultList;
	}
	
	@RequestMapping(value = "/uploadDataTableDirect", method = RequestMethod.POST)
	public List<DashboardUploadResponse> getDashboardUploadDatatablesDirect(@RequestBody DataTablesRequest datatableRequest){
		List<DashboardUpload> uploadList = new ArrayList<DashboardUpload>();
		final List<DashboardUploadResponse> resultList = new ArrayList<DashboardUploadResponse>();

		uploadList = amlBatchServiceImpl.findDashboardUploadByTableName("W8BeneFormDirect");

		if(!uploadList.isEmpty()){
			for (DashboardUpload entity : uploadList) {
				final DashboardUploadResponse response = dashboardUploadConverter.convertFromEntity(entity);
				resultList.add(response);
			}
		}

		return resultList;
	}

	@RequestMapping(value = "/updateCheckbox", method = RequestMethod.PUT)
	public DashboardUploadResponse activateCheckbox(@RequestBody DashboardUploadResponse response){
		DashboardUpload dashboardUpload = dashboardUploadDao.findAmlBatchUploadById(response.getId());
		dashboardUpload.setDescription(response.getDescription());
		dashboardUpload = dashboardUploadDao.save(dashboardUpload);

		DashboardUploadResponse result = dashboardUploadConverter.convertFromEntity(dashboardUpload);


		return result;
	}

	@RequestMapping(value = "/insertW8BeneForms", method = RequestMethod.POST)
	public String insertW8BeneForms(@RequestBody List<W8BeneFormResponse> entities){

		w8BeneFormService.insertW8BeneForms(entities);

		return "insertW8BeneForms successful";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("id") Integer id){

		w8BeneFormService.delete(id);

		return "OK!";
	}

	@RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
	public String deleteAll(){

		w8BeneFormService.deleteAll();

		return "OK!";
	}
}
