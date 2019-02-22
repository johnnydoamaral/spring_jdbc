create table person
(
id integer not null,
name varchar(255) not null,
location varchar(255),
birth_date timestamp,
primary key (id)
);

insert into person 
(id, name, location, birth_date) 
values 
(1001, 'Johnny', 'Brazil', sysdate());

insert into person 
(id, name, location, birth_date) 
values 
(1002, 'Mary', 'Netherlands', sysdate());

insert into person 
(id, name, location, birth_date) 
values 
(1003, 'Paul', 'Greece', sysdate());