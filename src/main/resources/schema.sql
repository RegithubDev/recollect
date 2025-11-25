SET @exists := (
    SELECT COUNT(*)
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'backend_customer'
      AND COLUMN_NAME = 'token_at'
);

SET @sql := IF(
    @exists = 0,
    'ALTER TABLE backend_customer ADD COLUMN token_at DATETIME(6) NULL',
    'SELECT 1'
);

PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;




SET @exists := (
    SELECT COUNT(*)
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'backend_adminuser'
      AND COLUMN_NAME = 'token_at'
);

SET @sql := IF(
    @exists = 0,
    'ALTER TABLE backend_adminuser ADD COLUMN token_at DATETIME(6) NULL',
    'SELECT 1'
);

PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;




SET @exists := (
    SELECT COUNT(*)
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'backend_provider'
      AND COLUMN_NAME = 'token_at'
);

SET @sql := IF(
    @exists = 0,
    'ALTER TABLE backend_provider ADD COLUMN token_at DATETIME(6) NULL',
    'SELECT 1'
);

PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;




SET @exists := (
    SELECT COUNT(*)
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'backend_servicecategory'
      AND COLUMN_NAME = 'url'
);

SET @sql := IF(
    @exists = 0,
    'ALTER TABLE backend_servicecategory ADD COLUMN url VARCHAR(255) NULL',
    'SELECT 1'
);

PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

UPDATE backend_servicecategory SET url = '/recollect/v1/mobile/list-bio-waste-categories' WHERE id = 1;
UPDATE backend_servicecategory SET url = '/recollect/v1/mobile/list-scrap-categories' WHERE id = 2;
