/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.common.model;


/**
 * The Class ObWebConstant.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
public class DashboardConstant {
	
	public static final String DB_CONSTRAINT_EXCEPTION = "org.hibernate.exception.ConstraintViolationException";
	
	public static final String ADMIN_ROLE = "ADMIN";
	public static final String USER_ROLE = "USER";
	public static final String ROLE_DEV = "ROLE_DEV";
	public static final String ROLE_SUPER_USER= "SUPER_USER";
	public static final String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";
	public static final String ROLE_GUEST = "Guest";
	public static final String ROLE_TESTER = "Tester";
	public static final String ROLE_USER = "User";
	public static final String ROLE_ADMINISTRATOR = "Administrator";
	public static final String REST_URI = "http://localhost:8085/restoauth-webapp-oceanbank";
	public static final String AML_BATCH_DATE_FORMAT = "EEE, d MMM yyyy h:mm a";
	public static final String ROLE_OCEAN_DEV = "Administrator";
	public static final String BANK_NAME = "Ocean Bank";
	public static final String MAIL_CODE_BLANK = "Empty";
	public static final String DASHBOARD_TRANSACTION_APPROVAL = "Approval";
	public static final String PDF_MERGING_CLASSPATH_LOCATION = "classpath:editpdf/testpdfresult/merged.pdf";
	public static final String PDF_MERGING_FILE_LOCATION = "C://dashboard//merged.pdf";
	public static final String PDF_MERGING_INDIVIDUAL_FILE_LOCATION = "C://dashboard//individual";
	public static final String PDF_FILE_LOCATION = "C://dashboard";
	public static final String PDF_TESTING_TEMPLATE_FILE = "C://dashboard//template//template4.pdf";

	
	// Dashboard Paths for IRS 1042-S
	public static final String TILES_IRS_FORM_1042S_TEMPLATE = "tiles_irs1042sForm";
	public static final String SHOW_IRS_FORM_PAGE = "/oceanbank/irs/1042sform";
	public static final String OPEN_NEW_WINDOW_IRS_FORM_MERGING_PDF_DIRECT = "/oceanbank/irs/1042sform/mergingPdf/newWindowDirect";
	public static final String SHOW_SELECTED_IRS_FORM = "/oceanbank/irs/1042sform/selectedForm";
	public static final String SHOW_SELECTED_IRS_FORM_ALL = "/oceanbank/irs/1042sform/selectedFormAll";
	public static final String SHOW_SELECTED_MAIL_CODE_IRS_FORM = "/oceanbank/irs/1042sform/selectedMailCodeForm";
	public static final String GET_IRS_FORM_BY_DATATABLE_JSON = "/dashboard/irs/1042sform/datatable";
	public static final String SHOW_IRS_MAIL_CODE_MODAL = "/dashboard/irs/mailCode";
	public static final String SHOW_IRS_MAIL_CODE_MODAL_PAGE = "/irs1042sforms/irsMailCodeSearchModal";
	
	// JSON API for dashboard webapp
	public static final String GET_USER_BY_BOOTSTRAP_VALIDATOR = "/administration/user/validator";
	public static final String GET_USER_BY_BOOTSTRAP_VALIDATOR_ORIGINAL = "/administration/user/validator/{originalUsername}";
	public static final String GET_USER_BY_DATATABLE_JSON = "/administration/user/datatable";
	public static final String GET_AML_BATCH_BY_DATATABLE = "/dashboard/aml/datatable";
	public static final String GET_AML_BATCH_CIF_BY_DATATABLE = "/aml/amlbatchcif/upload/datatable";
	public static final String GET_AML_BATCH_REQUEST_UPLOAD_BY_DATATABLE = "/aml/amlbatchrequest/upload/datatable";
	public static final String GET_DASHBOARD_COMMENT_BY_DATATABLE = "/aml/dashboardcomment/upload/datatable";
	public static final String GET_DASHBOARD_LOG_BY_DATATABLE = "/dashboard/aml/log/datatable";
	
	// Dashboard webapp page navigation
	/** The Constant SHOW_HOME_PAGE. */
	public static final String SHOW_HOME_PAGE = "/home";
	public static final String SHOW_LOGIN_PAGE = "/login";
	public static final String SHOW_ADMIN_PAGE = "/administration";
	public static final String SHOW_UPDATE_MODAL_PAGE = "/administration/user/update/{row_id}";
	public static final String SHOW_NEW_MODAL_PAGE = "/administration/user/new";
	public static final String SHOW_ADMIN_USER_EDIT_FORM = "/manageusers/editUserForm";
	public static final String SHOW_ADMIN_USER_CREATE_FORM = "/manageusers/createUserForm";
	public static final String UPDATE_USER_DATATABLE = "/administration/user/update";
	public static final String CREATE_USER_DATATABLE = "/administration/user/create";
	public static final String DELETE_USER_DATATABLE = "/administration/user/{row_id}";
	public static final String SHOW_CHANGE_PASSWORD_PAGE = "/login/changepassword";
	public static final String EXECUTE_CHANGE_PASSWORD = "/login/changepassword/execute";
	public static final String SHOW_403_ERROR_PAGE = "/error/403accessDeniedPage";
	public static final String SHOW_NO_AML_BATCH_REQUEST_PAGE = "/error/NoAmlBatchRequestPageAvailable";
	
	// Excel Converter
	public static final String SHOW_EXCEL_CONVERTER_PAGE = "/excelConverter";
	public static final String SHOW_UPLOAD_EXCEL_CONVERTER_MODAL = "/excelConverter/upload/modal";
	public static final String SHOW_UPLOAD_EXCEL_CONVERTER_MODAL_TILES = "/excelconverter/uploadExcelModal";
	public static final String OPEN_ADVISOR_TEXT_FILE_NEW_WINDOW = "/excelconverter/textFileToNewWindow";
	public static final String TXT_MERGING_INDIVIDUAL_FILE_LOCATION = "C://dashboard//advisorfile";
	public static final String AML_BATCH_REQUEST_UPLOAD_FILE_LOCATION = "C://dashboard//amlbatchrequestupload";
	public static final String COMMON_FILE_LOCATION = "C://dashboard//common";
	public static final String TXT_MERGING_INDIVIDUAL_FILE_LOCATION_FULL = "C://dashboard//advisorfile//advisorConverted.txt";
	
	// AML Batch Request
	public static final String SHOW_AML_BATCH_PAGE = "/aml";
	public static final String SHOW_AML_BATCH_REQUEST_PAGE_BY_REQUEST_ID = "/aml/request/requestId";
	public static final String SHOW_AML_BATCH_REQUEST_LOG_PAGE_BY_REQUEST_ID = "/aml/request/requestId/log";
	public static final String DELETE_AML_BATCH_REQUEST_PAGE_BY_REQUEST_ID = "/aml/request/requestId/delete/{requestId}";
	public static final String GENERATE_AML_BATCH_REQUEST_ID = "/aml/ajax";
	public static final String SHOW_CREATE_AML_BATCH_CIF_MODAL = "/amlbatchapprovals/createAmlBatchCifModal";
	public static final String SHOW_CREATE_DASHBOARD_COMMENT_MODAL = "/amlbatchapprovals/createCommentModal";
	public static final String SHOW_UPDATE_DASHBOARD_COMMENT_MODAL = "/amlbatchapprovals/updateCommentModal";
	public static final String SHOW_UPDATE_AML_BATCH_CIF_MODAL = "/amlbatchapprovals/updateAmlBatchCifModal";
	public static final String SHOW_UPLOAD_EXCEL_AML_BATCH_CIF_MODAL = "/amlbatchapprovals/uploadExcelModal";
	public static final String SHOW_AML_BATCH_REQUEST_UPLOAD_MODAL = "/amlbatchapprovals/amlBatchRequestUpload";
	public static final String SHOW_CREATE_AML_BATCH_CIF_BY_REQUEST_ID = "/aml/request/cif/modal/{requestId}";
	public static final String SHOW_CREATE_DASHBOARD_COMMENT_BY_ID = "/aml/dashboardcomment/modal/create/{id}";
	public static final String SHOW_UPDATE_DASHBOARD_COMMENT_BY_ID = "/aml/dashboardcomment/modal/update/{id}";
	public static final String SHOW_UPDATE_AML_BATCH_CIF_BY_ID = "/aml/request/cif/modal/update/{id}";
	public static final String SHOW_LOADING_AML_BATCH_CIF_BY_MODAL = "/aml/request/cif/modal/loading";
	public static final String SHOW_UPLOAD_EXCEL_AML_BATCH_CIF_MODAL_BY_REQUEST_ID = "/aml/amlbatchcif/upload/{requestId}";
	public static final String SHOW_AML_BATCH_REQUEST_UPLOAD_BY_REQUEST_ID = "/aml/amlbatchrequest/upload/{requestId}";
	
	public static final String EXECUTE_UPLOAD_EXCEL_AML_BATCH_CIF_MODAL = "/fileUpload/aml";
	
	public static final String EXECUTE_AML_BATCH_REQUEST_UPLOAD = "/aml/dashboardupload/upload/execute";
	public static final String OPEN_AML_BATCH_REQUEST_UPLOAD = "/aml/dashboardupload/upload/openNewWindow/{id}";
	public static final String DELETE_DASHBOARDUPLOAD_BY_ID = "/aml/dashboardupload/delete/{id}";
	public static final String EXECUTE_UPLOAD_EXCEL_CONVERTER_MODAL = "/fileUpload/excelConverter"; // ***** here
	public static final String EXECUTE_AML_BATCH_APPROVAL_BY_REQUEST_ID = "/aml/request/cif/modal/execute/approval/{requestId}";
	public static final String EXECUTE_AML_BATCH_REVERSAL_BY_REQUEST_ID = "/aml/request/cif/modal/execute/reversal/{requestId}";
	public static final String EXECUTE_AML_BATCH_DISAPPROVAL_BY_REQUEST_ID = "/aml/request/cif/modal/execute/disapproval/{requestId}";
	public static final String CREATE_AML_BATCH_CIF = "/aml/request/cif/create";
	public static final String UPDATE_AML_BATCH_CIF = "/aml/request/cif/update";
	public static final String DELETE_AML_BATCH_CIF_BY_ID = "/aml/request/cif/delete/{id}";
	public static final String CREATE_DASHBOARD_COMMENT= "/aml/request/dashboardcomment/create";
	public static final String UPDATE_DASHBOARD_COMMENT = "/aml/request/dashboardcomment/update";
	//public static final String DELETE_AML_BATCH_CIF_BY_ID = "/aml/request/cif/delete/{id}";
	public static final String SP_EXECUTE_AML_BATCH_APPROVAL_700 = "IBMOB700.execute_aml_approval_700";
	public static final String SP_EXECUTE_AML_BATCH_REVERSAL_700 = "IBMOB700.execute_aml_reversal_700";
	public static final String SP_EXECUTE_AML_BATCH_DISAPPROVAL_700 = "IBMOB700.execute_aml_disapproval_700";
	public static final String SP_EXECUTE_AML_BATCH_APPROVAL = "IBMOB700.execute_aml_approval";
	public static final String SP_EXECUTE_AML_BATCH_REVERSAL = "IBMOB700.execute_aml_reversal";
	public static final String SP_EXECUTE_AML_BATCH_DISAPPROVAL = "IBMOB700.execute_aml_disapproval";
	public static final String AML_BATCH_STATUS_CIF_NOT_FOUND = "CIF not found";
	public static final String AML_BATCH_STATUS_ONE_OR_MORE_CIF_NOT_FOUND = "one or more CIF not found";
	public static final String AML_BATCH_STATUS_APPROVED = "Approved";
	public static final String AML_BATCH_STATUS_REVERSED = "Reversed";
	public static final String AML_BATCH_STATUS_DISAPPROVED = "Disapproved";
	public static final String AML_BATCH_STATUS_CIF_NOT_IN_REVERSAL_MEMORY = "CIF not in reversal memory";
	public static final String AML_BATCH_STATUS_NO_CIF_TO_PROCESS = "No CIF to process";
	public static final String AML_BATCH_STATUS_AWAITING_EXECUTION = "Awaiting Execution";
	public static final String AML_BATCH_STATUS_COMPLETE = "Complete";
	public static final String AML_BATCH_STATUS_NOT_COMPLETE = "Not Complete";
	public static final String AML_BATCH_STATUS_REVERSAL_RECORD_NOT_FOUND = "Reversal record not found";
	public static final String AML_BATCH_TABLE_NAME = "AmlBatchRequest";
	public static final String DASHBOARD_UPLOAD_TABLE_NAME = "DashboardUpload";
	public static final String DASHBOARD_COMMENT_TABLE_NAME = "DashboardComment";
	public static final String AML_MESSAGE_1 = "Created AML Batch CIF.";
	public static final String AML_MESSAGE_2 = "Updated AML Batch CIF.";
	public static final String AML_MESSAGE_3 = "Created multiple AML Batch CIF using Excel file.";
	public static final String AML_MESSAGE_4 = "Created AML Batch Request.";
	public static final String AML_MESSAGE_5 = "Updated AML Batch Request.";
	public static final String AML_MESSAGE_6 = "Execute AML Batch Request is successful.";
	public static final String AML_MESSAGE_7 = "Execute AML Batch Request failed. ";
	public static final String AML_MESSAGE_8 = "Uploaded a file";
	public static final String AML_MESSAGE_9 = "Added a comment.";
	public static final String AML_MESSAGE_10 = "Comment message is updated.";
	
	public static final String AML_MESSAGE_EXCEPTION_1 = "There is no request to process since all CIF are Approved, Reversed or Disapproved.";
	public static final String AML_MESSAGE_EXCEPTION_2 = "There are no CIF records to process.";
	public static final String AML_MESSAGE_EXCEPTION_3 = "There is probably an SQL Exception thrown from Stored Procedure call.";
	public static final String AML_MESSAGE_EXCEPTION_4 = "There are one or more CIF records not in PAMLCUSTOM table.";
	public static final String AML_MESSAGE_EXCEPTION_5 = "There are one or more CIF records not in amlbatchreversal table.";
	
	// Dashboard Tiles page
	public static final String TILES_ADMINISTRATION_TEMPLATE = "tiles_administration";
	public static final String TILES_CHANGE_PASSWORD_TEMPLATE = "tiles_change_password";
	public static final String TILES_LOGIN_TEMPLATE = "tiles_login";
	public static final String TILES_HOME_TEMPLATE = "tiles_home";
	public static final String TILES_403_ERROR_TEMPLATE = "tiles_403accessDeniedPage";
	public static final String TILES_AML_BATCH_ERROR_TEMPLATE = "tiles_noAmlBatchRequestPageAvailable";
	public static final String TILES_AML_BATCH_PAGE_TEMPLATE = "tiles_amlBatch";
	public static final String TILES_AML_BATCH_PAGE_REQUEST_TEMPLATE = "tiles_amlBatchRequest";
	public static final String TILES_AML_BATCH_LOG_PAGE_REQUEST_TEMPLATE = "tiles_amlBatchRequestLog";
	public static final String TILES_EXCEL_CONVERTER_TEMPLATE = "tiles_excelConverter";
	

	public static String W8BENEFORM_TEMPLATE_DIRECTORY = "C://dashboard//w8beneform//template";
	public static String W8BENEFORM_TEMPLATE_UPLOAD_DIRECTORY = W8BENEFORM_TEMPLATE_DIRECTORY + "//upload_";
}
