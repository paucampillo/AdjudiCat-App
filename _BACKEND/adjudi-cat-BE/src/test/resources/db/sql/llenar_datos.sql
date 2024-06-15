INSERT INTO adj_ambit (codi, nom) VALUES
(1, 'Ambit 1'),
(2, 'Ambit 2'),
(3, 'Ambit 3'),
(4, 'Ambit 4'),
(5, 'Ambit 5'),
(6, 'Ambit 6'),
(7, 'Ambit 7'),
(8, 'Ambit 8'),
(9, 'Ambit 9');

INSERT INTO adj_idioma (id_idioma, codi, nom) VALUES
(1, 'CAT', 'Català'),
(2, 'CAS', 'Castellano'),
(3, 'EN', 'English');

INSERT INTO adj_lot (numero, descripcio) VALUES
('1', 'Lot 1'),
('2', 'Lot 2'),
('3', 'Lot 3'),
('4', 'Lot 4'),
('5', 'Lot 5'),
('6', 'Lot 6'),
('7', 'Lot 7'),
('8', 'Lot 8'),
('9', 'Lot 9');

INSERT INTO adj_organ (codi, nom) VALUES
(1, 'Organ 1'),
(2, 'Organ 2'),
(3, 'Organ 3'),
(4, 'Organ 4'),
(5, 'Organ 5'),
(6, 'Organ 6'),
(7, 'Organ 7'),
(8, 'Organ 8'),
(9, 'Organ 9');

INSERT INTO adj_rol (id_rol, codi, nom, visible) VALUES
(1, 'ADM', 'Administrador', 0),
(2, 'USR', 'Usuari', 1),
(3, 'EP', 'Empresa privada', 1);

INSERT INTO adj_usuari (id_usuari, nom, email, contrasenya, id_idioma, id_rol, bloquejat) VALUES
(1, 'admin', 'admin@localhost', 'admin', 1, 1, 0),
(2, 'usuari', 'usuari@localhost', 'usuari', 1, 2, 0),
(3, 'public', 'public@localhost', 'public', 1, 3, 0),
(14, 'generalitat', 'generalitat@localhost', 'generalitat', 1, 3, 0),
(19, 'chatbot', 'chatbot@localhost', 'chatbot', 1, 1, 0),
(70, 'system', 'system@localhost', 'system', 1, 1, 0),
(4, 'bloquejat', 'bloquejat@localhost', 'bloquejat', 1, 2, 1);

INSERT INTO adj_usuari_login_fallido (numero_intentos, data_finalizacion_ban, id_usuari) VALUES
(3, '2025-01-01', 4);

INSERT INTO adj_contracte (codi_expedient, id_ambit, id_lot, id_usuari_creacio, termini_presentacio_ofertes) VALUES
('1', 1, 1, 3, '2020-01-01'),
('2', 2, 2, 3, '2021-01-01'),
('3', 3, 3, 3, '2022-01-01'),
('4', 4, 4, 3, '2023-01-01'),
('5', 5, 5, 3, '2024-01-01'),
('6', 6, 6, 3, '2025-01-01'),
('7', 7, 7, 3, '2026-01-01'),
('8', 8, 8, 3, '2027-01-01'),
('9', 9, 9, 3, '2028-01-01');

INSERT INTO adj_oferta (id_contracte, id_empresa, ganadora, import_adjudicacio_amb_iva) VALUES
(2, 2, 1, 1000),
(7, 2, 0, 2000000);

INSERT INTO adj_favorits (id_usuari, id_contracte) VALUES
(2, 1),
(3, 8);

INSERT INTO adj_missatge (id_emissor, id_receptor, missatge, data_hora_envio) VALUES
(1, 2, 'Hola', '2020-01-01'),
(2, 1, 'Hola, que tal', '2020-01-01');

INSERT INTO adj_chatbot (texto) VALUES
('Para licitar, haz clic en la sección correspondiente presente en cada oferta. Tu puja tiene que ser menor a la puja más baja actual. Tras introducir el valor, pulsa Confirmar.'),
('Para guardar un artículo como favorito, haz clic en el corazón presente en la oferta. Las ofertas marcadas como favorito se mostrarán en secciones especiales.');

INSERT INTO adj_relacio_articles (palabra, id_articulo) VALUES
('licitar', 1),
('oferta', 1),
('puja', 1),
('pujar', 1),
('subasta', 1),
('favorito', 2),
('guardar', 2),
('corazon', 2),
('estrella', 2);

INSERT INTO adj_valoracio (puntuacio, descripcio, data_hora_valoracio, id_empresa_valorada, id_organisme) VALUES
(5, 'Molt bé', '2020-01-01', 2, 1),
(1, 'Molt malament', '2020-01-01', 2, 1),
(3, 'Regular', '2020-01-01', 2, 1),
(4, 'Bé', '2020-01-01', 2, 1),
(2, 'Malament', '2020-01-01', 2, 1);