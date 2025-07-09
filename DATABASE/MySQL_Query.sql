-- ✅ 1. Use the right database
USE expiry_tracker;

-- ✅ 2. Drop existing product table (⚠️ will remove old data)
DROP TABLE IF EXISTS product;

-- ✅ 3. Recreate table with full structure
CREATE TABLE product (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(100),                        -- Optional (you can keep empty for now)
    supplier VARCHAR(255),                        -- Optional
    price DECIMAL(10, 2) NOT NULL DEFAULT 0.00,   -- Optional, default to 0
    quantity INT NOT NULL CHECK (quantity >= 1),
    purchase_date DATE,                           -- Optional
    manufacturing_date DATE,                      -- ✅ Newly added field
    expiry_date DATE NOT NULL,
    alert_days INT DEFAULT 7,                     -- ✅ Newly added field
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
SELECT * FROM product;

USE expiry_tracker;

CREATE TABLE IF NOT EXISTS alerts (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_id BIGINT,
    alert_type VARCHAR(50),           -- e.g. "Expired" or "Expiring Soon"
    alert_message TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
);



USE expiry_tracker;

DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS alerts;

CREATE TABLE product (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(100),
    supplier VARCHAR(255),
    price DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    quantity INT NOT NULL CHECK (quantity >= 1),
    purchase_date DATE,
    manufacturing_date DATE,
    expiry_date DATE NOT NULL,
    alert_days INT DEFAULT 7,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE alerts (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    alert_type VARCHAR(50),
    alert_message TEXT,
    created_at DATETIME,
    product_id BIGINT,
    FOREIGN KEY (product_id) REFERENCES product(id)
);

SELECT * FROM product;
SELECT * FROM alerts;

USE expiry_tracker;
SELECT * FROM product ORDER BY id DESC;


