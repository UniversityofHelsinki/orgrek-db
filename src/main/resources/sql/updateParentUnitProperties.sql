UPDATE EDGE
SET START_DATE= :startDate, END_DATE= :endDate
WHERE PARENT_NODE_ID= :parentNodeId AND CHILD_NODE_ID =:childNodeId
AND HIERARCHY= :hierarchy
