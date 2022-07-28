SELECT SN.ID, SN.UNIQUE_ID, SN.START_DATE, SN.END_DATE, S.START_DATE edge_start_date, S.END_DATE edge_end_date, FN.NAME full_name, FN.LANGUAGE FROM SUCCESSOR_RELATION S
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
WHERE S.NODE_ID = :nodeId
