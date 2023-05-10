UPDATE EDGE SET
                CHILD_NODE_ID = :child_node_id,
                PARENT_NODE_ID = :parent_node_id,
                START_DATE = :start_date,
                END_DATE = :end_date,
                HIERARCHY = :hierarchy
WHERE ID = :id