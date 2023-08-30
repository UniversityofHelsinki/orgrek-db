/* Parents and successors of a node */
SELECT * FROM EDGE WHERE CHILD_NODE_ID = :id AND HIERARCHY IN (:hierarchies)