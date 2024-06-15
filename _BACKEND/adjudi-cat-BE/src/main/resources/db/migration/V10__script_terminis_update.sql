UPDATE adj_contracte
SET termini_presentacio_ofertes = CONCAT('2024', RIGHT(termini_presentacio_ofertes, 15))
WHERE termini_presentacio_ofertes < '2022-07-01' AND termini_presentacio_ofertes IS NOT NULL;
