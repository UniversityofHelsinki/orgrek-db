SELECT PN.ID, PN.UNIQUE_ID, PN.START_DATE, PN.END_DATE, P.START_DATE edge_start_date, P.END_DATE edge_end_date, FN.LANGUAGE, max(fn.name) keep (dense_rank first order by fn.start_date desc nulls last, fn.end_date desc nulls first) full_name FROM PREDECESSOR_RELATION P
    JOIN NODE N ON P.NODE_ID = N.ID
    JOIN NODE PN ON P.PREDECESSOR_ID = PN.ID
    JOIN FULL_NAME FN on P.PREDECESSOR_ID = FN.NODE_ID
    AND (((PN.START_DATE IS NULL OR PN.START_DATE <= trunc(to_date(:date, 'DD.MM.YYYY'))) AND
          (PN.END_DATE IS NULL OR PN.END_DATE >= trunc(to_date(:date, 'DD.MM.YYYY'))) AND
          ((FN.START_DATE IS NULL OR FN.START_DATE <= trunc(to_date(:date, 'DD.MM.YYYY')))
              AND (FN.END_DATE IS NULL OR FN.END_DATE >= trunc(to_date(:date, 'DD.MM.YYYY')))))
        OR (PN.END_DATE < trunc(to_date(:date, 'DD.MM.YYYY')) AND
            (FN.START_DATE IS NULL OR FN.START_DATE <= PN.END_DATE)
            AND (FN.END_DATE IS NULL OR FN.END_DATE >= PN.END_DATE)))
where P.NODE_ID = :nodeId group by PN.ID, PN.UNIQUE_ID, PN.START_DATE, PN.END_DATE, P.START_DATE, P.END_DATE, FN.LANGUAGE
