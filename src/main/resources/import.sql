/*
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES(1, 'Andres', 'Guzman', 'profesor@bolsadeideas.com', '2017-08-28', ' ');

INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES(2, 'David', 'Garcia', 'david@yomismo.com', '2021-11-18', ' ');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('John', 'Doe', 'john.doe@gmail.com', '2017-08-02', ' ');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Linus', 'Torvalds', 'linus.torvalds@gmail.com', '2017-08-03', ' ');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Jane', 'Doe', 'jane.doe@gmail.com', '2017-08-04', ' ');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Rasmus', 'Lerdorf', 'rasmus.lerdorf@gmail.com', '2017-08-05', ' ');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Erich', 'Gamma', 'erich.gamma@gmail.com', '2017-08-06', ' ');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Richard', 'Helm', 'richard.helm@gmail.com', '2017-08-07', ' ');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Ralph', 'Johnson', 'ralph.johnson@gmail.com', '2017-08-08', ' ');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('John', 'Vlissides', 'john.vlissides@gmail.com', '2017-08-09', ' ');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('James', 'Gosling', 'james.gosling@gmail.com', '2017-08-010', ' ');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Bruce', 'Lee', 'bruce.lee@gmail.com', '2017-08-11', ' ');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Johnny', 'Doe', 'johnny.doe@gmail.com', '2017-08-12', ' ');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('John', 'Roe', 'john.roe@gmail.com', '2017-08-13', ' ');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Jane', 'Roe', 'jane.roe@gmail.com', '2017-08-14', ' ');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Richard', 'Doe', 'richard.doe@gmail.com', '2017-08-15', ' ');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Janie', 'Doe', 'janie.doe@gmail.com', '2017-08-16', ' ');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Phillip', 'Webb', 'phillip.webb@gmail.com', '2017-08-17', ' ');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Stephane', 'Nicoll', 'stephane.nicoll@gmail.com', '2017-08-18', ' ');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Sam', 'Brannen', 'sam.brannen@gmail.com', '2017-08-19', ' ');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Juergen', 'Hoeller', 'juergen.Hoeller@gmail.com', '2017-08-20', ' ');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Janie', 'Roe', 'janie.roe@gmail.com', '2017-08-21', ' ');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('John', 'Smith', 'john.smith@gmail.com', '2017-08-22', ' ');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Joe', 'Bloggs', 'joe.bloggs@gmail.com', '2017-08-23', ' ');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('John', 'Stiles', 'john.stiles@gmail.com', '2017-08-24', ' ');
INSERT INTO clientes (nombre, apellido, email, create_at, foto) VALUES('Richard', 'Roe', 'stiles.roe@gmail.com', '2017-08-25', ' ');


//Populate tabla productos
INSERT INTO producto (create_at, nombre, precio) VALUES (NOW(), 'Dia de seguiment', 500);
INSERT INTO producto (create_at, nombre, precio) VALUES (NOW(), 'Escombratge electrònic particular', 450);
INSERT INTO producto (create_at, nombre, precio) VALUES (NOW(), 'Escombratge electrònic empresa', 600);
INSERT INTO producto (create_at, nombre, precio) VALUES (NOW(), 'Localització', 550);
INSERT INTO producto (create_at, nombre, precio) VALUES (NOW(), 'LAU', 1000);
INSERT INTO producto (create_at, nombre, precio) VALUES (NOW(), 'Despeses diàries', 100);
INSERT INTO producto (create_at, nombre, precio) VALUES (NOW(), 'Desplaçament fora CCAA', 250);

// Creamos algunas facturas
INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES('Factura equipos de oficina', null, 1, NOW());
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 1, 1);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(2, 1, 4);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 1, 5);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 1, 7);

//Insert en facturas 2
INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES('Factura Bicicleta', 'Alguna nota importante!', 1, NOW());
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(3, 2, 6);

*/


/* Entrada en la tabla users con las contraseñas encriptadas por Bcrypt */
INSERT INTO user (username, password, enabled) VALUES('David', '$2a$10$6HcZ6WEhkEC9Q4lTYP6TP.KCVz0TXpnm9V2c8.sWamNUa8.Fmse/m', 1);
INSERT INTO user (username, password, enabled) VALUES('admin', '$2a$10$uXFKXsL.sdwYNXkERYsQ2OyzwuaNLoI.CUpUCfMGa1JUHoRP06Vpu', 1);


INSERT INTO producto (create_at, nombre, precio) VALUES (NOW(), 'Dia de seguiment', 500);
INSERT INTO producto (create_at, nombre, precio) VALUES (NOW(), 'Localització', 1000);
INSERT INTO producto (create_at, nombre, precio) VALUES (NOW(), 'Escombrat electrònic particular', 600);
INSERT INTO producto (create_at, nombre, precio) VALUES (NOW(), 'Escombrat electrònic empresa', 1200);
INSERT INTO producto (create_at, nombre, precio) VALUES (NOW(), 'Desplaçament i dietes', 100);
INSERT INTO producto (create_at, nombre, precio) VALUES (NOW(), 'Desplaçament i dietes fora CCAA', 300);

