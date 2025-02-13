create table public.parcels
(
    name      varchar(255) primary key,
    form      text    not null,
    symbol    char(1) not null,
    is_loaded bool    not null default false
);

alter table public.parcels
    owner to postgres;

