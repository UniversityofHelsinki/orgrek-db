package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.*;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.Constants;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.OrgUnitDbUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    public List<FullName> getFullNames(String nodeId, String date) {
        String sql = "SELECT * FROM FULL_NAME WHERE NODE_ID = :nodeId AND " +
                "(START_DATE IS NULL OR START_DATE <= trunc(to_date(:date, 'DD.MM.YYYY'))) AND" +
                "(END_DATE IS NULL OR END_DATE >= trunc(to_date(:date, 'DD.MM.YYYY')))";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.NODE_ID_FIELD, nodeId);
        params.addValue("date", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(FullName.class));
    }

    public List<FullName> getAllFullNames(String nodeId) {
        String sql = "SELECT * FROM FULL_NAME WHERE NODE_ID = :nodeId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.NODE_ID_FIELD, nodeId);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(FullName.class));
    }

    public List<FullName> getHistoryAndCurrentFullNames(String nodeId, String date) {
        String sql = "SELECT * FROM FULL_NAME WHERE NODE_ID = :nodeId AND " +
                "(START_DATE IS NULL OR START_DATE <= trunc(to_date(:date, 'DD.MM.YYYY')))";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.NODE_ID_FIELD, nodeId);
        params.addValue("date", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(FullName.class));
    }

    public List<FullName> getFutureAndCurrentFullNames(String nodeId, String date) {
        String sql = "SELECT * FROM FULL_NAME WHERE NODE_ID = :nodeId AND " +
                "(END_DATE IS NULL OR END_DATE >= trunc(to_date(:date, 'DD.MM.YYYY')))";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.NODE_ID_FIELD, nodeId);
        params.addValue("date", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(FullName.class));
    }

    public List<Relative> getChildren(String nodeId, String date) {
        String sql = "SELECT N.ID, N.UNIQUE_ID, N.START_DATE, N.END_DATE, E.HIERARCHY, E.START_DATE edge_start_date, E.END_DATE edge_end_date, FN.NAME full_name, FN.LANGUAGE FROM EDGE E " +
        "JOIN NODE N ON E.CHILD_NODE_ID = N.ID " +
        "JOIN FULL_NAME FN ON E.CHILD_NODE_ID = FN.NODE_ID AND E.HIERARCHY != 'history' " +
        "WHERE ((E.START_DATE IS NULL OR E.START_DATE <= trunc(to_date(:date, 'DD.MM.YYYY'))) AND " +
                "(E.END_DATE IS NULL OR E.END_DATE >= trunc(to_date(:date, 'DD.MM.YYYY')))) AND " +
                "((FN.START_DATE IS NULL OR FN.START_DATE <= trunc(to_date(:date, 'DD.MM.YYYY'))) AND " +
                "(FN.END_DATE IS NULL OR FN.END_DATE >= trunc(to_date(:date, 'DD.MM.YYYY')))) AND " +
        "E.PARENT_NODE_ID = :nodeId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("nodeId", nodeId);
        params.addValue("date", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Relative.class));
    }

    public List<Relative> getParents(String nodeId, String date) {
        String sql = "SELECT N.ID, N.UNIQUE_ID, N.START_DATE, N.END_DATE, E.HIERARCHY, E.START_DATE edge_start_date, E.END_DATE edge_end_date, FN.NAME full_name, FN.LANGUAGE FROM EDGE E " +
        "JOIN NODE N ON E.PARENT_NODE_ID = N.ID " +
        "JOIN FULL_NAME FN ON E.PARENT_NODE_ID = FN.NODE_ID AND E.HIERARCHY != 'history' " +
        "WHERE ((E.START_DATE IS NULL OR E.START_DATE <= trunc(to_date(:date, 'DD.MM.YYYY'))) AND " +
        "E.END_DATE IS NULL OR E.END_DATE >= trunc(to_date(:date, 'DD.MM.YYYY'))) AND " +
                "((FN.START_DATE IS NULL OR FN.START_DATE <= trunc(to_date(:date, 'DD.MM.YYYY'))) AND " +
                "(FN.END_DATE IS NULL OR FN.END_DATE >= trunc(to_date(:date, 'DD.MM.YYYY')))) AND " +
        "E.CHILD_NODE_ID = :nodeId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("nodeId", nodeId);
        params.addValue("date", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Relative.class));
    }

    public List<Relative> getCurrentAndFutureParents(String nodeId, String date) {
        String sql = "SELECT N.ID, N.UNIQUE_ID, N.START_DATE, N.END_DATE, E.HIERARCHY, E.START_DATE edge_start_date, E.END_DATE edge_end_date, FN.NAME full_name, FN.LANGUAGE FROM EDGE E " +
                "JOIN NODE N ON E.PARENT_NODE_ID = N.ID " +
                "JOIN FULL_NAME FN ON E.PARENT_NODE_ID = FN.NODE_ID AND E.HIERARCHY != 'history' " +
                "WHERE (E.END_DATE IS NULL OR E.END_DATE >= trunc(to_date(:date, 'DD.MM.YYYY'))) AND " +
                "((FN.END_DATE IS NULL OR FN.END_DATE >= trunc(to_date(:date, 'DD.MM.YYYY'))) AND " +
                "(FN.START_DATE IS NULL OR FN.START_DATE <= trunc(to_date(:date, 'DD.MM.YYYY')))) AND " +
                "E.CHILD_NODE_ID = :nodeId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("nodeId", nodeId);
        params.addValue("date", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Relative.class));
    }

    public List<Relative> getCurrentAndFutureChildren(String nodeId, String date) {
        String sql = "SELECT N.ID, N.UNIQUE_ID, N.START_DATE, N.END_DATE, E.HIERARCHY, E.START_DATE edge_start_date, E.END_DATE edge_end_date, FN.NAME full_name, FN.LANGUAGE FROM EDGE E " +
                "JOIN NODE N ON E.CHILD_NODE_ID = N.ID " +
                "JOIN FULL_NAME FN ON E.CHILD_NODE_ID = FN.NODE_ID AND E.HIERARCHY != 'history' " +
                "WHERE (E.END_DATE IS NULL OR E.END_DATE >= trunc(to_date(:date, 'DD.MM.YYYY'))) AND " +
                "((FN.END_DATE IS NULL OR FN.END_DATE >= trunc(to_date(:date, 'DD.MM.YYYY'))) AND" +
                "(FN.START_DATE IS NULL OR FN.START_DATE <= trunc(to_date(:date, 'DD.MM.YYYY')))) AND " +
                "E.PARENT_NODE_ID = :nodeId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("nodeId", nodeId);
        params.addValue("date", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Relative.class));
    }

    public List<Relative> getCurrentAndPastParents(String nodeId, String date) {
        String sql = "SELECT N.ID, N.UNIQUE_ID, N.START_DATE, N.END_DATE, E.HIERARCHY, E.START_DATE edge_start_date, E.END_DATE edge_end_date, FN.NAME full_name, FN.LANGUAGE FROM EDGE E " +
                "JOIN NODE N ON E.PARENT_NODE_ID = N.ID " +
                "JOIN FULL_NAME FN ON E.PARENT_NODE_ID = FN.NODE_ID AND E.HIERARCHY != 'history' " +
                "WHERE (E.START_DATE IS NULL OR E.START_DATE <= trunc(to_date(:date, 'DD.MM.YYYY'))) AND " +
                "((FN.START_DATE IS NULL OR FN.START_DATE <= trunc(to_date(:date, 'DD.MM.YYYY'))) AND " +
                "(FN.END_DATE IS NULL OR FN.END_DATE >= trunc(to_date(:date, 'DD.MM.YYYY')))) AND " +
                "E.CHILD_NODE_ID = :nodeId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("nodeId", nodeId);
        params.addValue("date", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Relative.class));
    }

    public List<Relative> getCurrentAndPastChildren(String nodeId, String date) {
        String sql = "SELECT N.ID, N.UNIQUE_ID, N.START_DATE, N.END_DATE, E.HIERARCHY, E.START_DATE edge_start_date, E.END_DATE edge_end_date, FN.NAME full_name, FN.LANGUAGE FROM EDGE E " +
                "JOIN NODE N ON E.CHILD_NODE_ID = N.ID " +
                "JOIN FULL_NAME FN ON E.CHILD_NODE_ID = FN.NODE_ID AND E.HIERARCHY != 'history' " +
                "WHERE (E.START_DATE IS NULL OR E.START_DATE <= trunc(to_date(:date, 'DD.MM.YYYY'))) AND " +
                "((FN.START_DATE IS NULL OR FN.START_DATE <= trunc(to_date(:date, 'DD.MM.YYYY'))) AND " +
                "(FN.END_DATE IS NULL OR FN.END_DATE >= trunc(to_date(:date, 'DD.MM.YYYY')))) AND " +
                "E.PARENT_NODE_ID = :nodeId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("nodeId", nodeId);
        params.addValue("date", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Relative.class));
    }

    public List<Relative> getAllChildren(String nodeId, String date) {
        String sql = "SELECT N.ID, N.UNIQUE_ID, N.START_DATE, N.END_DATE, E.HIERARCHY, E.START_DATE edge_start_date, E.END_DATE edge_end_date, FN.NAME full_name, FN.LANGUAGE FROM EDGE E " +
                "JOIN NODE N ON E.CHILD_NODE_ID = N.ID " +
                "JOIN FULL_NAME FN ON E.CHILD_NODE_ID = FN.NODE_ID AND E.HIERARCHY != 'history' " +
                "WHERE ((FN.START_DATE IS NULL OR FN.START_DATE <= trunc(to_date(:date, 'DD.MM.YYYY'))) AND " +
                "(FN.END_DATE IS NULL OR FN.END_DATE >= trunc(to_date(:date, 'DD.MM.YYYY')))) AND " +
                "E.PARENT_NODE_ID = :nodeId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("nodeId", nodeId);
        params.addValue("date", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Relative.class));
    }

    public List<Relative> getAllParents(String nodeId, String date) {
        String sql = "SELECT N.ID, N.UNIQUE_ID, N.START_DATE, N.END_DATE, E.HIERARCHY, E.START_DATE edge_start_date, E.END_DATE edge_end_date, FN.NAME full_name, FN.LANGUAGE FROM EDGE E " +
                "JOIN NODE N ON E.PARENT_NODE_ID = N.ID " +
                "JOIN FULL_NAME FN ON E.PARENT_NODE_ID = FN.NODE_ID AND E.HIERARCHY != 'history' " +
                "WHERE ((FN.START_DATE IS NULL OR FN.START_DATE <= trunc(to_date(:date, 'DD.MM.YYYY'))) AND " +
                "(FN.END_DATE IS NULL OR FN.END_DATE >= trunc(to_date(:date, 'DD.MM.YYYY')))) AND " +
                "E.CHILD_NODE_ID = :nodeId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("nodeId", nodeId);
        params.addValue("date", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Relative.class));
    }

    public List<Relative> getPredecessors1(String nodeId, String date) {
        String sql = "SELECT PN.ID, PN.UNIQUE_ID, PN.START_DATE, PN.END_DATE, P.START_DATE edge_start_date, P.END_DATE edge_end_date, FN.NAME full_name, FN.LANGUAGE FROM PREDECESSOR_RELATION P " +
        "JOIN NODE N ON P.NODE_ID = N.ID " +
        "JOIN NODE PN ON P.PREDECESSOR_ID = PN.ID " +
        "JOIN FULL_NAME FN on P.PREDECESSOR_ID = FN.NODE_ID " +
        "AND (((PN.START_DATE IS NULL OR PN.START_DATE <= trunc(to_date(:date, 'DD.MM.YYYY'))) AND " +
                "(PN.END_DATE IS NULL OR PN.END_DATE >= trunc(to_date(:date, 'DD.MM.YYYY'))) AND " +
                "((FN.START_DATE IS NULL OR FN.START_DATE <= trunc(to_date(:date, 'DD.MM.YYYY'))) " +
        "AND (FN.END_DATE IS NULL OR FN.END_DATE >= trunc(to_date(:date, 'DD.MM.YYYY'))))) " +
        "OR (PN.END_DATE < trunc(to_date(:date, 'DD.MM.YYYY')) AND " +
                        "(FN.START_DATE IS NULL OR FN.START_DATE <= PN.END_DATE) " +
                "AND (FN.END_DATE IS NULL OR FN.END_DATE >= PN.END_DATE))) " +
        "where P.NODE_ID = :nodeId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("nodeId", nodeId);
        params.addValue("date", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Relative.class));
    }

    public List<Relative> getSuccessors1(String nodeId, String date) {
        String sql = "SELECT SN.ID, SN.UNIQUE_ID, SN.START_DATE, SN.END_DATE, S.START_DATE edge_start_date, S.END_DATE edge_end_date, FN.NAME full_name, FN.LANGUAGE FROM SUCCESSOR_RELATION S " +
        "JOIN NODE N ON S.NODE_ID = N.ID " +
        "JOIN NODE SN ON S.SUCCESSOR_ID = SN.ID " +
        "JOIN FULL_NAME FN ON S.SUCCESSOR_ID = FN.NODE_ID " +
        "AND (((SN.START_DATE IS NULL OR SN.START_DATE <= trunc(to_date(:date, 'DD.MM.YYYY'))) AND " +
                "(SN.END_DATE IS NULL OR SN.END_DATE >= trunc(to_date(:date, 'DD.MM.YYYY'))) AND " +
                "((FN.START_DATE IS NULL OR FN.START_DATE <= trunc(to_date(:date, 'DD.MM.YYYY'))) " +
        "AND (FN.END_DATE IS NULL OR FN.END_DATE >= trunc(to_date(:date, 'DD.MM.YYYY'))))) " +
        "OR ((SN.START_DATE > trunc(to_date(:date, 'DD.MM.YYYY'))) AND " +
                "((FN.START_DATE IS NULL OR FN.START_DATE <= SN.START_DATE)) " +
        "AND (FN.END_DATE IS NULL OR FN.END_DATE >= SN.START_DATE))) " +
        "WHERE S.NODE_ID = :nodeId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("nodeId", nodeId);
        params.addValue("date", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Relative.class));
    }
    public List<TreeNode> getTreeNodes(String start, Set<String> hierarchies, String date) throws IOException {
        ClassPathResource resource = new ClassPathResource("sql/tree.sql");
        BufferedReader reader = new BufferedReader
                (new InputStreamReader(resource.getInputStream()));
        String sql = reader.lines().map(line -> line + "\n").collect(Collectors.joining());

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("start", start);
        params.addValue("hierarchies", hierarchies);
        params.addValue("date", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(TreeNode.class));
    }


}