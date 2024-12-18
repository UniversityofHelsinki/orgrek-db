UPDATE NODE_ATTR
SET NODE_ID = :node_id, "KEY" = :key, "VALUE"= :value, START_DATE = TO_DATE(:start_date, 'DD.MM.YYYY'), END_DATE = TO_DATE(:end_date, 'DD.MM.YYYY')
WHERE ID = :id AND NODE_ID = :node_id
