ALTER TABLE adj_usuari
    DROP COLUMN ubicacio;

ALTER TABLE adj_usuari
    ADD pais VARCHAR(32),
    ADD codi_postal INTEGER,
    ADD direccio VARCHAR(128);