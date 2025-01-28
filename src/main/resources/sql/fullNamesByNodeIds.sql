SELECT
    NODE_ID,
    "VALUE" AS NAME,
    CASE
        WHEN "KEY" = 'name_fi' THEN 'fi'
        WHEN "KEY" = 'name_en' THEN 'en'
        WHEN "KEY" = 'name_sv' THEN 'sv'
        ELSE NULL END
    AS LANGUAGE,
    START_DATE,
    END_DATE
FROM NODE_ATTR WHERE NODE_ID IN (:nodeIds) AND "KEY" IN ('name_fi', 'name_sv', 'name_en')
  AND (START_DATE IS NULL OR START_DATE <= TRUNC(TO_DATE(:date, 'DD.MM.YYYY')))
  AND (END_DATE IS NULL OR END_DATE >= TRUNC(TO_DATE(:date, 'DD.MM.YYYY')))