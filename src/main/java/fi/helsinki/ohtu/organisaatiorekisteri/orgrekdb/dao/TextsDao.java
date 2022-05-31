package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.TextDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.ResultSet;
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
    private static final String NODE_ID = "node_id";

    public List<Map<String, String>> getAllTexts() {
        final String SQL_GET_ALL_TEXTS = "SELECT KEY, LANGUAGE, VALUE FROM TEXT";

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        List<Map<String, String>> query = jdbcTemplate.query(SQL_GET_ALL_TEXTS, (rs, rowNum) -> {
            Map<String, String> textKey = new HashMap<>();
            textKey.put(KEY_FIELD, rs.getString(KEY_FIELD));
            textKey.put(LANGUAGE_FIELD, rs.getString(LANGUAGE_FIELD));
            textKey.put(VALUE_FIELD, rs.getString(VALUE_FIELD));
            return textKey;
        });

        return query;
    }

    /**
     * Return texts by language. OR-579
     * <p>
     * Key value pairs are returned in a map
     * <p>
     * {
     * "opetus": "undervisning",
     * "tutkimus": "forskning"
     * }
     *
     * @return text entries in a map
     */
    public Map<String, String> getTextsByLang(String lang) {
        String sqlGetTextsByLang = "SELECT KEY, VALUE FROM TEXT where LANGUAGE= ? ";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        Map<String, String> textKey = new HashMap<>();
        jdbcTemplate.query(sqlGetTextsByLang, (ResultSet rs) -> {
            while (rs.next()) {
                textKey.put(rs.getString(KEY_FIELD), rs.getString(VALUE_FIELD));
            }
            return textKey;
        }, lang);
        return textKey;
    }

    /**
     * Return node attribute texts by language. OR-632
     * <p>
     * Key value pairs are returned in a map
     * <p>
     * {
     * "3142": "Hjelt-instituutti",
     * }
     *
     * @return text entries in a map
     */

    public Map<String, String> getAttributeNamesByLang(String language, String currentDate) {
        String lang;
        if (language.equals("sv")) lang = "name_sv";
        else if (language.equals("en")) lang = "name_en";
        else lang = "name_fi";
        String sql = "SELECT NODE_ID, VALUE FROM NODE_ATTR WHERE KEY = :lang " +
                "AND (END_DATE is null or :currentDate is null or trunc(END_DATE) >= to_date(:currentDate,'YYYY-MM-DD')) and " +
                "(START_DATE is null or  :currentDate is null or trunc(START_DATE) <= to_date(:currentDate, 'YYYY-MM-DD'))";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("lang", lang);
        params.addValue("currentDate", currentDate);
        Map<String, String> textKey = new HashMap<>();
        getNamedParameterJdbcTemplate().query(sql, params, (resultSet, i) -> {
            textKey.put(resultSet.getString(NODE_ID), resultSet.getString(VALUE_FIELD));
            return textKey;
        });
            return textKey;
    }

    /**
     * Return degree titles (bachelor, master and doctoral)
     * <p>
     * Language, key, value pairs are returned in a list of TextDTO objects
     * <p>
     *
     * @return TextDTO entries in a list
     */
    public List<TextDTO> getDegreeTitles() {

        String sql = "SELECT KEY, LANGUAGE, VALUE FROM TEXT WHERE KEY IN ('kandiohjelma-joryt', 'maisteriohjelma-joryt', 'tohtoriohjelma-joryt')";

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        List<TextDTO> query = jdbcTemplate.query(sql, (rs, rowNum) -> {
            TextDTO textDTO = new TextDTO();
            textDTO.setKey(rs.getString(KEY_FIELD));
            textDTO.setValue(rs.getString(VALUE_FIELD));
            textDTO.setLanguage(rs.getString(LANGUAGE_FIELD));
            return textDTO;
        });

        return query;
    }

}



