create or replace
PROCEDURE UPDATEMORELICENSEPOOLRELATIONS(
    ORGANIZATIONID LICENSEPOOL_ORGANIZATION.ORGANIZATION_ID%TYPE,
    CHILD_ORGANIZATIONID LICENSEPOOL_ORGANIZATION.ORGANIZATION_ID%TYPE)
AS
  TOT_EMPS    NUMBER;
  FETCH_LEVEL NUMBER := 9999;
  x           NUMBER;
  CURSOR CURSOR_EXISTING_SUBSCRIPTION
  IS
    SELECT ORG.ORG_ID
    FROM EPRISE.ORG_RELATION ORG
      START WITH ORG.RELATED_ORG_ID = ORGANIZATIONID
      CONNECT BY PRIOR ORG.ORG_ID   = ORG.RELATED_ORG_ID
    AND LEVEL                      <= FETCH_LEVEL ;
  CURSOR CURSOR_ORGANIZATION
  IS
    SELECT ORG.ORG_ID AS ORG_ID,
      LEVEL           AS leveler
    FROM EPRISE.ORG_RELATION ORG
    WHERE ORG.ORG_ID                = ORGANIZATIONID
      START WITH ORG.RELATED_ORG_ID = CHILD_ORGANIZATIONID
      CONNECT BY PRIOR ORG.ORG_ID   = ORG.RELATED_ORG_ID
    AND LEVEL                      <= FETCH_LEVEL ;
  CURSOR CHILDREN
  IS
    SELECT ORG.RELATED_ORG_ID AS RELATE_ORG_ID,
      LEVEL                   AS leveler
    FROM EPRISE.ORG_RELATION ORG
      START WITH ORG_ID               = CHILD_ORGANIZATIONID
      CONNECT BY PRIOR RELATED_ORG_ID = ORG_ID
    AND LEVEL                        <= FETCH_LEVEL;
  CURSOR CHILDREN2
  IS
    SELECT ORG.RELATED_ORG_ID AS RELATE_ORG_ID,
      LEVEL                   AS leveler
    FROM EPRISE.ORG_RELATION ORG
      START WITH ORG_ID               = CHILD_ORGANIZATIONID
      CONNECT BY PRIOR RELATED_ORG_ID = ORG_ID
    AND LEVEL                        <= FETCH_LEVEL;
BEGIN
  FOR SUBSCRIPTION_EXISTING_REC IN CURSOR_EXISTING_SUBSCRIPTION
  LOOP
    FOR CHILDREN_REC IN CHILDREN
    LOOP
      BEGIN
        INSERT
        INTO LICENSEPOOL_ORGANIZATION
          (
            ORGANIZATION_LP_ID,
            LICENSEPOOL_ID,
            ORGANIZATION_ID,
            USED_QUANTITY,
            ORGANIZATION_LEVEL,
            DENYNEWSUBSCRIPTION,
            DT_ADDED,
            DT_MODIFIED,
            MODIFIED_BY,
            ADDED_BY
          )
          (SELECT SYS_GUID()             AS ORGANIZATION_LP_ID,
              LICENSEPOOL_ID             AS LICENSEPOOL_ID,
              CHILDREN_REC.RELATE_ORG_ID AS ORGANIZATION_ID,
              0                          AS USED_QUANTITY,
              CHILDREN_REC.leveler       AS ORGANIZATION_LEVEL,
              '0'                        AS DENYNEWSUBSCRIPTION,
              SYSDATE                    AS DT_ADDED,
              SYSDATE                    AS DT_MODIFIED,
              'SYSTEM'                   AS MODIFIED_BY,
              'SYSTEM'                   AS ADDED_BY
            FROM LICENSEPOOL
            WHERE ORGANIZATION_ID = SUBSCRIPTION_EXISTING_REC.ORG_ID
          );
      EXCEPTION
      WHEN OTHERS THEN
        NULL;
      END;
      SELECT COUNT (ORGANIZATION_LP_ID)
      INTO x
      FROM LICENSEPOOL_ORGANIZATION
      WHERE ORGANIZATION_ID = CHILD_ORGANIZATIONID
      AND LICENSEPOOL_ID   IN
        (SELECT LICENSEPOOL_ID AS LICENSEPOOL_ID
        FROM LICENSEPOOL
        WHERE ORGANIZATION_ID = SUBSCRIPTION_EXISTING_REC.ORG_ID
        );
      IF x                = 0 THEN
        FOR CHILDORG_REC IN CURSOR_ORGANIZATION
        LOOP
          BEGIN
            INSERT
            INTO LICENSEPOOL_ORGANIZATION
              (
                ORGANIZATION_LP_ID,
                LICENSEPOOL_ID,
                ORGANIZATION_ID,
                USED_QUANTITY,
                ORGANIZATION_LEVEL,
                DENYNEWSUBSCRIPTION,
                DT_ADDED,
                DT_MODIFIED,
                MODIFIED_BY,
                ADDED_BY
              )
              (SELECT SYS_GUID()          AS ORGANIZATION_LP_ID,
                  LICENSEPOOL_ID          AS LICENSEPOOL_ID,
                  CHILD_ORGANIZATIONID    AS ORGANIZATION_ID,
                  0                       AS USED_QUANTITY,
                  CHILDORG_REC.leveler +1 AS ORGANIZATION_LEVEL,
                  '0'                     AS DENYNEWSUBSCRIPTION,
                  SYSDATE                 AS DT_ADDED,
                  SYSDATE                 AS DT_MODIFIED,
                  'SYSTEM'                AS MODIFIED_BY,
                  'SYSTEM'                AS ADDED_BY
                FROM LICENSEPOOL
                WHERE ORGANIZATION_ID = SUBSCRIPTION_EXISTING_REC.ORG_ID
              );
          EXCEPTION
          WHEN OTHERS THEN
            NULL;
          END;
        END LOOP;
      END IF;
    END LOOP;
  END LOOP;
  FOR CHILDREN_REC IN CHILDREN2
  LOOP
    BEGIN
      INSERT
      INTO LICENSEPOOL_ORGANIZATION
        (
          ORGANIZATION_LP_ID,
          LICENSEPOOL_ID,
          ORGANIZATION_ID,
          USED_QUANTITY,
          ORGANIZATION_LEVEL,
          DENYNEWSUBSCRIPTION,
          DT_ADDED,
          DT_MODIFIED,
          MODIFIED_BY,
          ADDED_BY
        )
        (SELECT SYS_GUID()             AS ORGANIZATION_LP_ID,
            LICENSEPOOL_ID             AS LICENSEPOOL_ID,
            CHILDREN_REC.RELATE_ORG_ID AS ORGANIZATION_ID,
            0                          AS USED_QUANTITY,
            CHILDREN_REC.leveler       AS ORGANIZATION_LEVEL,
            '0'                        AS DENYNEWSUBSCRIPTION,
            SYSDATE                    AS DT_ADDED,
            SYSDATE                    AS DT_MODIFIED,
            'SYSTEM'                   AS MODIFIED_BY,
            'SYSTEM'                   AS ADDED_BY
          FROM LICENSEPOOL
          WHERE ORGANIZATION_ID = ORGANIZATIONID
        );
    EXCEPTION
    WHEN OTHERS THEN
      NULL;
    END;
  END LOOP;
  SELECT COUNT (ORGANIZATION_LP_ID)
  INTO x
  FROM LICENSEPOOL_ORGANIZATION
  WHERE ORGANIZATION_ID = CHILD_ORGANIZATIONID
  AND LICENSEPOOL_ID   IN
    (SELECT LICENSEPOOL_ID AS LICENSEPOOL_ID
    FROM LICENSEPOOL
    WHERE ORGANIZATION_ID = ORGANIZATIONID
    );
  IF x                = 0 THEN
    FOR CHILDORG_REC IN CURSOR_ORGANIZATION
    LOOP
      BEGIN
        INSERT
        INTO LICENSEPOOL_ORGANIZATION
          (
            ORGANIZATION_LP_ID,
            LICENSEPOOL_ID,
            ORGANIZATION_ID,
            USED_QUANTITY,
            ORGANIZATION_LEVEL,
            DENYNEWSUBSCRIPTION,
            DT_ADDED,
            DT_MODIFIED,
            MODIFIED_BY,
            ADDED_BY
          )
          (SELECT SYS_GUID()          AS ORGANIZATION_LP_ID,
              LICENSEPOOL_ID          AS LICENSEPOOL_ID,
              CHILD_ORGANIZATIONID    AS ORGANIZATION_ID,
              0                       AS USED_QUANTITY,
              CHILDORG_REC.leveler +1 AS ORGANIZATION_LEVEL,
              '0'                     AS DENYNEWSUBSCRIPTION,
              SYSDATE                 AS DT_ADDED,
              SYSDATE                 AS DT_MODIFIED,
              'SYSTEM'                AS MODIFIED_BY,
              'SYSTEM'                AS ADDED_BY
            FROM LICENSEPOOL
            WHERE ORGANIZATION_ID = ORGANIZATIONID
          );
      EXCEPTION
      WHEN OTHERS THEN
        NULL;
      END;
    END LOOP;
  END IF;
  COMMIT;
END;