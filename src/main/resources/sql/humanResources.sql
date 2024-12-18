SELECT 
	NODE.UNIQUE_ID,
	CODE."VALUE" AS CODE,
	TYPE."VALUE" AS TYPE,
	NAME_FI."VALUE" AS NAME_FI,
	NAME_EN."VALUE" AS NAME_EN,
	NAME_SV."VALUE" AS NAME_SV,
	PARENT.UNIQUE_ID AS PARENT
FROM EDGE 
	JOIN NODE ON NODE.ID = EDGE.CHILD_NODE_ID AND
		(NODE.START_DATE IS NULL OR NODE.START_DATE <= TRUNC(CURRENT_DATE)) AND
		(NODE.END_DATE IS NULL OR NODE.END_DATE >= TRUNC(CURRENT_DATE))
	JOIN NODE_ATTR CODE ON CODE.NODE_ID = NODE.ID 
		AND CODE."KEY" = 'hr_lyhenne' AND
		(CODE.START_DATE IS NULL OR CODE.START_DATE <= TRUNC(CURRENT_DATE)) AND
		(CODE.END_DATE IS NULL OR CODE.END_DATE >= TRUNC(CURRENT_DATE))
	JOIN NODE_ATTR TYPE ON TYPE.NODE_ID = NODE.ID AND
		TYPE."KEY" = 'type' AND 
		(TYPE.START_DATE IS NULL OR TYPE.START_DATE <= TRUNC(CURRENT_DATE)) AND
		(TYPE.END_DATE IS NULL OR TYPE.END_DATE >= TRUNC(CURRENT_DATE))
	JOIN HIERARCHY_FILTER TYPE_FILTER 
		ON TYPE_FILTER.HIERARCHY = 'henkilosto' AND
		TYPE_FILTER."KEY" = TYPE."KEY" AND
		TYPE_FILTER."VALUE" = TYPE."VALUE" AND
		(TYPE_FILTER.START_DATE IS NULL OR TYPE_FILTER.START_DATE <= TRUNC(CURRENT_DATE)) AND
		(TYPE_FILTER.END_DATE IS NULL OR TYPE_FILTER.END_DATE >= TRUNC(CURRENT_DATE))
	JOIN NODE_ATTR NAME_FI ON NODE.ID = NAME_FI.NODE_ID AND
		NAME_FI."KEY" = 'name_fi' AND
		(NAME_FI.START_DATE IS NULL OR NAME_FI.START_DATE <= TRUNC(CURRENT_DATE)) AND
		(NAME_FI.END_DATE IS NULL OR NAME_FI.END_DATE >= TRUNC(CURRENT_DATE))
	JOIN NODE_ATTR NAME_SV ON NODE.ID = NAME_SV.NODE_ID AND
		NAME_SV."KEY" = 'name_sv' AND
		(NAME_SV.START_DATE IS NULL OR NAME_SV.START_DATE <= TRUNC(CURRENT_DATE)) AND
		(NAME_SV.END_DATE IS NULL OR NAME_SV.END_DATE >= TRUNC(CURRENT_DATE))
	JOIN NODE_ATTR NAME_EN ON NODE.ID = NAME_EN.NODE_ID AND
		NAME_EN."KEY" = 'name_en' AND
		(NAME_EN.START_DATE IS NULL OR NAME_EN.START_DATE <= TRUNC(CURRENT_DATE)) AND
		(NAME_EN.END_DATE IS NULL OR NAME_EN.END_DATE >= TRUNC(CURRENT_DATE))
	LEFT JOIN NODE PARENT ON PARENT.ID = EDGE.PARENT_NODE_ID AND
		(PARENT.START_DATE IS NULL OR PARENT.START_DATE <= TRUNC(CURRENT_DATE)) AND
		(PARENT.END_DATE IS NULL OR PARENT.END_DATE >= TRUNC(CURRENT_DATE))
	WHERE EDGE.HIERARCHY = 'henkilosto' AND
		(EDGE.START_DATE IS NULL OR EDGE.START_DATE <= TRUNC(CURRENT_DATE)) AND
		(EDGE.END_DATE IS NULL OR EDGE.END_DATE >= TRUNC(CURRENT_DATE))

