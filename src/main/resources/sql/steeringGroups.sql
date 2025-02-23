/*
Steering group API
This should produce equivalent results as JOHTO_VOIMASSA_OLEVAT_YKSIKOT_API view.

This clause selects all nodes that are valid today in 'johto' hierarchy.
Node is in 'johto' hierarchy if there is a row in EDGE table having
its CHILD_NODE_ID column value equal to the node's id and the row is 
valid today.

In this context node is valid if it's valid today, has a valid type in hierarchy 'johto'
and has the following attributes valid today: 
name_fi, name_en, name_sv, iam_ryhma. 
Exception: If node's type is 'kansio', then the iam_ryhma isn't required.

Object is valid today if its start_date is less than or equal to today and 
end_date is greater than or equal to today. 
NULL value in the columns start_date or end_date 
indicates -INFINITY and INFINITY respectively.

Node has a valid type in 'johto' hierarchy if there is a row
in the table HIERARCY_FILTER that is valid today 
with 'johto' as the value of the column 
HIERARCHY and 'type' as the value of the column "KEY".

Node's parent is set to NULL if node's type is 'kansio'.

*/
SELECT 
	NODE.UNIQUE_ID,
	OTHER_REQUIRED_ATTR."VALUE" CODE,
	TYPE."VALUE" AS TYPE,
	NAME_FI."VALUE" AS NAME_FI,
	NAME_EN."VALUE" AS NAME_EN,
	NAME_SV."VALUE" AS NAME_SV,
	PARENT.UNIQUE_ID PARENT
FROM NODE 
	JOIN EDGE EDGE ON NODE.ID = EDGE.CHILD_NODE_ID
		AND EDGE.HIERARCHY = 'johto'
		AND (EDGE.START_DATE IS NULL OR EDGE.START_DATE <= TRUNC(CURRENT_DATE))
		AND (EDGE.END_DATE IS NULL OR EDGE.END_DATE >= TRUNC(CURRENT_DATE))
	JOIN NODE_ATTR TYPE ON TYPE.NODE_ID = NODE.ID AND TYPE."KEY" = 'type'
		AND (TYPE.START_DATE IS NULL OR TYPE.START_DATE <= TRUNC(CURRENT_DATE))
		AND (TYPE.END_DATE IS NULL OR TYPE.END_DATE >= TRUNC(CURRENT_DATE))
	JOIN HIERARCHY_FILTER TYPE_FILTER ON TYPE_FILTER."KEY" = TYPE."KEY"
		AND TYPE_FILTER."VALUE" = TYPE."VALUE"
		AND TYPE_FILTER.HIERARCHY = 'johto'
		AND (TYPE_FILTER.START_DATE IS NULL OR TYPE_FILTER.START_DATE <= TRUNC(CURRENT_DATE))
		AND (TYPE_FILTER.END_DATE IS NULL OR TYPE.END_DATE >= TRUNC(CURRENT_DATE))
	JOIN NODE_ATTR NAME_FI ON NAME_FI.NODE_ID = NODE.ID
		AND NAME_FI."KEY" = 'name_fi'
		AND (NAME_FI.START_DATE IS NULL OR NAME_FI.START_DATE <= TRUNC(CURRENT_DATE))
		AND (NAME_FI.END_DATE IS NULL OR NAME_FI.END_DATE >= TRUNC(CURRENT_DATE))
	JOIN NODE_ATTR NAME_EN ON NAME_EN.NODE_ID = NODE.ID
		AND NAME_EN."KEY" = 'name_en'
		AND (NAME_EN.START_DATE IS NULL OR NAME_EN.START_DATE <= TRUNC(CURRENT_DATE))
		AND (NAME_EN.END_DATE IS NULL OR NAME_EN.END_DATE >= TRUNC(CURRENT_DATE))
	JOIN NODE_ATTR NAME_SV ON NAME_SV.NODE_ID = NODE.ID
		AND NAME_SV."KEY" = 'name_sv'
		AND (NAME_SV.START_DATE IS NULL OR NAME_SV.START_DATE <= TRUNC(CURRENT_DATE))
		AND (NAME_SV.END_DATE IS NULL OR NAME_SV.END_DATE >= TRUNC(CURRENT_DATE))
	LEFT JOIN NODE_ATTR OTHER_REQUIRED_ATTR ON TYPE."VALUE" NOT IN ('kansio')
		AND OTHER_REQUIRED_ATTR.NODE_ID = NODE.ID
		AND OTHER_REQUIRED_ATTR."KEY" IN ('iam_ryhma')
		AND (OTHER_REQUIRED_ATTR.START_DATE IS NULL OR OTHER_REQUIRED_ATTR.START_DATE <= TRUNC(CURRENT_DATE))
		AND (OTHER_REQUIRED_ATTR.END_DATE IS NULL OR OTHER_REQUIRED_ATTR.END_DATE >= TRUNC(CURRENT_DATE))
	LEFT JOIN NODE PARENT ON EDGE.PARENT_NODE_ID = PARENT.ID AND OTHER_REQUIRED_ATTR.ID IS NOT NULL
	WHERE (TYPE."VALUE" IN ('kansio') OR OTHER_REQUIRED_ATTR.ID IS NOT NULL)
	ORDER BY NODE.UNIQUE_ID ASC
