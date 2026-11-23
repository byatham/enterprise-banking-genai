
-- ============================================
-- V2: Initial system roles
-- ============================================

INSERT INTO roles (name)
VALUES
    ('ADMIN'),
    ('ANALYST'),
    ('CUSTOMER')
ON CONFLICT (name) DO NOTHING;
