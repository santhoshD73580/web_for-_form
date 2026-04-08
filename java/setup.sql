-- ─────────────────────────────────────────────────────────────
--  MySQL Setup Script  –  Run this ONCE before starting the app
-- ─────────────────────────────────────────────────────────────

-- 1. Create the database
CREATE DATABASE IF NOT EXISTS registration_db
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE registration_db;

-- 2. Create the users table
CREATE TABLE IF NOT EXISTS users (
  id         INT          AUTO_INCREMENT PRIMARY KEY,
  name       VARCHAR(100) NOT NULL,
  email      VARCHAR(150) NOT NULL UNIQUE,
  phone      VARCHAR(20)  NOT NULL,
  created_at TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);

-- 3. (Optional) Create a dedicated app user instead of using root
-- CREATE USER 'app_user'@'localhost' IDENTIFIED BY 'StrongPass123!';
-- GRANT SELECT, INSERT ON registration_db.users TO 'app_user'@'localhost';
-- FLUSH PRIVILEGES;

-- 4. Verify
DESCRIBE users;
