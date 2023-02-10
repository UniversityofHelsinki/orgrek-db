SELECT NODE.* FROM NODE JOIN NODE_TYPE ON NODE.ID = NODE_TYPE.NODE_ID
WHERE NODE.ID IN
      (SELECT distinct CHILD_NODE_ID
       FROM (SELECT * FROM edge WHERE
           (END_DATE IS NULL OR END_DATE > trunc(:today))
                                  AND (START_DATE IS NULL OR START_DATE <= trunc(:today))
                                  AND hierarchy = 'toiminnanohjaus')
       START WITH PARENT_NODE_ID = :nodeId
       CONNECT BY PRIOR CHILD_NODE_ID = PARENT_NODE_ID)
    AND NODE_TYPE.VALUE IN ('kandiohjelma', 'maisteriohjelma', 'tohtoriohjelma')
    AND NODE_TYPE.END_DATE IS NULL OR NODE_TYPE.END_DATE > trunc(:today)
    AND (NODE_TYPE.START_DATE IS NULL OR NODE_TYPE.START_DATE <= trunc(:today))
