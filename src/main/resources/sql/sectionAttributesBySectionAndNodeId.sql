SELECT
    NODE_ATTR.*
FROM NODE_ATTR
         JOIN SECTION_ATTR
              ON NODE_ATTR.NODE_ID = :nodeId AND NODE_ATTR."KEY" = SECTION_ATTR.ATTR AND SECTION_ATTR.SECTION  = :section
ORDER BY ORDER_NRO, NODE_ATTR.START_DATE DESC NULLS FIRST, NODE_ATTR.END_DATE DESC NULLS FIRST
