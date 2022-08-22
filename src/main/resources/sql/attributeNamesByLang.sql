SELECT NODE_ID, VALUE FROM NODE_ATTR WHERE KEY = :lang
                AND (END_DATE is null or :currentDate is null or trunc(END_DATE) >= to_date(:currentDate,'YYYY-MM-DD')) and
                (START_DATE is null or  :currentDate is null or trunc(START_DATE) <= to_date(:currentDate, 'YYYY-MM-DD'))
