SELECT 
	NODE.UNIQUE_ID,
	TYPE.VALUE AS TYPE,
	NAME_FI.VALUE AS NAME_FI,
	NAME_EN.VALUE AS NAME_EN,
	NAME_SV.VALUE AS NAME_SV,
	ABBREVIATION.VALUE AS ABBREVIATION,
	PARENT.UNIQUE_ID AS PARENT
FROM EDGE 
	JOIN NODE NODE ON EDGE.CHILD_NODE_ID = NODE.ID
		AND EDGE.HIERARCHY = 'virallinen'
		AND (EDGE.START_DATE IS NULL OR EDGE.START_DATE <= TRUNC(CURRENT_DATE))
		AND (EDGE.END_DATE IS NULL OR EDGE.END_DATE >= TRUNC(CURRENT_DATE))
	JOIN HIERARCHY_FILTER TYPE_HIERARCHY_FILTER ON TYPE_HIERARCHY_FILTER.HIERARCHY = EDGE.HIERARCHY 
		AND TYPE_HIERARCHY_FILTER.KEY = 'type' AND TYPE_HIERARCHY_FILTER.VALUE NOT IN ('toimintayksikko', 'toimielin')
		AND (TYPE_HIERARCHY_FILTER.START_DATE IS NULL OR TYPE_HIERARCHY_FILTER.START_DATE <= TRUNC(CURRENT_DATE))
		AND (TYPE_HIERARCHY_FILTER.END_DATE IS NULL OR TYPE_HIERARCHY_FILTER.END_DATE >= TRUNC(CURRENT_DATE))
	JOIN NODE_ATTR TYPE ON TYPE.NODE_ID = NODE.ID
		AND TYPE_HIERARCHY_FILTER.KEY = TYPE.KEY
		AND TYPE_HIERARCHY_FILTER.VALUE = TYPE.VALUE
		AND (TYPE.START_DATE IS NULL OR TYPE.START_DATE <= TRUNC(CURRENT_DATE))
		AND (TYPE.END_DATE IS NULL OR TYPE.END_DATE >= TRUNC(CURRENT_DATE))
	JOIN NODE_ATTR NAME_FI ON NAME_FI.NODE_ID = NODE.ID
		AND NAME_FI.KEY = 'name_fi'
		AND (NAME_FI.START_DATE IS NULL OR NAME_FI.START_DATE <= TRUNC(CURRENT_DATE))
		AND (NAME_FI.END_DATE IS NULL OR NAME_FI.END_DATE >= TRUNC(CURRENT_DATE))
	JOIN NODE_ATTR NAME_EN ON NAME_EN.NODE_ID = NODE.ID
		AND NAME_EN.KEY = 'name_en'
		AND (NAME_EN.START_DATE IS NULL OR NAME_EN.START_DATE <= TRUNC(CURRENT_DATE))
		AND (NAME_EN.END_DATE IS NULL OR NAME_EN.END_DATE >= TRUNC(CURRENT_DATE))
	JOIN NODE_ATTR NAME_SV ON NAME_SV.NODE_ID = NODE.ID
		AND NAME_SV.KEY = 'name_sv'
		AND (NAME_SV.START_DATE IS NULL OR NAME_SV.START_DATE <= TRUNC(CURRENT_DATE))
		AND (NAME_SV.END_DATE IS NULL OR NAME_SV.END_DATE >= TRUNC(CURRENT_DATE))
	JOIN HIERARCHY_FILTER ABBREVIATION_HIERARCHY_FILTER ON ABBREVIATION_HIERARCHY_FILTER.HIERARCHY = EDGE.HIERARCHY 
		AND ABBREVIATION_HIERARCHY_FILTER.KEY = 'lyhenne'
		AND (ABBREVIATION_HIERARCHY_FILTER.START_DATE IS NULL OR ABBREVIATION_HIERARCHY_FILTER.START_DATE <= TRUNC(CURRENT_DATE))
		AND (ABBREVIATION_HIERARCHY_FILTER.END_DATE IS NULL OR ABBREVIATION_HIERARCHY_FILTER.END_DATE >= TRUNC(CURRENT_DATE))
	LEFT JOIN NODE_ATTR ABBREVIATION ON ABBREVIATION.NODE_ID = NODE.ID
		AND ABBREVIATION.KEY = ABBREVIATION_HIERARCHY_FILTER.KEY
		AND (ABBREVIATION.START_DATE IS NULL OR ABBREVIATION.START_DATE <= TRUNC(CURRENT_DATE))
		AND (ABBREVIATION.END_DATE IS NULL OR ABBREVIATION.END_DATE >= TRUNC(CURRENT_DATE))
	LEFT JOIN NODE PARENT ON EDGE.PARENT_NODE_ID = PARENT.ID
	
	
	
	
	
	
