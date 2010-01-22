-- UPDATE_LICENSE_TYPE.sql

DECLARE x NUMBER;
BEGIN

SELECT COUNT(*) INTO x FROM user_tables WHERE table_name = 'LICENSEPOOL';

IF x > 0 THEN
     UPDATE LICENSEPOOL SET TYPE ='Student seat based licensing';
  END IF;
END;

/
