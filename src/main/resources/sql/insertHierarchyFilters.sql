INSERT INTO HIERARCHY_FILTER (ID, HIERARCHY, KEY, VALUE, START_DATE, END_DATE)
    VALUES (NODE_SEQ.nextval, :hierarchy, :key, :value, trunc(to_date(:startDate, 'DD.MM.YYYY')), trunc(to_date(:endDate, 'DD.MM.YYYY')))
