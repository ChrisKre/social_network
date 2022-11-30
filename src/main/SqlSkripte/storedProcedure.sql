SET search_path TO social_network, public;

CREATE OR REPLACE FUNCTION getShortestPath(IN a BIGINT , IN b BIGINT)
RETURNS TABLE(start BIGINT, ende BIGINT, kosten integer, counter integer, pfad BIGINT[])
    LANGUAGE plpgsql
AS $$
BEGIN
return query WITH RECURSIVE init (start, ende, kosten, counter, pfad) AS (
    SELECT k.pid, k.knows, 0, 1, array[k.pid, k.knows]
    FROM pkp_symmetric AS k
    WHERE k.pid = a
    UNION ALL

(WITH init(start, ende, kosten, counter, pfad) AS (TABLE init)
    SELECT k.pid, k.knows, i1.kosten +1, i1.counter + 1, i1.pfad || array[k.knows]
    FROM knows AS k, init AS i1
    WHERE k.pid = i1.ende
    AND NOT k.knows = ANY(i1.pfad)
    AND (i1.start) IN (select i2.start
    from init AS i2)
    )
    ) SELECT * FROM init WHERE init.ende=b ORDER BY counter limit 1;

end;$$;

CREATE OR REPLACE PROCEDURE getShortestPathProcedure(INOUT start BIGINT , INOUT ende BIGINT, INOUT länge INTEGER, INOUT pfad BIGINT[])
    LANGUAGE plpgsql
AS $$
begin
SELECT path.start, path.ende, path.counter, path.pfad INTO start, ende, länge, pfad FROM getShortestPath(start, ende) as path;
end;$$;

Call getShortestPathProcedure(2199023255625, 3298534883405, 0, array[]::BIGINT[]);