SELECT DISTINCT KEY FROM HIERARCHY_FILTER
                    WHERE KEY != 'type' AND HIERARCHY in (:hierarchies)
                    UNION (select 'lyhenne' from dual)
                    UNION (select 'emo_lyhenne' from dual)