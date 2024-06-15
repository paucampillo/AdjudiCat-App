CREATE TABLE adj_chatbot
(
    id_articulo INTEGER AUTO_INCREMENT,
    texto VARCHAR(1023) NOT NULL,
    PRIMARY KEY (id_articulo)
);

CREATE TABLE adj_relacio_articles
(
    palabra VARCHAR(16),
    id_articulo INTEGER NOT NULL,
    PRIMARY KEY (palabra),
    FOREIGN KEY (id_articulo) REFERENCES adj_chatbot (id_articulo)
);
