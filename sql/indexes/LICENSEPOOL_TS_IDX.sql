DECLARE
  lpoolidx NUMBER;
BEGIN
  SELECT COUNT(*)
  INTO lpoolidx
  FROM user_indexes
  WHERE index_name='LICENSEPOOL_TS_IDX'
  AND table_name  ='SUBSCRIPTION_REQUEST'
  AND index_type  ='NORMAL';
  IF lpoolidx     = 0 THEN
    EXECUTE IMMEDIATE '   
CREATE INDEX LICENSEPOOL_TS_IDX ON SUBSCRIPTION_REQUEST (DT_MODIFIED)   
';
  END IF;
END;
/