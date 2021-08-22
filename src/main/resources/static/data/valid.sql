drop table if exists comments;
drop table if exists rates;
drop table if exists products;
drop table if exists users;

create table users
(
    username varchar(100) not null,
    password varchar(100) not null,
    role     varchar(50)  not null,
    status   varchar(50)  not null,

    CONSTRAINT users_pk PRIMARY KEY (username)
);

create table products
(
    id    int            not null auto_increment,
    name  varchar(255)   not null,
    price decimal(15, 2) not null,

    CONSTRAINT products_pk PRIMARY KEY (id)
);

create table rates
(
    username   varchar(100) not null,
    product_id int          not null,
    rate       enum ('ONE','TWO','THREE','FOUR','FIVE'),

    CONSTRAINT rates_pk PRIMARY KEY (username, product_id),
    FOREIGN KEY (username) REFERENCES users (username),
    FOREIGN KEY (product_id) REFERENCES products (id)
);

create table comments
(
    id         int          not null auto_increment,
    username   varchar(100) not null,
    product_id int          not null,
    comment    varchar(150),

    CONSTRAINT comments_pk PRIMARY KEY (id),
    FOREIGN KEY (username) REFERENCES users (username),
    FOREIGN KEY (product_id) REFERENCES products (id)
);

-- password '123'
insert into users
values ('Khach', '$2a$10$JxfLhmcSvxKJpRcnjLBf3eNqSA2C0AFD3rJcpLeGwrPnMwKrwByiO', 'ADMIN', 'ACTIVE');
insert into users
values ('Jor', '$2a$10$JxfLhmcSvxKJpRcnjLBf3eNqSA2C0AFD3rJcpLeGwrPnMwKrwByiO', 'USER', 'ACTIVE');
insert into users
values ('Minas', '$2a$10$JxfLhmcSvxKJpRcnjLBf3eNqSA2C0AFD3rJcpLeGwrPnMwKrwByiO', 'USER', 'BLOCKED');

insert into products (name, price)
values ('Xiaomi Redmi Note 7', 89000);
insert into products (name, price)
values ('Samsung Galaxy S10', 320000);
insert into products (name, price)
values ('Xiaomi Redmi Note 10', 180000);
insert into products (name, price)
values ('Samsung Galaxy A22 ', 117000);

insert into comments (username, product_id, comment)
values ('Khach', 1, 'Good phone!!');
insert into comments (username, product_id, comment)
values ('Jor', 1, 'Nice!!');
insert into comments (username, product_id, comment)
values ('Minas', 2, 'Giny kaseq?');
insert into comments (username, product_id, comment)
values ('Jor', 3, 'Im want this one!');

insert into rates (username, product_id, rate)
VALUES ('Khach', 1, 'FIVE');
insert into rates (username, product_id, rate)
VALUES ('Khach', 2, 'FOUR');
insert into rates (username, product_id, rate)
VALUES ('Minas', 1, 'ONE');
insert into rates (username, product_id, rate)
VALUES ('Minas', 4, 'FIVE');
insert into rates (username, product_id, rate)
VALUES ('Jor', 3, 'FOUR');
insert into rates (username, product_id, rate)
VALUES ('Jor', 1, 'FIVE');
insert into rates (username, product_id, rate)
VALUES ('Jor', 2, 'THREE');
