-- LicensePool.sql

DECLARE x NUMBER;
BEGIN
SELECT COUNT(*) INTO x FROM user_tables WHERE TABLE_NAME = 'LICENSEPOOL';

IF x = 0 THEN
   EXECUTE IMMEDIATE
   '
   CREATE TABLE "LICENSEPOOL"
   (
      "LICENSEPOOL_ID" CHAR(32) NOT NULL ENABLE,
      "TYPE" VARCHAR2(128) NOT NULL ENABLE,
      "START_DATE" TIMESTAMP (6) NOT NULL ENABLE,
      "END_DATE" TIMESTAMP (6) NOT NULL ENABLE,
      "QUANTITY" NUMBER(16) NOT NULL ENABLE,
      "ORGANIZATION_ID" VARCHAR2(32) NOT NULL ENABLE,
      "PRODUCT_ID" VARCHAR2(32) NOT NULL ENABLE,
      "DENYNEWSUBSCRIPTION" CHAR(8) NOT NULL ENABLE,
      "SOURCESYSTEM" VARCHAR2(128) NOT NULL ENABLE,
      "DT_ADDED" TIMESTAMP (6) NOT NULL ENABLE,
      "DT_MODIFIED" TIMESTAMP (6) NOT NULL ENABLE,
      "MODIFIED_BY" VARCHAR2(32) NOT NULL ENABLE,
      "ADDED_BY" VARCHAR2(32) NOT NULL ENABLE,
      "IS_CANCELLED" CHAR(1)  NOT NULL ENABLE,
      "STATUS" CHAR(25)  GENERATED ALWAYS AS (SUBSTR(COMPUTE_STATUS_LICENSEPOOL(IS_CANCELLED, START_DATE, END_DATE),1,6)) VIRTUAL,
      CONSTRAINT "IDX_LICENSEPOOL_1" PRIMARY KEY ("LICENSEPOOL_ID") ENABLE
   )
  ';
END IF;

END;

/
