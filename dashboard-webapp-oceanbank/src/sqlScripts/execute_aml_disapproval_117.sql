CREATE PROCEDURE IBMOB700.execute_aml_disapproval_117(IN i_requestId char(15), OUT o_status char(50)) 
LANGUAGE SQL 
BEGIN atomic

	-- declare variables
	DECLARE S_AMLDCURREN DATE;
	DECLARE qty_cif INT DEFAULT 0;
	DECLARE qty_cif2 INT DEFAULT 0;
	DECLARE qty_cif3 INT DEFAULT 0;
	DECLARE qty_cif4 INT DEFAULT 0;
	DECLARE status_approved CHAR(15);
	
	-- declare error handlers
	DECLARE no_cif_record CONDITION
	FOR SQLSTATE '88B01';
	DECLARE request_complete CONDITION
	FOR SQLSTATE '88B02';
	DECLARE cif_not_found CONDITION
	FOR SQLSTATE '88B03';
	DECLARE EXIT HANDLER
		FOR no_cif_record SET o_status = 'no CIF to process';
	DECLARE EXIT HANDLER
		FOR request_complete SET o_status = 'complete';
	DECLARE EXIT HANDLER
		FOR cif_not_found SET o_status = 'one or more CIF not found';
	DECLARE EXIT HANDLER
		FOR SQLEXCEPTION SET o_status = 'sql exception thrown';
		
	SET status_approved = 'Disapproved';
	
	-- check the amlbatchcif if records found for processing
	SET qty_cif = (
			SELECT COUNT(*)
			FROM IBMOB700.AMLBATCHCIF
			WHERE requestId = i_requestId
			);
	-- get total rows to be approved
	SET qty_cif2 = (
			SELECT COUNT(*)
			FROM IBMOB700.AMLBATCHCIF
			WHERE requestId = i_requestId
				AND STATUS = status_approved
			);
	SET qty_cif3 = (
			SELECT COUNT(*)
			FROM XPERTV117.PAMLCUSTOM a
			WHERE EXISTS (
					SELECT 1
					FROM IBMOB700.AMLBATCHCIF b
					WHERE b.requestId = i_requestId
						AND b.STATUS != status_approved
						AND b.cifReference = a.AMLCCIFCOD
					)
			);
	SET qty_cif4 = (
			SELECT COUNT(*)
			FROM IBMOB700.AMLBATCHCIF
			WHERE requestId = i_requestId
				AND STATUS != status_approved
			);

	-- update AMLBATCHCIF status that were not Found
	UPDATE IBMOB700.AMLBATCHCIF A
	SET A.STATUS = 'CIF not found'
	WHERE NOT EXISTS (
			SELECT 1
			FROM XPERTV117.PAMLCUSTOM B
			WHERE B.AMLCCIFCOD = A.cifReference
			)
		AND A.requestId = i_requestId;

	-- validate the amlbatchcif records
	IF qty_cif < 1 
		THEN SIGNAL SQLSTATE '88B01';
	ELSEIF qty_cif = qty_cif2 
		THEN SIGNAL SQLSTATE '88B02';
	ELSEIF qty_cif3 <> qty_cif4 
		THEN SIGNAL SQLSTATE '88B03';
	ELSE
		SET o_status = 'OK';
	END IF;
	

-- update AMLBATCHREVERSAL that are existing also in AMLBATCHCIF with status not yet complete
UPDATE IBMOB700.AMLBATCHREVERSAL a
SET requestId = i_requestId
	,flagApproval = (
		SELECT AMLLFLAAPR
		FROM XPERTV117.PAMLCUSTOM b
		WHERE AMLCCIFCOD = a.cifReference
		)
	,updatedOnAml = (
		SELECT AMLDUPDATE
		FROM XPERTV117.PAMLCUSTOM b
		WHERE AMLCCIFCOD = a.cifReference
		)
	,batchUpdatedOnAml = (
		SELECT AMLTUPDATE
		FROM XPERTV117.PAMLCUSTOM b
		WHERE AMLCCIFCOD = a.cifReference
		)
	,updatedByAml = (
		SELECT AMLCUPDUSE
		FROM XPERTV117.PAMLCUSTOM b
		WHERE AMLCCIFCOD = a.cifReference
		)
	,createdOn = CURRENT TIMESTAMP
	,iseriesname = (SELECT iseriesname FROM IBMOB700.AMLBATCHCIF WHERE cifreference = a.cifReference AND requestId = i_requestId)
WHERE EXISTS (
		SELECT 1
		FROM IBMOB700.AMLBATCHCIF b
		WHERE b.requestId = i_requestId
			AND b.cifReference = a.cifReference
		);

-- insert AMLBATCHREVERSAL not yet existing
INSERT INTO IBMOB700.AMLBATCHREVERSAL (
	requestId
	,cifReference
	,flagApproval
	,updatedOnAml
	,batchUpdatedOnAml
	,updatedByAml
	,createdOn
	,iseriesname
	)
SELECT i_requestId
	,a.AMLCCIFCOD
	,a.AMLLFLAAPR
	,a.AMLDUPDATE
	,a.AMLTUPDATE
	,a.AMLCUPDUSE
	,CURRENT TIME
	,(SELECT iseriesname FROM IBMOB700.AMLBATCHCIF WHERE cifreference = a.AMLCCIFCOD AND requestId = i_requestId)
FROM XPERTV117.PAMLCUSTOM a
WHERE EXISTS (
		SELECT 1
		FROM IBMOB700.AMLBATCHCIF b
		LEFT JOIN IBMOB700.AMLBATCHREVERSAL c ON c.cifReference = b.cifReference
		WHERE b.requestId = i_requestId
			AND b.STATUS != status_approved
			AND b.cifReference = a.AMLCCIFCOD
			AND c.cifReference IS NULL
		);

-- update PAMLCUSTOM
UPDATE XPERTV117.PAMLCUSTOM A
SET (
		AMLLFLAAPR
		,AMLDUPDATE
		,AMLTUPDATE
		,AMLCUPDUSE
		) = (
		9
		,CURRENT DATE
		,CURRENT TIME
		,(SELECT iseriesname FROM IBMOB700.AMLBATCHCIF WHERE cifreference = A.AMLCCIFCOD AND requestId = i_requestId)
		)
WHERE A.AMLCCIFCOD IN (
		SELECT cifReference
		FROM IBMOB700.AMLBATCHCIF
		WHERE requestId = i_requestId
			AND STATUS != status_approved
		)
	AND A.AMLCSTAKYC = '1';

-- insert the logs
INSERT INTO XPERTV117.PAMLLOGCIF (
	AMLCBANKNU
	,AMLCCIFCOD
	,AMLCTYPLOG
	,AMLCSBTSUB
	,AMLCUSERID
	,AMLDDATLOG
	,AMLTTIMLOG
	,AMLNSECLOG
	,AMLCBRANCH
	,AMLCWORKST
	,AMLALNOTES
	)
SELECT A.AMLCBANKNU
	,A.AMLCCIFCOD
	,9
	,0 AMLCSBTSUB
	,(SELECT iseriesname FROM IBMOB700.AMLBATCHCIF WHERE cifreference = A.AMLCCIFCOD AND requestId = i_requestId)
	,CURRENT DATE
	,CURRENT TIME
	,1
	,A.AMLCBRANCH
	,'EOD'
	,B.auditDescription
FROM XPERTV117.PAMLCUSTOM A,	
IBMOB700.AMLBATCHCIF B
WHERE A.AMLCCIFCOD = B.cifReference
	AND B.requestId = i_requestId
	AND B.STATUS != status_approved
	AND A.AMLCSTAKYC = '1'
	AND A.AMLCBANKNU = 1;

-- update AMLBATCHCIF status that were Approved
UPDATE IBMOB700.AMLBATCHCIF A
SET A.STATUS = status_approved
WHERE A.requestId = i_requestId
	AND A.status != status_approved
	AND A.cifReference IN (
		SELECT B.AMLCCIFCOD
			FROM XPERTV700.PAMLCUSTOM B
			WHERE B.AMLCCIFCOD = A.cifReference
			AND B.AMLLFLAAPR = '9'
		);
	
END