create or replace
PROCEDURE UPDATELICENSEPOOLRELATIONS(
    ORGANIZATIONID LICENSEPOOL_ORGANIZATION.ORGANIZATION_ID%TYPE)
AS
  TOT_EMPS NUMBER;
  FETCH_LEVEL NUMBER := 9999;
   
  CURSOR CURSOR_EXISTING_SUBSCRIPTION
  IS
    SELECT ORG.ORG_ID
    FROM @eprise.hibernate.connection.username@.ORG_RELATION ORG
      START WITH ORG.RELATED_ORG_ID = ORGANIZATIONID
      CONNECT BY PRIOR ORG.ORG_ID   = ORG.RELATED_ORG_ID
    AND LEVEL                      <= FETCH_LEVEL ;
   
BEGIN
FOR SUBSCRIPTION_EXISTING_REC IN CURSOR_EXISTING_SUBSCRIPTION
    LOOP
  DELETE
  FROM LICENSEPOOL_ORGANIZATION
  WHERE ORGANIZATION_LP_ID IN
    (SELECT ORGANIZATION_LP_ID
    FROM LICENSEPOOL_ORGANIZATION
    WHERE LICENSEPOOL_ID IN
      (SELECT LICENSEPOOL_ID
      FROM LICENSEPOOL
      WHERE ORGANIZATION_ID = SUBSCRIPTION_EXISTING_REC.ORG_ID
      )
    )
  AND ORGANIZATION_LEVEL <> 0
  AND ORGANIZATION_ID    IN
    (select RELATED_ORG_ID
      FROM @eprise.hibernate.connection.username@.ORG_RELATION ORG
    START WITH ORG_ID               = ORGANIZATIONID
    CONNECT BY PRIOR RELATED_ORG_ID = ORG_ID
  AND LEVEL                        <= FETCH_LEVEL
  );
END LOOP;
DELETE
FROM LICENSEPOOL_ORGANIZATION
WHERE ORGANIZATION_LP_ID IN
  (SELECT ORGANIZATION_LP_ID
  FROM LICENSEPOOL_ORGANIZATION
  WHERE LICENSEPOOL_ID IN
    (SELECT LICENSEPOOL_ID
    FROM LICENSEPOOL
    WHERE ORGANIZATION_ID = ORGANIZATIONID
    )
  )
AND ORGANIZATION_LEVEL <> 0
and ORGANIZATION_ID    in
  (select RELATED_ORG_ID
  FROM @eprise.hibernate.connection.username@.ORG_RELATION ORG
    START WITH ORG_ID               = ORGANIZATIONID
    CONNECT BY PRIOR RELATED_ORG_ID = ORG_ID
  AND LEVEL                        <= FETCH_LEVEL
  );
TOT_EMPS := TOT_EMPS - 1;
-- commit subscriptions
COMMIT;
END;