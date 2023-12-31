SELECT 
	S.PREDECESSOR_ID AS ID,
	PREDECESSOR.UNIQUE_ID,
	PREDECESSOR.START_DATE,
	PREDECESSOR.END_DATE,
	S.START_DATE AS EDGE_START_DATE,
	S.END_DATE AS EDGE_END_DATE,
	COALESCE(CURR.NAME, FUTURE.NAME, PAST.NAME, PREDECESSOR.NAME) FULL_NAME,
	COALESCE(CURR.LANGUAGE, FUTURE.LANGUAGE, PAST.LANGUAGE, 'fi') AS LANGUAGE
FROM PREDECESSOR_RELATION S
	JOIN NODE PREDECESSOR ON S.PREDECESSOR_ID = PREDECESSOR.ID
	LEFT JOIN FULL_NAME PAST ON PREDECESSOR.ID = PAST.NODE_ID
		AND PREDECESSOR.END_DATE < TRUNC(CURRENT_DATE)
		AND (PAST.START_DATE IS NULL OR PAST.START_DATE < PREDECESSOR.END_DATE)
		AND (PAST.END_DATE = PREDECESSOR.END_DATE)
	LEFT JOIN FULL_NAME CURR ON PREDECESSOR.ID = CURR.NODE_ID
		AND (PREDECESSOR.START_DATE IS NULL OR PREDECESSOR.START_DATE < TRUNC(CURRENT_DATE))
		AND (PREDECESSOR.END_DATE IS NULL OR PREDECESSOR.END_DATE >= TRUNC(CURRENT_DATE))
		AND (CURR.START_DATE IS NULL OR CURR.START_DATE < TRUNC(CURRENT_DATE))
		AND (CURR.END_DATE IS NULL OR CURR.END_DATE >= TRUNC(CURRENT_DATE))
	LEFT JOIN FULL_NAME FUTURE ON PREDECESSOR.ID = CURR.NODE_ID
		AND (PREDECESSOR.START_DATE > TRUNC(CURRENT_DATE))
		AND (FUTURE.START_DATE <= PREDECESSOR.START_DATE)
		AND (FUTURE.END_DATE IS NULL OR FUTURE.END_DATE = PREDECESSOR.START_DATE)
	WHERE S.NODE_ID = :nodeId
