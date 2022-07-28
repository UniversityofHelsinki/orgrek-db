SELECT * FROM NODE WHERE ID IN
                (SELECT CHILD_NODE_ID FROM EDGE WHERE PARENT_NODE_ID = :nodeId and
                (HIERARCHY is null or HIERARCHY != :edgeType) AND
                (START_DATE is null or trunc(START_DATE) <= to_date(:dt, 'DD.MM.YYYY')))
                AND (START_DATE is null or trunc(START_DATE) <= to_date(:dt, 'DD.MM.YYYY'))
