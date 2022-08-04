SELECT * FROM NODE_ATTR WHERE NODE_ID = :id AND
                (NODE_ATTR.END_DATE IS NULL OR
                (NODE_ATTR.END_DATE >= trunc(:attrend))) AND
                (NODE_ATTR.START_DATE IS NULL OR
                (NODE_ATTR.START_DATE <= trunc(:attrstart)))
