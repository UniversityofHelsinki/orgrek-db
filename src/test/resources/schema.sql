drop table if exists NODE_ATTR;
drop table if exists NODE;
drop table if exists TEXTS;
drop table if exists EDGE;
drop table if exists HIERARCHY_FILTER;
drop view if exists PREDECESSOR_RELATION;
drop view if exists SUCCESSOR_RELATION;

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

CREATE TABLE NODE_ATTR
(
    ID         INTEGER NOT NULL
        PRIMARY KEY,
    NODE_ID    VARCHAR2(255 CHAR)
        REFERENCES NODE,
    "KEY"        VARCHAR2(255 char),
    "VALUE"      VARCHAR2(255 char),
    START_DATE TIMESTAMP(6),
    END_DATE   TIMESTAMP(6)
);

create table TEXT
(
    "KEY"       VARCHAR2(255 char),
    LANGUAGE  VARCHAR2(3 char),
    "VALUE"     VARCHAR2(255 char),
    USER_NAME VARCHAR2(20 char),
    TIMESTAMP TIMESTAMP(6)
);

create table EDGE
(
    ID             NUMBER not null
        primary key,
    CHILD_NODE_ID  VARCHAR2(255 char)
        references NODE,
    PARENT_NODE_ID VARCHAR2(255 char)
        references NODE,
    START_DATE     TIMESTAMP(6),
    END_DATE       TIMESTAMP(6),
    HIERARCHY           VARCHAR2(255 char)
);

create table HIERARCHY_FILTER
(
    ID         NUMBER not null
        primary key,
    HIERARCHY VARCHAR2(255 char),
    "KEY" VARCHAR2(255 char),
    "VALUE" VARCHAR2(255 char),
    START_DATE TIMESTAMP(6),
    END_DATE   TIMESTAMP(6)
);

create view PREDECESSOR_RELATION as
select "ID","CHILD_NODE_ID" PREDECESSOR_ID,"PARENT_NODE_ID" NODE_ID,"START_DATE","END_DATE" from EDGE
where HIERARCHY='history';

create view SUCCESSOR_RELATION as
select "ID",PARENT_NODE_ID SUCCESSOR_ID,CHILD_NODE_ID NODE_ID,"START_DATE","END_DATE" from EDGE
where HIERARCHY='history';

create view NODE_TYPE as
select "ID","NODE_ID","KEY","VALUE","START_DATE","END_DATE" from NODE_ATTR
where "KEY"='type';

create table SECTION_ATTR
(
    ID NUMBER not null,
    SECTION VARCHAR2(255) not null,
    ATTR VARCHAR2(255) not null,
    START_DATE TIMESTAMP(6),
    END_DATE TIMESTAMP(6)
);

create table HIERARCHY_PUBLICITY
(
    ID NUMBER not null
        constraint "HIERARCHY_PUBLICITY_pk"
            primary key,
    HIERARCHY VARCHAR2(255) not null,
    PUBLICITY NUMBER not null
);

create table FULL_NAME
(
    NODE_ID VARCHAR(32) NOT NULL,
    NAME VARCHAR(256),
    START_DATE TIMESTAMP(6),
    END_DATE TIMESTAMP(6),
    LANGUAGE VARCHAR(32)
);

create table ATTRIBUTE_ORDER
(
    "KEY" VARCHAR(255) NOT NULL,
    "VALUE" VARCHAR(255) NOT NULL,
    "ORDER" NUMBER NOT NULL
);

CREATE SEQUENCE NODE_SEQ
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START WITH 1
    INCREMENT BY 1
    CACHE 8
    NOCYCLE;

create sequence UNIQUE_ID_SEQ
    minvalue 100000000
    nocache
