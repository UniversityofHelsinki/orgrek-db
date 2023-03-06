INSERT INTO NODE_ATTR (ID, NODE_ID, KEY, VALUE, START_DATE, END_DATE)
VALUES (:id, :node_id, :key, :value, TO_DATE(:start_date, 'DD.MM.YYYY'), TO_DATE(:end_date, 'DD.MM.YYYY'))
