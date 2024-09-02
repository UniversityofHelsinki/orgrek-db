SELECT 
    NODE.UNIQUE_ID AS UNIQUE_ID, 
    CODE.VALUE AS CODE,
    TYPE.VALUE AS TYPE,
    NAME_FI.VALUE AS NAME_FI,
    NAME_EN.VALUE AS NAME_EN,
    NAME_SV.VALUE AS NAME_SV,
    PARENT.UNIQUE_ID AS PARENT
	FROM NODE 
	JOIN EDGE EDGE ON EDGE.HIERARCHY = 'talous' AND (NODE.ID = EDGE.CHILD_NODE_ID)
		AND (EDGE.START_DATE IS NULL OR EDGE.START_DATE <= TRUNC(CURRENT_DATE))
		AND (EDGE.END_DATE IS NULL OR EDGE.END_DATE >= TRUNC(CURRENT_DATE))
	JOIN HIERARCHY_FILTER HIERARCHY_FILTER ON HIERARCHY_FILTER.HIERARCHY = EDGE.HIERARCHY
		AND (HIERARCHY_FILTER.START_DATE IS NULL OR HIERARCHY_FILTER.START_DATE <= TRUNC(CURRENT_DATE))
		AND (HIERARCHY_FILTER.END_DATE IS NULL OR HIERARCHY_FILTER.END_DATE >= TRUNC(CURRENT_DATE))
	JOIN NODE_ATTR TYPE ON NODE.ID = TYPE.NODE_ID 
		AND TYPE.KEY = 'type'
		AND TYPE.VALUE = HIERARCHY_FILTER.VALUE
		AND (TYPE.START_DATE IS NULL OR TYPE.START_DATE <= TRUNC(CURRENT_DATE))
		AND (TYPE.END_DATE IS NULL OR TYPE.END_DATE >= TRUNC(CURRENT_DATE))
	JOIN NODE_ATTR NAME_FI ON NODE.ID = NAME_FI.NODE_ID
		AND NAME_FI.KEY = 'name_fi'
		AND (NAME_FI.START_DATE IS NULL OR NAME_FI.START_DATE <= TRUNC(CURRENT_DATE))
		AND (NAME_FI.END_DATE IS NULL OR NAME_FI.END_DATE >= TRUNC(CURRENT_DATE))
	JOIN NODE_ATTR NAME_SV ON NODE.ID = NAME_SV.NODE_ID
		AND NAME_SV.KEY = 'name_sv'
		AND (NAME_SV.START_DATE IS NULL OR NAME_SV.START_DATE <= TRUNC(CURRENT_DATE))
		AND (NAME_SV.END_DATE IS NULL OR NAME_SV.END_DATE >= TRUNC(CURRENT_DATE))
	JOIN NODE_ATTR NAME_EN ON NODE.ID = NAME_EN.NODE_ID
		AND NAME_EN.KEY = 'name_en'
		AND (NAME_EN.START_DATE IS NULL OR NAME_EN.START_DATE <= TRUNC(CURRENT_DATE))
		AND (NAME_EN.END_DATE IS NULL OR NAME_EN.END_DATE >= TRUNC(CURRENT_DATE))
	JOIN NODE_ATTR UNIQUE_CODE ON NODE.ID = UNIQUE_CODE.NODE_ID
		AND UNIQUE_CODE.KEY = 'uniikki_koodi' AND UNIQUE_CODE.VALUE = 'kylla'
		AND (UNIQUE_CODE.START_DATE IS NULL OR UNIQUE_CODE.START_DATE <= TRUNC(CURRENT_DATE))
		AND (UNIQUE_CODE.END_DATE IS NULL OR UNIQUE_CODE.END_DATE >= TRUNC(CURRENT_DATE))
	JOIN NODE_ATTR CODE ON NODE.ID = CODE.NODE_ID
		AND CODE.KEY = 'talous_tunnus'
		AND (CODE.START_DATE IS NULL OR CODE.START_DATE <= TRUNC(CURRENT_DATE))
		AND (CODE.END_DATE IS NULL OR CODE.END_DATE >= TRUNC(CURRENT_DATE))
	LEFT JOIN NODE PARENT ON PARENT.ID = EDGE.PARENT_NODE_ID

