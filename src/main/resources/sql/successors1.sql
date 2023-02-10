SELECT SN.ID, SN.UNIQUE_ID, SN.START_DATE, SN.END_DATE, S.START_DATE edge_start_date, S.END_DATE edge_end_date, FN.LANGUAGE, max(fn.name) keep (dense_rank first order by fn.start_date desc nulls last, fn.end_date desc nulls first) full_name FROM SUCCESSOR_RELATION S
JOIN NODE N ON S.NODE_ID = N.ID
JOIN NODE SN ON S.SUCCESSOR_ID = SN.ID
JOIN FULL_NAME FN ON S.SUCCESSOR_ID = FN.NODE_ID
    AND (((SN.START_DATE IS NULL OR SN.START_DATE <= trunc(to_date(:date, 'DD.MM.YYYY'))) AND
          (SN.END_DATE IS NULL OR SN.END_DATE >= trunc(to_date(:date, 'DD.MM.YYYY'))) AND
          ((FN.START_DATE IS NULL OR FN.START_DATE <= trunc(to_date(:date, 'DD.MM.YYYY')))
              AND (FN.END_DATE IS NULL OR FN.END_DATE >= trunc(to_date(:date, 'DD.MM.YYYY')))))
        OR ((SN.START_DATE > trunc(to_date(:date, 'DD.MM.YYYY'))) AND
            ((FN.START_DATE IS NULL OR FN.START_DATE <= SN.START_DATE))
            AND (FN.END_DATE IS NULL OR FN.END_DATE >= SN.START_DATE)))
WHERE S.NODE_ID = :nodeId group by sn.ID, SN.UNIQUE_ID, SN.START_DATE, SN.END_DATE, S.START_DATE, S.END_DATE, FN.LANGUAGE
