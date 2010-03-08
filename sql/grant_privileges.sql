-- GRANT_PRIVILEGES

BEGIN

-- TABLES

EXECUTE IMMEDIATE 'grant select,insert,update,delete ON LICENSEPOOL to @user@';
EXECUTE IMMEDIATE 'grant select,insert,update,delete ON LICENSEPOOL_ORDERLINEITEM to @user@';
EXECUTE IMMEDIATE 'grant select,insert,update,delete ON LICENSEPOOL_ORGANIZATION to @user@';

-- FUNCTIONS

EXECUTE IMMEDIATE 'grant execute ON COMPUTE_STATUS_LICENSEPOOL to @user@';

-- SEQUENCES

EXECUTE IMMEDIATE 'grant select ON RUMBA_PRODUCT_ENT_SEQ to @user@';
EXECUTE IMMEDIATE 'grant select ON RUMBA_PRODUCT_SEQ to @user@';
EXECUTE IMMEDIATE 'grant select ON RUMBA_RESOURCE_ENT_SEQ to @user@';
EXECUTE IMMEDIATE 'grant select ON RUMBA_RESOURCE_SEQ to @user@';

END;

/
