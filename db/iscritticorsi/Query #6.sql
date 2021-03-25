SELECT c.codins, c.nome, c.crediti, c.pd, COUNT(*) AS tot
FROM corso c, iscrizione i
WHERE c.codins = i.condins AND c.pd = 1
GROUP BY c.codins, c.nome, c.crediti, c.pd