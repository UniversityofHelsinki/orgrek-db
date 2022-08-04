SELECT * FROM NODE WHERE ID IN
                (SELECT CHILD_NODE_ID FROM EDGE WHERE PARENT_NODE_ID = :nodeId and
                (HIERARCHY is null or HIERARCHY != :edgeType) and
                (END_DATE is null or trunc(END_DATE) >= to_date(:dt,'DD.MM.YYYY')) and
                (START_DATE is null or trunc(START_DATE) <= to_date(:dt, 'DD.MM.YYYY'))) and
                (END_DATE is null or trunc(END_DATE) >= to_date(:dt,'DD.MM.YYYY')) and
                (START_DATE is null or trunc(START_DATE) <= to_date(:dt, 'DD.MM.YYYY'))
