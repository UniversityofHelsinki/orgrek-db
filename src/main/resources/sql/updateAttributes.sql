UPDATE NODE_ATTR
SET NODE_ID = :node_id, KEY = :key, VALUE= :value, START_DATE = FROM_TZ(TO_TIMESTAMP(:start_date, 'DD.MM.YYYY'),'0:00'), END_DATE = FROM_TZ(TO_TIMESTAMP(:end_date, 'DD.MM.YYYY'),'0:00')
WHERE ID = :id AND NODE_ID = :node_id
