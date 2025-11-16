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
