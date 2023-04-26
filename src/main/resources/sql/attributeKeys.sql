SELECT DISTINCT KEY FROM HIERARCHY_FILTER
                    JOIN SECTION_ATTR ON SECTION IN (:sections) AND KEY=ATTR
                    WHERE KEY != 'type' AND HIERARCHY in (:hierarchies)
                    UNION (select 'lyhenne' from dual)
                    UNION (select 'emo_lyhenne' from dual)