create sequence HIBERNATE_SEQUENCE;

create table ATTACK
(
    ID          INTEGER,
    NAME        CHARACTER VARYING(255),
    ATTRIBUTE CHARACTER VARYING (255),
    TYPE CHARACTER VARYING (255),
    COST INTEGER ,
    POWER INTEGER ,
    INHERITABLE BIT
);

create table digimon
(
    ID        INTEGER,
    MEMORY    INTEGER,
    NAME      CHARACTER VARYING(255),
    SLOTS     INTEGER,
    STAGE     CHARACTER VARYING(255),
    TYPE      CHARACTER VARYING(255),
    ATTRIBUTE CHARACTER VARYING(255),
    SKILL_ID  INTEGER
);

create table digimon_stats
(
    ID         IDENTITY (342),
    HP         INTEGER,
    SP         INTEGER,
    ATTACK     INTEGER,
    DEFENSE    INTEGER,
    INTELLECT  INTEGER,
    SPEED      INTEGER,
    DIGIMON_ID INTEGER,
    LEVEL      INTEGER
);

create table digimon_evolve_from
(
    DIGIMON_ID     INTEGER,
    EVOLVE_FROM_ID INTEGER
);

create table evolution
(
    ID      INTEGER,
    ABI     INTEGER,
    ATK     INTEGER,
    CAM     CHARACTER VARYING(255),
    DEF     INTEGER,
    DNA     CHARACTER VARYING(255),
    HP      INTEGER,
    INT     INTEGER,
    LEVEL   INTEGER,
    SP      INTEGER,
    SPD     INTEGER,
    FROM_ID INTEGER,
    TO_ID   INTEGER
);

create table skill
(
    ID          INTEGER,
    DESCRIPTION CHARACTER VARYING(255),
    NAME        CHARACTER VARYING(255)
);

create table LEARN_ATTACK
(
    id                IDENTITY ,
    learned_by_id     INTEGER,
    learned_attack_id INTEGER,
    at                INTEGER
)