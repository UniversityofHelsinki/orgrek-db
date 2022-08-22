SELECT * FROM FULL_NAME WHERE NODE_ID = :nodeId AND
                (START_DATE IS NULL OR START_DATE <= trunc(to_date(:date, 'DD.MM.YYYY')))
