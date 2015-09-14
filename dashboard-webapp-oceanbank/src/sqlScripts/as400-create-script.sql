
ALTER TABLE IBMOB700.AmlBatchCif ADD iseriesname VARCHAR(45);
ALTER TABLE IBMOB700.AmlBatchReversal ADD iseriesname VARCHAR(45);
ALTER TABLE IBMOB700.DashboardUser ADD iseriesname VARCHAR(45);

DROP TABLE IBMOB700.AmlBatchRequest;
DROP TABLE IBMOB700.AmlBatchCif;

CREATE TABLE IBMOB700.AmlBatchRequest(
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
CREATE INDEX "request_id_index" ON IBMOB700.AmlBatchRequest (id);
CREATE INDEX "request_requestId_transactionType_index" ON IBMOB700.AmlBatchRequest (requestId, transactionType);
CREATE UNIQUE INDEX "requestId_UNIQUE" ON IBMOB700.AmlBatchRequest (requestId);

CREATE TABLE IBMOB700.AmlBatchCif (
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
    REFERENCES IBMOB700.AmlBatchRequest (id)
    ON UPDATE RESTRICT);
CREATE INDEX "amlBatchRequestId_index" ON IBMOB700.AmlBatchCif (amlBatchRequest_id);
CREATE INDEX "cif_requestId_transactionType_index" ON IBMOB700.AmlBatchCif (requestId, transactionType);
CREATE UNIQUE INDEX "cif_requestId_unique" ON IBMOB700.AmlBatchCif (requestId, cifReference);

CREATE TABLE IBMOB700.AmlBatchReversal (
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

INSERT INTO IBMOB700.DashboardUser (username, password, firstname, lastname, email, createdon, modifiedon) VALUES (
	'nell'
	,'medina'
	,'Marinell'
	,'Medina'
	,'medinama@ph.ibm.com'
	, CURRENT TIMESTAMP
	, CURRENT TIMESTAMP
); 
  
  
CREATE TABLE IBMOB700.DashboardUser(
  user_id INT GENERATED ALWAYS AS IDENTITY,
  username VARCHAR(45),
  password VARCHAR(45),
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
CREATE UNIQUE INDEX "idx_username" ON IBMOB700.DashboardUser (username);
CREATE UNIQUE INDEX "idx_iseriesname" ON IBMOB700.DashboardUser (iseriesname);

INSERT INTO IBMOB700.DashboardRole (role_name, user_id) VALUES (
	'Administrator'
	, 1
);

CREATE TABLE IBMOB700.DashboardRole (
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
    REFERENCES IBMOB700.DashboardUser (user_id)
    ON UPDATE RESTRICT);
    
CREATE TABLE IBMOB700.oauth_client_details (
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

CREATE TABLE IBMOB700.oauth_access_token (
  token_id varchar(256) DEFAULT NULL,
  token blob,
  authentication_id varchar(256) DEFAULT NULL,
  user_name varchar(256) DEFAULT NULL,
  client_id varchar(256) DEFAULT NULL,
  authentication blob,
  refresh_token varchar(256) DEFAULT NULL
);  

CREATE TABLE IBMOB700.oauth_refresh_token (
  token_id varchar(256) DEFAULT NULL,
  token blob,
  authentication blob
);

INSERT INTO IBMOB700.oauth_client_details(client_id, resource_ids, client_secret, scope, authorized_grant_types, authorities, access_token_validity, refresh_token_validity)
VALUES ('oceandev', 'rest_api', '$2a$11$rkoH2xGwJHpWm3/dffocK.bRmYdmgko3C9h8wZpd0qwRw.uhuQSnq', 'trust,read,write', 'client_credentials,authorization_code,implicit,password,refresh_token', 'ROLE_DEV', '518400000', '5184000000');
INSERT INTO IBMOB700.oauth_client_details(client_id, resource_ids, client_secret, scope, authorized_grant_types, authorities, access_token_validity, refresh_token_validity)
VALUES ('oceantest', 'rest_api', '$2a$11$Xs.L9mHog2fzBl83nz.Zg.9c8xtdknKzNj4h3CrkKuO9VifggM1am', 'trust,read,write', 'client_credentials,authorization_code,implicit,password,refresh_token', 'ROLE_TEST', '518400000', '5184000000');



DELETE FROM ALLEN.DashboardUser;
TRUNCATE TABLE ALLEN.DashboardUser;
SELECT * FROM ALLEN.DashboardUser;
SELECT * FROM ALLEN.DashboardRole;

    
CREATE TABLE IBMOB700.DashboardLog (
  id INT GENERATED ALWAYS AS IDENTITY,
  table_id INT NOT NULL,
  table_name VARCHAR(60) NOT NULL,
  description VARCHAR(200),
  createdby VARCHAR(45),
  createdon TIMESTAMP,
  modifiedby VARCHAR(45),
  modifiedon TIMESTAMP,
  PRIMARY KEY (id));
CREATE INDEX "id_index" ON IBMOB700.DashboardLog (id);
CREATE INDEX "id_table_name_index" ON IBMOB700.DashboardLog (id, table_id, table_name);

CREATE TABLE IBMOB700.DashboardUpload (
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
CREATE INDEX "id_index_dashboardupload" ON IBMOB700.DashboardUpload (id);
CREATE INDEX "id_tableid_tablename_dashboardupload" ON IBMOB700.DashboardUpload (id, table_id, table_name);

CREATE TABLE IBMOB700.DashboardComment (
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
CREATE INDEX "id_index_dashboardcomment" ON IBMOB700.DashboardComment (id);
CREATE INDEX "id_tableid_tablename_dashboardcomment" ON IBMOB700.DashboardComment(id, table_id, table_name);

