CREATE TABLE adj_departament
(id_departament INTEGER AUTO_INCREMENT,
codi INTEGER UNIQUE,
nom VARCHAR(128),
PRIMARY KEY (id_departament));

CREATE TABLE adj_organ
(id_organ INTEGER AUTO_INCREMENT,
 codi INTEGER UNIQUE,
 nom VARCHAR(128),
 id_departament INTEGER,
 PRIMARY KEY (id_organ),
 FOREIGN KEY (id_departament) REFERENCES adj_departament (id_departament));

CREATE TABLE adj_idioma
(id_idioma INTEGER AUTO_INCREMENT,
codi VARCHAR(8) UNIQUE NOT NULL,
nom VARCHAR(16) NOT NULL,
PRIMARY KEY (id_idioma));

CREATE TABLE adj_rol (
 id_rol INTEGER AUTO_INCREMENT,
 codi VARCHAR(8) UNIQUE NOT NULL,
 nom VARCHAR(16) NOT NULL,
 visible BOOLEAN NOT NULL,
 PRIMARY KEY (id_rol));

CREATE TABLE adj_usuari
(id_usuari INTEGER AUTO_INCREMENT,
 nom VARCHAR(128) NOT NULL,
 email VARCHAR(128) NOT NULL,
 contrasenya VARCHAR(64) NOT NULL,
 ubicacio VARCHAR(255),
 telefon VARCHAR(16),
 notificicacions_actives BOOLEAN,
 descripcio VARCHAR(255),
 enllac_perfil_social VARCHAR(255),
 ident_nif VARCHAR(128),
 bloquejat BOOLEAN,
 id_organ INTEGER,
 id_idioma INTEGER NOT NULL,
 id_rol INTEGER NOT NULL,
 PRIMARY KEY (id_usuari),
 FOREIGN KEY (id_organ) REFERENCES adj_organ (id_organ),
 FOREIGN KEY (id_idioma) REFERENCES adj_idioma (id_idioma),
 FOREIGN KEY (id_rol) REFERENCES adj_rol (id_rol));

CREATE TABLE adj_usuari_login_fallido
(id_login_fallido INTEGER AUTO_INCREMENT,
 numero_intentos INTEGER NOT NULL,
 data_finalizacion_ban DATETIME NOT NULL,
 id_usuari INTEGER NOT NULL,
 PRIMARY KEY (id_login_fallido),
 FOREIGN KEY (id_usuari) REFERENCES adj_usuari (id_usuari));

CREATE TABLE adj_ambit
(id_ambit INTEGER AUTO_INCREMENT,
codi INTEGER UNIQUE,
nom VARCHAR(128),
PRIMARY KEY (id_ambit));

CREATE TABLE adj_lot
(id_lot INTEGER AUTO_INCREMENT,
 numero VARCHAR(32) UNIQUE,
 descripcio VARCHAR(255),
 PRIMARY KEY (id_lot));

CREATE TABLE adj_missatge
(id_missatge INTEGER AUTO_INCREMENT,
missatge VARCHAR(255)NOT NULL,
data_hora_envio DATETIME NOT NULL,
id_emissor INTEGER NOT NULL,
id_receptor INTEGER NOT NULL,
PRIMARY KEY (id_missatge),
FOREIGN KEY (id_emissor) REFERENCES adj_usuari (id_usuari),
FOREIGN KEY (id_receptor) REFERENCES adj_usuari (id_usuari),
CHECK (id_emissor <> id_receptor));

CREATE TABLE adj_contracte
(id_contracte INTEGER AUTO_INCREMENT,
codi_expedient INTEGER UNIQUE,
tipus_contracte VARCHAR(128),
subtipus_contracte VARCHAR(128),
procediment VARCHAR(255),
objecte_contracte VARCHAR(255),
pressupost_licitacio DOUBLE,
valor_estimat_contracte DOUBLE,
lloc_execucio VARCHAR(255),
duracio_contracte VARCHAR(255),
termini_presentacio_ofertes DATETIME,
data_publicacio_anunci DATETIME,
ofertes_rebudes INTEGER,
resultat VARCHAR(64),
enllac_publicacio VARCHAR(255),
data_adjudicacio_contracte DATETIME,
id_ambit INTEGER,
id_lot INTEGER,
PRIMARY KEY (id_contracte),
 FOREIGN KEY (id_ambit) REFERENCES adj_ambit (id_ambit),
 FOREIGN KEY (id_lot) REFERENCES adj_lot (id_lot));

CREATE TABLE adj_oferta
(id_oferta INTEGER AUTO_INCREMENT,
id_contracte INTEGER NOT NULL,
id_empresa INTEGER NOT NULL,
data_hora_oferta DATETIME,
import_adjudicacio_sense DOUBLE,
import_adjudicacio_amb_iva DOUBLE,
ganadora BOOLEAN NOT NULL,
PRIMARY KEY (id_oferta),
 FOREIGN KEY (id_contracte) REFERENCES adj_contracte (id_contracte),
 FOREIGN KEY (id_empresa) REFERENCES adj_usuari (id_usuari),
UNIQUE(id_contracte, id_empresa));

CREATE TABLE adj_valoracio
(id_valoracio INTEGER AUTO_INCREMENT,
puntuacio INTEGER NOT NULL,
descripcio VARCHAR(255),
data_hora_valoracio DATETIME NOT NULL,
id_empresa_valorada INTEGER NOT NULL,
id_organisme INTEGER NOT NULL,
PRIMARY KEY (id_valoracio),
 FOREIGN KEY (id_empresa_valorada) REFERENCES adj_usuari (id_usuari),
 FOREIGN KEY (id_organisme) REFERENCES adj_usuari (id_usuari));