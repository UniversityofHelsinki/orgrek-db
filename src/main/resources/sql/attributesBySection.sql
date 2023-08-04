SELECT ATTRIBUTE.*, SECTION_ATTR.SECTION FROM NODE_ATTR ATTRIBUTE 
	JOIN SECTION_ATTR ON
		ATTRIBUTE.KEY = SECTION_ATTR.ATTR
	JOIN HIERARCHY_FILTER ON 
		HIERARCHY_FILTER.HIERARCHY IN (:hierarchies) 
		AND HIERARCHY_FILTER.KEY = ATTRIBUTE.KEY
		AND (HIERARCHY_FILTER.VALUE IS NULL OR ATTRIBUTE.VALUE = HIERARCHY_FILTER.VALUE)
		AND (HIERARCHY_FILTER.START_DATE IS NULL OR HIERARCHY_FILTER.START_DATE <= TRUNC(CURRENT_DATE))
		AND (HIERARCHY_FILTER.END_DATE IS NULL OR HIERARCHY_FILTER.END_DATE >= TRUNC(CURRENT_DATE))
	WHERE NODE_ID = :id
	ORDER BY 
    SECTION_ATTR.ORDER_NRO ASC, 
    ATTRIBUTE.START_DATE DESC NULLS FIRST, 
    ATTRIBUTE.END_DATE DESC NULLS FIRST
