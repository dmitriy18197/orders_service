drop table if exists ORDERS;

create table ORDERS (
    id SERIAL PRIMARY KEY NOT NULL,
    created varchar,
    status varchar
);