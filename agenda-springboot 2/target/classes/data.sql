-- Ciudades
INSERT INTO ciudades (id, nombre, provincia, pais) VALUES (1, 'Canning', 'Buenos Aires', 'Argentina');
INSERT INTO ciudades (id, nombre, provincia, pais) VALUES (2, 'Cordoba', 'Cordoba', 'Argentina');
INSERT INTO ciudades (id, nombre, provincia, pais) VALUES (3, 'Starefossen', 'Bergen', 'Noruega');
INSERT INTO ciudades (id, nombre, provincia, pais) VALUES (4, 'Pilar', 'Buenos Aires', 'Argentina');


-- Personas
INSERT INTO personas (id, nombre, apellido, ciudad_id, telefono) VALUES
(1, 'Juan', 'Pérez', 1, '1157013159');
INSERT INTO personas (id, nombre, apellido, ciudad_id, telefono) VALUES
(2, 'Agustin', 'Ocampo', 1, '1157013158');
INSERT INTO personas (id, nombre, apellido, ciudad_id, telefono) VALUES
(3, 'Emma', 'Andersen', 3, '92 0345 11233');
INSERT INTO personas (id, nombre, apellido, ciudad_id, telefono) VALUES
(4, 'Juan', 'Pérez', 4, '1157013159');
INSERT INTO personas (id, nombre, apellido, ciudad_id, telefono) VALUES
(5, 'Juan', 'Pérez', 2, '1157013159');


-- Empresas
INSERT INTO empresas (id, nombre, cuit, ciudad_id) VALUES (1, 'Acme S.A.', '30-44930482-6',1);
INSERT INTO empresas (id, nombre, cuit, ciudad_id) VALUES (2, 'Besysoft', '30-71012231-4',4);

-- Relaciones empresa-contacto
INSERT INTO empresa_contactos (empresa_id, persona_id) VALUES (1, 1);
INSERT INTO empresa_contactos (empresa_id, persona_id) VALUES (1, 3);
INSERT INTO empresa_contactos (empresa_id, persona_id) VALUES (2, 2); /* ;) */

-- Para modo de pruebas, como las tablas son cradas desde 0 cada vez que se corre la aplicacion
-- para no tener problemas con los ID's, hardcodeamos desde que numero comienza la secuencia de IDs

ALTER TABLE ciudades ALTER COLUMN id RESTART WITH 5;
ALTER TABLE personas ALTER COLUMN id RESTART WITH 6;
ALTER TABLE empresas ALTER COLUMN id RESTART WITH 3;