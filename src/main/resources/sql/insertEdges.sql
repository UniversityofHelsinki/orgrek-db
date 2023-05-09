INSERT INTO EDGE (ID, PARENT_NODE_ID, CHILD_NODE_ID, START_DATE, END_DATE, HIERARCHY)
    VALUES (NODE_SEQ.nextval, :parent_node_id, :child_node_id, :start_date, :end_date, :hierarchy)