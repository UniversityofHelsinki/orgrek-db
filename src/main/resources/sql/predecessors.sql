SELECT PREDECESSOR_RELATION.PREDECESSOR_ID AS ID, NODE.NAME, NODE.START_DATE, NODE.END_DATE, PREDECESSOR_RELATION.START_DATE AS EDGE_START_DATE,
                PREDECESSOR_RELATION.END_DATE AS EDGE_END_DATE, NODE.UNIQUE_ID
                FROM PREDECESSOR_RELATION
                INNER JOIN NODE
                ON PREDECESSOR_RELATION.PREDECESSOR_ID = NODE.ID
                WHERE PREDECESSOR_RELATION.NODE_ID = :startId