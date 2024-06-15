ALTER TABLE adj_contracte
    ADD id_usuari_creacio INTEGER;

ALTER TABLE adj_contracte
    ADD FOREIGN KEY (id_usuari_creacio) REFERENCES adj_usuari (id_usuari);