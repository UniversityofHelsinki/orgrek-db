/* Children and successors of a node */
SELECT * FROM EDGE WHERE PARENT_NODE_ID = :id AND HIERARCHY IN (:hierarchies)
