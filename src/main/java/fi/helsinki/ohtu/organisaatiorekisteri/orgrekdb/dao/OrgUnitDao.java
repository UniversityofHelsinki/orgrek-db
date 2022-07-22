package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.*;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.Constants;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.OrgUnitDbUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

import static fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.OrgUnitDbUtil.extractSteeringProgrammes;

@Repository(value = "orgUnitDao")
public class OrgUnitDao extends NamedParameterJdbcDaoSupport {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    public Node getNodeByNodeId(String id) {
        String sql = "SELECT * FROM NODE WHERE ID = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        return getNamedParameterJdbcTemplate().queryForObject(sql, params, BeanPropertyRowMapper.newInstance(Node.class));
    }

    public Node getNodeByUniqueId(int id) {
        String sql = "SELECT * FROM NODE WHERE UNIQUE_ID = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        return getNamedParameterJdbcTemplate().queryForObject(sql, params, BeanPropertyRowMapper.newInstance(Node.class));
    }

    public List<Attribute> getAttributeListByDate(String id, Date date) {
        Date attrstart = date;
        Date attrend = date;
        String sql = "SELECT * FROM NODE_ATTR WHERE NODE_ID = :id AND " +
                "(NODE_ATTR.END_DATE IS NULL OR " +
                "(NODE_ATTR.END_DATE >= trunc(:attrend))) AND " +
                "(NODE_ATTR.START_DATE IS NULL OR " +
                "(NODE_ATTR.START_DATE <= trunc(:attrstart)))";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        params.addValue("attrend", attrend);
        params.addValue("attrstart", attrstart);
        List<Attribute> attributes = getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Attribute.class));
        return attributes;
    }


    public List<Node> getCurrentParentsByChildNodeId(String nodeId, String date) {
        String sql = "SELECT * FROM NODE WHERE ID IN " +
                "(SELECT PARENT_NODE_ID FROM EDGE WHERE CHILD_NODE_ID = :nodeId and " +
                "(HIERARCHY is null or HIERARCHY != :edgeType) and " +
                "(END_DATE is null or trunc(END_DATE) >= to_date(:dt,'DD.MM.YYYY')) and " +
                "(START_DATE is null or trunc(START_DATE) <= to_date(:dt, 'DD.MM.YYYY'))) and " +
                "(END_DATE is null or trunc(END_DATE) >= to_date(:dt,'DD.MM.YYYY')) and " +
                "(START_DATE is null or trunc(START_DATE) <= to_date(:dt, 'DD.MM.YYYY'))";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.EDGE_TYPE_FIELD, Constants.HISTORY_UNIT_TYPE);
        params.addValue(Constants.NODE_ID_FIELD, nodeId);
        params.addValue("dt", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Node.class));
    }

    public List<Node> getHistoryAndCurrentParentsByChildNodeId(String nodeId, String date) {
        String sql = "SELECT * FROM NODE WHERE ID IN " +
                "(SELECT PARENT_NODE_ID FROM EDGE WHERE CHILD_NODE_ID = :nodeId and " +
                "(HIERARCHY is null or HIERARCHY != :edgeType) AND " +
                "(START_DATE is null or trunc(START_DATE) <= to_date(:dt, 'DD.MM.YYYY'))) " +
                "AND (START_DATE is null or trunc(START_DATE) <= to_date(:dt, 'DD.MM.YYYY'))";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.EDGE_TYPE_FIELD, Constants.HISTORY_UNIT_TYPE);
        params.addValue(Constants.NODE_ID_FIELD, nodeId);
        params.addValue("dt", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Node.class));
    }

    public List<Node> getFutureAndCurrentParentsByChildNodeId(String nodeId, String date) {
        String sql = "SELECT * FROM NODE WHERE ID IN " +
                "(SELECT PARENT_NODE_ID FROM EDGE WHERE CHILD_NODE_ID = :nodeId and " +
                "(HIERARCHY is null or HIERARCHY != :edgeType) AND " +
                "(END_DATE is null or trunc(END_DATE) >= to_date(:dt, 'DD.MM.YYYY'))) " +
                "AND (END_DATE is null or trunc(END_DATE) >= to_date(:dt, 'DD.MM.YYYY'))";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.EDGE_TYPE_FIELD, Constants.HISTORY_UNIT_TYPE);
        params.addValue(Constants.NODE_ID_FIELD, nodeId);
        params.addValue("dt", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Node.class));
    }

    public List<NodeWrapper> getCurrentTypesByChildNodeId(String nodeId, String date) {
        String sql = "SELECT PARENT_NODE_ID AS NODE_ID, HIERARCHY, START_DATE, END_DATE FROM EDGE WHERE CHILD_NODE_ID = :nodeId and " +
                "(HIERARCHY is null or HIERARCHY != :edgeType) and " +
                "(END_DATE is null or trunc(END_DATE) >= to_date(:dt,'DD.MM.YYYY')) and " +
                "(START_DATE is null or trunc(START_DATE) <= to_date(:dt, 'DD.MM.YYYY'))";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.EDGE_TYPE_FIELD, Constants.HISTORY_UNIT_TYPE);
        params.addValue(Constants.NODE_ID_FIELD, nodeId);
        params.addValue("dt", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(NodeWrapper.class));
    }

    public List<NodeWrapper> getHistoryAndCurrentTypesByChildNodeId(String nodeId, String date) {
        String sql = "SELECT PARENT_NODE_ID AS NODE_ID, HIERARCHY, START_DATE, END_DATE FROM EDGE WHERE CHILD_NODE_ID = :nodeId and " +
                "(HIERARCHY is null or HIERARCHY != :edgeType) AND " +
                "(START_DATE is null or trunc(START_DATE) <= to_date(:dt, 'DD.MM.YYYY'))";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.EDGE_TYPE_FIELD, Constants.HISTORY_UNIT_TYPE);
        params.addValue(Constants.NODE_ID_FIELD, nodeId);
        params.addValue("dt", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(NodeWrapper.class));
    }

    public List<NodeWrapper> getFutureAndCurrentTypesByChildNodeId(String nodeId, String date) {
        String sql = "SELECT PARENT_NODE_ID AS NODE_ID, HIERARCHY, START_DATE, END_DATE FROM EDGE WHERE CHILD_NODE_ID = :nodeId and " +
                "(HIERARCHY is null or HIERARCHY != :edgeType) AND " +
                "(END_DATE is null or trunc(END_DATE) >= to_date(:dt, 'DD.MM.YYYY'))";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.NODE_ID_FIELD, nodeId);
        params.addValue(Constants.EDGE_TYPE_FIELD, Constants.HISTORY_UNIT_TYPE);
        params.addValue("dt", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(NodeWrapper.class));
    }

    public List<Node> getCurrentChildrenByParentNodeId(String nodeId, String date) {
        String sql = "SELECT * FROM NODE WHERE ID IN " +
                "(SELECT CHILD_NODE_ID FROM EDGE WHERE PARENT_NODE_ID = :nodeId and " +
                "(HIERARCHY is null or HIERARCHY != :edgeType) and " +
                "(END_DATE is null or trunc(END_DATE) >= to_date(:dt,'DD.MM.YYYY')) and " +
                "(START_DATE is null or trunc(START_DATE) <= to_date(:dt, 'DD.MM.YYYY'))) and " +
                "(END_DATE is null or trunc(END_DATE) >= to_date(:dt,'DD.MM.YYYY')) and " +
                "(START_DATE is null or trunc(START_DATE) <= to_date(:dt, 'DD.MM.YYYY'))";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.EDGE_TYPE_FIELD, Constants.HISTORY_UNIT_TYPE);
        params.addValue(Constants.NODE_ID_FIELD, nodeId);
        params.addValue("dt", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Node.class));
    }

    public List<Node> getHistoryAndCurrentChildrenByParentNodeId(String nodeId, String date) {
        String sql = "SELECT * FROM NODE WHERE ID IN " +
                "(SELECT CHILD_NODE_ID FROM EDGE WHERE PARENT_NODE_ID = :nodeId and " +
                "(HIERARCHY is null or HIERARCHY != :edgeType) AND " +
                "(START_DATE is null or trunc(START_DATE) <= to_date(:dt, 'DD.MM.YYYY'))) " +
                "AND (START_DATE is null or trunc(START_DATE) <= to_date(:dt, 'DD.MM.YYYY'))";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.NODE_ID_FIELD, nodeId);
        params.addValue(Constants.EDGE_TYPE_FIELD, Constants.HISTORY_UNIT_TYPE);
        params.addValue("dt", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Node.class));
    }

    public List<Node> getFutureAndCurrentChildrenByParentNodeId(String nodeId, String date) {
        String sql = "SELECT * FROM NODE WHERE ID IN " +
                "(SELECT CHILD_NODE_ID FROM EDGE WHERE PARENT_NODE_ID = :nodeId and " +
                "(HIERARCHY is null or HIERARCHY != :edgeType) AND " +
                "(END_DATE is null or trunc(END_DATE) >= to_date(:dt, 'DD.MM.YYYY'))) " +
                "AND (END_DATE is null or trunc(END_DATE) >= to_date(:dt, 'DD.MM.YYYY'))";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.NODE_ID_FIELD, nodeId);
        params.addValue(Constants.EDGE_TYPE_FIELD, Constants.HISTORY_UNIT_TYPE);
        params.addValue("dt", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Node.class));
    }

    public List<NodeWrapper> getCurrentTypesByParentNodeId(String nodeId, String date) {
        String sql = "SELECT CHILD_NODE_ID AS NODE_ID, HIERARCHY, START_DATE, END_DATE FROM EDGE WHERE PARENT_NODE_ID = :nodeId and " +
                "(HIERARCHY is null or HIERARCHY != :edgeType) and " +
                "(END_DATE is null or trunc(END_DATE) >= to_date(:dt,'DD.MM.YYYY')) and " +
                "(START_DATE is null or trunc(START_DATE) <= to_date(:dt, 'DD.MM.YYYY'))";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.EDGE_TYPE_FIELD, Constants.HISTORY_UNIT_TYPE);
        params.addValue(Constants.NODE_ID_FIELD, nodeId);
        params.addValue("dt", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(NodeWrapper.class));
    }

    public List<NodeWrapper> getHistoryAndCurrentTypesByParentNodeId(String nodeId, String date) {
        String sql = "SELECT CHILD_NODE_ID AS NODE_ID, HIERARCHY, START_DATE, END_DATE FROM EDGE WHERE PARENT_NODE_ID = :nodeId and " +
                "(HIERARCHY is null or HIERARCHY != :edgeType) AND " +
                "(START_DATE is null or trunc(START_DATE) <= to_date(:dt, 'DD.MM.YYYY'))";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.NODE_ID_FIELD, nodeId);
        params.addValue(Constants.EDGE_TYPE_FIELD, Constants.HISTORY_UNIT_TYPE);
        params.addValue("dt", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(NodeWrapper.class));
    }

    public List<NodeWrapper> getFutureAndCurrentTypesByParentNodeId(String nodeId, String date) {
        String sql = "SELECT CHILD_NODE_ID AS NODE_ID, HIERARCHY, START_DATE, END_DATE FROM EDGE WHERE PARENT_NODE_ID = :nodeId and " +
                "(HIERARCHY is null or HIERARCHY != :edgeType) AND " +
                "(END_DATE is null or trunc(END_DATE) >= to_date(:dt, 'DD.MM.YYYY'))";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.NODE_ID_FIELD, nodeId);
        params.addValue(Constants.EDGE_TYPE_FIELD, Constants.HISTORY_UNIT_TYPE);
        params.addValue("dt", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(NodeWrapper.class));
    }

    public List<NodeEdgeHistoryWrapper> getPredecessors(String startId) {
        String sql = "SELECT PREDECESSOR_RELATION.PREDECESSOR_ID AS ID, NODE.NAME, NODE.START_DATE, NODE.END_DATE, PREDECESSOR_RELATION.START_DATE AS EDGE_START_DATE, " +
                "PREDECESSOR_RELATION.END_DATE AS EDGE_END_DATE, NODE.UNIQUE_ID " +
                "FROM PREDECESSOR_RELATION " +
                "INNER JOIN NODE " +
                "ON PREDECESSOR_RELATION.PREDECESSOR_ID = NODE.ID " +
                "WHERE PREDECESSOR_RELATION.NODE_ID = :startId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.START_ID_FIELD, startId);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(NodeEdgeHistoryWrapper.class));
    }

    public List<NodeEdgeHistoryWrapper> getSuccessors(String endId) {
        String sql = "SELECT SUCCESSOR_RELATION.SUCCESSOR_ID AS ID, NODE.NAME, NODE.START_DATE, NODE.END_DATE, SUCCESSOR_RELATION.START_DATE AS EDGE_START_DATE, " +
                "SUCCESSOR_RELATION.END_DATE AS EDGE_END_DATE, NODE.UNIQUE_ID " +
                "FROM SUCCESSOR_RELATION " +
                "INNER JOIN NODE " +
                "ON SUCCESSOR_RELATION.SUCCESSOR_ID = NODE.ID " +
                "WHERE NODE_ID = :endId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.END_ID_FIELD, endId);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(NodeEdgeHistoryWrapper.class));
    }

    public List<Attribute> getHistoryAndCurrentAttributeListByDate(String id, Date date) {
        Date attrstart = date;
        String sql = "SELECT * FROM NODE_ATTR WHERE NODE_ID = :id AND " +
                "(NODE_ATTR.START_DATE IS NULL OR " +
                "(NODE_ATTR.START_DATE <= trunc(:attrstart)))";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        params.addValue("attrstart", attrstart);
        List<Attribute> attributes = getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Attribute.class));
        return attributes;
    }

    public List<Attribute> getFutureAndCurrentAttributeListByDate(String id, Date date) {
        Date attrstart = date;
        String sql = "SELECT * FROM NODE_ATTR WHERE NODE_ID = :id AND " +
                "(NODE_ATTR.END_DATE IS NULL OR " +
                "(NODE_ATTR.END_DATE >= trunc(:attrstart)))";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        params.addValue("attrstart", attrstart);
        List<Attribute> attributes = getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Attribute.class));
        return attributes;
    }

    public Map<String, List<SteeringGroup>> getSteeringGroups() {
        String sql = "SELECT NA.NODE_ID, T.KEY, T.VALUE, T.LANGUAGE FROM NODE_ATTR NA " +
                " JOIN NODE N ON NA.NODE_ID = N.ID " +
                " JOIN TEXT T ON NA.VALUE = T.KEY " +
                " WHERE NA.KEY = 'iam-johtoryhma' " +
                " AND (N.END_DATE IS NULL OR N.END_DATE > trunc(:today)) " +
                " AND (N.START_DATE IS NULL OR N.START_DATE <= trunc(:today)) " +
                " AND N.ID IN (SELECT CHILD_NODE_ID FROM EDGE WHERE HIERARCHY='toiminnanohjaus') "+
                " AND (N.END_DATE IS NULL OR N.END_DATE > trunc(:today))" +
                " AND (N.START_DATE IS NULL OR N.START_DATE <= trunc(:today)) "+
                " ORDER BY NA.NODE_ID, T.LANGUAGE";

        Timestamp ts = Timestamp.from(Instant.now());
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("today", ts);

        List<Map<String, Object>> rows = getNamedParameterJdbcTemplate().queryForList(sql, params);
        Map<String, List<SteeringGroup>> groups = extractSteeringProgrammes(rows);
        return groups;
    }


    public List<DegreeProgrammeDTO> getDegreeProgrammesAndAttributes() {
        String sql = "SELECT nat.NODE_ID, nat.KEY, nat.VALUE from NODE_ATTR nat " +
                "WHERE  nat.NODE_ID in (SELECT N.ID FROM NODE N, NODE_ATTR NA  WHERE N.ID=NA.NODE_ID AND NA.KEY = 'iam-johtoryhma' " +
                "AND NA.NODE_ID IN " +
                "(SELECT NODE_ID FROM NODE_TYPE WHERE " +
                "NODE_TYPE.VALUE IN ('kandiohjelma', 'maisteriohjelma', 'tohtoriohjelma')) " +
                "AND N.ID IN (SELECT CHILD_NODE_ID FROM EDGE WHERE HIERARCHY='toiminnanohjaus') " +
                "AND (N.END_DATE IS NULL OR N.END_DATE > trunc(:today)) " +
                "AND (N.START_DATE IS NULL OR N.START_DATE <= trunc(:today))) " +
                "ORDER BY NODE_ID, KEY";

        Timestamp ts = Timestamp.from(Instant.now());
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("today", ts);

        List<Map<String, Object>> rows = getNamedParameterJdbcTemplate().queryForList(sql, params);
        List<DegreeProgrammeDTO> degreeProgrammes = OrgUnitDbUtil.extractDegreeProgrammeDTOs(rows);
        return degreeProgrammes;
    }

    public List<Node> getDegreeProgrammes(Integer uniqueId) {
        Node node = getNodeByUniqueId(uniqueId);
        String sql = "SELECT NODE.* FROM NODE JOIN NODE_TYPE ON NODE.ID = NODE_TYPE.NODE_ID " +
                "WHERE NODE.ID IN " +
                "(SELECT distinct CHILD_NODE_ID " +
                "FROM (SELECT * FROM edge WHERE " +
                "   (END_DATE IS NULL OR END_DATE > trunc(:today))" +
                "   AND (START_DATE IS NULL OR START_DATE <= trunc(:today))" +
                "   AND hierarchy = 'toiminnanohjaus') " +
                "START WITH PARENT_NODE_ID = :nodeId " +
                "CONNECT BY PRIOR CHILD_NODE_ID = PARENT_NODE_ID) " +
                "AND NODE_TYPE.VALUE IN ('kandiohjelma', 'maisteriohjelma', 'tohtoriohjelma')";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.NODE_ID_FIELD, node.getId());
        params.addValue("today", Timestamp.from(Instant.now()));
        return getNamedParameterJdbcTemplate().query(sql, params , BeanPropertyRowMapper.newInstance(Node.class));
    }

    public List<TreeNodeWrapper> getTreeNodes(String hierarchy) {
        String sql = "with nodes as (SELECT distinct CHILD_NODE_ID, PARENT_NODE_ID, LEVEL  FROM edge where " +
                "(END_DATE IS NULL OR END_DATE > trunc(sysdate))" +
                "AND (START_DATE IS NULL OR START_DATE <= trunc(sysdate)) " +
                "and HIERARCHY = 'talous' " +
                "START WITH PARENT_NODE_ID = 'a1' " +
                "CONNECT BY NOCYCLE PRIOR CHILD_NODE_ID = PARENT_NODE_ID and HIERARCHY = 'talous' and " +
                "(END_DATE IS NULL OR END_DATE > trunc(sysdate)) " +
                "AND (START_DATE IS NULL OR START_DATE <= trunc(sysdate)) " +
                "ORDER BY LEVEL) " +
                "select nc.*,n.UNIQUE_ID, n_fi.name name_fi,n_en.name name_en,n_sv.name name_sv from nodes nc, node n, full_name n_fi, full_name n_en, full_name n_sv\n" +
                "where " +
                "nc.child_node_id = n_fi.NODE_ID and nc.CHILD_NODE_ID = n.ID " +
                "and " +
                "(n_fi.END_DATE IS NULL OR n_fi.END_DATE > sysdate) and " +
                "(n_fi.START_DATE IS NULL OR n_fi.START_DATE <= sysdate) " +
                "and n_fi.language='fi' " +
                "and " +
                "nc.child_node_id = n_en.NODE_ID and nc.CHILD_NODE_ID = n.ID " +
                "and " +
                "(n_en.END_DATE IS NULL OR n_en.END_DATE > sysdate) and " +
                "(n_en.START_DATE IS NULL OR n_en.START_DATE <= sysdate) " +
                "and n_en.language='en' " +
                "and " +
                "nc.child_node_id = n_sv.NODE_ID and nc.CHILD_NODE_ID = n.ID " +
                "and" +
                "(n_sv.END_DATE IS NULL OR n_sv.END_DATE > sysdate) and " +
                "(n_sv.START_DATE IS NULL OR n_sv.START_DATE <= sysdate) " +
                "and n_sv.language='sv' " +
                "union " +
                "select n.ID ,null, 0, n.UNIQUE_ID, n_fi.name child_name_fi,n_en.name child_name_en,n_sv.name child_name_sv from node n, full_name n_fi, full_name n_en, full_name n_sv " +
                "where " +
                "'a1' = n_fi.NODE_ID and 'a1' = n.id " +
                "and " +
                "(n_fi.END_DATE IS NULL OR n_fi.END_DATE > sysdate) and " +
                "(n_fi.START_DATE IS NULL OR n_fi.START_DATE <= sysdate) " +
                "and n_fi.language='fi' " +
                "and " +
                "'a1' = n_en.NODE_ID and  'a1' = n.id " +
                "and " +
                "(n_en.END_DATE IS NULL OR n_en.END_DATE > sysdate) and " +
                "(n_en.START_DATE IS NULL OR n_en.START_DATE <= sysdate) " +
                "and n_en.language='en' " +
                "and " +
                "'a1' = n_sv.NODE_ID and 'a1' = n.id " +
                "and " +
                "(n_sv.END_DATE IS NULL OR n_sv.END_DATE > sysdate) and " +
                "(n_sv.START_DATE IS NULL OR n_sv.START_DATE <= sysdate) " +
                "and n_sv.language='sv'";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.NODE_ID_FIELD, "a1");
        params.addValue(Constants.HIERARCHY, hierarchy);
        params.addValue("today", Timestamp.from(Instant.now()));
        return getNamedParameterJdbcTemplate().query(sql, params , BeanPropertyRowMapper.newInstance(TreeNodeWrapper.class));
    }


}

