create table public.inbox
(
    hash       varchar(255) primary key,
    payload    jsonb       not null,
    status     varchar(20) NOT NULL DEFAULT 'NEW',
    created_at TIMESTAMP DEFAULT now()
);

alter table public.inbox
    owner to postgres;

