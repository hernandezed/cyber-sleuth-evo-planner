create sequence HIBERNATE_SEQUENCE;

create table ATTACK
(
    ID   INTEGER,
    NAME CHARACTER VARYING(255),
    INHERITABLE BIT
);

create table digimon
(
    ID       INTEGER,
    MEMORY   INTEGER,
    MINI     CHARACTER VARYING(255),
    NAME     CHARACTER VARYING(255),
    PORTRAIT CHARACTER VARYING(255),
    SLOTS    INTEGER,
    STAGE    CHARACTER VARYING(255),
    TYPE     CHARACTER VARYING(255),
    SKILL_ID INTEGER
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
    id                INTEGER,
    learned_by_id     INTEGER,
    learned_attack_id INTEGER,
    at INTEGER
)