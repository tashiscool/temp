-- GRANT_PRIVILEGES

BEGIN

-- TABLES
EXECUTE IMMEDIATE 'grant select,insert,update,delete ON LICENSEPOOL to @user@';
EXECUTE IMMEDIATE 'grant select,insert,update,delete ON LICENSEPOOL_ORDERLINEITEM to @user@';
EXECUTE IMMEDIATE 'grant select,insert,update,delete ON LICENSEPOOL_ORGANIZATION to @user@';

-- grants for solr
EXECUTE IMMEDIATE 'grant select ON licensepool               to @solr.user@';
EXECUTE IMMEDIATE 'grant select ON licensepool_orderlineitem to @solr.user@';


-- FUNCTIONS
EXECUTE IMMEDIATE 'grant execute ON COMPUTE_STATUS_LICENSEPOOL to @user@';

END;

/
