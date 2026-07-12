create database if not exists forum;

use forum;


create table users
(
    user_id           int auto_increment
        primary key,
    first_name        varchar(32)                      not null,
    last_name         varchar(32)                      not null,
    email             varchar(80)                      not null,
    username          varchar(32)                      not null,
    password          varchar(255)                     not null,
    role              enum ('ROLE_USER', 'ROLE_ADMIN') not null,
    is_blocked        tinyint(1) default 0             not null,
    profile_photo_url varchar(2048)                    null,
    constraint users_pk
        unique (email),
    constraint users_pk_2
        unique (username)
);

create table phone_numbers
(
    phone_number_id int auto_increment
        primary key,
    number          varchar(20) not null,
    user_id         int         null,
    constraint phone_numbers_users_fk
        foreign key (user_id) references users (user_id)
);

create table posts
(
    post_id    int auto_increment
        primary key,
    title      varchar(64)                           not null,
    content    varchar(8192)                         not null,
    user_id    int                                   not null,
    created_at timestamp default current_timestamp() not null,
    constraint posts_users_fk
        foreign key (user_id) references users (user_id)
);

create table comments
(
    comment_id int auto_increment
        primary key,
    content    varchar(4096)                         not null,
    created_at timestamp default current_timestamp() not null,
    post_id    int                                   not null,
    user_id    int                                   not null,
    constraint comments_posts_fk
        foreign key (post_id) references posts (post_id)
            on delete cascade,
    constraint comments_users_fk
        foreign key (user_id) references users (user_id)
);

create table likes
(
    like_id    int auto_increment
        primary key,
    created_at timestamp default current_timestamp() not null,
    user_id    int                                   not null,
    post_id    int                                   not null,
    constraint unique_likes
        unique (user_id, post_id),
    constraint likes_posts_fk
        foreign key (post_id) references posts (post_id),
    constraint likes_users_fk
        foreign key (user_id) references users (user_id)
);
