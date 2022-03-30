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


INSERT INTO authoritie VALUES (1, 1, 'ROLE_USER'), (2, 2, 'ROLE_USER'), (3, 2, 'ROLE_ADMIN');
