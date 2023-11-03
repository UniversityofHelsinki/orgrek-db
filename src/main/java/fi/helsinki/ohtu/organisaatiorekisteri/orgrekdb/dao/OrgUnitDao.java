package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Attribute;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.DegreeProgrammeDTO;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.EdgeWrapper;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.EducationUnit;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.FullName;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.NewNodeDTO;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Node;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.NodeWrapper;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Relative;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.SteeringGroup;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.TreeNode;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.Constants;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.OrgUnitDbUtil;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.ReadSqlFiles;

@Repository(value = "orgUnitDao")
public class OrgUnitDao extends NamedParameterJdbcDaoSupport {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    public Node getNodeByNodeId(String id) throws IOException {
        String sql = ReadSqlFiles.sqlString("nodeByNodeId.sql");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        return getNamedParameterJdbcTemplate().queryForObject(sql, params, BeanPropertyRowMapper.newInstance(Node.class));
    }

    public Node getNodeByUniqueId(int id) throws IOException {
        String sql = ReadSqlFiles.sqlString("nodeByUniqueId.sql");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        return getNamedParameterJdbcTemplate().queryForObject(sql, params, BeanPropertyRowMapper.newInstance(Node.class));
    }

    public List<Node> getNodes(List<String> nodeIds) throws IOException {
        String sql = ReadSqlFiles.sqlString("nodes.sql");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("ids", nodeIds);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Node.class));
    }


    public List<Attribute> getAttributeListByDate(String id, Date date) throws IOException {
        Date attrstart = date;
        Date attrend = date;
        String sql = ReadSqlFiles.sqlString("attributeListByDate.sql");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        params.addValue("attrend", attrend);
        params.addValue("attrstart", attrstart);
        List<Attribute> attributes = getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Attribute.class));
        return attributes;
    }


    public List<Node> getCurrentParentsByChildNodeId(String nodeId, String date) throws IOException {
        String sql = ReadSqlFiles.sqlString("currentParentsByChildNodeId.sql");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.EDGE_TYPE_FIELD, Constants.HISTORY_UNIT_TYPE);
        params.addValue(Constants.NODE_ID_FIELD, nodeId);
        params.addValue("dt", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Node.class));
    }

    public List<Node> getHistoryAndCurrentParentsByChildNodeId(String nodeId, String date) throws IOException {
        String sql = ReadSqlFiles.sqlString("historyAndCurrentParentsByChildNodeId.sql");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.EDGE_TYPE_FIELD, Constants.HISTORY_UNIT_TYPE);
        params.addValue(Constants.NODE_ID_FIELD, nodeId);
        params.addValue("dt", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Node.class));
    }

    public List<Node> getFutureAndCurrentParentsByChildNodeId(String nodeId, String date) throws IOException {
        String sql = ReadSqlFiles.sqlString("futureAndCurrentParentsByChildNodeId.sql");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.EDGE_TYPE_FIELD, Constants.HISTORY_UNIT_TYPE);
        params.addValue(Constants.NODE_ID_FIELD, nodeId);
        params.addValue("dt", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Node.class));
    }

    public List<NodeWrapper> getCurrentTypesByChildNodeId(String nodeId, String date) throws IOException {
        String sql = ReadSqlFiles.sqlString("currentTypesByChildNodeId.sql");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.EDGE_TYPE_FIELD, Constants.HISTORY_UNIT_TYPE);
        params.addValue(Constants.NODE_ID_FIELD, nodeId);
        params.addValue("dt", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(NodeWrapper.class));
    }

    public List<NodeWrapper> getHistoryAndCurrentTypesByChildNodeId(String nodeId, String date) throws IOException {
        String sql = ReadSqlFiles.sqlString("historyAndCurrentTypesByChildNodeId.sql");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.EDGE_TYPE_FIELD, Constants.HISTORY_UNIT_TYPE);
        params.addValue(Constants.NODE_ID_FIELD, nodeId);
        params.addValue("dt", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(NodeWrapper.class));
    }

    public List<NodeWrapper> getFutureAndCurrentTypesByChildNodeId(String nodeId, String date) throws IOException {
        String sql = ReadSqlFiles.sqlString("futureAndCurrentTypesByChildNodeId.sql");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.NODE_ID_FIELD, nodeId);
        params.addValue(Constants.EDGE_TYPE_FIELD, Constants.HISTORY_UNIT_TYPE);
        params.addValue("dt", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(NodeWrapper.class));
    }

    public List<Node> getCurrentChildrenByParentNodeId(String nodeId, String date) throws IOException {
        String sql = ReadSqlFiles.sqlString("currentChildrenByParentNodeId.sql");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.EDGE_TYPE_FIELD, Constants.HISTORY_UNIT_TYPE);
        params.addValue(Constants.NODE_ID_FIELD, nodeId);
        params.addValue("dt", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Node.class));
    }

    public List<Node> getHistoryAndCurrentChildrenByParentNodeId(String nodeId, String date) throws IOException {
        String sql = ReadSqlFiles.sqlString("historyAndCurrentChildrenByParentNodeId.sql");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.NODE_ID_FIELD, nodeId);
        params.addValue(Constants.EDGE_TYPE_FIELD, Constants.HISTORY_UNIT_TYPE);
        params.addValue("dt", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Node.class));
    }

    public List<Node> getFutureAndCurrentChildrenByParentNodeId(String nodeId, String date) throws IOException {
        String sql = ReadSqlFiles.sqlString("futureAndCurrentChildrenByParentNodeId.sql");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.NODE_ID_FIELD, nodeId);
        params.addValue(Constants.EDGE_TYPE_FIELD, Constants.HISTORY_UNIT_TYPE);
        params.addValue("dt", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Node.class));
    }

    public List<NodeWrapper> getCurrentTypesByParentNodeId(String nodeId, String date) throws IOException {
        String sql = ReadSqlFiles.sqlString("currentTypesByParentNodeId.sql");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.EDGE_TYPE_FIELD, Constants.HISTORY_UNIT_TYPE);
        params.addValue(Constants.NODE_ID_FIELD, nodeId);
        params.addValue("dt", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(NodeWrapper.class));
    }

    public List<NodeWrapper> getHistoryAndCurrentTypesByParentNodeId(String nodeId, String date) throws IOException {
        String sql = ReadSqlFiles.sqlString("historyAndCurrentTypesByParentNodeId.sql");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.NODE_ID_FIELD, nodeId);
        params.addValue(Constants.EDGE_TYPE_FIELD, Constants.HISTORY_UNIT_TYPE);
        params.addValue("dt", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(NodeWrapper.class));
    }

    public List<NodeWrapper> getFutureAndCurrentTypesByParentNodeId(String nodeId, String date) throws IOException {
        String sql = ReadSqlFiles.sqlString("futureAndCurrentTypesByParentNodeId.sql");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.NODE_ID_FIELD, nodeId);
        params.addValue(Constants.EDGE_TYPE_FIELD, Constants.HISTORY_UNIT_TYPE);
        params.addValue("dt", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(NodeWrapper.class));
    }

    public List<Attribute> getHistoryAndCurrentAttributeListByDate(String id, Date date) throws IOException {
        Date attrstart = date;
        String sql = ReadSqlFiles.sqlString("historyAndCurrentAttributeListByDate.sql");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        params.addValue("attrstart", attrstart);
        List<Attribute> attributes = getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Attribute.class));
        return attributes;
    }

    public List<Attribute> getFutureAndCurrentAttributeListByDate(String id, Date date) throws IOException {
        Date attrstart = date;
        String sql = ReadSqlFiles.sqlString("futureAndCurrentAttributeListByDate.sql");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        params.addValue("attrstart", attrstart);
        List<Attribute> attributes = getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Attribute.class));
        return attributes;
    }

    public List<SteeringGroup> getSteeringGroups() throws IOException {
        String sql = ReadSqlFiles.sqlString("steeringGroups.sql");

        List<SteeringGroup> queryResults = getNamedParameterJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(SteeringGroup.class));
        return queryResults;
    }

    public List<DegreeProgrammeDTO> getDegreeProgrammesAndAttributes() throws IOException {
        String sql = ReadSqlFiles.sqlString("degreeProgrammesAndAttributes.sql");

        Timestamp ts = Timestamp.from(Instant.now());
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("today", ts);

        List<Map<String, Object>> rows = getNamedParameterJdbcTemplate().queryForList(sql, params);
        List<DegreeProgrammeDTO> degreeProgrammes = OrgUnitDbUtil.extractDegreeProgrammeDTOs(rows);
        return degreeProgrammes;
    }

    public List<Node> getDegreeProgrammes(Integer uniqueId) throws IOException {
        Node node = getNodeByUniqueId(uniqueId);

        String sql = ReadSqlFiles.sqlString("degreeProgramme.sql");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.NODE_ID_FIELD, node.getId());
        params.addValue("today", Timestamp.from(Instant.now()));
        return getNamedParameterJdbcTemplate().query(sql, params , BeanPropertyRowMapper.newInstance(Node.class));
    }

    public List<SteeringGroup> getHumanResources() throws IOException {
        String sql = ReadSqlFiles.sqlString("humanResources.sql");
        List<SteeringGroup> queryResults = 
            getNamedParameterJdbcTemplate().query(
                sql, 
                BeanPropertyRowMapper.newInstance(SteeringGroup.class)
        );

        return queryResults;
    }

    /**
     * Used in public orgrek apis. PO wants nodes that can be reached
     * from a specific node (usually HY) in a specific hierarchy. 
     * These nodes must have certain attribute(s) valid. 
     * Node's start and end date are not taken into account, only edges'.
     */
    public List<SteeringGroup> getHierarchyOrgUnits(
        String startNodeId, 
        Date date, 
        String hierarchy, 
        String hierarchySpecificCode
      ) throws IOException {
      String sql = ReadSqlFiles.sqlString("hierarchyOrgUnits.sql");
      SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
      MapSqlParameterSource params = new MapSqlParameterSource();
      params.addValue("start", startNodeId);
      params.addValue("date", df.format(date));
      params.addValue("hierarchy", hierarchy);
      params.addValue("key", hierarchySpecificCode);

      List<SteeringGroup> results = getNamedParameterJdbcTemplate().query(
          sql, params, BeanPropertyRowMapper.newInstance(SteeringGroup.class)
      );
      return results;
    }

    public List<EducationUnit> getEducationUnits() throws IOException {
      String sql = ReadSqlFiles.sqlString("educationUnits.sql");
      return getNamedParameterJdbcTemplate().query(
          sql, BeanPropertyRowMapper.newInstance(EducationUnit.class)
      );
    }

    public List<FullName> getFavorableNames(int uniqueId, String date) throws IOException {
        String sql = ReadSqlFiles.sqlString("favorableFullNames.sql");
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("date", date);
        params.addValue("uniqueId", uniqueId);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(FullName.class));
    }

    public List<FullName> getFullNames(String nodeId, String date)  throws IOException {
        String sql = ReadSqlFiles.sqlString("fullNames.sql");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.NODE_ID_FIELD, nodeId);
        params.addValue("date", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(FullName.class));
    }

    public List<FullName> getAllFullNames(String nodeId)  throws IOException {
        String sql = ReadSqlFiles.sqlString("allFullNames.sql");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.NODE_ID_FIELD, nodeId);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(FullName.class));
    }

    public List<FullName> getHistoryAndCurrentFullNames(String nodeId, String date)  throws IOException {
        String sql = ReadSqlFiles.sqlString("historyAndCurrentFullNames.sql");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.NODE_ID_FIELD, nodeId);
        params.addValue("date", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(FullName.class));
    }

    public List<FullName> getFutureAndCurrentFullNames(String nodeId, String date) throws IOException {
        String sql = ReadSqlFiles.sqlString("futureAndCurrentFullNames.sql");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.NODE_ID_FIELD, nodeId);
        params.addValue("date", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(FullName.class));
    }

    public List<Relative> getChildren(String nodeId, String date) throws IOException {
        String sql = ReadSqlFiles.sqlString("children.sql");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("nodeId", nodeId);
        params.addValue("date", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Relative.class));
    }

    public List<Relative> getParents(String nodeId, String date) throws IOException {
        String sql = ReadSqlFiles.sqlString("parents.sql");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("nodeId", nodeId);
        params.addValue("date", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Relative.class));
    }

    public List<Relative> getCurrentAndFutureParents(String nodeId, String date) throws IOException {
        String sql = ReadSqlFiles.sqlString("currentAndFutureParents.sql");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("nodeId", nodeId);
        params.addValue("date", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Relative.class));
    }

    public List<Relative> getCurrentAndFutureChildren(String nodeId, String date) throws IOException {
        String sql = ReadSqlFiles.sqlString("currentAndFutureChildren.sql");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("nodeId", nodeId);
        params.addValue("date", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Relative.class));
    }

    public List<Relative> getCurrentAndPastParents(String nodeId, String date) throws IOException {
        String sql = ReadSqlFiles.sqlString("currentAndPastParents.sql");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("nodeId", nodeId);
        params.addValue("date", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Relative.class));
    }

    public List<Relative> getCurrentAndPastChildren(String nodeId, String date) throws IOException {
        String sql = ReadSqlFiles.sqlString("currentAndPastChildren.sql");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("nodeId", nodeId);
        params.addValue("date", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Relative.class));
    }

    public List<Relative> getAllChildren(String nodeId, String date) throws IOException {
        String sql = ReadSqlFiles.sqlString("allChildren.sql");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("nodeId", nodeId);
        params.addValue("date", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Relative.class));
    }

    public List<Relative> getAllParents(String nodeId, String date) throws IOException {
        String sql = ReadSqlFiles.sqlString("allParents.sql");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("nodeId", nodeId);
        params.addValue("date", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Relative.class));
    }


    public List<Relative> getPredecessors(String nodeId, String date) throws IOException {
        String sql = ReadSqlFiles.sqlString("predecessors.sql");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("nodeId", nodeId);
        params.addValue("date", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Relative.class));
    }

    public List<Relative> getSuccessors(String nodeId)  throws IOException {
        String sql = ReadSqlFiles.sqlString("successors.sql");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("nodeId", nodeId);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Relative.class));
    }

    public List<TreeNode> getTreeNodes(Set<String> hierarchies, String date) throws IOException {
        String sql = ReadSqlFiles.sqlString("tree.sql");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("hierarchies", hierarchies);
        params.addValue("date", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(TreeNode.class));
    }

    public EdgeWrapper addNewUpperUnit(EdgeWrapper edgeWrapper) throws IOException {
        String sql = ReadSqlFiles.sqlString("addNewUpperUnit.sql");
        String sqlSequence = "select node_seq.nextval from dual";
        int sequence = getJdbcTemplate().queryForObject(sqlSequence, Integer.class);
        edgeWrapper.setId(sequence);
        getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(edgeWrapper));
        return edgeWrapper;
    }


    public Node updateNodeProperties(Node node) throws IOException{
        String sql = ReadSqlFiles.sqlString("updateNodeProperties.sql");
        getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(node));
        return node;
    }

    public NewNodeDTO insertNode(NewNodeDTO newNodeDTO) throws IOException{
        String sql = ReadSqlFiles.sqlString("insertNode.sql");
        String nodeIdSequence = "select NODE_SEQ.nextval from dual";
        String uniqueIdSequence = "select UNIQUE_ID_SEQ.nextval from dual";
        Integer nodeId = getJdbcTemplate().queryForObject(nodeIdSequence, Integer.class);
        Integer uniqueId = getJdbcTemplate().queryForObject(uniqueIdSequence, Integer.class);
        newNodeDTO.setChildNodeId(nodeId.toString());
        newNodeDTO.setChildUniqueId(uniqueId.toString());
        getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(newNodeDTO));
        return newNodeDTO;
    }

    public void updateFullNameView() {
        SimpleJdbcCall spCall = new SimpleJdbcCall(getJdbcTemplate());
        spCall.withProcedureName(Constants.UPDATE_FULL_NAME_VIEW_PROCEDURE_NAME);
        spCall.execute();
    }

}
