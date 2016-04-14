ALTER TABLE `usersdb`.`dashboarduser` 
ADD COLUMN `iseriesname` VARCHAR(45) NULL DEFAULT NULL AFTER `lastname`,
ADD UNIQUE INDEX `iseriesname_UNIQUE` (`iseriesname` ASC);



CREATE TABLE `dashboarduser` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(60) DEFAULT NULL,
  `firstname` varchar(45) DEFAULT NULL,
  `lastname` varchar(45) DEFAULT NULL,
  `iseriesname` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `enabled` int(11) NOT NULL DEFAULT '1',
  `createdby` varchar(45) DEFAULT NULL,
  `createdon` datetime DEFAULT NULL,
  `modifiedby` varchar(45) DEFAULT NULL,
  `modifiedon` datetime DEFAULT NULL,
  `accountNonLocked` tinyint(4) DEFAULT '1',
  `accountNonExpired` tinyint(4) DEFAULT '1',
  `resetToken` varchar(60) DEFAULT NULL,
  `resetExpiry` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `idx_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;



CREATE TABLE IF NOT EXISTS `test-oceanbank-db`.`DashboardRole` (
  `role_id` INT NOT NULL AUTO_INCREMENT,
  `role_name` VARCHAR(45) NOT NULL,
  `user_id` INT NOT NULL,
  `createdby` VARCHAR(45) NULL,
  `createdon` DATETIME NULL,
  `modifiedby` VARCHAR(45) NULL,
  `modifiedon` DATETIME NULL,
  PRIMARY KEY (`role_id`),
  INDEX `fk_role_user_idx` (`user_id` ASC),
  CONSTRAINT `fk_role_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `test-oceanbank-db`.`DashboardUser` (`user_id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `test-oceanbank-db`.`DashboardAmlBatchRequest` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(70) NULL,
  `description` VARCHAR(200) NULL,
  `requestId` VARCHAR(45) NOT NULL,
  `transactionType` VARCHAR(45) NOT NULL,
  `createdby` VARCHAR(45) NULL,
  `createdon` DATETIME NULL,
  `modifiedby` VARCHAR(45) NULL,
  `modifiedon` DATETIME NULL,
  PRIMARY KEY (`id`),
  INDEX `request_id_index` (`id` ASC),
  INDEX `request_requestId_transactionType_index` (`requestId` ASC, `transactionType` ASC),
  UNIQUE INDEX `requestId_UNIQUE` (`requestId` ASC))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `test-oceanbank-db`.`DashboardAmlBatchContainer` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `requestId` VARCHAR(45) NOT NULL,
  `transactionType` VARCHAR(45) NOT NULL,
  `cifReference` VARCHAR(45) NOT NULL,
  `auditDescription` VARCHAR(200) NOT NULL,
  `status` VARCHAR(45) NULL,
  `amlBatchRequest_id` INT NOT NULL,
  `createdby` VARCHAR(45) NULL,
  `createdon` DATETIME NULL,
  `modifiedby` VARCHAR(45) NULL,
  `modifiedon` DATETIME NULL,
  PRIMARY KEY (`id`),
  INDEX `amlBatchRequestId_index` (`amlBatchRequest_id` ASC),
  INDEX `container_requestId_transactionType_index` (`requestId` ASC, `transactionType` ASC),
  CONSTRAINT `fk_container_request`
    FOREIGN KEY (`amlBatchRequest_id`)
    REFERENCES `test-oceanbank-db`.`DashboardAmlBatchRequest` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


DROP TABLE IF EXISTS oauth_client_details;
 
CREATE TABLE oauth_client_details (
  client_id varchar(100) NOT NULL,
  resource_ids varchar(100) DEFAULT NULL,
  client_secret varchar(100) DEFAULT NULL,
  scope varchar(100) DEFAULT NULL,
  authorized_grant_types varchar(100) DEFAULT NULL,
  web_server_redirect_uri varchar(100) DEFAULT NULL,
  authorities varchar(100) DEFAULT NULL,
  access_token_validity int(11) DEFAULT NULL,
  refresh_token_validity int(11) DEFAULT NULL,
  additional_information varchar(100) DEFAULT NULL,
  autoapprove varchar(100) DEFAULT NULL,
  PRIMARY KEY (client_id)
);

INSERT INTO oauth_client_details(client_id, resource_ids, client_secret, scope, authorized_grant_types, authorities, access_token_validity, refresh_token_validity)
VALUES ('oceandev', 'rest_api', '$2a$11$rkoH2xGwJHpWm3/dffocK.bRmYdmgko3C9h8wZpd0qwRw.uhuQSnq', 'trust,read,write', 'client_credentials,authorization_code,implicit,password,refresh_token', 'ROLE_DEV', '518400', '5184000');

INSERT INTO oauth_client_details(client_id, resource_ids, client_secret, scope, authorized_grant_types, authorities, access_token_validity, refresh_token_validity)
VALUES ('oceantest', 'rest_api', '$2a$11$Xs.L9mHog2fzBl83nz.Zg.9c8xtdknKzNj4h3CrkKuO9VifggM1am', 'trust,read,write', 'client_credentials,authorization_code,implicit,password,refresh_token', 'ROLE_TEST', '518400', '5184000');
 
DROP TABLE IF EXISTS oauth_access_token;
 
CREATE TABLE oauth_access_token (
  token_id varchar(256) DEFAULT NULL,
  token blob,
  authentication_id varchar(256) DEFAULT NULL,
  user_name varchar(256) DEFAULT NULL,
  client_id varchar(256) DEFAULT NULL,
  authentication blob,
  refresh_token varchar(256) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
 
 
DROP TABLE IF EXISTS oauth_refresh_token;
 
CREATE TABLE oauth_refresh_token (
  token_id varchar(256) DEFAULT NULL,
  token blob,
  authentication blob
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE TNPARG (
	TNGTIN DECIMAL(9, 0) NOT NULL DEFAULT 0
	,TNGNA1 CHAR(40)  NOT NULL DEFAULT ''
	,TNGNA2 CHAR(40)  NOT NULL DEFAULT ''
	,TNGNA3 CHAR(40)  NOT NULL DEFAULT ''
	,TNGADR CHAR(40)  NOT NULL DEFAULT ''
	,TNGAD2 CHAR(40)  NOT NULL DEFAULT ''
	,TNGCTY CHAR(20)  NOT NULL DEFAULT ''
	,TNGSTT CHAR(2)  NOT NULL DEFAULT ''
	,TNGZIP DECIMAL(9, 0) NOT NULL DEFAULT 0
	,TNGPYT DECIMAL(9, 0) NOT NULL DEFAULT 0
	,TNGPYN CHAR(35)  NOT NULL DEFAULT ''
	,`TNGPY#` CHAR(10)  NOT NULL DEFAULT ''
	,TNGPST CHAR(2)  NOT NULL DEFAULT ''
	,TNGPYS CHAR(15)  NOT NULL DEFAULT ''
	,TNGWIE CHAR(1)  NOT NULL DEFAULT ''
	,TNGPCD CHAR(2)  NOT NULL DEFAULT ''
	,TNGCCD CHAR(2)  NOT NULL DEFAULT ''
	,TNGPHO DECIMAL(10, 0) NOT NULL DEFAULT 0
	,TNGPHE DECIMAL(10, 0) NOT NULL DEFAULT 0
	,TNGCNM CHAR(45)  NOT NULL DEFAULT ''
	,TNGDTTL CHAR(45)  NOT NULL DEFAULT ''
	,TNGFTIN CHAR(9)  NOT NULL DEFAULT ''
	,TNGIIN CHAR(19)  NOT NULL DEFAULT ''
	,TNGSTS3 DECIMAL(2, 0) NOT NULL DEFAULT 0
	,TNGSTS4 DECIMAL(2, 0) NOT NULL DEFAULT 0
	,TNPYGIIN CHAR(19)  NOT NULL DEFAULT ''
	,TNPRINM CHAR(40)  NOT NULL DEFAULT ''
	,TNPRIEIN DECIMAL(9, 0) NOT NULL DEFAULT 0
	);
	
INSERT INTO TNPARG (TNGTIN,TNGNA1,TNGNA2,TNGNA3,TNGADR,TNGAD2,TNGCTY,TNGSTT,TNGZIP,TNGPYT,TNGPYN,`TNGPY#`,TNGPST,TNGPYS,TNGWIE,TNGPCD,TNGCCD,TNGPHO,TNGPHE,TNGCNM,TNGDTTL,TNGFTIN,TNGIIN,TNGSTS3,TNGSTS4,TNPYGIIN,TNPRINM,TNPRIEIN) VALUES ('592237280','Ocean Bank                              ','','','780 NW 42nd Avenue                      ','','Miami               ','FL','331265597','0','','','','','0','','','3055695237','0','Loida Denis                                  ','VP & Systems Support                         ','','','1','0','','','0');

CREATE TABLE CIS7349F2 (
	F2MAILC CHAR(1) NOT NULL DEFAULT ''
	,F2MAILD CHAR(35) NOT NULL DEFAULT ''
	,F2COUNT NUMERIC(6, 0) NULL DEFAULT 0
	) 
	
CREATE TABLE IF NOT EXISTS `test-oceanbank-db`.`DashboardLog` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `table_id` INT NOT NULL,
  `table_name` VARCHAR(60) NOT NULL,
  `description` VARCHAR(200) NULL,
  `createdby` VARCHAR(45) NULL,
  `createdon` DATETIME NULL,
  `modifiedby` VARCHAR(45) NULL,
  `modifiedon` DATETIME NULL,
  PRIMARY KEY (`id`),
  INDEX `id_index` (`id` ASC),
  INDEX `id_table_name_index` (`id` ASC, `table_id` ASC, `table_name` ASC))
ENGINE = InnoDB


CREATE TABLE `w8beneform` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cif` varchar(45) DEFAULT NULL,
  `name` varchar(500) DEFAULT NULL,
  `physicalCountryInc` varchar(45) DEFAULT NULL,
  `physicalAddress` varchar(500) DEFAULT NULL,
  `physicalCity` varchar(45) DEFAULT NULL,
  `physicalCountry` varchar(45) DEFAULT NULL,
  `altAddress` varchar(500) DEFAULT NULL,
  `altCity` varchar(45) DEFAULT NULL,
  `altCountry` varchar(45) DEFAULT NULL,
  `account` varchar(45) DEFAULT NULL,
  `labelName` varchar(200) DEFAULT NULL,
  `officer` varchar(45) DEFAULT NULL,
  `branch` varchar(45) DEFAULT NULL,
  `altAddressLabel` varchar(500) DEFAULT NULL,
  `altCityLabel` varchar(45) DEFAULT NULL,
  `altCountryLabel` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8;


CREATE TABLE `user_attempt` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `attempt` int(11) NOT NULL DEFAULT '0',
  `modifiedby` varchar(45) DEFAULT NULL,
  `modifiedon` datetime DEFAULT NULL,
  `createdby` varchar(45) DEFAULT NULL,
  `createdon` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_uq` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

CREATE TABLE `user_password` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(60) DEFAULT NULL,
  `modifiedby` varchar(45) DEFAULT NULL,
  `modifiedon` datetime DEFAULT NULL,
  `createdby` varchar(45) DEFAULT NULL,
  `createdon` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;


