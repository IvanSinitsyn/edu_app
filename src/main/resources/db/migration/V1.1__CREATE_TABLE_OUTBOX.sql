CREATE TABLE outbox
(
    hash       varchar(255) primary key,
    payload    JSONB NOT NULL,
    status     VARCHAR(20) DEFAULT 'NEW',
    created_at TIMESTAMP   DEFAULT now()
);

alter table public.outbox
    owner to postgres;