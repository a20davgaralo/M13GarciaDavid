-- auto-generated definition

use a20davgaralo_projecteM13;

create table user
(
    id       int auto_increment
        primary key,
    username varchar(50)          not null,
    password varchar(80)          not null,
    enabled   tinyint(1) default 1 not null,
    constraint user_username_uindex
        unique (username)
);

-- auto-generated definition
create table authoritie
(
    id        int auto_increment
        primary key,
    user_id   int         not null,
    authority varchar(45) not null,
    constraint user_id_authorities_unique
        unique (user_id, authority),
    constraint fk_authorities_users
        foreign key (user_id) references user (id)
            on update cascade on delete cascade
);


# Afegim una clau forànea a la taula user per identificar a un client amb aquest usuari
ALTER TABLE
    user
ADD COLUMN
    id_cliente BIGINT(20),
ADD FOREIGN KEY
    user (id_cliente) REFERENCES cliente (id)
ON DELETE CASCADE ON UPDATE CASCADE;

DESCRIBE cliente;


