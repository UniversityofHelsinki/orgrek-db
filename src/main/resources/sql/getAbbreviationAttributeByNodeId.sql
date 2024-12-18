SELECT * FROM NODE_ATTR WHERE NODE_ID = :node_id AND "KEY"='lyhenne' 
    AND (START_DATE IS NULL OR START_DATE <= TRUNC(CURRENT_DATE))
    AND (END_DATE IS NULL OR END_DATE >= TRUNC(CURRENT_DATE))
