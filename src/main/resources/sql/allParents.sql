WITH LANGUAGES(LANGUAGE) as (
    select 'fi' from dual
    union
    select 'en' from dual
    union
    select 'sv' from dual
) SELECT N.ID, N.UNIQUE_ID,
       N.START_DATE, N.END_DATE,
       E.HIERARCHY,
       E.ID AS EDGE_ID,
       E.START_DATE edge_start_date, E.END_DATE edge_end_date,
       COALESCE(FUTURE.NAME, PAST.NAME, CURR.NAME) as full_name, LANGUAGES.LANGUAGE as language
FROM EDGE E
         JOIN NODE N ON
            E.PARENT_NODE_ID = N.ID AND E.CHILD_NODE_ID = :nodeId AND E.HIERARCHY != 'history'
         CROSS JOIN LANGUAGES
         LEFT JOIN FULL_NAME FUTURE ON E.PARENT_NODE_ID = FUTURE.NODE_ID AND LANGUAGES.LANGUAGE = FUTURE.LANGUAGE AND
                                       ((N.START_DATE is not null and N.START_DATE > trunc(to_date(:date, 'DD.MM.YYYY')) AND
                                         (FUTURE.START_DATE IS NULL OR FUTURE.START_DATE <= N.START_DATE) AND
                                         (FUTURE.END_DATE IS NULL OR FUTURE.END_DATE >= N.START_DATE)))
         LEFT JOIN FULL_NAME PAST ON FUTURE.NODE_ID IS NULL AND E.PARENT_NODE_ID = PAST.NODE_ID AND LANGUAGES.LANGUAGE = PAST.LANGUAGE AND
                                     ((N.END_DATE is not null and N.END_DATE <= trunc(to_date(:date, 'DD.MM.YYYY')) AND
                                       (PAST.START_DATE IS NULL OR PAST.START_DATE <= N.END_DATE) AND
                                       (PAST.END_DATE IS NULL OR PAST.END_DATE >= N.END_DATE)))
         LEFT JOIN FULL_NAME CURR ON FUTURE.NODE_ID IS NULL AND PAST.NODE_ID IS NULL AND E.PARENT_NODE_ID = CURR.NODE_ID AND LANGUAGES.LANGUAGE = CURR.LANGUAGE AND
                                     (CURR.START_DATE IS NULL OR CURR.START_DATE <= trunc(to_date(:date, 'DD.MM.YYYY'))) AND
                                     (CURR.END_DATE IS NULL OR CURR.END_DATE >= trunc(to_date(:date, 'DD.MM.YYYY')))
