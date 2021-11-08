package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Attribute;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Node;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.NodeEdgeHistoryWrapper;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.NodeWrapper;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

@Repository(value = "orgUnitDao")
public class OrgUnitDao extends NamedParameterJdbcDaoSupport {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    public Node getNodeByUniqueId(int id) {
        String sql = "SELECT * FROM NODE WHERE UNIQUE_ID = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        return getNamedParameterJdbcTemplate().queryForObject(sql, params, BeanPropertyRowMapper.newInstance(Node.class));
    }

    public List<Attribute> getCurrentAttributeList(String id, Date date) {
        String sql = "SELECT * FROM NODE_ATTR WHERE NODE_ID = :id AND " +
                "(NODE_ATTR.END_DATE IS NULL OR " +
                "(NODE_ATTR.END_DATE >= trunc(:date)))";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        params.addValue("date", date);
        List<Attribute> attributes = getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Attribute.class));
        return attributes;
    }


    public List<Node> getCurrentParentsByChildNodeId(String nodeId, String date) {
        String sql = "SELECT * FROM NODE WHERE ID IN " +
                "(SELECT PARENT_NODE_ID FROM EDGE WHERE CHILD_NODE_ID = :nodeId and " +
                "(TYPE is null or TYPE != :edgeType) and " +
                "(END_DATE is null or trunc(END_DATE) >= to_date(:dt,'DD.MM.YYYY')))";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.EDGE_TYPE_FIELD, Constants.HISTORY_UNIT_TYPE);
        params.addValue(Constants.NODE_ID_FIELD, nodeId);
        params.addValue("dt", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Node.class));
    }

    public List<NodeWrapper> getCurrentTypesByChildNodeId(String nodeId, String date) {
        String sql = "SELECT PARENT_NODE_ID AS NODE_ID, TYPE FROM EDGE WHERE CHILD_NODE_ID = :nodeId and " +
                "(TYPE is null or TYPE != :edgeType) and " +
                "(END_DATE is null or trunc(END_DATE) >= to_date(:dt,'DD.MM.YYYY'))";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.EDGE_TYPE_FIELD, Constants.HISTORY_UNIT_TYPE);
        params.addValue(Constants.NODE_ID_FIELD, nodeId);
        params.addValue("dt", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(NodeWrapper.class));
    }

    public List<Node> getCurrentChildrenByParentNodeId(String nodeId, String date) {
        String sql = "SELECT * FROM NODE WHERE ID IN " +
                "(SELECT CHILD_NODE_ID FROM EDGE WHERE PARENT_NODE_ID = :nodeId and " +
                "(TYPE is null or TYPE != :edgeType) and " +
                "(END_DATE is null or trunc(END_DATE) >= to_date(:dt,'DD.MM.YYYY')))";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.EDGE_TYPE_FIELD, Constants.HISTORY_UNIT_TYPE);
        params.addValue(Constants.NODE_ID_FIELD, nodeId);
        params.addValue("dt", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Node.class));
    }

    public List<NodeWrapper> getCurrentTypesByParentNodeId(String nodeId, String date) {
        String sql = "SELECT CHILD_NODE_ID AS NODE_ID, TYPE FROM EDGE WHERE PARENT_NODE_ID = :nodeId and " +
                "(TYPE is null or TYPE != :edgeType) and " +
                "(END_DATE is null or trunc(END_DATE) >= to_date(:dt,'DD.MM.YYYY'))";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.EDGE_TYPE_FIELD, Constants.HISTORY_UNIT_TYPE);
        params.addValue(Constants.NODE_ID_FIELD, nodeId);
        params.addValue("dt", date);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(NodeWrapper.class));
    }

    public List<NodeEdgeHistoryWrapper> getPredecessors(String startId, String edgeType) {
        String sql = "SELECT EDGE.CHILD_NODE_ID AS NODE_ID, NODE.NAME, NODE.START_DATE, NODE.END_DATE, EDGE.START_DATE AS EDGE_START_DATE, EDGE.END_DATE AS EDGE_END_DATE " +
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
        String sql = "SELECT EDGE.PARENT_NODE_ID AS NODE_ID, NODE.NAME, NODE.START_DATE, NODE.END_DATE, EDGE.START_DATE AS EDGE_START_DATE, EDGE.END_DATE AS EDGE_END_DATE " +
                "FROM EDGE " +
                "INNER JOIN NODE " +
                "ON EDGE.PARENT_NODE_ID = NODE.ID " +
                "WHERE CHILD_NODE_ID = :endId AND TYPE = :edgeType";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Constants.END_ID_FIELD, endId);
        params.addValue(Constants.EDGE_TYPE_FIELD, edgeType);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(NodeEdgeHistoryWrapper.class));
    }

}

