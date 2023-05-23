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
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Repository(value = "edgeDao")
public class EdgeDao extends NamedParameterJdbcDaoSupport {

    private static SimpleDateFormat yearMonthDay = new SimpleDateFormat("dd.MM.yyyy");

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
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(parentUnitProperties.toArray());
        return getNamedParameterJdbcTemplate().batchUpdate(sql, batch);
    }

    public int[] addNewUpperUnits(List<EdgeWrapper> parentUnits) throws IOException {
        String sql = ReadSqlFiles.sqlString("addNewUpperUnit.sql");
        MapSqlParameterSource[] paramMaps = parentUnits.stream().map(attribute -> {
            Integer sequence = getJdbcTemplate().queryForObject("SELECT NODE_SEQ.nextval FROM dual", Integer.class);
            attribute.setId(sequence);
            MapSqlParameterSource params = new MapSqlParameterSource();
            return getMapSqlParameterSource(attribute, params);
        }).collect(Collectors.toList()).toArray(new MapSqlParameterSource[]{});
        return getNamedParameterJdbcTemplate().batchUpdate(sql, paramMaps);
    }

    public int[] updateUpperUnits(List<EdgeWrapper> parentUnits) throws IOException {
        String sql = ReadSqlFiles.sqlString("updateParentUnitProperties.sql");
        MapSqlParameterSource[] paramMaps = parentUnits.stream().map(attribute -> {
            MapSqlParameterSource params = new MapSqlParameterSource();
            return getMapSqlParameterSource(attribute, params);
        }).collect(Collectors.toList()).toArray(new MapSqlParameterSource[]{});
        return getNamedParameterJdbcTemplate().batchUpdate(sql, paramMaps);
    }

    public int[] deleteUpperUnits(List<EdgeWrapper> parentUnits) throws IOException {
        String sql = ReadSqlFiles.sqlString("deleteUpperUnit.sql");
        MapSqlParameterSource[] paramMaps = parentUnits.stream().map(attribute -> {
            MapSqlParameterSource params = new MapSqlParameterSource();
            return getMapSqlParameterSource(attribute, params);
        }).collect(Collectors.toList()).toArray(new MapSqlParameterSource[]{});
        return getNamedParameterJdbcTemplate().batchUpdate(sql, paramMaps);
    }

    private MapSqlParameterSource getMapSqlParameterSource(EdgeWrapper attribute, MapSqlParameterSource params) {
        params.addValue("child_node_id", attribute.getChildNodeId());
        params.addValue("parent_node_id", attribute.getParentNodeId());
        params.addValue("hierarchy", attribute.getHierarchy());
        String start = null;
        String end = null;
        if (attribute.getStartDate() != null) {
            start = yearMonthDay.format(attribute.getStartDate());
        }
        if (attribute.getEndDate() != null) {
            end = yearMonthDay.format(attribute.getEndDate());
        }
        params.addValue("start_date", start);
        params.addValue("end_date", end);
        params.addValue("id", attribute.getId());
        return params;
    }

    public int[] insertEdges(List<EdgeWrapper> edges) throws IOException {
        String sql = ReadSqlFiles.sqlString("insertEdges.sql");

        MapSqlParameterSource[] parameterMappings = edges.stream()
                .map(EdgeWrapper::asSqlMapping)
                .collect(Collectors.toList())
                .toArray(new MapSqlParameterSource[]{});
        int[] results = getNamedParameterJdbcTemplate().batchUpdate(sql, parameterMappings);
        return results;
    }

    public int[] updateEdges(List<EdgeWrapper> edges) throws IOException {
        String sql = ReadSqlFiles.sqlString("updateEdges.sql");
        MapSqlParameterSource[] parameterMappings = edges.stream()
                .map(EdgeWrapper::asSqlMapping)
                .collect(Collectors.toList())
                .toArray(new MapSqlParameterSource[]{});
        int[] results = getNamedParameterJdbcTemplate().batchUpdate(sql, parameterMappings);
        return results;
    }

    private List<Integer> asIdentifiers(List<EdgeWrapper> edges) {
        List<Integer> ids = edges
                .stream()
                .map(e -> e.getId())
                .collect(Collectors.toList());
        return ids;
    }
    public int deleteEdges(List<EdgeWrapper> edges) throws IOException {
        if (edges.size() == 0) {
            return 0;
        }
        String sql = ReadSqlFiles.sqlString("deleteEdges.sql");
        List<Integer> ids = asIdentifiers(edges);
        MapSqlParameterSource mapping = new MapSqlParameterSource();
        mapping.addValue("ids", ids);
        int rowsAffected = getNamedParameterJdbcTemplate().update(sql, mapping);
        return rowsAffected;
    }

}
