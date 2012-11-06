-- DROP_LP_PROCS(UPDATELICENSEPOOLRELATIONS UPDATEMORELICENSEPOOLRELATIONS and DeleteLICENSEPOOLRELATIONS)


DECLARE x NUMBER;
BEGIN
SELECT COUNT(*) INTO x FROM user_source WHERE TYPE='PROCEDURE' and NAME='UPDATELICENSEPOOLRELATIONS';

IF x > 0 THEN
   EXECUTE IMMEDIATE 
   '
      drop procedure UPDATELICENSEPOOLRELATIONS
   ';
END IF;

END;


/

DECLARE x NUMBER;
BEGIN
SELECT COUNT(*) INTO x FROM user_source WHERE TYPE='PROCEDURE' and NAME='UPDATEMORELICENSEPOOLRELATIONS';

IF x > 0 THEN
   EXECUTE IMMEDIATE 
   '
      drop procedure UPDATEMORELICENSEPOOLRELATIONS
   ';
END IF;

END;

/

DECLARE x NUMBER;
BEGIN
SELECT COUNT(*) INTO x FROM user_source WHERE TYPE='PROCEDURE' and NAME='DeleteLICENSEPOOLRELATIONS';

IF x > 0 THEN
   EXECUTE IMMEDIATE 
   '
      drop procedure DeleteLICENSEPOOLRELATIONS
   ';
END IF;

END;

/