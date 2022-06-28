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
                "(TYPE is null or TYPE != :edgeType) and " +
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
                "(TYPE is null or TYPE != :edgeType) AND " +
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
                "(TYPE is null or TYPE != :edgeType) AND " +
                "(END_DATE is null or trunc(END_DATE) >= to_date(:dt, 'DD.MM.YYYY'))) " +
                "AND (END_DATE is null or trunc(END_DATE) >= to_date(:dt, 'DD.MM.YYYY'))";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.EDGE_TYPE_FIELD, Constants.HISTORY_UNIT_TYPE);
        params.addValue(Constants.NODE_ID_FIELD, nodeId);
        params.addValue("dt", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Node.class));
    }

    public List<NodeWrapper> getCurrentTypesByChildNodeId(String nodeId, String date) {
        String sql = "SELECT PARENT_NODE_ID AS NODE_ID, TYPE, START_DATE, END_DATE FROM EDGE WHERE CHILD_NODE_ID = :nodeId and " +
                "(TYPE is null or TYPE != :edgeType) and " +
                "(END_DATE is null or trunc(END_DATE) >= to_date(:dt,'DD.MM.YYYY')) and " +
                "(START_DATE is null or trunc(START_DATE) <= to_date(:dt, 'DD.MM.YYYY'))";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.EDGE_TYPE_FIELD, Constants.HISTORY_UNIT_TYPE);
        params.addValue(Constants.NODE_ID_FIELD, nodeId);
        params.addValue("dt", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(NodeWrapper.class));
    }

    public List<NodeWrapper> getHistoryAndCurrentTypesByChildNodeId(String nodeId, String date) {
        String sql = "SELECT PARENT_NODE_ID AS NODE_ID, TYPE, START_DATE, END_DATE FROM EDGE WHERE CHILD_NODE_ID = :nodeId and " +
                "(TYPE is null or TYPE != :edgeType) AND " +
                "(START_DATE is null or trunc(START_DATE) <= to_date(:dt, 'DD.MM.YYYY'))";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.EDGE_TYPE_FIELD, Constants.HISTORY_UNIT_TYPE);
        params.addValue(Constants.NODE_ID_FIELD, nodeId);
        params.addValue("dt", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(NodeWrapper.class));
    }

    public List<NodeWrapper> getFutureAndCurrentTypesByChildNodeId(String nodeId, String date) {
        String sql = "SELECT PARENT_NODE_ID AS NODE_ID, TYPE, START_DATE, END_DATE FROM EDGE WHERE CHILD_NODE_ID = :nodeId and " +
                "(TYPE is null or TYPE != :edgeType) AND " +
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
                "(TYPE is null or TYPE != :edgeType) and " +
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
                "(TYPE is null or TYPE != :edgeType) AND " +
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
                "(TYPE is null or TYPE != :edgeType) AND " +
                "(END_DATE is null or trunc(END_DATE) >= to_date(:dt, 'DD.MM.YYYY'))) " +
                "AND (END_DATE is null or trunc(END_DATE) >= to_date(:dt, 'DD.MM.YYYY'))";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.NODE_ID_FIELD, nodeId);
        params.addValue(Constants.EDGE_TYPE_FIELD, Constants.HISTORY_UNIT_TYPE);
        params.addValue("dt", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Node.class));
    }

    public List<NodeWrapper> getCurrentTypesByParentNodeId(String nodeId, String date) {
        String sql = "SELECT CHILD_NODE_ID AS NODE_ID, TYPE, START_DATE, END_DATE FROM EDGE WHERE PARENT_NODE_ID = :nodeId and " +
                "(TYPE is null or TYPE != :edgeType) and " +
                "(END_DATE is null or trunc(END_DATE) >= to_date(:dt,'DD.MM.YYYY')) and " +
                "(START_DATE is null or trunc(START_DATE) <= to_date(:dt, 'DD.MM.YYYY'))";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.EDGE_TYPE_FIELD, Constants.HISTORY_UNIT_TYPE);
        params.addValue(Constants.NODE_ID_FIELD, nodeId);
        params.addValue("dt", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(NodeWrapper.class));
    }

    public List<NodeWrapper> getHistoryAndCurrentTypesByParentNodeId(String nodeId, String date) {
        String sql = "SELECT CHILD_NODE_ID AS NODE_ID, TYPE, START_DATE, END_DATE FROM EDGE WHERE PARENT_NODE_ID = :nodeId and " +
                "(TYPE is null or TYPE != :edgeType) AND " +
                "(START_DATE is null or trunc(START_DATE) <= to_date(:dt, 'DD.MM.YYYY'))";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.NODE_ID_FIELD, nodeId);
        params.addValue(Constants.EDGE_TYPE_FIELD, Constants.HISTORY_UNIT_TYPE);
        params.addValue("dt", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(NodeWrapper.class));
    }

    public List<NodeWrapper> getFutureAndCurrentTypesByParentNodeId(String nodeId, String date) {
        String sql = "SELECT CHILD_NODE_ID AS NODE_ID, TYPE, START_DATE, END_DATE FROM EDGE WHERE PARENT_NODE_ID = :nodeId and " +
                "(TYPE is null or TYPE != :edgeType) AND " +
                "(END_DATE is null or trunc(END_DATE) >= to_date(:dt, 'DD.MM.YYYY'))";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.NODE_ID_FIELD, nodeId);
        params.addValue(Constants.EDGE_TYPE_FIELD, Constants.HISTORY_UNIT_TYPE);
        params.addValue("dt", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(NodeWrapper.class));
    }

    public List<NodeEdgeHistoryWrapper> getPredecessors(String startId, String edgeType) {
        String sql = "SELECT EDGE.CHILD_NODE_ID AS ID, NODE.NAME, NODE.START_DATE, NODE.END_DATE, EDGE.START_DATE AS EDGE_START_DATE, " +
                "EDGE.END_DATE AS EDGE_END_DATE, NODE.UNIQUE_ID " +
                "FROM EDGE " +
                "INNER JOIN NODE " +
                "ON EDGE.CHILD_NODE_ID = NODE.ID " +
                "WHERE PARENT_NODE_ID = :startId AND TYPE = :edgeType";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.START_ID_FIELD, startId);
        params.addValue(Constants.EDGE_TYPE_FIELD, edgeType);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(NodeEdgeHistoryWrapper.class));
    }

    public List<NodeEdgeHistoryWrapper> getSuccessors(String endId, String edgeType) {
        String sql = "SELECT EDGE.PARENT_NODE_ID AS ID, NODE.NAME, NODE.START_DATE, NODE.END_DATE, EDGE.START_DATE AS EDGE_START_DATE, " +
                "EDGE.END_DATE AS EDGE_END_DATE, NODE.UNIQUE_ID " +
                "FROM EDGE " +
                "INNER JOIN NODE " +
                "ON EDGE.PARENT_NODE_ID = NODE.ID " +
                "WHERE CHILD_NODE_ID = :endId AND TYPE = :edgeType";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.END_ID_FIELD, endId);
        params.addValue(Constants.EDGE_TYPE_FIELD, edgeType);
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
                " AND N.ID IN (SELECT CHILD_NODE_ID FROM EDGE WHERE TYPE='toiminnanohjaus' " +
                " AND (END_DATE IS NULL OR END_DATE > trunc(:today))" +
                " AND (START_DATE IS NULL OR START_DATE <= trunc(:today))) "+
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
                "(SELECT NODE_ID FROM NODE_ATTR WHERE NODE_ATTR.KEY='type' " +
                "AND NODE_ATTR.VALUE IN ('kandiohjelma', 'maisteriohjelma', 'tohtoriohjelma')) " +
                "AND N.ID IN (SELECT CHILD_NODE_ID FROM EDGE WHERE TYPE='toiminnanohjaus' " +
                "AND (END_DATE IS NULL OR END_DATE > trunc(:today)) " +
                "AND (START_DATE IS NULL OR START_DATE <= trunc(:today))) " +
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

}

