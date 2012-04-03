-- Check seed data for entities in LicensePool schema

BEGIN
    DELETE FROM LICENSEPOOL_ORDERLINEITEM
          WHERE ORDERLINEITEM_LP_ID = '00000000000000000000000000000000';

    DELETE FROM LICENSEPOOL_ORGANIZATION
          WHERE ORGANIZATION_LP_ID = '00000000000000000000000000000000';

    DELETE FROM LICENSEPOOL
          WHERE LICENSEPOOL_ID = '00000000000000000000000000000000';

    -- Seed data for LicensePoolLifeCycle
    Insert into LICENSEPOOL (
        LICENSEPOOL_ID,
        TYPE,
        START_DATE,
        END_DATE,
        QUANTITY,
        ORGANIZATION_ID,
        PRODUCT_ID,
        DENYNEWSUBSCRIPTION,
        SOURCESYSTEM,
        DT_ADDED,
        DT_MODIFIED,
        MODIFIED_BY,
        ADDED_BY,
        IS_CANCELLED
    ) values (
        '00000000000000000000000000000000',     -- LICENSEPOOL_ID
        'Student seat based licensing',         -- TYPE
        CURRENT_TIMESTAMP,                      -- START_DATE
        to_timestamp(                           -- END_DATE
            '30-JAN-30 12.00.00.000000000 AM',
            'DD-MON-RR HH.MI.SS.FF AM'
        ),
        1,                                      -- QUANTITY
        '00000000000000000000000000000000',     -- ORGANIZATION_ID
        '99999999',                             -- PRODUCT_ID
        '0',                                    -- DENYNEWSUBSCRIPTION
        'http://idpdev.pearsoncmg.com/synapse', -- SOURCESYSTEM
        CURRENT_TIMESTAMP,                      -- DT_ADDED
        CURRENT_TIMESTAMP,                      -- DT_MODIFIED
        'System',                               -- MODIFIED_BY
        'System',                               -- ADDED_BY
        'N'                                     -- IS_CANCELLED
    );

    -- Support for seed data, LicensePool/Organization
    Insert into LICENSEPOOL_ORGANIZATION (
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
    ) values (
        '00000000000000000000000000000000', -- ORGANIZATION_LP_ID
        '00000000000000000000000000000000', -- LICENSEPOOL_ID
        '00000000000000000000000000000000', -- ORGANIZATION_ID
        1,                                  -- USED_QUANTITY
        0,                                  -- ORGANIZATION_LEVEL
        '0',                                -- DENYNEWSUBSCRIPTION
        CURRENT_TIMESTAMP,                  -- DT_ADDED
        CURRENT_TIMESTAMP,                  -- DT_MODIFIED
        'System',                           -- MODIFIED_BY
        'System'                            -- ADDED_BY
    );

    -- Support for seed data, LicensePool/OrderLineItem
    Insert into LICENSEPOOL_ORDERLINEITEM (
        ORDERLINEITEM_LP_ID,
        LICENSEPOOL_ID,
        ORDERLINEITEM_ID,
        DT_ADDED,
        DT_MODIFIED,
        MODIFIED_BY,
        ADDED_BY
    ) values (
        '00000000000000000000000000000000', -- ORDERLINEITEM_LP_ID
        '00000000000000000000000000000000', -- LICENSEPOOL_ID
        'ffffffffffffffffffffffffffffffff', -- ORDERLINEITEM_ID; via order's check_seed_data.sql
        CURRENT_TIMESTAMP,                  -- DT_ADDED
        CURRENT_TIMESTAMP,                  -- DT_MODIFIED
        'System',                           -- MODIFIED_BY
        'System'                            -- ADDED_BY
    );
END;
/
