SELECT * FROM FULL_NAME WHERE NODE_ID = :nodeId AND
                (END_DATE IS NULL OR END_DATE >= trunc(to_date(:date, 'DD.MM.YYYY')))
