UPDATE TEXT SET
                KEY=:key, LANGUAGE=:language, VALUE=:value, USER_NAME=:user_name, TIMESTAMP=current_date
                WHERE KEY=:key AND LANGUAGE=:language
