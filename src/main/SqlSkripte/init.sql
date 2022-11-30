--DROP SCHEMA IF EXISTS social_network; 
--CREATE SCHEMA social_network;
SET search_path TO social_network, public;

DROP VIEW IF EXISTS pkp_symmetric;

DROP TABLE IF EXISTS PersonLikesPost;
DROP TABLE IF EXISTS PersonLikesComment;
DROP TABLE IF EXISTS PostHasTag;
DROP TABLE IF EXISTS CommentHasTag;
DROP TABLE IF EXISTS Comment_;
DROP TABLE IF EXISTS Post;
DROP TABLE IF EXISTS City CASCADE;
DROP TABLE IF EXISTS Country CASCADE;
DROP TABLE IF EXISTS Continent;

DROP TABLE IF EXISTS PersonHasInterest;
DROP TABLE IF EXISTS HasTag;
DROP TABLE IF EXISTS HasMember;
DROP TABLE IF EXISTS Forum CASCADE;
DROP TABLE IF EXISTS TagHasType;
DROP TABLE IF EXISTS TagIsSubclassOf;
DROP TABLE IF EXISTS TagClass;
DROP TABLE IF EXISTS Tag CASCADE;

DROP TABLE IF EXISTS StudyAt;
DROP TABLE IF EXISTS WorkAt;
DROP TABLE IF EXISTS Knows CASCADE;
DROP TABLE IF EXISTS University;
DROP TABLE IF EXISTS Company;
DROP TABLE IF EXISTS Organisation;
DROP TABLE IF EXISTS Speaks;
DROP TABLE IF EXISTS HasEmail;
DROP TABLE IF EXISTS Person CASCADE;
DROP TABLE IF EXISTS Place;

DROP DOMAIN IF EXISTS lng;
DROP DOMAIN IF EXISTS email_address;



CREATE TABLE Continent(
  plID INTEGER NOT NULL UNIQUE,
  name VARCHAR NOT NULL,
  PRIMARY KEY (plID)
);

CREATE TABLE Country(
  plID INTEGER NOT NULL,
  name VARCHAR NOT NULL,
  isPartOf INTEGER NOT NULL,
  PRIMARY KEY (plID),
  FOREIGN KEY (isPartOf)
      REFERENCES continent (plID)
);

CREATE TABLE City(
    plID INTEGER NOT NULL,
    name VARCHAR NOT NULL,
    isPartOf INTEGER NOT NULL,
    PRIMARY KEY (plID),
    FOREIGN KEY (isPartOf)
        REFERENCES country (plID)
);



CREATE TABLE Person(
    pID BIGINT NOT NULL,
    browserUsed VARCHAR,
    creationDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    firstName VARCHAR (255) NOT NULL,
    lastName VARCHAR (255),
    gender VARCHAR (9) NOT NULL,
    birthday TIMESTAMP,
    locationIP VARCHAR(255),
    isLocatedIn BIGINT,

    PRIMARY KEY (pID),
    FOREIGN KEY (isLocatedIn)
    	REFERENCES City (plID) ON DELETE CASCADE,
    CONSTRAINT Date_check CHECK (birthday <= CURRENT_TIMESTAMP),
    CONSTRAINT Creation_Check CHECK (creationDate <= CURRENT_TIMESTAMP)
);

CREATE TABLE Knows(
	pID BIGINT NOT NULL,
	knows BIGINT NOT NULL,
	creationDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (pID,knows),
	FOREIGN KEY (pID)
		REFERENCES person (pID) ON DELETE CASCADE,
	FOREIGN KEY (knows)
		REFERENCES person (pID) ON DELETE CASCADE,
	CONSTRAINT Creation_Check CHECK (creationDate <= CURRENT_TIMESTAMP)
);

CREATE TABLE Organisation(
	OID INTEGER NOT NULL,
	name VARCHAR (255) NOT NULL,
	PRIMARY KEY (OID)
);

CREATE TABLE University(
    OID INTEGER NOT NULL,
    name VARCHAR NOT NULL,
    isLocatedIn INTEGER NOT NULL,
    PRIMARY KEY (OID),
    FOREIGN KEY (isLocatedIn)
        REFERENCES City (PLID) ON DELETE CASCADE
);

CREATE TABLE Company(
    OID INTEGER NOT NULL,
    name VARCHAR NOT NULL,
    isLocatedIn INTEGER NOT NULL,
    PRIMARY KEY (OID),
    FOREIGN KEY (isLocatedIn)
        REFERENCES country (PLID) ON DELETE CASCADE
);

CREATE TABLE WorkAt(
	pID BIGINT NOT NULL,
	oID INTEGER NOT NULL,
	workFrom INTEGER,

	PRIMARY KEY (pID, oID),
	FOREIGN KEY (pID)
		REFERENCES Person (pID) ON DELETE CASCADE,
	FOREIGN KEY (oID)
		REFERENCES Company (oID) ON DELETE CASCADE
);

CREATE TABLE StudyAt(
	pID BIGINT NOT NULL,
	oID INTEGER NOT NULL,
	classYear INTEGER,

	PRIMARY KEY (pID, oID),
	FOREIGN KEY (pID)
		REFERENCES person (pID) ON DELETE CASCADE,
	FOREIGN KEY (oID)
		REFERENCES University (oID) ON DELETE CASCADE
);


CREATE TABLE Tag(
	tID BIGINT NOT NULL,
	name VARCHAR (255) NOT NULL,
	PRIMARY KEY (tID)
);

CREATE TABLE Forum(
	fID BIGINT NOT NULL,
	title VARCHAR (255) NOT NULL,
	creationDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	hasModerator BIGINT NOT NULL,
	PRIMARY KEY (fID),
	FOREIGN KEY (hasModerator)
		REFERENCES person (pID) ON DELETE CASCADE,
    CONSTRAINT Creation_Check CHECK (creationDate <= CURRENT_TIMESTAMP)
);

CREATE TABLE HasTag(
	fID BIGINT NOT NULL,
	tID BIGINT NOT NULL,
	PRIMARY KEY (fID, tID),
	FOREIGN KEY (fID)
	    REFERENCES Forum (fID) ON DELETE CASCADE,
	FOREIGN KEY (tID)
	    REFERENCES Tag (tID) ON DELETE CASCADE
);

CREATE TABLE HasMember(
    fID BIGINT NOT NULL,
    pID BIGINT NOT NULL,
    joinDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (fID, pID),
    FOREIGN KEY (fID)
        REFERENCES forum (fID) ON DELETE CASCADE,
    FOREIGN KEY (pID)
        REFERENCES person (pID) ON DELETE CASCADE
);

CREATE DOMAIN email_address AS TEXT
check (value ~* '^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$');

CREATE TABLE HasEmail(
    pID BIGINT NOT NULL,
    email email_address NOT NULL,
    PRIMARY KEY (pID, email),
    FOREIGN KEY (pID)
        REFERENCES person (pID) ON DELETE CASCADE
);

CREATE DOMAIN lng AS TEXT;

CREATE TABLE Speaks(
    pID BIGINT NOT NULL,
    lng lng NOT NULL,
    PRIMARY KEY(pID, lng),
    FOREIGN KEY (pID)
        REFERENCES person (pID) ON DELETE CASCADE
);

CREATE TABLE Post(
    poID BIGINT NOT NULL UNIQUE,
    imageFile BYTEA,
    creationDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    locationIP VARCHAR,
    browserUsed VARCHAR,
    language VARCHAR,
    content VARCHAR,
    length INTEGER,
    hasCreator BIGINT NOT NULL,
    fID BIGINT NOT NULL,
    plID BIGINT NOT NULL,

    PRIMARY KEY (poID),
    FOREIGN KEY (hasCreator)
        REFERENCES person (pID) ON DELETE CASCADE,
    FOREIGN KEY (fID)
        REFERENCES forum (fID) ON DELETE CASCADE,
	FOREIGN KEY (plID)
        REFERENCES country (plID) ON DELETE CASCADE,
    CONSTRAINT Content_Check CHECK (imageFile IS NOT NULL OR content IS NOT NULL),
    CONSTRAINT Creation_Check CHECK (creationDate <= CURRENT_TIMESTAMP)
);

CREATE TABLE PostHasTag(
    poID BIGINT NOT NULL,
    tID BIGINT NOT NULL,
    PRIMARY KEY (poID, tID),
    FOREIGN KEY (poID)
        REFERENCES post (poID) ON DELETE CASCADE,
    FOREIGN KEY (tID)
        REFERENCES tag (tID) ON DELETE CASCADE
);

CREATE TABLE PersonLikesPost(
    pID BIGINT NOT NULL,
    poID BIGINT NOT NULL,
    creationDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (pID, poID),
    FOREIGN KEY (pID)
        REFERENCES person (pID) ON DELETE CASCADE,
    FOREIGN KEY (poID)
    	REFERENCES post (poID) ON DELETE CASCADE,
    CONSTRAINT Creation_Check CHECK (creationDate <= CURRENT_TIMESTAMP)
);

CREATE TABLE Comment_(
    coID BIGINT NOT NULL UNIQUE,
    creationDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    locationIP VARCHAR,
    browserUsed VARCHAR,
    content VARCHAR NOT NULL,
    length INTEGER,
    hasCreator BIGINT NOT NULL,
    plID BIGINT NOT NULL,
    replyOfPost BIGINT,
    replyOfComment BIGINT,

    PRIMARY KEY (coID),
    FOREIGN KEY (hasCreator)
        REFERENCES person (pID) ON DELETE CASCADE,
    FOREIGN KEY (replyOfPost)
        REFERENCES post (poID) ON DELETE CASCADE,
    FOREIGN KEY (replyOfComment)
        REFERENCES comment_ (coID) ON DELETE CASCADE,
	FOREIGN KEY (plID)
        REFERENCES country (plID) ON DELETE CASCADE,
    CONSTRAINT Creation_Check CHECK (creationDate <= CURRENT_TIMESTAMP)
);

CREATE TABLE CommentHasTag(
    coID BIGINT NOT NULL,
    tID BIGINT NOT NULL,
    PRIMARY KEY (coID, tID),
    FOREIGN KEY (coID)
        REFERENCES comment_ (coID) ON DELETE CASCADE,
    FOREIGN KEY (tID)
        REFERENCES tag (tID) ON DELETE CASCADE
);

CREATE TABLE TagClass(
	tcID BIGINT NOT NULL,
	name VARCHAR (255),
	PRIMARY KEY (tcID)
);

CREATE TABLE TagHasType(
	tID BIGINT NOT NULL,
	tcID BIGINT NOT NULL,
	PRIMARY KEY (tid, tcID),
	FOREIGN KEY (tID)
        REFERENCES tag (tID) ON DELETE CASCADE,
    FOREIGN KEY (tcID)
        REFERENCES tagclass (tcID) ON DELETE CASCADE
);

CREATE TABLE PersonLikesComment(
    pID BIGINT NOT NULL,
    coID BIGINT NOT NULL,
    creationDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (pID, coID),
    FOREIGN KEY (pID)
        REFERENCES person (pID) ON DELETE CASCADE,
    FOREIGN KEY (coID)
        REFERENCES comment_ (coID) ON DELETE CASCADE,
    CONSTRAINT Creation_Check CHECK (creationDate <= CURRENT_TIMESTAMP)
);

CREATE TABLE PersonHasInterest(
    pID BIGINT NOT NULL,
    tID BIGINT NOT NULL,
    PRIMARY KEY (pID, tID),
    FOREIGN KEY (pID)
        REFERENCES person (pID) ON DELETE CASCADE,
    FOREIGN KEY (tID)
        REFERENCES tag (tID) ON DELETE CASCADE
);

CREATE TABLE TagIsSubclassOf(
	tcID BIGINT NOT NULL,
	isSubclassOf BIGINT NOT NULL,
	PRIMARY KEY (tcID, isSubclassOf),
	FOREIGN KEY (tcID)
        REFERENCES tagclass (tcID) ON DELETE CASCADE,
	FOREIGN KEY (isSubclassOf)
        REFERENCES tagclass (tcID) ON DELETE CASCADE
);

CREATE VIEW pkp_symmetric AS
SELECT knows, pid FROM social_network.knows
UNION
SELECT pid AS knows, knows AS pid FROM social_network.knows;
CREATE TABLE Place(
                      plID INTEGER NOT NULL,
                      name VARCHAR NOT NULL,
                      PRIMARY KEY (plID, name)
);