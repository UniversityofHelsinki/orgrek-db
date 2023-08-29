WITH ROOT (ID, PARENT_NODE_ID, CHILD_NODE_ID, START_DATE, END_DATE, HIERARCHY) AS (
	SELECT 
		ID, 
		PARENT_NODE_ID,
		CHILD_NODE_ID,
		START_DATE,
		END_DATE, 
		HIERARCHY 
	FROM EDGE 
	WHERE PARENT_NODE_ID IS NULL 
		AND CHILD_NODE_ID IS NOT NULL
		AND (START_DATE IS NULL OR START_DATE <= TRUNC(TO_DATE(:date, 'DD.MM.YYYY')))
		AND (END_DATE IS NULL OR END_DATE >= TRUNC(TO_DATE(:date, 'DD.MM.YYYY')))
		AND HIERARCHY IN (:hierarchies)
		UNION ALL 
	SELECT 
		EDGE.ID, 
		EDGE.PARENT_NODE_ID,
		EDGE.CHILD_NODE_ID, 
		EDGE.START_DATE, 
		EDGE.END_DATE, 
		EDGE.HIERARCHY 
	FROM ROOT JOIN EDGE ON ROOT.CHILD_NODE_ID = EDGE.PARENT_NODE_ID 
		AND ROOT.HIERARCHY = EDGE.HIERARCHY 
		AND (EDGE.START_DATE IS NULL OR EDGE.START_DATE <= TRUNC(TO_DATE(:date, 'DD.MM.YYYY')))
		AND (EDGE.END_DATE IS NULL OR EDGE.END_DATE >= TRUNC(TO_DATE(:date, 'DD.MM.YYYY')))
), LANGUAGES(LANGUAGE) AS (
	SELECT 'fi' FROM dual
	UNION
	SELECT 'sv' FROM dual
	UNION
	SELECT 'en' FROM dual
) SELECT PARENT_NODE_ID, CHILD_NODE_ID, CHILD.UNIQUE_ID, HIERARCHY, LANGUAGES.LANGUAGE, COALESCE(FULL_NAME.NAME, CHILD.NAME) AS NODE_NAME FROM ROOT 
		CROSS JOIN LANGUAGES 
		JOIN NODE CHILD ON ROOT.CHILD_NODE_ID = CHILD.ID
		LEFT JOIN FULL_NAME ON CHILD.ID = FULL_NAME.NODE_ID 
			AND LANGUAGES.LANGUAGE = FULL_NAME.LANGUAGE 
			AND (FULL_NAME.START_DATE IS NULL OR FULL_NAME.START_DATE <= TRUNC(TO_DATE(:date, 'DD.MM.YYYY')))
			AND (FULL_NAME.END_DATE IS NULL OR FULL_NAME.END_DATE >= TRUNC(TO_DATE(:date, 'DD.MM.YYYY')))
