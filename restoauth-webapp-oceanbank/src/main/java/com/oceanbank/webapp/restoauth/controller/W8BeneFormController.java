package com.oceanbank.webapp.restoauth.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import com.oceanbank.webapp.common.model.MailCodeResponse;
import com.oceanbank.webapp.common.model.W8BeneFormResponse;
import com.oceanbank.webapp.restoauth.converter.DashboardConverter;
import com.oceanbank.webapp.restoauth.converter.DashboardUploadConverter;
import com.oceanbank.webapp.restoauth.dao.DashboardUploadRepository;
import com.oceanbank.webapp.restoauth.dao.W8BeneFormDirectDao;
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
	
	@Autowired
	private W8BeneFormDirectDao w8BeneFormDirectDao;

	//private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	private DashboardConverter<DashboardUpload, DashboardUploadResponse> dashboardUploadConverter = new DashboardUploadConverter();

	@RequestMapping(value = "/createPdfToDisk", method = RequestMethod.POST)
	public String createPdfToDisk(@RequestBody IrsFormSelected selected) throws DashboardException, IOException{

		List<DashboardUpload> uploads = amlBatchServiceImpl.findDashboardUploadByTableNameAndDescription("W8BeneForm", "active");
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
	
		return "OK!";
	}
	
	@RequestMapping(value = "/dataTable", method = RequestMethod.POST)
	public List<W8BeneFormResponse> getBySearchOnDatatables(@RequestBody DataTablesRequest datatableRequest){
	
		String searchParameter = datatableRequest.getValue();
    	List<W8BeneFormResponse> responseList = w8BeneFormService.findByDatatableSearch(searchParameter);
		
		
		return responseList;
	}
	
	@RequestMapping(value = "/dataTableDirect", method = RequestMethod.POST)
	public List<W8BeneFormResponse> getBySearchOnDatatablesDirect(@RequestBody DataTablesRequest datatableRequest) throws Exception{
	
		String searchParameter = datatableRequest.getValue();
		String officerCode = datatableRequest.getMailCode();
		
		
    	List<W8BeneFormResponse> responseList = w8BeneFormService.findByDatatableSearchDirect(searchParameter, officerCode);
		
		
		return responseList;
	}
	
	@RequestMapping(value = "/createPdfToDiskDirect", method = RequestMethod.POST)
	public String createPdfToDiskDirect(@RequestBody IrsFormSelected selected) throws DashboardException, IOException{

		List<DashboardUpload> uploads = amlBatchServiceImpl.findDashboardUploadByTableNameAndDescription("W8BeneFormDirect", "active");
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
		
		
		String result = "OK!";
		if(list.size() == 1){
			W8BeneFormDirect d = list.get(0);
			if(d.getAltAddress().trim().equalsIgnoreCase("OCEAN BANK HOLD MAIL")){
				result = "W8_BEN_E_" + d.getOfficer().trim() + "_" + d.getPkId().getCif().trim() + "_HOLD";
			}else{
				result = "W8_BEN_E_" + d.getOfficer().trim() + "_" + d.getPkId().getCif().trim();
			}

		}
		
		return result;
	}
	
	@RequestMapping(value = "/createPdfToDiskDirectFromFilter", method = RequestMethod.POST)
	public String createPdfToDiskDirectFromFilter(@RequestBody IrsFormSelected selected) throws DashboardException, IOException{
		List<DashboardUpload> uploads = amlBatchServiceImpl.findDashboardUploadByTableNameAndDescription("W8BeneFormDirect", "active");
		if(uploads.isEmpty()){

			throw new DashboardException("There is no PDF template found", null);
		
		}else{
			if(uploads.size() > 1){
				throw new DashboardException("There are 1 or more template enabled. Please enable 1 only.", null);
			}
		}
		DashboardUpload activeTemplate = uploads.get(0);
		String fullLocation = DashboardConstant.W8BENEFORM_TEMPLATE_UPLOAD_DIRECTORY + activeTemplate.getId() + "//" + activeTemplate.getFileName();
		
		List<W8BeneFormDirect> list = new ArrayList<W8BeneFormDirect>();
		list = w8BeneFormService.findByOfficerCodes(selected);
		
		synchronized (this) {
			w8BeneFormService.createPdfToDiskDirect(list, fullLocation);
		}
		
		String result = "OK!";
		if(list.size() == 1){
			W8BeneFormDirect d = list.get(0);
			result = "W8_BEN_E_" + d.getOfficer().trim() + "_" + d.getPkId().getCif().trim();
		}
		
		return result;
	}
	
	@RequestMapping(value = "/createPdfToDiskDirectFromFilterHoldMail", method = RequestMethod.POST)
	public String createPdfToDiskDirectFromFilterHoldMail(@RequestBody IrsFormSelected selected) throws DashboardException, IOException{
		List<DashboardUpload> uploads = amlBatchServiceImpl.findDashboardUploadByTableNameAndDescription("W8BeneFormDirect", "active");
		if(uploads.isEmpty()){

			throw new DashboardException("There is no PDF template found", null);
		
		}else{
			if(uploads.size() > 1){
				throw new DashboardException("There are 1 or more template enabled. Please enable 1 only.", null);
			}
		}
		DashboardUpload activeTemplate = uploads.get(0);
		String fullLocation = DashboardConstant.W8BENEFORM_TEMPLATE_UPLOAD_DIRECTORY + activeTemplate.getId() + "//" + activeTemplate.getFileName();
		
		List<W8BeneFormDirect> list = new ArrayList<W8BeneFormDirect>();
		list = w8BeneFormService.findByAltAddress(selected);
		
		synchronized (this) {
			w8BeneFormService.createPdfToDiskDirect(list, fullLocation);
		}
		
		String result = "OK!";
		if(list.size() == 1){
			W8BeneFormDirect d = list.get(0);
			result = "W8_BEN_E_" + d.getOfficer() + "_" + d.getPkId().getCif() + "_HOLD" ;
		}
		
		return result;
	}
	
	@RequestMapping(value = "/createPdfToDiskDirectFromFilterCif", method = RequestMethod.POST)
	public String createPdfToDiskDirectFromFilterCif(@RequestBody IrsFormSelected selected) throws DashboardException, IOException{
		List<DashboardUpload> uploads = amlBatchServiceImpl.findDashboardUploadByTableNameAndDescription("W8BeneFormDirect", "active");
		if(uploads.isEmpty()){

			throw new DashboardException("There is no PDF template found", null);
		
		}else{
			if(uploads.size() > 1){
				throw new DashboardException("There are 1 or more template enabled. Please enable 1 only.", null);
			}
		}
		DashboardUpload activeTemplate = uploads.get(0);
		String fullLocation = DashboardConstant.W8BENEFORM_TEMPLATE_UPLOAD_DIRECTORY + activeTemplate.getId() + "//" + activeTemplate.getFileName();
		
		List<W8BeneFormDirect> list = new ArrayList<W8BeneFormDirect>();
		list = w8BeneFormService.findByOfficerCodesCif(selected);
		
		synchronized (this) {
			w8BeneFormService.createPdfToDiskDirect(list, fullLocation);
		}
		
		
		return "OK!";
	}
	
	@RequestMapping(value = "/createPdfToDiskDirectAll", method = RequestMethod.POST)
	public String createPdfToDiskDirectFromFilterCif() throws DashboardException, IOException{
		List<DashboardUpload> uploads = amlBatchServiceImpl.findDashboardUploadByTableNameAndDescription("W8BeneFormDirect", "active");
		if(uploads.isEmpty()){

			throw new DashboardException("There is no PDF template found", null);
		
		}else{
			if(uploads.size() > 1){
				throw new DashboardException("There are 1 or more template enabled. Please enable 1 only.", null);
			}
		}
		DashboardUpload activeTemplate = uploads.get(0);
		String fullLocation = DashboardConstant.W8BENEFORM_TEMPLATE_UPLOAD_DIRECTORY + activeTemplate.getId() + "//" + activeTemplate.getFileName();
		
		List<W8BeneFormDirect> list = new ArrayList<W8BeneFormDirect>();
		list = w8BeneFormDirectDao.findAll();
		
		synchronized (this) {
			w8BeneFormService.createPdfToDiskDirect(list, fullLocation);
		}
		
		String result = "OK!";
		if(list.size() == 1){
			W8BeneFormDirect d = list.get(0);
			result = "W8_BEN_E_" + d.getOfficer() + "_" + d.getPkId().getCif();
		}
		
		return result;
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
		List<DashboardUploadResponse> resultList = new ArrayList<DashboardUploadResponse>();
		
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
	
	@RequestMapping(value = "/officerCodes", method = RequestMethod.POST)
	public List<MailCodeResponse> getOfficerCodes(){
		List<String> officerCodes = new ArrayList<String>();
		List<MailCodeResponse> officerCodes2 = new ArrayList<MailCodeResponse>();
		officerCodes = w8BeneFormDirectDao.findDistinctOfficerCodes();
		
		for(String m : officerCodes){
			int count = w8BeneFormDirectDao.findByOfficer(m.trim()).size();
			System.out.println(m + " - " + count);
			MailCodeResponse r = new MailCodeResponse();
			r.setCode(m);
			r.setDescription(count + "");
			officerCodes2.add(r);
		}

		return officerCodes2;
	}
}
