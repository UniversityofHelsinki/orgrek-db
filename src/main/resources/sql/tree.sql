with traversed(id, child_node_id, parent_node_id, start_date, end_date, hierarchy) as (
    select
        ID,
        CHILD_NODE_ID,
        PARENT_NODE_ID,
        START_DATE,
        END_DATE,
        HIERARCHY from edge where PARENT_NODE_ID = :start and (hierarchy in (:hierarchies))
                              and (start_date is null or start_date <= trunc(to_date(:date, 'DD.MM.YYYY')))
                              and (END_DATE is null or END_DATE >= trunc(to_date(:date, 'DD.MM.YYYY')))
    union all
    select
        e.ID id,
        e.CHILD_NODE_ID child_node_id ,
        e.PARENT_NODE_ID parent_node_id,
        e.START_DATE start_date,
        e.END_DATE end_date,
        e.HIERARCHY hierarchy
    from traversed t join EDGE e on t.CHILD_NODE_ID = e.PARENT_NODE_ID and e.hierarchy = t.hierarchy and (e.start_date is null or e.start_date <= trunc(to_date(:date, 'DD.MM.YYYY')))
        and (e.end_date is null or e.end_date >= trunc(to_date(:date, 'DD.MM.YYYY')))
), languages(language) as (
    select 'fi' from dual
    union
    select 'sv' from dual
    union
    select 'en' from dual
) select parent_node_id,
         child_node_id,
         parent_node.unique_id parent_node_unique_id,
         child_node.unique_id child_node_unique_id,
         coalesce(p_talous_tunnus.value, p_oppiaine_tunnus.value) parent_code,
         coalesce(c_talous_tunnus.value, c_oppiaine_tunnus.value) child_code,
         case when pfn.name is null then parent_node.name else pfn.name end as parent_name,
         case when cfn.name is null then child_node.name else cfn.name end as child_name,
         languages.language language,
         listagg(distinct traversed.hierarchy, ' ') within group (order by parent_node_id, child_node_id) hierarchies
from traversed
         cross join languages
         left join full_name cfn on traversed.child_node_id=cfn.node_id and cfn.LANGUAGE = languages.language
    and (cfn.start_date is null or cfn.START_DATE <= trunc(to_date(:date, 'DD.MM.YYYY')))
    and (cfn.END_DATE is null or cfn.END_DATE >= trunc(to_date(:date, 'DD.MM.YYYY')))
         left join full_name pfn on traversed.parent_node_id = pfn.node_id and pfn.LANGUAGE = languages.language
    and cfn.language = pfn.language
    and (pfn.START_DATE is null or pfn.START_DATE <= trunc(to_date(:date, 'DD.MM.YYYY')))
    and (pfn.END_DATE is null or pfn.END_DATE >= trunc(to_date(:date, 'DD.MM.YYYY')))
         join node parent_node on traversed.parent_node_id = parent_node.id
    and (parent_node.START_DATE is null or parent_node.START_DATE <= trunc(to_date(:date, 'DD.MM.YYYY')))
    and (parent_node.END_DATE is null or parent_node.END_DATE >= trunc(to_date(:date, 'DD.MM.YYYY')))
         join node child_node on traversed.child_node_id = child_node.id
    and (child_node.START_DATE is null or child_node.START_DATE <= trunc(to_date(:date, 'DD.MM.YYYY')))
    and (child_node.END_DATE is null or child_node.END_DATE >= trunc(to_date(:date, 'DD.MM.YYYY')))
         left join NODE_ATTR p_talous_tunnus on ((not 'opetus' = any(:hierarchies)) or 'talous' = any(:hierarchies)) and traversed.parent_node_id = p_talous_tunnus.node_id and p_talous_tunnus.key='talous_tunnus' and
                                      (p_talous_tunnus.start_date is null or p_talous_tunnus.start_date <= trunc(to_date(:date, 'DD.MM.YYYY'))) and
                                      (p_talous_tunnus.END_DATE is null or p_talous_tunnus.END_DATE >= trunc(to_date(:date, 'DD.MM.YYYY')))
         left join NODE_ATTR p_oppiaine_tunnus on ('opetus' = any(:hierarchies) and not 'talous' = any(:hierarchies)) and traversed.parent_node_id = p_oppiaine_tunnus.NODE_ID and p_oppiaine_tunnus.key='oppiaine_tunnus' and
                                      (p_oppiaine_tunnus.START_DATE is null or p_oppiaine_tunnus.START_DATE <= trunc(to_date(:date, 'DD.MM.YYYY'))) and
                                      (p_oppiaine_tunnus.END_DATE is null or p_oppiaine_tunnus.END_DATE >= trunc(to_date(:date, 'DD.MM.YYYY')))
         left join NODE_ATTR c_talous_tunnus on ((not 'opetus' = any(:hierarchies)) or 'talous' = any(:hierarchies)) and traversed.child_node_id = c_talous_tunnus.node_id and c_talous_tunnus.key='talous_tunnus' and
                                                (c_talous_tunnus.start_date is null or c_talous_tunnus.start_date <= trunc(to_date(:date, 'DD.MM.YYYY'))) and
                                                (c_talous_tunnus.END_DATE is null or c_talous_tunnus.END_DATE >= trunc(to_date(:date, 'DD.MM.YYYY')))
         left join NODE_ATTR c_oppiaine_tunnus on ('opetus' = any(:hierarchies) and not 'talous' = any(:hierarchies)) and traversed.child_node_id = c_oppiaine_tunnus.NODE_ID and c_oppiaine_tunnus.key='oppiaine_tunnus' and
                                                  (c_oppiaine_tunnus.START_DATE is null or c_oppiaine_tunnus.START_DATE <= trunc(to_date(:date, 'DD.MM.YYYY'))) and
                                                  (c_oppiaine_tunnus.END_DATE is null or c_oppiaine_tunnus.END_DATE >= trunc(to_date(:date, 'DD.MM.YYYY')))
group by parent_node_id, child_node_id, parent_node.unique_id, child_node.unique_id, p_talous_tunnus.value, p_oppiaine_tunnus.value, c_talous_tunnus.value, c_oppiaine_tunnus.value, pfn.name, cfn.name, parent_node.name, child_node.name, languages.language order by parent_node_id, child_node_id
