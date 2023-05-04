SELECT N.ID, E.ID AS EDGE_ID, N.UNIQUE_ID, N.START_DATE, N.END_DATE, E.HIERARCHY, E.START_DATE edge_start_date, E.END_DATE edge_end_date, FN.NAME full_name, FN.LANGUAGE FROM EDGE E
        JOIN NODE N ON E.CHILD_NODE_ID = N.ID
        JOIN FULL_NAME FN ON E.CHILD_NODE_ID = FN.NODE_ID AND E.HIERARCHY != 'history'
        WHERE ((N.START_DATE is null or N.START_DATE <= trunc(to_date(:date, 'DD.MM.YYYY'))) and
            (N.END_DATE is null or N.END_DATE >= trunc(to_date(:date, 'DD.MM.YYYY')))) and
            ((E.START_DATE IS NULL OR E.START_DATE <= trunc(to_date(:date, 'DD.MM.YYYY'))) AND
                (E.END_DATE IS NULL OR E.END_DATE >= trunc(to_date(:date, 'DD.MM.YYYY')))) AND
                ((FN.START_DATE IS NULL OR FN.START_DATE <= trunc(to_date(:date, 'DD.MM.YYYY'))) AND
                (FN.END_DATE IS NULL OR FN.END_DATE >= trunc(to_date(:date, 'DD.MM.YYYY')))) AND
        E.PARENT_NODE_ID = :nodeId
