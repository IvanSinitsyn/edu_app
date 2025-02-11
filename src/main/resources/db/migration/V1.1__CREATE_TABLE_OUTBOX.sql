CREATE TABLE outbox
(
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    payload    JSONB NOT NULL,
    created_at TIMESTAMP        DEFAULT now(),
    status     VARCHAR(20)      DEFAULT 'NEW'
);