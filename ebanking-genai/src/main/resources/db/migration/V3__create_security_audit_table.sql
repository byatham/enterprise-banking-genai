
-- ============================================
-- V3: Security audit logs
-- ============================================

CREATE TABLE audit_logs (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    tenant_id UUID NOT NULL,
    user_id UUID,

    action VARCHAR(100) NOT NULL,
    resource_type VARCHAR(100),
    resource_id UUID,

    ip_address VARCHAR(45),
    user_agent VARCHAR(500),

    outcome VARCHAR(30) NOT NULL,
    details JSONB,

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_audit_tenant
        FOREIGN KEY (tenant_id)
        REFERENCES tenants(id),

    CONSTRAINT fk_audit_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE SET NULL,

    CONSTRAINT chk_audit_outcome
        CHECK (outcome IN ('SUCCESS', 'FAILURE', 'DENIED'))
);

CREATE INDEX idx_audit_tenant_created
    ON audit_logs(tenant_id, created_at DESC);

CREATE INDEX idx_audit_user_created
    ON audit_logs(user_id, created_at DESC);

CREATE INDEX idx_audit_action
    ON audit_logs(action);
