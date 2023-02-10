SELECT * FROM NODE WHERE ID IN
                (SELECT PARENT_NODE_ID FROM EDGE WHERE CHILD_NODE_ID = :nodeId and
                (HIERARCHY is null or HIERARCHY != :edgeType) AND
                (END_DATE is null or trunc(END_DATE) >= to_date(:dt, 'DD.MM.YYYY')))
                AND (END_DATE is null or trunc(END_DATE) >= to_date(:dt, 'DD.MM.YYYY'))
