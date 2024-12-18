SELECT * FROM NODE_ATTR WHERE "KEY" = :key
                          and NODE_ID = :nodeId
                          and "VALUE" = :value
                          and ((trunc(START_DATE) is null AND trunc(END_DATE) is null) OR
                            (trunc(START_DATE) >= to_date(:startDate,'DD.MM.YYYY') AND trunc(START_DATE) <= to_date(:endDate,'DD.MM.YYYY')) OR
                            (trunc(END_DATE) >= to_date(:startDate,'DD.MM.YYYY') AND trunc(END_DATE) <= to_date(:endDate,'DD.MM.YYYY')) OR
                            (trunc(START_DATE) <= to_date(:startDate, 'DD.MM.YYYY') AND trunc(END_DATE) >= to_date(:endDate, 'DD.MM.YYYY')) OR
                            (trunc(START_DATE) <= to_date(:startDate, 'DD.MM.YYYY') AND trunc(END_DATE) is null) OR
                            (trunc(START_DATE) is null) AND trunc(END_DATE) >= to_date(:endDate, 'DD.MM.YYYY'))
