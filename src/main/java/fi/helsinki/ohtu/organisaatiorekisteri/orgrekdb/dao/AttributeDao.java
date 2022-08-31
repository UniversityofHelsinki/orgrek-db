package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Attribute;
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
import java.util.List;
import java.util.stream.Collectors;

@Repository(value = "attributeDao")
public class AttributeDao extends NamedParameterJdbcDaoSupport {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    public List<Attribute> getAttributesByNodeId(String nodeId) throws IOException {
        String sql = ReadSqlFiles.sqlString("getAttributesByNodeId.sql");
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("node_id", nodeId);
        System.out.println(sql + nodeId);
        List<Attribute> attributes = getNamedParameterJdbcTemplate()
                .query(sql, params, BeanPropertyRowMapper.newInstance(Attribute.class));
        return attributes;
    }


    public int[] insertAttributes(List<Attribute> attributes) throws IOException {

        String sql = ReadSqlFiles.sqlString("insertAttributes.sql");

        MapSqlParameterSource[] paramMaps = attributes.stream().map(attribute -> {
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("id", attribute.getId());
            params.addValue("node_id", attribute.getNodeId());
            params.addValue("key", attribute.getKey());
            params.addValue("value", attribute.getValue());
            params.addValue("start_date", attribute.getStartDate());
            params.addValue("end_date", attribute.getEndDate());
            return params;
        }).collect(Collectors.toList()).toArray(new MapSqlParameterSource[]{});
        int[] result = getNamedParameterJdbcTemplate().batchUpdate(sql, paramMaps);
        return result;
    }

    public int[] updateAttributes(List<Attribute> attributes) throws IOException {
        String sql = ReadSqlFiles.sqlString("updateAttributes.sql");

        MapSqlParameterSource[] paramMaps = attributes.stream().map(attribute -> {
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("node_id", attribute.getNodeId());
            params.addValue("key", attribute.getKey());
            params.addValue("value", attribute.getValue());
            params.addValue("start_date", attribute.getStartDate());
            params.addValue("end_date", attribute.getEndDate());
            params.addValue("id", attribute.getId());
            return params;
        }).collect(Collectors.toList()).toArray(new MapSqlParameterSource[]{});
        int[] result = getNamedParameterJdbcTemplate().batchUpdate(sql, paramMaps);
        return result;

    }
}
