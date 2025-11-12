-- SQL script to fix existing users with NULL roles
-- Run this script manually in your MySQL database if you have existing users with NULL roles

UPDATE users SET role = 'USER' WHERE role IS NULL;

-- Verify the update
SELECT id, email, role FROM users WHERE role IS NULL;

