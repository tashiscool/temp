BEGIN
    DELETE FROM LICENSEPOOL_ORGANIZATION  WHERE ORGANIZATION_LP_ID  = 'ffffffffffffffffffffffffffffffff';
    DELETE FROM LICENSEPOOL_ORDERLINEITEM WHERE ORDERLINEITEM_LP_ID = 'ffffffffffffffffffffffffffffffff';
    DELETE FROM LICENSEPOOL               WHERE LICENSEPOOL_ID      = 'ffffffffffffffffffffffffffffffff';
END;
/
