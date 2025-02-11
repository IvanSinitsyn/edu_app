create table public.inbox
(
    id         uuid primary key,
    topic      varchar(255)         default gen_random_uuid() not null,
    partition  int         not null,
    "offset"   bigint      not null,
    payload    jsonb       not null,
    status     varchar(20) NOT NULL DEFAULT 'NEW',
    created_at TIMESTAMP            DEFAULT now(),
    UNIQUE (topic, partition, "offset")
);

alter table public.inbox
    owner to postgres;

