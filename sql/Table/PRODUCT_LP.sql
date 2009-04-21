PROMPT Running script PRODUCT_LP.sql

DECLARE x NUMBER;
BEGIN
SELECT COUNT(*) INTO x FROM user_tables WHERE TABLE_NAME = 'LicensePool_Product';
_

IF x = 0 THEN
   EXECUTE IMMEDIATE 
   '
   CREATE TABLE "LICENSEPOOL_PRODUCT" 
   (
      "LICENSEPOOL_ID" CHAR(32) NOT NULL ENABLE, 
      "PRODUCT_ID" CHAR(32) NOT NULL ENABLE,
      CONSTRAINT "IDX_LICENSEPOOL_PRODUCT_1" PRIMARY KEY ("LICENSEPOOL_ID","PRODUCT_ID") ENABLE      
   )
  ';
END IF;

END;

/
