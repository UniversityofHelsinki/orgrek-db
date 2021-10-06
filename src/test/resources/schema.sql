drop table if exists NODE_ATTR;
drop table if exists NODE;
drop table if exists TEXTS;

create table NODE
(
    ID         VARCHAR2(255 char) not null
        primary key,
    NAME       VARCHAR2(150 char),
    START_DATE TIMESTAMP(6),
    END_DATE   TIMESTAMP(6),
    TIMESTAMP  TIMESTAMP(6),
    UNIQUE_ID  VARCHAR2(32 char)
        constraint U_UNIQUE_ID
        unique
);

create table NODE_ATTR
(
    ID         NUMBER not null
        primary key,
    NODE_ID    VARCHAR2(255 char)
        references NODE,
    KEY        VARCHAR2(255 char),
    VALUE      VARCHAR2(255 char),
    START_DATE TIMESTAMP(6),
    END_DATE   TIMESTAMP(6)
);

create table TEXT
(
    KEY       VARCHAR2(255 char),
    LANGUAGE  VARCHAR2(3 char),
    VALUE     VARCHAR2(255 char),
    USER_NAME VARCHAR2(20 char),
    TIMESTAMP TIMESTAMP(6)
)
