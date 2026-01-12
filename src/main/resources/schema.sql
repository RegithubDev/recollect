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
      AND COLUMN_NAME = 'category_url'
);

SET @sql := IF(
    @exists = 0,
    'ALTER TABLE backend_servicecategory ADD COLUMN category_url VARCHAR(255) NULL',
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
      AND COLUMN_NAME = 'order_url'
);

SET @sql := IF(
    @exists = 0,
    'ALTER TABLE backend_servicecategory ADD COLUMN order_url VARCHAR(255) NULL',
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
      AND COLUMN_NAME = 'schedule_url'
);

SET @sql := IF(
    @exists = 0,
    'ALTER TABLE backend_servicecategory ADD COLUMN schedule_url VARCHAR(255) NULL',
    'SELECT 1'
);

PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;




SET @exists := (
    SELECT COUNT(*)
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'backend_bwgbagprice'
      AND COLUMN_NAME = 'is_active'
);

SET @sql := IF(
    @exists = 0,
    'ALTER TABLE backend_bwgbagprice ADD COLUMN is_active BIT(1) NOT NULL DEFAULT 1',
    'SELECT 1'
);

PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;




SET @exists := (
    SELECT COUNT(*)
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'backend_scrapregion'
      AND COLUMN_NAME = 'geometry'
);

SET @sql := IF(
    @exists = 0,
    'ALTER TABLE backend_scrapregion ADD COLUMN geometry MULTIPOLYGON SRID 4326',
    'SELECT 1'
);

PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;




SET @exists := (
    SELECT COUNT(*)
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'backend_localbody'
      AND COLUMN_NAME = 'geometry'
);

SET @sql := IF(
    @exists = 0,
    'ALTER TABLE backend_localbody ADD COLUMN geometry MULTIPOLYGON SRID 4326',
    'SELECT 1'
);

PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;




SET @exists := (
    SELECT COUNT(*)
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'backend_completeorders'
      AND COLUMN_NAME = 'client_price'
);

SET @sql := IF(
    @exists = 0,
    'ALTER TABLE backend_completeorders ADD COLUMN client_price double NULL',
    'SELECT 1'
);

PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;



/*
SET @exists := (
    SELECT COUNT(*)
    FROM INFORMATION_SCHEMA.STATISTICS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'backend_scrapregion'
      AND INDEX_NAME = 'idx_sr_geometry'
);

SET @sql := IF(
    @exists = 0,
    'ALTER TABLE backend_scrapregion ADD SPATIAL INDEX idx_sr_geometry (geometry)',
    'SELECT 1'
);

PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;




SET @exists := (
    SELECT COUNT(*)
    FROM INFORMATION_SCHEMA.STATISTICS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'backend_localbody'
      AND INDEX_NAME = 'idx_lb_geometry'
);

SET @sql := IF(
    @exists = 0,
    'ALTER TABLE backend_localbody ADD SPATIAL INDEX idx_lb_geometry (geometry)',
    'SELECT 1'
);

PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
 */
