-- Check seed data for entities in LicensePool schema

-- Seed data for LicensePoolLifeCycle
DECLARE x NUMBER;
BEGIN
SELECT COUNT(*) INTO x FROM LICENSEPOOL WHERE LICENSEPOOL_ID = 'ffffffffffffffffffffffffffffffff';

IF x = 0 THEN

	Insert into LICENSEPOOL (LICENSEPOOL_ID,TYPE,START_DATE,END_DATE,QUANTITY,ORGANIZATION_ID,PRODUCT_ID,DENYNEWSUBSCRIPTION,SOURCESYSTEM,DT_ADDED,DT_MODIFIED,MODIFIED_BY,ADDED_BY,IS_CANCELLED)
	values ('ffffffffffffffffffffffffffffffff','Student seat based licensing',CURRENT_TIMESTAMP,to_timestamp('30-JAN-30 12.00.00.000000000 AM','DD-MON-RR HH.MI.SS.FF AM'),1,'ffffffffffffffffffffffffffffffff','99999999','0','http://idpdev.pearsoncmg.com/synapse',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'System','System','N');

END IF;

END;

/

-- Support for seed data, LicensePool/Organization
DECLARE x NUMBER;
BEGIN
SELECT COUNT(*) INTO x FROM LICENSEPOOL_ORGANIZATION WHERE ORGANIZATION_LP_ID = 'ffffffffffffffffffffffffffffffff';

IF x = 0 THEN

	Insert into LICENSEPOOL_ORGANIZATION (ORGANIZATION_LP_ID,LICENSEPOOL_ID,ORGANIZATION_ID,USED_QUANTITY,ORGANIZATION_LEVEL,DENYNEWSUBSCRIPTION,DT_ADDED,DT_MODIFIED,MODIFIED_BY,ADDED_BY)
	values ('ffffffffffffffffffffffffffffffff','ffffffffffffffffffffffffffffffff','ffffffffffffffffffffffffffffffff',1,0,'0',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'System','System');

END IF;

END;

/

-- Support for seed data, LicensePool/OrderLineItem
DECLARE x NUMBER;
BEGIN
SELECT COUNT(*) INTO x FROM LICENSEPOOL_ORDERLINEITEM WHERE ORDERLINEITEM_LP_ID = 'ffffffffffffffffffffffffffffffff';

IF x = 0 THEN

	Insert into LICENSEPOOL_ORDERLINEITEM (ORDERLINEITEM_LP_ID,LICENSEPOOL_ID,ORDERLINEITEM_ID,DT_ADDED,DT_MODIFIED,MODIFIED_BY,ADDED_BY)
	values ('ffffffffffffffffffffffffffffffff','ffffffffffffffffffffffffffffffff','ffffffffffffffffffffffffffffffff',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'System','System');

END IF;

END;

/
