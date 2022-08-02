package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.HierarchyFilter;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Node;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Repository(value = "hierarchyFilterDao")
public class HierarchyFilterDao extends NamedParameterJdbcDaoSupport {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    public List<HierarchyFilter> getHierarchyFilters() {
        String sql = "SELECT * FROM HIERARCHY_FILTER";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<HierarchyFilter> hierarchyFilters = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(HierarchyFilter.class));
        return hierarchyFilters;
    }

    public List<HierarchyFilter> getHierarchyFilter(String hierarchy) {
        String sql = "SELECT * FROM HIERARCHY_FILTER WHERE HIERARCHY = :hierarchy" +
                        " AND (END_DATE IS NULL OR END_DATE > trunc(:today))" +
                        " AND (START_DATE IS NULL OR START_DATE <= trunc(:today))";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.HIERARCHY, hierarchy);
        params.addValue("today", Timestamp.from(Instant.now()));
        List<HierarchyFilter> hierarchyFilters = getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(HierarchyFilter.class));
        return hierarchyFilters;
    }
}

