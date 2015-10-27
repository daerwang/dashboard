
SELECT * FROM ALLEN.DASHBOARDUSER;
SELECT * FROM ALLEN.DASHBOARDROLE;


UPDATE ALLEN.DASHBOARDUSER A
SET (A.password) = (
		CASE A.username
			WHEN IFNULL(SELECT B.role_name FROM ALLEN.DASHBOARDROLE B WHERE B.role_name = A.username) THEN 'walah'
			ELSE 'AYUN'
		END
	)
WHERE A.requestId = i_requestId;

UPDATE XPERTV700.PAMLCUSTOM A
SET (AMLLFLAAPR) = (
						CASE A.AMLLFLARQD
							WHEN '1' THEN 'A'
							WHEN '2' THEN 'A'
							WHEN '3' THEN '4'
							WHEN '4' THEN 'G'
						END
						,S_AMLDCURREN
						,CURRENT TIME
						,'EOD'
					)
WHERE A.AMLCCIFCOD IN (SELECT cifReference 
						FROM IBMOB700.AMLBATCHCIF 
						WHERE requestId = 'AML-2015-0002')
AND A.AMLCSTAKYC = '1';


UPDATE ALLEN.DASHBOARDUSER A
SET A.password = 'ayun oh'
WHERE NOT EXISTS(
	SELECT B.role_name FROM ALLEN.DASHBOARDROLE B WHERE B.role_name = A.username
	)
	
SELECT COALESCE(A.password,'Approved'), B.role_name 
FROM ALLEN.DASHBOARDROLE B 
LEFT JOIN ALLEN.DASHBOARDUSER A ON B.role_name = A.username

UPDATE IBMOB700.AMLBATCHCIF A
		SET A.status = 'Approved'
	WHERE EXISTS(
			SELECT 1 FROM XPERTV700.PAMLCUSTOM B
			WHERE B.AMLCCIFCOD = A.cifReference
		)	
	AND A.requestId = 'AML-2015-0002';
	
UPDATE IBMOB700.AMLBATCHCIF A
		SET A.status = 'CIF not found'
	WHERE NOT EXISTS(
			SELECT 1 FROM XPERTV700.PAMLCUSTOM B
			WHERE B.AMLCCIFCOD = A.cifReference
		)	
	AND A.requestId = 'AML-2015-0002';

-- testing
SELECT AMLCCIFCOD, AMLCPERSON FROM XPERTV700.PAMLCUSTOM WHERE AMLCPERSON != 'P' AND AMLCCIFCOD LIKE 'AAA0%' ORDER BY AMLCCIFCOD desc;
	
SELECT * FROM XPERTV700.KYCDISBTCH;
UPDATE XPERTV700.PAMLCUSTOM A
SET AMLLFLAAPR = 'B'
WHERE AMLCCIFCOD IN ('BAA0707','FAB5344');

UPDATE IBMOB700.AMLBATCHCIF A
	SET A.status = ''
WHERE A.requestId = 'AML-2015-0002';
	
SELECT * FROM IBMOB700.AMLBATCHCIF WHERE requestId = 'AML-2015-0002';
SELECT AMLLFLAAPR FROM XPERTV700.PAMLCUSTOM WHERE AMLCCIFCOD IN ('BAA0707','FAB5344');

SELECT * FROM XPERTV700.PAMLLOGCIF WHERE AMLCCIFCOD = 'AAA0552' AND AMLDDATLOG = '2015-02-03' AND AMLTTIMLOG = '12:17:27' ORDER BY AMLDDATLOG DESC;
SELECT * FROM XPERTV700.PAMLLOGCIF WHERE AMLCCIFCOD = 'BAA0707' ORDER BY AMLDDATLOG DESC;
SELECT * FROM XPERTV700.PAMLLOGCIF WHERE AMLCCIFCOD = 'AAA0552' ORDER BY AMLDDATLOG DESC;


SELECT * FROM XPERTV700.PAMLFLAGCU;
SELECT P.AMLLFLAAPR,P.AMLIFLAACL,Z.AMLLFLASHD
FROM PAMLFLAGCU Z
INNER JOIN PAMLCUSTOM P ON Z.AMLLFLAAPR = P.AMLLFLAAPR	
WHERE P.AMLCCIFCOD = 'FAB5344'



SELECT * FROM IBMOB700.oauth_client_details ;
SELECT * FROM IBMOB700.amlbatchrequest;
SELECT * FROM IBMOB700.amlbatchcif;

UPDATE IBMOB700.AMLBATCHCIF A
		SET A.status = ''
	WHERE EXISTS(
			SELECT 1 FROM XPERTV700.PAMLCUSTOM B WHERE B.AMLCCIFCOD = A.cifReference
		)
	AND A.requestId = 'AML-2015-0002';

UPDATE XPERTV700.PAMLCUSTOM A
SET AMLLFLAAPR = 'X'
WHERE AMLCCIFCOD IN ('AAA0552', 'AAA1063')
SELECT AMLLFLARQD, AMLLFLAAPR, AMLCCIFCOD FROM XPERTV700.PAMLCUSTOM A WHERE A.AMLCCIFCOD IN ('AAA0552', 'AAA1063', 'AAA6303');
SELECT AMLCCIFCOD,AMLLFLAAPR, AMLDUPDATE, AMLTUPDATE, AMLCUPDUSE FROM XPERTV700.PAMLCUSTOM A WHERE A.AMLCCIFCOD IN ('AAA0552', 'AAA1063');
SELECT AMLLFLARQD, AMLLFLAAPR, AMLCCIFCOD FROM XPERTV700.PAMLCUSTOM A WHERE A.AMLCCIFCOD IN ('GAA5191');

UPDATE XPERTV700.PAMLCUSTOM A
	SET (AMLLFLAAPR, AMLDUPDATE, AMLTUPDATE, AMLCUPDUSE) = (
							(CASE A.AMLLFLARQD
								WHEN '1' THEN 'A'
								WHEN '2' THEN 'A'
								WHEN '3' THEN '4'
								WHEN '4' THEN 'G'
							END)
							,'2015-02-03'
							,CURRENT TIME
							,'EOD'
						)
	WHERE A.AMLCCIFCOD IN (SELECT cifReference 
							FROM IBMOB700.AMLBATCHCIF 
							WHERE requestId = 'AML-2015-0002')
	AND A.AMLCSTAKYC = '1';			
-- testing



SELECT * FROM XPERTV700.TAMLAPPROV WHERE AMLCCIFCOD IN ('AAA0552', 'CAB2789');
SELECT * FROM XPERTV700.PAMLGENCOP;



UPDATE XPERTV700.PAMLCUSTOM A
SET (AMLLFLAAPR) = (
					SELECT (
							CASE A.AMLLFLARQD
								WHEN '1' THEN 'A'
								WHEN '2' THEN 'A'
								WHEN '3' THEN '4'
								WHEN '4' THEN 'G'
							END
							)
					FROM XPERTV700.TAMLAPPROV B
					WHERE A.AMLCCIFCOD = B.AMLCCIFCOD
				)
WHERE A.AMLCCIFCOD IN ('AAA0552', 'FAA5126', 'FAA6072')
AND A.AMLCSTAKYC = '1';

SELECT COUNT(*) FROM XPERTV700.PAMLCUSTOM WHERE AMLCCIFCOD = 'FAA6072';


SELECT cifReference, auditDescription 
		FROM IBMOB700.AMLBATCHCIF 
		WHERE requestId = 'AML-2015-0002';
		
SELECT AMLLFLARQD, AMLLFLAAPR, AMLCCIFCOD		
FROM XPERTV700.PAMLCUSTOM A
INNER JOIN IBMOB700.AMLBATCHCIF B ON B.cifReference = A.AMLCCIFCOD AND B.requestId = 'AML-2015-0002'
WHERE A.AMLCBANKNU = 1
	AND A.AMLCSTAKYC = '1';

SELECT AMLLFLARQD, AMLCCIFCOD FROM XPERTV700.PAMLCUSTOM WHERE AMLLFLARQD IN ('3', '4')
INSERT INTO XPERTV700.TAMLAPPROV (AMLCCIFCOD,AMLLFLARQD,AMLLFLAAPR,AMLXMESSAG,AMLLFLAAP1) VALUES ('FAA5126   ','2','A','Batch Approved for review of Medium Risk KYCs','A');
INSERT INTO XPERTV700.TAMLAPPROV (AMLCCIFCOD,AMLLFLARQD,AMLLFLAAPR,AMLXMESSAG,AMLLFLAAP1) VALUES ('FAA6072   ','2','A','Batch Approved for review of Medium Risk KYCs','A');
-- CAB2789 
-- FAA5126 (3)
-- FAA6072 (4)

SELECT AMLLFLAAPR, AMLDUPDATE, AMLTUPDATE, AMLCUPDUSE 
FROM XPERTV700.PAMLCUSTOM
WHERE AMLCCIFCOD IN ('AAA0552', 'AAA0650');

SELECT * 
FROM XPERTV700.PAMLCUSTOM
WHERE AMLCCIFCOD IN ('AAA0552', 'AAA0650', 'AAA1063', 'AAA1157', 'AAA1470');



select * from IBMOB700.oauth_client_details;
select * from IBMOB700.oauth_access_token;
select * from IBMOB700.oauth_refresh_token;

DELETE from IBMOB700.oauth_client_details;
DELETE from IBMOB700.oauth_access_token;
DELETE from IBMOB700.oauth_refresh_token;

SELECT COUNT(*) FROM FALLIB.CIS7349F1;
SELECT COUNT(DISTINCT TNFCSZ) FROM FALLIB.CIS7349F1 WHERE TNFCSZ = ' ';
SELECT * FROM FALLIB.CIS7349F1 WHERE TNCIF#='JAA0799';
SELECT * FROM FALLIB.CIS7349F1 WHERE TNFNM1 LIKE '%CESAR AUGUSTO%';
SELECT * FROM FALLIB.TNPARG;
SELECT * FROM FALLIB.CIS7349F2;


SELECT * FROM IBMOB700.dashboarduser;
SELECT * FROM IBMOB700.dashboardrole;

DELETE FROM IBMOB700.dashboarduser;

DROP TABLE IBMOB700.dashboardrole;
DROP TABLE IBMOB700.dashboarduser;

DROP TABLE IBMOB700.DashboardAmlBatchRequest;
DROP TABLE IBMOB700.DashboardAmlBatchContainer;

PAMLLOGCIF
PAMLTYPLOG
OAMLLOGCIF
OAMLTYPLOG

PAMLCUSTOM

SELECT * FROM XPERTV700.PAMLUSERID WHERE AMLCUSERID LIKE 'MXM0%' and AMLAUSERNA LIKE '%IBM%';
SELECT * FROM XPERTV17.PAMLUSERID WHERE AMLCUSERID LIKE 'MXM0%' and AMLAUSERNA LIKE '%IBM%';
SELECT * FROM XPERTV700.PAMLUSERID WHERE AMLAUSERNA LIKE '%IBM%';
SELECT * FROM XPERTV700.PAMLUSERID;
UPDATE XPERTV700.PAMLUSERID SET AMLCROLECO = 2 WHERE AMLCUSERID = 'MXM01018';
UPDATE XPERTV17.PAMLUSERID SET AMLCROLECO = 2 WHERE AMLCUSERID = 'MXM01018';
SELECT * FROM XPERTLIB.PAMLENVDES;
SELECT * FROM XPERTV17.PAMLUSERID WHERE AMLCUSERID LIKE 'HRKYC%';
SELECT * FROM XPERTV16.PAMLUSERID WHERE AMLCUSERID LIKE 'HRKYC%';
SELECT * FROM XPERTV16.PAMLUSERID;

INSERT INTO ALLEN.ROLES (role_name)

INSERT INTO ALLEN.USERS (username, password) VALUES ('admin1', 'password1');
INSERT INTO ALLEN.ROLES (role_name, user_id) VALUES ('ADMIN_ROLE', 1);
insert into ALLEN.ROLES (ROLE_NAME, USER_ID) values ('ADMIN', 32)

SELECT * FROM FALLIB.CIS7349F1 fetch first 100 rows only;
SELECT * FROM FALLIB.CIS7349F1 WHERE TNFNM1 LIKE '%ALICIA%';
SELECT * FROM FALLIB.CIS7349F1 WHERE TNSNME LIKE '%ALICIA AAGAARD CELIS%';
SELECT COUNT(*) FROM FALLIB.CIS7349F1; --6891--4891 

SELECT TNSNME FROM (
	SELECT row_number() OVER (ORDER BY TNSNME) as name , TNSNME, TNFEIN, TNACCT 
	FROM FALLIB.CIS7349F1) as t
WHERE t.name BETWEEN 400 AND 450;


SELECT DISTINCT TNFNM1, TNACCT, TNFEIN FROM FALLIB.CIS7349F1; 

SELECT DISTINCT TNLFM, TNACCT, TNFFTI FROM FALLIB.CIS7349F1;
SELECT COUNT(DISTINCT TNTIN) as count FROM FALLIB.CIS7349F1; 
SELECT DISTINCT TNTIN as count FROM FALLIB.CIS7349F1; 

SELECT * FROM ALLEN.USERS;
SELECT * FROM ALLEN.ROLES;
SELECT * FROM ALLEN.ROLES a where a.user_id = 16;

DELETE FROM ALLEN.USERS
DELETE FROM ALLEN.ROLES
ALTER ALLEN.ROLES ALTER COLUMN RESTART WITH 1 

ALTER TABLE ALLEN.USERS DROP CONSTRAINT "fk_users_roles"

	
DROP TABLE ALLEN.USERS 
DROP TABLE ALLEN.ROLES 

ALTER TABLE ALLEN.USERS ADD email VARCHAR(45)

SELECT * FROM XPERTV700.DashboardUser; 
SELECT * FROM XPERTV700.DashboardRole; 
UPDATE XPERTV700.DashboardRole SET role_name = 'Administrator' where role_id = 3;

DROP TABLE XPERTV700.DashboardUser; 
DROP TABLE XPERTV700.DashboardRole; 
DROP INDEX "idx_username";
----------------

CREATE TABLE XPERTV700.DashboardUser(
  user_id INT GENERATED ALWAYS AS IDENTITY,
  username VARCHAR(45),
  password VARCHAR(45),
  firstname VARCHAR(45),
  lastname VARCHAR(45),
  email VARCHAR(45),
  enabled INT NOT NULL DEFAULT 1,
  createdby VARCHAR(45),
  createdon TIMESTAMP,
  modifiedby VARCHAR(45),
  modifiedon TIMESTAMP,
  PRIMARY KEY (user_id));
CREATE UNIQUE INDEX "idx_username" ON XPERTV700.DashboardUser (username);



CREATE TABLE XPERTV700.DashboardRole (
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
    REFERENCES XPERTV700.DashboardUser (user_id)
    ON UPDATE RESTRICT);


SELECT * FROM XPERTV700.DashboardUser;



SELECT AMLCENVNAM
		,AMLXLIBRAR
		,AMLXDATMFL
		,AMLXOBKDAT
		,AMLXOBKPRD
		,AMLXOBKTST
		,AMLLENVDEF
		,AMLLPROTST
		,AMLLENVACT
	FROM XPERTLIB.PAMLENVDES;
	
SELECT * FROM XPERTLIB.PAMLENVCON;
SELECT * FROm XPERTV700.PAMLUSERID a where a.amlcuserid like 'MXM%';
SELECT * FROM XPERTV700.TAMLAPPROV;
SELECT * FROM XPERTV700.PAMLCASE;
SELECT * FROM XPERTV700.PAMLFLAGCU; -- Reference Table for User Status
SELECT * FROM XPERTV700.TAMLAPPROV;
SELECT * FROM XPERTV700.PAMLLOGCIF;
SELECT * FROM XPERTV700.PAMLTYPLOG; -- Reference Table for Logs
SELECT AMLDCUSSIN, AMLDNEXREV FROM XPERTV700.PAMLCUSTOM a WHERE a.AMLCCIFCOD = 'VAA5394';
SELECT COUNT(*) FROM XPERTV700.PAMLCUSTOM;
SELECT * FROM XPERTV700.PAMLCUSTOM;

<logic:equal name="sUserType" value="0">CSR</logic:equal>
<logic:equal name="sUserType" value="1">ACCOUNT OFFICER</logic:equal>
<logic:equal name="sUserType" value="2">BSA SUPER USER</logic:equal>
<logic:equal name="sUserType" value="3">VIEWER</logic:equal>
<logic:equal name="sUserType" value="4">BSA ANALYST</logic:equal>
<logic:equal name="sUserType" value="5">KYC ANALYST</logic:equal>
<logic:equal name="sUserType" value="8">DATA ENTRY</logic:equal>
<logic:equal name="sUserType" value="9">DATA ENTRY SUPERVISOR</logic:equal>
<logic:equal name="sUserType" value="13">SECURITY</logic:equal>
<logic:equal name="sUserType" value="15">KYC SUPERVISOR</logic:equal>
<logic:equal name="sUserType" value="17">NRA ANALYST</logic:equal>
<logic:equal name="sUserType" value="18">BSA MANAGER</logic:equal>

INSERT INTO XPERTV700.PAMLUSERID (
	AMLCBANKNU,
	AMLCUSERID,
	AMLAUSERNA,
	AMLCROLECO,
	AMLCBRANCH,
	AMLCCOSCEN,
	AMLAEMAILA,
	AMLLUSEFLA,
	AMLCUPDATE,
	AMLDUPDATE,
	AMLTUPDATE,
	AMLCASSQRS
)
VALUES(
	1, -- AMLCBANKNU
	'MXM01016', -- AMLCUSERID
	'Medina, Marinell - IBM', -- AMLAUSERNA
	0, -- AMLCROLECO
	0, -- AMLCBRANCH
	0, -- AMLCCOSCEN
	'medinama@ph.ibm.com', -- AMLAEMAILA
	1, -- AMLLUSEFLA
	'MANUAL', -- AMLCUPDATE
	CURRENT DATE, -- AMLDUPDATE
	CURRENT TIME, -- AMLDUPDATE
	'EOD' -- AMLCASSQRS
)
/*
0 - CSR
1 - ACCOUNT OFFICER
2 - BSA SUPER USER
3 - VIEWER
4 - BSA ANALYST
5 - KYC ANALYST
8 - DATA ENTRY
9 - DATA ENTRY SUPERVISO
13 - SECURITY
15 - KYC SUPERVISOR
17 - NRA ANALYST
18 - BSA MANAGER
*/
-- CXC01011 
SELECT * FROM XPERTV700.PAMLUSERID WHERE AMLCUSERID = 'MXM01018';
SELECT * FROM XPERTV700.PAMLUSERID WHERE AMLCUSERID LIKE 'MXM010%';
SELECT * FROM XPERTV700.PAMLUSERID WHERE AMLCUSERID LIKE 'CXC010%';
SELECT * FROM XPERTV700.PAMLROLECO;
SELECT AMLLFLAAPR FROM XPERTV700.PAMLCUSTOM WHERE AMLCCIFCOD = 'AAA0227';
SELECT * FROM XPERTV700.PAMLFLAGCU;

UPDATE XPERTV700.PAMLCUSTOM SET AMLLFLAAPR = '6' WHERE AMLCCIFCOD = 'AAA0227';
UPDATE XPERTV700.PAMLUSERID SET AMLCROLECO = '0' WHERE AMLCUSERID = 'MXM01017';

SELECT AMLLFLAAPR, AMLCRANANU, AMLIANUSAL, AMLINETWTH FROM XPERTV700.PAMLCUSTOM WHERE AMLCCIFCOD = 'AAA7602';

-- AAA7533
-- AAA7602
-- AAC5086
-- AAC5084
-- AAC5085

SELECT AMLCCIFCOD, AMLCRANANU, AMLIANUSAL, AMLINETWTH FROM XPERTV700.PAMLCUSTOM WHERE AMLCCIFCOD IN ('AAA7602', 'AAA7533', 'AAC5086', 'AAC5084', 'AAC5085');








SELECT S.AMLXSBTSUB
		,L.AMLCBANKNU
		,L.AMLCCIFCOD
		,L.AMLCTYPLOG
		,L.AMLCSBTSUB
		,L.AMLCUSERID
		,L.AMLDDATLOG
		,L.AMLTTIMLOG
		,L.AMLNSECLOG
		,L.AMLCBRANCH
		,L.AMLCWORKST
		,L.AMLALNOTES
	FROM XPERTV700.PAMLLOGCIF AS L
	INNER JOIN XPERTV700.PAMLTYPLOG AS S ON L.AMLCBANKNU = S.AMLCBANKNU
		AND L.AMLCTYPLOG = S.AMLCTYPLOG
		AND L.AMLCSBTSUB = S.AMLCSBTSUB
	WHERE L.AMLCCIFCOD = 'AAC0666'
	ORDER BY L.AMLTTIMLOG DESC;