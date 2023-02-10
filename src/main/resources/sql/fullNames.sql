SELECT * FROM FULL_NAME WHERE NODE_ID = :nodeId AND
                (START_DATE IS NULL OR START_DATE <= trunc(to_date(:date, 'DD.MM.YYYY'))) AND
                (END_DATE IS NULL OR END_DATE >= trunc(to_date(:date, 'DD.MM.YYYY')))
