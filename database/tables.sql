
-- Table: audit_event_type
CREATE TABLE IF NOT EXISTS audit_event_type (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    description VARCHAR(50) NOT NULL
);

-- Table: audit_logs
CREATE TABLE IF NOT EXISTS audit_logs (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    event_type UUID REFERENCES audit_event_type(id),
    application_name VARCHAR(100),
    description TEXT,
    metadata JSONB
);
