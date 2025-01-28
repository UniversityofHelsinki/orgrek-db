UPDATE HIERARCHY_FILTER SET
                HIERARCHY=:hierarchy,
                "KEY"=:key,
                "VALUE"=:value,
                START_DATE=trunc(to_date(:startDate, 'DD.MM.YYYY')),
                END_DATE=trunc(to_date(:endDate, 'DD.MM.YYYY'))
WHERE ID=:id
