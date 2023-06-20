/*
Human resources API
This should produce equivalent results as UUSI_HENKILOSTO_VOIMASSA_OLEVAT_YKSIKOT_API view.

This clause selects all nodes that are valid today in 'uusi_henkilosto' hierarchy.
Node is in 'uusi_henkilosto' hierarchy if there is a row in EDGE table having
its CHILD_NODE_ID column value equal to the node's id and the row is 
valid today.

In this context node is valid if it's valid today, has a valid type in hierarchy 'johto'
and has the following attributes valid today: 
name_fi, hr_lyhenne. 

name_en and name_sv are replaced by name_fi + ' EN' or ' SV' respectively.
There is a comment in the view describing this:
"--ASHA:a varten tuotetaan näkymässä SV, kunnes tieto on olemassa"

Object is valid today if its start_date is less than or equal to today and 
end_date is greater than or equal to today. 
NULL value in the columns start_date or end_date 
indicates -INFINITY and INFINITY respectively.

Node has a valid type in 'uusi_henkilosto' hierarchy if there is a row
in the table HIERARCY_FILTER that is valid today 
with 'uusi_henkilosto' as the value of the column 
HIERARCHY and 'type' as the value of the column KEY.

Node's parent is set to NULL if hr_lyhenne is 'H01'.

tl;dr selects all valid nodes in 'uusi_henkilosto' hierarchy 
having type='uusi_henkilosto' OR has an hr_lyhenne attribute.

*/
SELECT 
	NODE.UNIQUE_ID AS UNIQUE_ID,
	CODE.VALUE AS CODE,
	TYPE.VALUE AS TYPE,
	NAME_FI.VALUE AS NAME_FI,
	NAME_FI.VALUE || ' EN' AS NAME_EN,
	NAME_FI.VALUE || ' SV' AS NAME_SV,
	PARENT.UNIQUE_ID AS PARENT
FROM NODE
	JOIN EDGE ON NODE.ID = EDGE.CHILD_NODE_ID AND HIERARCHY = 'uusi_henkilosto'
		AND (EDGE.START_DATE IS NULL OR EDGE.START_DATE <= TRUNC(CURRENT_DATE))
		AND (EDGE.END_DATE IS NULL OR EDGE.END_DATE >= TRUNC(CURRENT_DATE))
	JOIN NODE_ATTR TYPE ON NODE.ID = TYPE.NODE_ID AND TYPE.KEY = 'type'
	  AND (TYPE.START_DATE IS NULL OR TYPE.START_DATE <= TRUNC(CURRENT_DATE))
	  AND (TYPE.END_DATE IS NULL OR TYPE.END_DATE >= TRUNC(CURRENT_DATE))
	 JOIN HIERARCHY_FILTER TYPE_FILTER ON TYPE_FILTER.KEY = TYPE.KEY
	 	AND TYPE_FILTER.VALUE = TYPE.VALUE
	 	AND TYPE_FILTER.HIERARCHY = 'uusi_henkilosto'
	 	AND (TYPE_FILTER.START_DATE IS NULL OR TYPE_FILTER.START_DATE <= TRUNC(CURRENT_DATE))
	 	AND (TYPE_FILTER.END_DATE IS NULL OR TYPE_FILTER.END_DATE >= TRUNC(CURRENT_DATE))
	 JOIN NODE_ATTR NAME_FI ON NAME_FI.NODE_ID = NODE.ID
	 	AND NAME_FI.KEY = 'name_fi'
	 	AND (NAME_FI.START_DATE IS NULL OR NAME_FI.START_DATE <= TRUNC(CURRENT_DATE))
	 	AND (NAME_FI.END_DATE IS NULL OR NAME_FI.END_DATE >= TRUNC(CURRENT_DATE))
	 JOIN NODE_ATTR CODE ON CODE.NODE_ID = NODE.ID
	 	AND CODE.KEY = 'hr_lyhenne'
	 	AND (CODE.START_DATE IS NULL OR CODE.START_DATE <= TRUNC(CURRENT_DATE))
	 	AND (CODE.END_DATE IS NULL OR CODE.END_DATE >= TRUNC(CURRENT_DATE))
	 LEFT JOIN NODE PARENT ON EDGE.PARENT_NODE_ID = PARENT.ID AND CODE.VALUE <> 'H01'
	 	AND (PARENT.START_DATE IS NULL OR PARENT.START_DATE <= TRUNC(CURRENT_DATE))
	 	AND (PARENT.END_DATE IS NULL OR PARENT.END_DATE >= TRUNC(CURRENT_DATE))
	 WHERE (NODE.START_DATE IS NULL OR NODE.START_DATE <= TRUNC(CURRENT_DATE))
	 	AND (NODE.END_DATE IS NULL OR NODE.END_DATE >= TRUNC(CURRENT_DATE))
    ORDER BY NODE.UNIQUE_ID ASC
