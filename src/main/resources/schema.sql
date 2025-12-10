SET @exists := (
    SELECT COUNT(*)
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'Backend_customer'
      AND COLUMN_NAME = 'token_at'
);

SET @sql := IF(
    @exists = 0,
    'ALTER TABLE Backend_customer ADD COLUMN token_at DATETIME(6) NULL',
    'SELECT 1'
);

PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;




SET @exists := (
    SELECT COUNT(*)
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'Backend_adminuser'
      AND COLUMN_NAME = 'token_at'
);

SET @sql := IF(
    @exists = 0,
    'ALTER TABLE Backend_adminuser ADD COLUMN token_at DATETIME(6) NULL',
    'SELECT 1'
);

PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;




SET @exists := (
    SELECT COUNT(*)
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'Backend_provider'
      AND COLUMN_NAME = 'token_at'
);

SET @sql := IF(
    @exists = 0,
    'ALTER TABLE Backend_provider ADD COLUMN token_at DATETIME(6) NULL',
    'SELECT 1'
);

PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;




SET @exists := (
    SELECT COUNT(*)
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'Backend_servicecategory'
      AND COLUMN_NAME = 'category_url'
);

SET @sql := IF(
    @exists = 0,
    'ALTER TABLE Backend_servicecategory ADD COLUMN category_url VARCHAR(255) NULL',
    'SELECT 1'
);

PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;




SET @exists := (
    SELECT COUNT(*)
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'Backend_servicecategory'
      AND COLUMN_NAME = 'order_url'
);

SET @sql := IF(
    @exists = 0,
    'ALTER TABLE Backend_servicecategory ADD COLUMN order_url VARCHAR(255) NULL',
    'SELECT 1'
);

PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
