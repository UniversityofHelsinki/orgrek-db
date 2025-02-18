SELECT 
	NODE.UNIQUE_ID AS UNIQUE_ID,
	NODE.START_DATE AS UNIT_START_DATE,
	NAME_FI.VALUE AS NAME_FI,
	NAME_EN.VALUE AS NAME_EN,
	NAME_SV.VALUE AS NAME_SV,
	PUBLICITY.VALUE AS PUBLICITY,
	TYPE.VALUE AS TYPE,
	CODE.VALUE AS CODE,
	UNIQUE_CODE.VALUE AS UNIQUE_CODE,
	PARENT.UNIQUE_ID AS PARENT
FROM NODE 
	LEFT JOIN EDGE VANHA_TUTKIMUS
		ON NODE.ID = VANHA_TUTKIMUS.CHILD_NODE_ID 
		AND VANHA_TUTKIMUS.HIERARCHY = 'vanha_tutkimus'
		AND (VANHA_TUTKIMUS.START_DATE IS NULL OR VANHA_TUTKIMUS.START_DATE <= TRUNC(CURRENT_DATE))
		AND (VANHA_TUTKIMUS.END_DATE IS NULL OR VANHA_TUTKIMUS.END_DATE >= TRUNC(CURRENT_DATE))
	LEFT JOIN EDGE TALOUS
		ON NODE.ID = TALOUS.CHILD_NODE_ID
		AND TALOUS.HIERARCHY = 'talous'
		AND VANHA_TUTKIMUS.ID IS NULL
		AND (TALOUS.START_DATE IS NULL OR TALOUS.START_DATE <= TRUNC(CURRENT_DATE))
		AND (TALOUS.END_DATE IS NULL OR TALOUS.END_DATE >= TRUNC(CURRENT_DATE))
	JOIN NODE_ATTR NAME_FI
		ON NODE.ID = NAME_FI.NODE_ID
		AND NAME_FI.KEY = 'name_fi'
		AND (NAME_FI.START_DATE IS NULL OR NAME_FI.START_DATE <= TRUNC(CURRENT_DATE))
		AND (NAME_FI.END_DATE IS NULL OR NAME_FI.END_DATE >= TRUNC(CURRENT_DATE))
	JOIN NODE_ATTR NAME_EN
		ON NODE.ID = NAME_EN.NODE_ID
		AND NAME_EN.KEY = 'name_en'
		AND (NAME_EN.START_DATE IS NULL OR NAME_EN.START_DATE <= TRUNC(CURRENT_DATE))
		AND (NAME_EN.END_DATE IS NULL OR NAME_EN.END_DATE >= TRUNC(CURRENT_DATE))
	JOIN NODE_ATTR NAME_SV
		ON NODE.ID = NAME_SV.NODE_ID 
		AND NAME_SV.KEY = 'name_sv'
		AND (NAME_SV.START_DATE IS NULL OR NAME_SV.START_DATE <= TRUNC(CURRENT_DATE))
		AND (NAME_SV.END_DATE IS NULL OR NAME_SV.END_DATE >= TRUNC(CURRENT_DATE))
	JOIN HIERARCHY_FILTER PUBLICITY_HIERARCHY_FILTER
		ON PUBLICITY_HIERARCHY_FILTER.HIERARCHY = 'vanha_tutkimus'
		AND PUBLICITY_HIERARCHY_FILTER.KEY = 'publicity'
		AND (PUBLICITY_HIERARCHY_FILTER.START_DATE IS NULL OR PUBLICITY_HIERARCHY_FILTER.START_DATE <= TRUNC(CURRENT_DATE))
		AND (PUBLICITY_HIERARCHY_FILTER.END_DATE IS NULL OR PUBLICITY_HIERARCHY_FILTER.END_DATE >= TRUNC(CURRENT_DATE))
	JOIN NODE_ATTR PUBLICITY
		ON NODE.ID = PUBLICITY.NODE_ID
		AND PUBLICITY.KEY = PUBLICITY_HIERARCHY_FILTER.KEY
		AND PUBLICITY.VALUE = PUBLICITY_HIERARCHY_FILTER.VALUE
		AND (PUBLICITY.START_DATE IS NULL OR PUBLICITY.START_DATE <= TRUNC(CURRENT_DATE))
		AND (PUBLICITY.END_DATE IS NULL OR PUBLICITY.END_DATE >= TRUNC(CURRENT_DATE))
	JOIN HIERARCHY_FILTER TYPE_HIERARCHY_FILTER 
		ON TYPE_HIERARCHY_FILTER.HIERARCHY = 'vanha_tutkimus'
		AND TYPE_HIERARCHY_FILTER.KEY = 'type'
		AND (TYPE_HIERARCHY_FILTER.START_DATE IS NULL OR TYPE_HIERARCHY_FILTER.START_DATE <= TRUNC(CURRENT_DATE))
		AND (TYPE_HIERARCHY_FILTER.END_DATE IS NULL OR TYPE_HIERARCHY_FILTER.END_DATE >= TRUNC(CURRENT_DATE))
	JOIN NODE_ATTR TYPE 
		ON NODE.ID = TYPE.NODE_ID
		AND TYPE.KEY = TYPE_HIERARCHY_FILTER.KEY
		AND TYPE.VALUE = TYPE_HIERARCHY_FILTER.VALUE
		AND (TYPE.START_DATE IS NULL OR TYPE.START_DATE <= TRUNC(CURRENT_DATE))
		AND (TYPE.END_DATE IS NULL OR TYPE.END_DATE >= TRUNC(CURRENT_DATE))
	LEFT JOIN NODE_ATTR CODE
		ON NODE.ID = CODE.NODE_ID
		AND CODE.KEY = 'talous_tunnus'
		AND (CODE.START_DATE IS NULL OR CODE.START_DATE <= TRUNC(CURRENT_DATE))
		AND (CODE.END_DATE IS NULL OR CODE.END_DATE >= TRUNC(CURRENT_DATE))
	JOIN HIERARCHY_FILTER UNIQUE_CODE_HIERARCHY_FILTER
		ON UNIQUE_CODE_HIERARCHY_FILTER.HIERARCHY = 'talous'
		AND UNIQUE_CODE_HIERARCHY_FILTER.KEY = 'uniikki_koodi'
		AND (UNIQUE_CODE_HIERARCHY_FILTER.START_DATE IS NULL OR UNIQUE_CODE_HIERARCHY_FILTER.START_DATE <= TRUNC(CURRENT_DATE))
		AND (UNIQUE_CODE_HIERARCHY_FILTER.END_DATE IS NULL OR UNIQUE_CODE_HIERARCHY_FILTER.END_DATE >= TRUNC(CURRENT_DATE))
	JOIN NODE_ATTR UNIQUE_CODE
		ON NODE.ID = UNIQUE_CODE.NODE_ID
		AND UNIQUE_CODE.KEY = UNIQUE_CODE_HIERARCHY_FILTER.KEY
		AND UNIQUE_CODE.VALUE = UNIQUE_CODE_HIERARCHY_FILTER.VALUE
		AND (UNIQUE_CODE.START_DATE IS NULL OR UNIQUE_CODE.START_DATE <= TRUNC(CURRENT_DATE))
		AND (UNIQUE_CODE.END_DATE IS NULL OR UNIQUE_CODE.END_DATE >= TRUNC(CURRENT_DATE))
	LEFT JOIN NODE PARENT
		ON PARENT.ID = COALESCE(VANHA_TUTKIMUS.PARENT_NODE_ID, TALOUS.PARENT_NODE_ID)
		AND (PARENT.START_DATE IS NULL OR PARENT.START_DATE <= TRUNC(CURRENT_DATE))
		AND (PARENT.END_DATE IS NULL OR PARENT.END_DATE >= TRUNC(CURRENT_DATE))
	WHERE 
		(VANHA_TUTKIMUS.ID IS NOT NULL OR TALOUS.ID IS NOT NULL)
