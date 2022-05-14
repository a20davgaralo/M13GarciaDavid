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
    a20davgaralo_projecteM13.user
ADD FOREIGN KEY
    user (cliente_num) REFERENCES cliente (id)
ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `a20davgaralo_projecteM13`.`authoritie`
    DROP FOREIGN KEY `FKrsvghc1cfpns54pmc3c3ck3b2`;
ALTER TABLE `a20davgaralo_projecteM13`.`authoritie`
    ADD CONSTRAINT `FKrsvghc1cfpns54pmc3c3ck3b2`
        FOREIGN KEY (`user_id`)
            REFERENCES `a20davgaralo_projecteM13`.`user` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE;




DESCRIBE cliente;



