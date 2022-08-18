WITH LANGUAGES(LANGUAGE) AS (
    SELECT 'fi' from dual
    union
    SELECT 'sv' from dual
    union
    SELECT 'en' from dual
) select coalesce(full_name.name, node.name) name,
       full_name.start_date start_date,
       full_name.end_date end_date,
       full_name.node_id node_id,
       LANGUAGES.LANGUAGE language
        from NODE
            cross join LANGUAGES
            left join FULL_NAME on
            NODE.id = FULL_NAME.NODE_ID and LANGUAGES.LANGUAGE = FULL_NAME.LANGUAGE AND
            (((NODE.END_DATE is not null and NODE.END_DATE <= to_date(:date, 'DD.MM.YYYY')) and
              (FULL_NAME.START_DATE is null or FULL_NAME.START_DATE <= NODE.END_DATE) and
              (FULL_NAME.END_DATE is null or FULL_NAME.END_DATE >= NODE.END_DATE)) or
             ((NODE.START_DATE is not null and NODE.START_DATE > to_date(:date, 'DD.MM.YYYY')) and
              (FULL_NAME.START_DATE is null or FULL_NAME.START_DATE <= NODE.START_DATE) and
              (FULL_NAME.END_DATE is null or FULL_NAME.END_DATE >= NODE.START_DATE)) or
             (((NODE.START_DATE is null or NODE.START_DATE <= to_date(:date, 'DD.MM.YYYY')) and
               (NODE.END_DATE is null or NODE.END_DATE > to_date(:date, 'DD.MM.YYYY'))) and
              (FULL_NAME.START_DATE is null or FULL_NAME.START_DATE <= to_date(:date, 'DD.MM.YYYY')) and
              (FULL_NAME.END_DATE is null or FULL_NAME.END_DATE >= to_date(:date, 'DD.MM.YYYY'))))
where UNIQUE_ID=:uniqueId
