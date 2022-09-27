package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.EdgeWrapper;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.ReadSqlFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Repository(value = "edgeDao")
public class EdgeDao extends NamedParameterJdbcDaoSupport {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    public List<String> getHierarchyTypes() throws IOException {
        String sql = ReadSqlFiles.sqlString("hierarchyTypes.sql");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<String> hierarchies = jdbcTemplate.queryForList(sql, String.class);
        return hierarchies;
    }

    public int[] updateParentUnitProperties(List<EdgeWrapper> parentUnitProperties) throws IOException {
        String sql = ReadSqlFiles.sqlString("updateParentUnitProperties.sql");

        MapSqlParameterSource[] paramMaps = parentUnitProperties.stream().map(property -> {
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("startDate", property.getStartDate());
            params.addValue("endDate", property.getEndDate());
            params.addValue("parentNodeId", property.getParentNodeId());
            params.addValue("childNodeId", property.getChildNodeId());
            params.addValue("hierarchy", property.getHierarchy());
            return params;
        }).collect(Collectors.toList()).toArray(new MapSqlParameterSource[]{});
        return getNamedParameterJdbcTemplate().batchUpdate(sql, paramMaps);
    }

}
