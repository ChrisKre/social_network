-- Aufgabe 2
-- a)
SET search_path TO social_network, public;
DROP VIEW IF EXISTS pkp_symmetric;

CREATE VIEW pkp_symmetric AS
SELECT knows, pid FROM social_network.knows
UNION
SELECT pid AS knows, knows AS pid FROM social_network.knows;

-- b)
-- 1)
SELECT COUNT(DISTINCT(pl1.plid)) FROM social_network.organisation AS org, social_network.place AS pl1, social_network.place AS pl2
WHERE org.isLocatedIn=pl1.plid AND pl1.isPartOf=pl2.plid AND pl2.isPArtOf=1461;

-- 2)
SELECT firstname, lAStname, COUNT(poid) FROM social_network.person AS person, social_network.post
WHERE pid=hascreator
AND birthday = (SELECT MAX (birthday) FROM social_network.person)
GROUP BY firstname, lastname;

-- 3)
SELECT COALESCE(COUNT(coid)) AS comment_count, place.name FROM social_network.comment_ AS comment_
RIGHT JOIN social_network.place ON place.plid = comment_.plid
WHERE place.placetype = 'country'
GROUP BY place.name
ORDER BY comment_count ASC;

-- 4)
SELECT COUNT(pid) AS benutzeranzahl, name FROM social_network.place, social_network.person
WHERE placetype='city'
AND person.islocatedin = plid
GROUP BY plid, name
ORDER BY benutzeranzahl DESC;

-- 5)
SELECT person2.firstname, person2.lastname	FROM social_network.knows AS knows, social_network.person AS person1, social_network.person AS person2
WHERE knows.pid = person1.pid
AND person1.firstname = 'Hans'
AND person1.lastname = 'Johansson'
AND knows.knows = person2.pid;

-- 6)
SELECT DISTINCT p2.firstname, p2.lastname FROM social_network.person p2
	INNER JOIN social_network.knows k3 ON p2.pid = k3.knows
	INNER JOIN social_network.knows k2 ON k3.pid = k2.knows
	WHERE k2.pid = (
	    SELECT pid FROM social_network.person
	    WHERE firstname = 'Hans'
		AND lastname = 'Johansson')
	AND k3.knows NOT IN (
	SELECT knows from social_network.knows k
		WHERE k.pid = (
		    SELECT pid FROM social_network.person
		    WHERE firstname = 'Hans'
		    AND lastname = 'Johansson'))
	ORDER BY p2.lastname;

-- 7)
SELECT pp.firstname, pp.lastname FROM social_network.person pp
INNER JOIN social_network.hasMember mmm ON pp.pid = mmm.pid
WHERE mmm.fid = ALL(
    SELECT mm.fid FROM social_network.person p
    INNER JOIN social_network.hasMember mm ON p.pid = mm.pid
    WHERE p.firstname = 'Mehmet'
    AND p.lastname = 'Koksal');

-- 8)
SELECT pl3.name, COUNT(*)/ CAST(SUM(COUNT(*)) OVER () AS float) AS Verteilung FROM social_network.person p
INNER JOIN social_network.place pl ON p.isLocatedIn = pl.plid
INNER JOIN social_network.place pl2 ON pl.ispartof = pl2.plid
INNER JOIN social_network.place pl3 ON pl2.ispartof = pl3.plid
GROUP BY pl3.name

-- 9)
SELECT f.title FROM social_network.forum f
INNER JOIN social_network.post p ON f.fid = p.fid
GROUP BY f.fid
HAVING COUNT(f.fid) > (
	SELECT AVG(count) FROM(
		SELECT COUNT(*) FROM social_network.forum f2
		INNER JOIN social_network.post p2 ON f2.fid = p2.fid
		GROUP BY f2.fid
)as counts)
ORDER BY f.title;

-- 10)
SELECT p2.firstname, p2.lastname FROM social_network.person p
INNER JOIN social_network.knows k ON p.pid = k.pid
INNER JOIN social_network.person p2 ON k.knows = p2.pid
WHERE p.pid = (SELECT p3.pid FROM social_network.person p3
			INNER JOIN social_network.personlikespost plp2 ON p3.pid = plp2.pid
			   WHERE plp2.poid = (
	SELECT plp.poid FROM social_network.personlikespost plp
	GROUP BY plp.poid
	ORDER BY COUNT(*) DESC
	LIMIT 1)
			  LIMIT 1)
ORDER BY p2.lastname;

-- c)
DROP TABLE IF EXISTS social_network.vergangeneArbeitsverhaeltnisse;
DROP FUNCTION IF EXISTS social_network.beendeArbeitsverhaeltnis() CASCADE;

CREATE TABLE social_network.vergangeneArbeitsverhaeltnisse(
	datum DATE NOT NULL DEFAULT(CURRENT_TIMESTAMP),
	pid BIGINT NOT NULL,
	oid BIGINT NOT NULL
);

CREATE FUNCTION social_network.beendeArbeitsverhaeltnis()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
BEGIN
	INSERT INTO social_network.vergangeneArbeitsverhaeltnisse(pid, oid)
	VALUES(OLD.pid, OLD.oid);
RETURN NEW;
END;
$$;

CREATE TRIGGER person_workedAt
	AFTER DELETE ON social_network.workat
	FOR EACH ROW
	EXECUTE PROCEDURE social_network.beendeArbeitsverhaeltnis();
END;

-- Testet Trigger
INSERT INTO social_network.workat(
	pid, oid, workfrom)
	VALUES (12094627905604, 1, 1);

DELETE FROM social_network.workat
WHERE pid = 12094627905604;

SELECT * from social_network.vergangeneArbeitsverhaeltnisse;