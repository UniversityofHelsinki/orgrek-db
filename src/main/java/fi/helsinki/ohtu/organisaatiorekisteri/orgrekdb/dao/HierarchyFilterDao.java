package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.HierarchyFilter;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.ReadSqlFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository(value = "hierarchyFilterDao")
public class HierarchyFilterDao extends NamedParameterJdbcDaoSupport {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    public List<HierarchyFilter> getCurrentHierarchyFilter(Date date) {
        Date attrstart = date;
        String sql = "SELECT * FROM HIERARCHY_FILTER WHERE" +
                " (END_DATE IS NULL OR END_DATE > trunc(:attrstart)) AND (START_DATE IS NULL OR START_DATE <= trunc(:attrstart))";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("attrstart", attrstart);
        List<HierarchyFilter> hierarchyFilters = getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(HierarchyFilter.class));
        return hierarchyFilters;
    }
    public List<HierarchyFilter> getHistoryAndCurrentHierarchyFilter(Date date) {
        Date attrstart = date;
        String sql = "SELECT * FROM HIERARCHY_FILTER WHERE" +
                " (START_DATE IS NULL OR START_DATE <= trunc(:attrstart))";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("attrstart", attrstart);
        List<HierarchyFilter> hierarchyFilters = getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(HierarchyFilter.class));
        return hierarchyFilters;
    }
    public List<HierarchyFilter> getFutureAndCurrentHierarchyFilter(Date date) {
        Date attrstart = date;
        String sql = "SELECT * FROM HIERARCHY_FILTER WHERE" +
                " (END_DATE IS NULL OR END_DATE > trunc(:attrstart))";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("attrstart", attrstart);
        List<HierarchyFilter> hierarchyFilters = getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(HierarchyFilter.class));
        return hierarchyFilters;
    }

    public List<HierarchyFilter> getHierarchyFilters() throws IOException  {
        String sql = ReadSqlFiles.sqlString("hierarchyFilters.sql");

        List<HierarchyFilter> hierarchyFilters = getNamedParameterJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(HierarchyFilter.class));
        return hierarchyFilters;
    }

    public int updateHierarchyFilter(HierarchyFilter hierarchyFilter) throws IOException {
        String sql = ReadSqlFiles.sqlString("updateHierarchyFilter.sql");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", hierarchyFilter.getId());
        params.addValue("hierarchy", hierarchyFilter.getHierarchy());
        params.addValue("key", hierarchyFilter.getKey());
        params.addValue("value", hierarchyFilter.getValue());
        params.addValue("startDate", hierarchyFilter.getStartDate());
        params.addValue("endDate", hierarchyFilter.getEndDate());
        return getNamedParameterJdbcTemplate().update(sql, params);
    }

    public int deleteHierarchyFilter(HierarchyFilter hierarchyFilter) throws IOException {
        String sql = ReadSqlFiles.sqlString("deleteHierarchyFilter.sql");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", hierarchyFilter.getId());
        return getNamedParameterJdbcTemplate().update(sql, params);
    }

    public int[] insertHierarchyFilters(List<HierarchyFilter> hierarchyFilters) throws IOException {
        String sql = ReadSqlFiles.sqlString("insertHierarchyFilters.sql");

        MapSqlParameterSource[] paramMaps = hierarchyFilters.stream().map(hierarchyFilter -> {
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("hierarchy", hierarchyFilter.getHierarchy());
            params.addValue("key", hierarchyFilter.getKey());
            params.addValue("value", hierarchyFilter.getValue());
            params.addValue("startDate", hierarchyFilter.getStartDate());
            params.addValue("endDate", hierarchyFilter.getEndDate());
            return params;
        }).collect(Collectors.toList()).toArray(new MapSqlParameterSource[]{});
        int[] result = getNamedParameterJdbcTemplate().batchUpdate(sql, paramMaps);
        return result;
    }
}

