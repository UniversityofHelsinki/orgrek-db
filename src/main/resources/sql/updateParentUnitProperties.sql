UPDATE EDGE
SET ID =:id, START_DATE = TO_DATE(:start_date, 'DD.MM.YYYY'), END_DATE = TO_DATE(:end_date, 'DD.MM.YYYY'), HIERARCHY= :hierarchy
WHERE PARENT_NODE_ID= :parent_node_id AND CHILD_NODE_ID= :child_node_id AND ID =:id


