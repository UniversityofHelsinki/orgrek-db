package fi.helsinki.ohtu.organisaatiorekisteri.orkregdb.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository(value = "textsDao")
public class TextsDao extends NamedParameterJdbcDaoSupport {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }


    /**
     * Return texts for the admin text tool. OR-294
     * <p>
     * Each row in the TEXT-table is presented as a map.
     * <p>
     * {
     * "key": "Opetus",
     * "language": "fi",
     * "value": "Opetus",
     * }
     *
     * @return all text entries in a list
     */

    private static final String VALUE_FIELD = "value";
    private static final String KEY_FIELD = "key";
    private static final String LANGUAGE_FIELD = "language";

    public List<Map<String, String>> getAllTexts() {
        final String SQL_GET_ALL_TEXTS = "SELECT KEY, LANGUAGE, VALUE FROM text";

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        List<Map<String, String>> query = jdbcTemplate.query(SQL_GET_ALL_TEXTS, new RowMapper<Map<String, String>>() {
            public Map<String, String> mapRow(ResultSet rs, int rowNum) throws SQLException {
                Map<String, String> textKey = new HashMap<String, String>();
                textKey.put(KEY_FIELD, rs.getString(KEY_FIELD));
                textKey.put(LANGUAGE_FIELD, rs.getString(LANGUAGE_FIELD));
                textKey.put(VALUE_FIELD, rs.getString(VALUE_FIELD));
                return textKey;
            }
        });

        return query;
    }


}
