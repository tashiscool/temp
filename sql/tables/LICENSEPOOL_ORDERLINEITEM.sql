-- ORDERLINEITEM_LP.sql

DECLARE x NUMBER;
BEGIN
SELECT COUNT(*) INTO x FROM user_tables WHERE TABLE_NAME = 'LICENSEPOOL_ORDERLINEITEM';


IF x = 0 THEN
   EXECUTE IMMEDIATE
   '
  CREATE TABLE "LICENSEPOOL_ORDERLINEITEM"
   (
      "ORDERLINEITEM_LP_ID" CHAR(32) NOT NULL ENABLE,
      "LICENSEPOOL_ID" CHAR(32) NOT NULL ENABLE,
      "ORDERLINEITEM_ID" CHAR(32) NOT NULL ENABLE,
      "DT_ADDED" TIMESTAMP (6) NOT NULL ENABLE,
      "DT_MODIFIED" TIMESTAMP (6) NOT NULL ENABLE,
      "MODIFIED_BY" VARCHAR2(32) NOT NULL ENABLE,
      "ADDED_BY" VARCHAR2(32) NOT NULL ENABLE,
      CONSTRAINT "IDX_LICENSEPOOL_ORDERLINE_1" PRIMARY KEY ("LICENSEPOOL_ID","ORDERLINEITEM_ID") ENABLE
   )
  ';
END IF;

END;

/


-- added on 04/19/2010
-- Index on LICENSEPOOL_ID

DECLARE x NUMBER;
BEGIN
SELECT COUNT(*) INTO x FROM user_indexes WHERE index_name='IDX_LICENSEPOOL_ORDERLINE_2' AND table_name='LICENSEPOOL_ORDERLINEITEM' AND index_type='NORMAL';

IF x = 0 THEN
   EXECUTE IMMEDIATE 
   '
      CREATE INDEX "IDX_LICENSEPOOL_ORDERLINE_2" ON "LICENSEPOOL_ORDERLINEITEM" ("LICENSEPOOL_ID")
   ';
END IF;

END;

/

