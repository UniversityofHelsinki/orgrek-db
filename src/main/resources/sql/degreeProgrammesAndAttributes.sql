SELECT nat.NODE_ID, nat.KEY, nat.VALUE from NODE_ATTR nat
                WHERE  nat.NODE_ID in (SELECT N.ID FROM NODE N, NODE_ATTR NA  WHERE N.ID=NA.NODE_ID AND NA.KEY = 'iam-johtoryhma'
                AND NA.NODE_ID IN
                (SELECT NODE_ID FROM NODE_TYPE WHERE
                NODE_TYPE.VALUE IN ('kandiohjelma', 'maisteriohjelma', 'tohtoriohjelma'))
                AND N.ID IN (SELECT CHILD_NODE_ID FROM EDGE WHERE HIERARCHY='toiminnanohjaus')
                AND (N.END_DATE IS NULL OR N.END_DATE > trunc(:today))
                AND (N.START_DATE IS NULL OR N.START_DATE <= trunc(:today)))
                ORDER BY NODE_ID, KEY
