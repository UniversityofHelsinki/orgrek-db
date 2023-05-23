INSERT INTO NODE (ID, NAME, START_DATE, END_DATE, TIMESTAMP, UNIQUE_ID)
    VALUES (:childNodeId, :name, :startDate, :endDate, current_date, UNIQUE_ID_SEQ.nextval)
