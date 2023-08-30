SELECT * FROM HIERARCHY_FILTER 
  WHERE HIERARCHY IN (:hierarchies)
    AND (START_DATE IS NULL OR START_DATE <= TRUNC(TO_DATE(:date, 'DD.MM.YYYY')))
    AND (END_DATE IS NULL OR END_DATE >= TRUNC(TO_DATE(:date, 'DD.MM.YYYY')))