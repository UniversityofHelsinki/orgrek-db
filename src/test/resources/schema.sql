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
    KEY VARCHAR2(255 char),
    VALUE VARCHAR2(255 char),
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
where KEY='type';

