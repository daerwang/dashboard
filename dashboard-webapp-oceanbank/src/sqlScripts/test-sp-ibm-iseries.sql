DROP PROCEDURE allen.TestABC
DROP PROCEDURE IBMOB700.execute_aml_approval_bank_700
/*
 * Test CIF:
 * BAA0707 - Personal
 * FAB5344 - Personal
 * GAA0141 - Non Personal
 * AAA0227 - Non Personal
 * 
 * PAMLTYPLOG
 * PAMLENVDES
 * PAMLMANDAT
 * SPKYCFLAGINF
 * PAMLFLAGCU
 * PAMLROLECO
 * 
 * setFlagApproved(rs.getString("AMLLFLAAPR"));
 * setFlagApprovedShortDesc(rs.getString("AMLLFLASHD"));
 * */

CREATE PROCEDURE IBMOB700.execute_aml_approval_bank_700(IN i_requestId char(15), OUT o_status char(50)) 
LANGUAGE SQL 
BEGIN atomic

	-- declare variables
	DECLARE S_AMLDCURREN DATE;
	DECLARE qty_cif INT DEFAULT 0;
	DECLARE qty_cif2 INT DEFAULT 0;
	DECLARE status_approved CHAR(15);
	
	-- declare error handlers
	DECLARE no_cif_record CONDITION FOR SQLSTATE '88B01';
	DECLARE request_complete CONDITION FOR SQLSTATE '88B02';
	DECLARE EXIT HANDLER FOR no_cif_record
		SET o_status = 'no CIF to process';
	DECLARE EXIT HANDLER FOR request_complete
		SET o_status = 'complete';
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
		SET o_status = 'sql exception thrown';
		
	SET status_approved = 'Approved';
		
	-- check the amlbatchcif if records found for processing
	SET qty_cif = (SELECT COUNT(*) FROM IBMOB700.AMLBATCHCIF WHERE requestId = i_requestId); 
	SET qty_cif2 = (SELECT COUNT(*) FROM IBMOB700.AMLBATCHCIF WHERE requestId = i_requestId AND status = status_approved); 
	IF qty_cif < 1 THEN
		SIGNAL SQLSTATE '88B01';
	ELSEIF qty_cif = qty_cif2 THEN	
		SIGNAL SQLSTATE '88B02';
	ELSE
	 	SET o_status = 'OK';
	END IF;
	
	-- get the date
	SELECT AMLDCURREN INTO S_AMLDCURREN
	FROM XPERTV700.PAMLGENCOP
	WHERE AMLCBANKNU = 1;

	-- update PAMLCUSTOM
	UPDATE XPERTV700.PAMLCUSTOM A
	SET (AMLLFLAAPR, AMLDUPDATE, AMLTUPDATE, AMLCUPDUSE) = (
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
							WHERE requestId = i_requestId AND status NOT IN (status_approved))
	AND A.AMLCSTAKYC = '1';							

	-- insert the logs
	INSERT INTO XPERTV700.PAMLLOGCIF (AMLCBANKNU, AMLCCIFCOD, AMLCTYPLOG, AMLCSBTSUB,AMLCUSERID,AMLDDATLOG,AMLTTIMLOG,AMLNSECLOG,AMLCBRANCH,AMLCWORKST,AMLALNOTES)
	SELECT A.AMLCBANKNU
		,A.AMLCCIFCOD
		,4
		,(
		  CASE A.AMLLFLARQD
				WHEN '1' THEN '4'
				WHEN '2' THEN '4'
				WHEN '3' THEN '1'
				WHEN '4' THEN '7'
		  END
		  ) AMLCSBTSUB
		,A.AMLCUPDUSE
		,A.AMLDUPDATE
		,A.AMLTUPDATE
		,1
		,0
		,'EOD'
		,B.auditDescription
	FROM XPERTV700.PAMLCUSTOM A
	INNER JOIN IBMOB700.AMLBATCHCIF B ON B.cifReference = A.AMLCCIFCOD AND B.requestId = i_requestId AND status NOT IN (status_approved)
	WHERE A.AMLCBANKNU = 1
	AND A.AMLCSTAKYC = '1';
	
	-- update AMLBATCHCIF status that were Approved
	UPDATE IBMOB700.AMLBATCHCIF A
		SET A.status = status_approved
	WHERE EXISTS(
			SELECT 1 FROM XPERTV700.PAMLCUSTOM B WHERE B.AMLCCIFCOD = A.cifReference
		)	
	AND A.requestId = i_requestId;
	
	-- update AMLBATCHCIF status that were not Found
	UPDATE IBMOB700.AMLBATCHCIF A
		SET A.status = 'CIF not found'
	WHERE NOT EXISTS(
			SELECT 1 FROM XPERTV700.PAMLCUSTOM B WHERE B.AMLCCIFCOD = A.cifReference
		)	
	AND A.requestId = i_requestId;
 
END




CREATE PROCEDURE ALLEN.test_out_parameter(OUT Stmt char(30)) 
LANGUAGE SQL 
BEGIN  
    set Stmt = 'Hi Nell';
END

CREATE PROCEDURE IBMOB700.test_out_parameter(OUT Stmt char(30)) 
LANGUAGE SQL 
BEGIN  
    set Stmt = 'Hi Nell';
END

CREATE PROCEDURE IBMOB700.test_get_one_resultset() 
RESULT SETS 1
LANGUAGE SQL 
BEGIN  
	DECLARE c1 CURSOR FOR 
	SELECT AMLLFLARQD, AMLLFLAAPR, AMLCCIFCOD FROM XPERTV700.PAMLCUSTOM A WHERE A.AMLCCIFCOD IN ('AAA0552', 'FAA5126', 'FAA6072');
	OPEN c1;
END

DROP PROCEDURE ALLEN.test_local_get_one_resultset;

CREATE PROCEDURE ALLEN.test_local_get_one_resultset() 
RESULT SETS 1
LANGUAGE SQL 
BEGIN  
	DECLARE c1 CURSOR FOR 
	SELECT username, password FROM ALLEN.USERS;
	OPEN c1;
END

CREATE procedure allen.plus1inout (IN arg int, OUT res int)  
BEGIN ATOMIC  
	set res = arg + 1; 
END

CALL ALLEN.test_out_parameter();
