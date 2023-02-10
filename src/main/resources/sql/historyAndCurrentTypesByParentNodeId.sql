SELECT CHILD_NODE_ID AS NODE_ID, HIERARCHY, START_DATE, END_DATE FROM EDGE WHERE PARENT_NODE_ID = :nodeId and
                (HIERARCHY is null or HIERARCHY != :edgeType) AND
                (START_DATE is null or trunc(START_DATE) <= to_date(:dt, 'DD.MM.YYYY'))
