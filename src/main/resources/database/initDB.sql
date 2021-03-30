drop table if exists clients;
create table clients
(
    id       serial       not null
        constraint clients_pkey
            primary key,
    name     varchar(255)  not null,
    surname  varchar(255)  not null,
    password varchar(255)  not null,
    role     varchar(255)  not null,
    email    varchar(255) not null,
    phone    varchar(255) default NULL::character varying,
    age      varchar(255) default NULL::character varying,
    authkey  varchar(255) default NULL::character varying
);

CREATE SEQUENCE hibernate_sequence START 1;