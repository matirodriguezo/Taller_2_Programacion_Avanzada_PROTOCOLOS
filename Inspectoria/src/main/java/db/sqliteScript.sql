--sqlite3
--.open c:/sqlite/prgavz.db
--.mode column
--.headers on
--.databases
--.tables

drop table persona;

create table persona
( id INTEGER PRIMARY KEY AUTOINCREMENT,
  dni int,
  nombres varchar(50)
);

insert into persona(dni, nombres) values (123, 'persona 1');
insert into persona(dni, nombres) values (456, 'persona 2');
insert into persona(dni, nombres) values (789, 'persona 3');

select * from persona;