package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Attribute;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Node;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.OrgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository(value = "orgUnitDao")
public class OrgUnitDao extends NamedParameterJdbcDaoSupport {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    public Map<String, List<Attribute>> getAllAttributesMap(String id) {
        String sql = "SELECT * FROM NODE_ATTR WHERE NODE_ID = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        List<Attribute> attributes = getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Attribute.class));
        return OrgUtil.getAttributeListAsMap(attributes);
    }

    public Node getNodeByUniqueId(int id) {
        String sql = "SELECT * FROM NODE WHERE UNIQUE_ID = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        return getNamedParameterJdbcTemplate().queryForObject(sql, params, BeanPropertyRowMapper.newInstance(Node.class));
    }

    public Map<String, List<Attribute>> getCurrentAttributeMap(String id, Date date) {
        String sql = "SELECT * FROM NODE_ATTR WHERE NODE_ID = :id AND " +
                "(NODE_ATTR.END_DATE IS NULL OR " +
                "(NODE_ATTR.END_DATE >= trunc(:date)))";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        params.addValue("date", date);
        List<Attribute> attributes = getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Attribute.class));
        return OrgUtil.getAttributeListAsMap(attributes);
    }
}

