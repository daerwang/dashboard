
--- Main Tables for User Management
CREATE TABLE IBMOB100.DashboardUser(
  user_id INT GENERATED ALWAYS AS IDENTITY,
  username VARCHAR(45),
  password VARCHAR(100),
  firstname VARCHAR(45),
  lastname VARCHAR(45),
  iseriesname VARCHAR(45),
  email VARCHAR(45),
  enabled INT NOT NULL DEFAULT 1,
  createdby VARCHAR(45),
  createdon TIMESTAMP,
  modifiedby VARCHAR(45),
  modifiedon TIMESTAMP,
  PRIMARY KEY (user_id));
CREATE UNIQUE INDEX "idx_username" ON IBMOB100.DashboardUser (username);
CREATE UNIQUE INDEX "idx_iseriesname" ON IBMOB100.DashboardUser (iseriesname);

CREATE TABLE IBMOB100.DashboardRole (
  role_id INT GENERATED ALWAYS AS IDENTITY,
  role_name VARCHAR(45) NOT NULL,
  user_id INT NOT NULL,
  createdby VARCHAR(45),
  createdon TIMESTAMP,
  modifiedby VARCHAR(45),
  modifiedon TIMESTAMP,
  PRIMARY KEY (role_id),
  CONSTRAINT "fk_roles_users"
    FOREIGN KEY (user_id)
    REFERENCES IBMOB100.DashboardUser (user_id)
    ON UPDATE RESTRICT);
    
CREATE TABLE IBMOB100.DashboardLog (
  id INT GENERATED ALWAYS AS IDENTITY,
  table_id INT NOT NULL,
  table_name VARCHAR(60) NOT NULL,
  description VARCHAR(200),
  createdby VARCHAR(45),
  createdon TIMESTAMP,
  modifiedby VARCHAR(45),
  modifiedon TIMESTAMP,
  PRIMARY KEY (id));
CREATE INDEX "id_index" ON IBMOB100.DashboardLog (id);
CREATE INDEX "id_table_name_index" ON IBMOB100.DashboardLog (id, table_id, table_name);

CREATE TABLE IBMOB100.DashboardUpload (
  id INT GENERATED ALWAYS AS IDENTITY,
  table_id INT NOT NULL,
  table_name VARCHAR(60) NOT NULL,
  description VARCHAR(200),
  filename VARCHAR(60),
  createdby VARCHAR(45),
  createdon TIMESTAMP,
  modifiedby VARCHAR(45),
  modifiedon TIMESTAMP,
  PRIMARY KEY (id));
CREATE INDEX "id_index_dashboardupload" ON IBMOB100.DashboardUpload (id);
CREATE INDEX "id_tableid_tablename_dashboardupload" ON IBMOB100.DashboardUpload (id, table_id, table_name);

CREATE TABLE IBMOB100.DashboardComment (
  id INT GENERATED ALWAYS AS IDENTITY,
  table_id INT NOT NULL,
  table_name VARCHAR(60) NOT NULL,
  description VARCHAR(100),
  message VARCHAR(1000),
  createdby VARCHAR(45),
  createdon TIMESTAMP,
  modifiedby VARCHAR(45),
  modifiedon TIMESTAMP,
  PRIMARY KEY (id));
CREATE INDEX "id_index_dashboardcomment" ON IBMOB100.DashboardComment (id);
CREATE INDEX "id_tableid_tablename_dashboardcomment" ON IBMOB100.DashboardComment(id, table_id, table_name);

CREATE TABLE IBMOB100.user_attempt (
  id INT GENERATED ALWAYS AS IDENTITY,
  username VARCHAR(45) NOT NULL,
  attempt INT NOT NULL,
  createdby VARCHAR(45),
  createdon TIMESTAMP,
  modifiedby VARCHAR(45),
  modifiedon TIMESTAMP,
  PRIMARY KEY (id));
  
  CREATE TABLE IBMOB100.user_password (
  id INT GENERATED ALWAYS AS IDENTITY,
  username VARCHAR(45) NOT NULL,
  password VARCHAR(60),
  createdby VARCHAR(45),
  createdon TIMESTAMP,
  modifiedby VARCHAR(45),
  modifiedon TIMESTAMP,
  PRIMARY KEY (id));

    
INSERT INTO IBMOB100.DashboardUser (username, password, firstname, lastname, email, createdon, modifiedon) VALUES (
	'nell'
	,'medina'
	,'Marinell'
	,'Medina'
	,'medinama@ph.ibm.com'
	, CURRENT TIMESTAMP
	, CURRENT TIMESTAMP
); 
INSERT INTO IBMOB100.DashboardRole (role_name, user_id) VALUES ('Administrator', 1);

-- Tables for REST Oauth2.0
CREATE TABLE IBMOB100.oauth_client_details (
  client_id varchar(100) NOT NULL,
  resource_ids varchar(100) DEFAULT NULL,
  client_secret varchar(100) DEFAULT NULL,
  scope varchar(100) DEFAULT NULL,
  authorized_grant_types varchar(100) DEFAULT NULL,
  web_server_redirect_uri varchar(100) DEFAULT NULL,
  authorities varchar(100) DEFAULT NULL,
  access_token_validity int DEFAULT NULL,
  refresh_token_validity int DEFAULT NULL,
  additional_information varchar(100) DEFAULT NULL,
  autoapprove varchar(100) DEFAULT NULL,
  PRIMARY KEY (client_id)
);

CREATE TABLE IBMOB100.oauth_access_token (
  token_id varchar(256) DEFAULT NULL,
  token blob,
  authentication_id varchar(256) DEFAULT NULL,
  user_name varchar(256) DEFAULT NULL,
  client_id varchar(256) DEFAULT NULL,
  authentication blob,
  refresh_token varchar(256) DEFAULT NULL
);  

INSERT INTO IBMOB100.oauth_client_details(client_id, resource_ids, client_secret, scope, authorized_grant_types, authorities, access_token_validity, refresh_token_validity)
VALUES ('oceandev', 'rest_api', '$2a$11$rkoH2xGwJHpWm3/dffocK.bRmYdmgko3C9h8wZpd0qwRw.uhuQSnq', 'trust,read,write', 'client_credentials,authorization_code,implicit,password,refresh_token', 'ROLE_DEV', '518400000', '5184000000');
INSERT INTO IBMOB100.oauth_client_details(client_id, resource_ids, client_secret, scope, authorized_grant_types, authorities, access_token_validity, refresh_token_validity)
VALUES ('oceantest', 'rest_api', '$2a$11$Xs.L9mHog2fzBl83nz.Zg.9c8xtdknKzNj4h3CrkKuO9VifggM1am', 'trust,read,write', 'client_credentials,authorization_code,implicit,password,refresh_token', 'ROLE_TEST', '518400000', '5184000000');


--- Tables for KYC approval and disapproval
CREATE TABLE IBMOB100.AmlBatchRequest(
  id INT GENERATED ALWAYS AS IDENTITY,
  name VARCHAR(70),
  description VARCHAR(200),
  requestId VARCHAR(45) NOT NULL,
  transactionType VARCHAR(45),
  status VARCHAR(45),
  createdby VARCHAR(45),
  createdon TIMESTAMP,
  modifiedby VARCHAR(45),
  modifiedon TIMESTAMP,
  PRIMARY KEY (id));
CREATE INDEX "request_id_index" ON IBMOB100.AmlBatchRequest (id);
CREATE INDEX "request_requestId_transactionType_index" ON IBMOB100.AmlBatchRequest (requestId, transactionType);
CREATE UNIQUE INDEX "requestId_UNIQUE" ON IBMOB100.AmlBatchRequest (requestId);

CREATE TABLE IBMOB100.AmlBatchCif (
  id INT GENERATED ALWAYS AS IDENTITY,
  requestId VARCHAR(45) NOT NULL,
  transactionType VARCHAR(45),
  cifReference VARCHAR(45),
  auditDescription VARCHAR(200),
  status VARCHAR(45),
  amlBatchRequest_id INT NOT NULL,
  iseriesname VARCHAR(45),
  createdby VARCHAR(45),
  createdon TIMESTAMP,
  modifiedby VARCHAR(45),
  modifiedon TIMESTAMP,
  PRIMARY KEY (id),
  CONSTRAINT "fk_container_request"
    FOREIGN KEY (amlBatchRequest_id)
    REFERENCES IBMOB100.AmlBatchRequest (id)
    ON UPDATE RESTRICT);
CREATE INDEX "amlBatchRequestId_index" ON IBMOB100.AmlBatchCif (amlBatchRequest_id);
CREATE INDEX "cif_requestId_transactionType_index" ON IBMOB100.AmlBatchCif (requestId, transactionType);
CREATE UNIQUE INDEX "cif_requestId_unique" ON IBMOB100.AmlBatchCif (requestId, cifReference);

CREATE TABLE IBMOB100.AmlBatchReversal (
  id INT GENERATED ALWAYS AS IDENTITY,
  requestId VARCHAR(45) NOT NULL,
  cifReference VARCHAR(45),
  flagApproval CHAR(5) NOT NULL,
  updatedOnAml DATE,
  batchUpdatedOnAml TIME,
  updatedByAml CHAR(15) NOT NULL,
  iseriesname VARCHAR(45),
  createdOn TIME,
  iseriesname VARCHAR(45),
  PRIMARY KEY (id));
  
  
  -- Table for W-8-BENE Form
  CREATE TABLE IBMOB100.w8beneform (
  id INT GENERATED ALWAYS AS IDENTITY,
  cif varchar(45),
  name varchar(500),
  physicalCountryInc varchar(45),
  physicalAddress varchar(500),
  physicalCity varchar(45),
  physicalCountry varchar(45),
  altAddress varchar(500),
  altCity varchar(45),
  altCountry varchar(45),
  account varchar(45),
  labelName varchar(200),
  officer varchar(45),
  branch varchar(45),
  altAddressLabel varchar(500),
  altCityLabel varchar(45),
  altCountryLabel varchar(45),
  PRIMARY KEY (id)
);