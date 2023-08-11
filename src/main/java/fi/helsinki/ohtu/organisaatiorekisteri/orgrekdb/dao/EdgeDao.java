package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.EdgeWithChildUniqueId;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.EdgeWrapper;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.HierarchyFilter;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.NewNodeDTO;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.Constants;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.ReadSqlFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
import java.util.Arrays;
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

    public List<String> getEdgeHierarchies() throws IOException  {
        String sql = ReadSqlFiles.sqlString("allEdgeHierarchies.sql");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<String> edghierarchieses = jdbcTemplate.queryForList(sql, String.class);
        return edghierarchieses;
        /*
        String sql = ReadSqlFiles.sqlString("hierarchyTypes.sql");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<String> hierarchies = jdbcTemplate.queryForList(sql, String.class);
        return hierarchies;
         */
    }

    public List<EdgeWrapper> getRoots() throws IOException {
        String sql = ReadSqlFiles.sqlString("roots.sql");
        List<EdgeWrapper> roots 
            = getNamedParameterJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(EdgeWrapper.class));
        return roots;
    }

    public List<EdgeWithChildUniqueId> getEdgesInHierarchy(String startNodeID, String hierarchy) throws IOException {
        String sql = ReadSqlFiles.sqlString("edgesStartingFromXInHierarchy.sql");
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("hierarchy", hierarchy);
        params.addValue("id", startNodeID);
        List<EdgeWithChildUniqueId> edges =
            getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(EdgeWithChildUniqueId.class));
        return edges;
    }

    public List<EdgeWrapper> getByParentAndHierarchies(String parentNodeId, List<String> hierarchies) throws IOException {
        String sql = ReadSqlFiles.sqlString("edgesByParentAndHierarchies.sql");
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", parentNodeId);
        params.addValue("hierarchies", hierarchies);
        List<EdgeWrapper> edges = getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(EdgeWrapper.class));
        return edges;
    }

    public List<EdgeWrapper> getByChildAndHierarchies(String childNodeId, List<String> hierarchies) throws IOException  {
        String sql = ReadSqlFiles.sqlString("edgesByChildAndHierarchies.sql");
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", childNodeId);
        params.addValue("hierarchies", hierarchies);
        List<EdgeWrapper> edges = getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(EdgeWrapper.class));
        return edges;
    }

    public List<EdgeWrapper> getPredecessors(String nodeId) throws IOException {
        return getByParentAndHierarchies(nodeId, Arrays.asList(new String[] { Constants.HISTORY }));
    }

    public List<EdgeWrapper> getSuccessors(String nodeId) throws IOException {
        return getByChildAndHierarchies(nodeId, Arrays.asList(new String[] { Constants.HISTORY }));
    }

    public List<EdgeWrapper> getChildren(String nodeId, List<String> hierarchies) throws IOException {
        return getByParentAndHierarchies(nodeId, hierarchies);
    }

    public List<EdgeWrapper> getParents(String nodeId, List<String> hierarchies) throws IOException {
        return getByChildAndHierarchies(nodeId , hierarchies);
    }

}
