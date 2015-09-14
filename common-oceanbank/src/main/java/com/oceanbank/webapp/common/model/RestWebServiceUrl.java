/**
 * 
 * Copyright (c) 2014-2015 the original author or authors.
 */
package com.oceanbank.webapp.common.model;


/**
 * The Class ObRestConstants.
 * 
 * @author Marinell Medina
 * @since 03.10.2015
 */
public final class RestWebServiceUrl {
	
	
	/**
	 * Instantiates a new ob rest constants.
	 */
	private RestWebServiceUrl(){}
	
	
	// Service URL for User controller
	/** The Constant GET_USER_SEARCH_BY_DATATABLE. */
	public static final String GET_USER_SEARCH_BY_DATATABLE = "/api/user/search/datatable";
	
	/** The Constant GET_USER_USERNAME_BY_BOOTSTRAP_VALIDATOR. */
	public static final String GET_USER_USERNAME_BY_BOOTSTRAP_VALIDATOR = "/api/user/username";
	
	/** The Constant GET_USER_USERNAME_BY_BOOTSTRAP_VALIDATOR_ORIGINAL. */
	public static final String GET_USER_USERNAME_BY_BOOTSTRAP_VALIDATOR_ORIGINAL = "/api/user/username/{originalUsername}";
	
	/** The Constant GET_USER_USERNAME. */
	public static final String GET_USER_USERNAME = "/api/user/username/{username}";
	
	/** The Constant EXECUTE_CHANGE_PASSWORD. */
	public static final String EXECUTE_CHANGE_PASSWORD = "/api/user/changepassword";
    
    /** The Constant GET_USER. */
    public static final String GET_USER = "/api/user/{userid}";
    
    /** The Constant GET_USER_WITH_ROLES. */
    public static final String GET_USER_WITH_ROLES = "/api/user/roles/{userid}";
    
    /** The Constant GET_USER_WITH_ROLES_BY_USERNAME. */
    public static final String GET_USER_WITH_ROLES_BY_USERNAME = "/api/user/roles/username/{username}";
    
    /** The Constant GET_ALL_USER. */
    public static final String GET_ALL_USER = "/api/user";
    
    /** The Constant CREATE_USER. */
    public static final String CREATE_USER = "/api/user/create";
    
    /** The Constant DELETE_USER. */
    public static final String DELETE_USER = "/api/user/delete/{userid}";
    
    /** The Constant UPDATE_USER. */
    public static final String UPDATE_USER = "/api/user/update";

    // Service URL for Role controller
    /** The Constant GET_ROLE_USERID. */
    public static final String GET_ROLE_USERID = "/api/role/user/{userid}";
    
    // Service URL for AML Batch controller
    /** The Constant GET_AML_BATCH_REQUEST_ID. */
    public static final String GET_AML_BATCH_REQUEST_ID = "/api/dashboard/aml/generateRequestId/{id}";
    
    /** The Constant CREATE_AML_BATCH_CIF. */
    public static final String CREATE_AML_BATCH_CIF = "/api/dashboard/aml/cif/create";
    public static final String CREATE_MANY_AML_BATCH_CIF = "/api/dashboard/aml/cif/many/create";
    public static final String DELETE_AML_BATCH_CIF_BY_ID = "/api/dashboard/aml/cif/delete/{id}";
    public static final String UPDATE_AML_BATCH_CIF = "/api/dashboard/aml/cif/update";
    public static final String CREATE_AML_BATCH_REQUEST_UPLOAD = "/api/aml/dashboardupload/create";
    public static final String CREATE_DASHBOARD_COMMENT = "/api/aml/dashboardcomment/create";
    public static final String UPDATE_DASHBOARD_COMMENT = "/api/aml/dashboardcomment/update";
    public static final String FIND_AML_BATCH_UPLOAD_BY_ID = "/api/aml/amlbatchrequestupload/find/{id}";
    public static final String FIND_DASHBOARD_COMMENT_BY_ID = "/api/aml/dashboardcomment/find/{id}";
    public static final String DELETE_AML_BATCH_UPLOAD_BY_ID = "/api/aml/amlbatchrequestupload/delete/{id}";
    public static final String DELETE_DASHBOARD_COMMENT_BY_ID = "/api/aml/dashboardcomment/delete/{id}";
    public static final String FIND_AML_BATCH_CIF_BY_ID = "/api/dashboard/aml/cif/find/{id}";
    public static final String EXECUTE_BATCH_APPROVAL_REQUEST_BY_REQUEST_ID = "/api/dashboard/aml/cif/approval/{requestId}";
    public static final String EXECUTE_BATCH_REVERSAL_REQUEST_BY_REQUEST_ID = "/api/dashboard/aml/cif/reversal/{requestId}";
    public static final String EXECUTE_BATCH_DISAPPROVAL_REQUEST_BY_REQUEST_ID = "/api/dashboard/aml/cif/disapproval/{requestId}";
    
    /** The Constants for DashboardLog. */
    public static final String CREATE_DASHBOARDLOG = "/api/dashboard/log/create";
    public static final String FIND_DASHBOARDLOG_BY_TABLEID_AND_TABLENAME = "/api/dashboard/log/find";
    public static final String GET_DASHBOARDLOG_DATATABLE = "/api/dashboard/log/datable";
    
    /** The Constant CREATE_AML_BATCH_REQUEST. */
    public static final String CREATE_AML_BATCH_REQUEST = "/api/dashboard/aml/request/create";
    
    /** The Constant UPDATE_AML_BATCH_REQUEST. */
    public static final String UPDATE_AML_BATCH_REQUEST = "/api/dashboard/aml/request/update";
    
    /** The Constant GET_AML_BATCH_REQUEST_BY_REQUEST_ID. */
    public static final String GET_AML_BATCH_REQUEST_BY_REQUEST_ID = "/api/dashboard/aml/requestId/{requestId}";
    public static final String GET_AML_BATCH_REQUEST_BY_ID = "/api/dashboard/aml/id/{id}";
    
    /** The Constant GET_AML_BATCH_SEARCH_BY_DATATABLE. */
    public static final String GET_AML_BATCH_DATATABLE = "/api/aml/datatable";
    public static final String GET_AML_BATCH_CIF_DATATABLE = "/api/aml/datatable/amlbatchcif";
    public static final String GET_AML_BATCH_REQUEST_UPLOAD_DATATABLE = "/api/aml/datatable/dashboardupload";
    public static final String GET_DASHBOARD_COMMENT_DATATABLE = "/api/aml/datatable/dashboardcomment";
    
    /** The Constant DELETE_AML_BATCH_REQUEST. */
    public static final String DELETE_AML_BATCH_REQUEST_BY_REQUESTID = "/api/dashboard/aml/requestId/delete/{requestId}";
    
    // Service URL for IRS Form controller
    /** The Constant GET_IRS_FORM_SEARCH_BY_DATATABLE. */
    public static final String GET_IRS_FORM_SEARCH_BY_DATATABLE = "/api/dashboard/irs/datatable/search";
    
    /** The Constant GENERATE_SELECTED_PDF. */
    public static final String GENERATE_SELECTED_PDF = "/api/dashboard/1042s/generatePdfFromSelected";
    
    /** The Constant GENERATE_SELECTED_PDF_ALL. */
    public static final String GENERATE_SELECTED_PDF_ALL = "/api/dashboard/1042s/generatePdfFromSelectedAll";
    
    /** The Constant GET_IRS_MAIL_CODE_DISTINCT. */
    public static final String GET_IRS_MAIL_CODE_DISTINCT = "/api/dashboard/1042s/mailCodeDistinct";
    
    /** The Constant GENERATE_SELECTED_PDF_MAIL_CODE. */
    public static final String GENERATE_SELECTED_PDF_MAIL_CODE = "/api/dashboard/1042s/generatePdfFromSelectedMailCode";
    
}
