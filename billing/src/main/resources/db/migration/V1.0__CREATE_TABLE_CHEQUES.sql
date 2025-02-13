create table public.cheques
(
    id           bigserial primary key,
    client_id    varchar(255)   not null,
    date         date           not null,
    trucks_count int            not null,
    parcel_count int            not null,
    type varchar(20) not null,
    cost         numeric(19, 4) not null
);

alter table public.cheques
    owner to postgres;

