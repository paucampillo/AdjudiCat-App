UPDATE adj_contracte
SET termini_presentacio_ofertes = DATE_ADD(data_publicacio_anunci, INTERVAL 1 MONTH)
WHERE termini_presentacio_ofertes IS NULL AND data_publicacio_anunci IS NOT NULL;