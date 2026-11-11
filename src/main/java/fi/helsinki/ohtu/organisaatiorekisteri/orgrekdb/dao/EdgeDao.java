package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.EdgeWithChildUniqueId;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.EdgeWrapper;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.Constants;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.ReadSqlFiles;

@Repository(value = "edgeDao")
public class EdgeDao extends NamedParameterJdbcDaoSupport {

    private static final RowMapper<EdgeWrapper> EDGE_ROW_MAPPER = EdgeDao::mapEdge;
    private static final RowMapper<EdgeWithChildUniqueId> EDGE_WITH_CHILD_UNIQUE_ID_ROW_MAPPER = EdgeDao::mapEdgeWithChildUniqueId;

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

    public List<String> getPublicHierarchyTypes() throws IOException {
      String sql = ReadSqlFiles.sqlString("publicHierarchyTypes.sql");
      JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
      List<String> hierarchies = jdbcTemplate.queryForList(sql, String.class);
      return hierarchies;
    }

    public int[] updateParentUnitProperties(List<EdgeWrapper> parentUnitProperties) throws IOException {
        String sql = ReadSqlFiles.sqlString("updateParentUnitProperties.sql");
        MapSqlParameterSource[] batch = parentUnitProperties.stream()
                .map(EdgeWrapper::asSqlMapping)
                .toArray(MapSqlParameterSource[]::new);
        return getNamedParameterJdbcTemplate().batchUpdate(sql, batch);
    }

    public int[] addNewUpperUnits(List<EdgeWrapper> parentUnits) throws IOException {
        String sql = ReadSqlFiles.sqlString("addNewUpperUnit.sql");
        MapSqlParameterSource[] paramMaps = parentUnits.stream().map(attribute -> {
            Integer sequence = getJdbcTemplate().queryForObject("SELECT NODE_SEQ.nextval FROM dual", Integer.class);
            attribute.setId(sequence);
            return EdgeWrapper.asSqlMapping(attribute);
        }).collect(Collectors.toList()).toArray(new MapSqlParameterSource[]{});
        return getNamedParameterJdbcTemplate().batchUpdate(sql, paramMaps);
    }

    public int[] updateUpperUnits(List<EdgeWrapper> parentUnits) throws IOException {
        String sql = ReadSqlFiles.sqlString("updateParentUnitProperties.sql");
        MapSqlParameterSource[] paramMaps = parentUnits.stream().map(attribute -> {
            return EdgeWrapper.asSqlMapping(attribute);
        }).collect(Collectors.toList()).toArray(new MapSqlParameterSource[]{});
        return getNamedParameterJdbcTemplate().batchUpdate(sql, paramMaps);
    }

    public int[] deleteUpperUnits(List<EdgeWrapper> parentUnits) throws IOException {
        String sql = ReadSqlFiles.sqlString("deleteUpperUnit.sql");
        MapSqlParameterSource[] paramMaps = parentUnits.stream().map(attribute -> {
            return EdgeWrapper.asSqlMapping(attribute);
        }).collect(Collectors.toList()).toArray(new MapSqlParameterSource[]{});
        return getNamedParameterJdbcTemplate().batchUpdate(sql, paramMaps);
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
            = getNamedParameterJdbcTemplate().query(sql, EDGE_ROW_MAPPER);
        return roots;
    }

    public List<EdgeWithChildUniqueId> getEdgesInHierarchy(
        String startNodeID, 
        String hierarchies,
        String newEdgeStartDate,
        String newEdgeEndDate
    ) throws IOException {
        String sql = ReadSqlFiles.sqlString("edgesStartingFromXInHierarchy.sql");
        MapSqlParameterSource params = new MapSqlParameterSource();
        addDateParameter(params, "newEdgeStartDate", newEdgeStartDate);
        addDateParameter(params, "newEdgeEndDate", newEdgeEndDate);
        //params.addValue("hierarchies", hierarchies);
        params.addValue("id", startNodeID);
        List<EdgeWithChildUniqueId> edges =
            getNamedParameterJdbcTemplate().query(sql, params, EDGE_WITH_CHILD_UNIQUE_ID_ROW_MAPPER);
        return edges;
    }

    static String dateParameterOrNull(String date) {
        return "null".equals(date) ? null : date;
    }

    static void addDateParameter(MapSqlParameterSource params, String name, String date) {
        params.addValue(name, dateParameterOrNull(date), Types.VARCHAR);
    }

    public List<EdgeWrapper> getByParentAndHierarchies(String parentNodeId, List<String> hierarchies) throws IOException {
        String sql = ReadSqlFiles.sqlString("edgesByParentAndHierarchies.sql");
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", parentNodeId);
        params.addValue("hierarchies", hierarchies);
        List<EdgeWrapper> edges = getNamedParameterJdbcTemplate().query(sql, params, EDGE_ROW_MAPPER);
        return edges;
    }

    public List<EdgeWrapper> getByChildAndHierarchies(String childNodeId, List<String> hierarchies) throws IOException  {
        String sql = ReadSqlFiles.sqlString("edgesByChildAndHierarchies.sql");
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", childNodeId);
        params.addValue("hierarchies", hierarchies);
        List<EdgeWrapper> edges = getNamedParameterJdbcTemplate().query(sql, params, EDGE_ROW_MAPPER);
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

    private static EdgeWrapper mapEdge(ResultSet resultSet, int rowNumber) throws SQLException {
        EdgeWrapper edge = new EdgeWrapper();
        edge.setId(resultSet.getInt("id"));
        edge.setParentNodeId(resultSet.getString("parent_node_id"));
        edge.setChildNodeId(resultSet.getString("child_node_id"));
        edge.setStartDate(toLocalDate(resultSet, "start_date"));
        edge.setEndDate(toLocalDate(resultSet, "end_date"));
        edge.setHierarchy(resultSet.getString("hierarchy"));
        return edge;
    }

    private static EdgeWithChildUniqueId mapEdgeWithChildUniqueId(ResultSet resultSet, int rowNumber) throws SQLException {
        EdgeWithChildUniqueId edge = new EdgeWithChildUniqueId();
        edge.setId(resultSet.getInt("id"));
        edge.setParentUniqueId(resultSet.getInt("parent_unique_id"));
        edge.setChildUniqueId(resultSet.getInt("child_unique_id"));
        edge.setStartDate(toLocalDate(resultSet, "start_date"));
        edge.setEndDate(toLocalDate(resultSet, "end_date"));
        edge.setHierarchy(resultSet.getString("hierarchy"));
        return edge;
    }

    private static LocalDate toLocalDate(ResultSet resultSet, String columnName) throws SQLException {
        LocalDateTime dateTime = resultSet.getObject(columnName, LocalDateTime.class);
        return dateTime == null ? null : dateTime.toLocalDate();
    }

}
