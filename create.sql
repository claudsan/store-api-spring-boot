create sequence itemreq start 1 increment 50;
create sequence ordereq start 1 increment 50;
create sequence stockmovreq start 1 increment 50;
create sequence userreq start 1 increment 50;
create table item (item_id int8 not null, name varchar(255) not null, quantity_avaiable int8 not null, primary key (item_id));
create table stock_mov (stock_mov_id int8 not null, creation_date timestamp, quantity int8 not null, type_movement varchar(255), item_id int8 not null, order_id int8, primary key (stock_mov_id));
create table store_order (order_id int8 not null, creation_date timestamp, order_status varchar(255), quantity int8 not null, quantity_served int8, item_id int8 not null, user_id int8 not null, primary key (order_id));
create table store_user (user_id int8 not null, email varchar(255), name varchar(255), primary key (user_id));
alter table stock_mov add constraint FK17aj6skljlmm6fgdp7x4jm5hb foreign key (item_id) references item;
alter table stock_mov add constraint FKhnrg4fpadvvl4bsajv1l4de18 foreign key (order_id) references store_order;
alter table store_order add constraint FK89p8ogc9ewv2qvq6kxk2qfkhx foreign key (item_id) references item;
alter table store_order add constraint FK2mixu294w725qu9gjjiskmjof foreign key (user_id) references store_user;
