UPDATE adj_contracte
SET data_publicacio_anunci = DATE('2023-01-01')
WHERE data_publicacio_anunci IS NULL;