SELECT NA.NODE_ID, T.KEY, T.VALUE, T.LANGUAGE FROM NODE_ATTR NA
                 JOIN NODE N ON NA.NODE_ID = N.ID
                 JOIN TEXT T ON NA.VALUE = T.KEY
                 WHERE NA.KEY = 'iam-johtoryhma'
                 AND (N.END_DATE IS NULL OR N.END_DATE > trunc(:today))
                 AND (N.START_DATE IS NULL OR N.START_DATE <= trunc(:today))
                 AND N.ID IN (SELECT CHILD_NODE_ID FROM EDGE WHERE HIERARCHY='toiminnanohjaus')
                 AND (N.END_DATE IS NULL OR N.END_DATE > trunc(:today))
                 AND (N.START_DATE IS NULL OR N.START_DATE <= trunc(:today))
                 ORDER BY NA.NODE_ID, T.LANGUAGE
