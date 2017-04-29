PRAGMA foreign_keys = off;

DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS receipts;
DROP TABLE IF EXISTS pallets;
DROP TABLE IF EXISTS locations;
DROP TABLE IF EXISTS ingredients;

CREATE TABLE customers (
	id varchar(20),
	address varchar(20),
	primary key (id, address)
);

CREATE TABLE orders (
	id integer,
	amount integer not null check (amount > 0),
	delivery_date date,
	customer_id varchar(20),
	product_name varchar(20),
	primary key (id),
	foreign key (customer_id) references customers(id),
	foreign key (product_name) references receipts(name)
);

CREATE TABLE receipts (
	name varchar(20),
	primary key (name)
);

CREATE TABLE pallets (
	id integer,
	product_name varchar(20),
	location varchar(20),
	production_time time,
	production_date date,
	primary key (id),
	foreign key (location) references locations(name)
);

CREATE TABLE locations (
	name varchar(20),
	primary key(name)
);

CREATE TABLE ingredients (
	name varchar(20),
	unit varchar(5),
	amount integer check(amount > 0),
	cookie_name varchar(20),
	primary key(name),
	foreign key(cookie_name) references receipts(name)
);

CREATE TABLE storage (
	name varchar(20),
	unit varchar(5),
	amount integer check(amount > 0),
	last_date date,
	primary key(name)
	
);

BEGIN TRANSACTION;

INSERT INTO customers (id, address) VALUES
('Finkakor AB', 'Helsingborg'),
('Småbröd AB', 'Malmö'),
('Kaffebröd AB','Landskrona'),
('Bjudkakor AB','Ystad'),
('Kalaskakor AB','Trelleborg'),
('Partykakor AB','Kristianstad'),
('Gästkakor AB','Hässleholm'),
('Skånekakor AB','Perstorp');

INSERT INTO orders (id, amount, delivery_date, customer_id, product_name) VALUES
(

INSERT INTO receipts (name) VALUES
('Nut ring'),
('Nut cookie'),
('Amneris'),
('Tango'),
('Almond delight'),
('Berliner');

INSERT INTO ingredients(name, unit, amount, cookie_name) VALUES
('Flour', 'g', 450, 'Nut ring'),
('Butter', 'g', 450, 'Nut ring'),
('Icing sugar', 'g', 190, 'Nut ring'),
('Roasted, chopped nuts', 'g', 225, 'Nut ring'),
('Fine-ground nuts', 'g', 750, 'Nut cookie'),
('Ground, roasted nuts', 'g', 625, 'Nut cookie'),
('Bread crumbs', 'g', 125, 'Nut cookie'),
('Sugar', 'g', 375, 'Nut cookie'),
('Egg whites', 'dl', 3.5, 'Nut cookie'),
('Chocolate', 'g', 50, 'Nut cookie'),
('Marzipan', 'g', 750, 'Amneris'),
('Butter', 'g', 250, 'Amneris'),
('Eggs', 'g', 250, 'Amneris'),
('Potato starch', 'g', 25, 'Amneris'),
('Wheat flour', 'g', 25, 'Amneris'),
('Butter', 'g', 200, 'Tango'),
('Sugar', 'g', 250, 'Tango'),
('Flour', 'g', 300, 'Tango'),
('Sodium bicarbonate', 'g', 4, 'Tango'),
('Vanilla', 'g', 2, 'Tango'),
('Butter', 'g', 400, 'Almond delight'),
('Sugar', 'g', 270, 'Almond delight'),
('Chopped almonds', 'h', 279, 'Almond delight'),
('Flour', 'g', 400, 'Almond delight'),
('Cinnamon', 'g', 10, 'Almond delight'),
('Flour', 'g', 350, 'Berliner'),
('Butter', '', 250, 'Berliner'),
('Icing sugar', 'g', 100, 'Berliner'),
('Eggs', 'g', 50, 'Berliner'),
('Vanilla sugar', 'g', 5, 'Berliner'),
('Chocolate', 'g', 50, 'Berliner');

INSERT INTO locations(name) VALUES
('Storage freezer'),
('Laboratory'),
('On the road'),
('N/A');

