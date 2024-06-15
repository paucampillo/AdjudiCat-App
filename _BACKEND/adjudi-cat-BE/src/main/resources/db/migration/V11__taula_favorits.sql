CREATE TABLE adj_favorits
(id_favorit INTEGER AUTO_INCREMENT,
 id_usuari INTEGER NOT NULL,
 id_contracte INTEGER NOT NULL,
 PRIMARY KEY (id_favorit),
 UNIQUE (id_usuari, id_contracte),
 FOREIGN KEY (id_usuari) REFERENCES adj_usuari (id_usuari),
 FOREIGN KEY (id_contracte) REFERENCES adj_contracte (id_contracte));