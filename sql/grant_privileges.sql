-- GRANT_PRIVILEGES

BEGIN

-- TABLES
EXECUTE IMMEDIATE 'grant select,insert,update,delete ON LICENSEPOOL to @licensepool.hibernate.connection.app.username@';
EXECUTE IMMEDIATE 'grant select,insert,update,delete ON LICENSEPOOL_ORDERLINEITEM to @licensepool.hibernate.connection.app.username@';
EXECUTE IMMEDIATE 'grant select,insert,update,delete ON LICENSEPOOL_ORGANIZATION to @licensepool.hibernate.connection.app.username@';

-- grants for solr
EXECUTE IMMEDIATE 'grant select ON licensepool               to @solr.user@';
EXECUTE IMMEDIATE 'grant select ON licensepool_orderlineitem to @solr.user@';

EXECUTE IMMEDIATE 'grant execute ON COMPUTE_STATUS_LICENSEPOOL to @licensepool.hibernate.connection.app.username@';

END;

/
