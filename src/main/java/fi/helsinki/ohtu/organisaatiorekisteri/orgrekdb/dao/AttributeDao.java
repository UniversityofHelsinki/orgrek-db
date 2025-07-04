package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Attribute;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.NodeAttributeKeyValueDTO;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.SectionAttribute;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.ReadSqlFiles;

@Repository(value = "attributeDao")
public class AttributeDao extends NamedParameterJdbcDaoSupport {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    private List<Attribute> getAttributeList(String nodeId, String sql, List<String> attributeList) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("node_id", nodeId);
        if (attributeList != null) {
            params.addValue("attributes", attributeList);
        }
        List<Attribute> attributes = getNamedParameterJdbcTemplate()
                .query(sql, params, BeanPropertyRowMapper.newInstance(Attribute.class));
        return attributes;
    }

    public String getAttributeAbbreviationByNodeId(String nodeId) throws IOException {
        String sql = ReadSqlFiles.sqlString("getAbbreviationAttributeByNodeId.sql");
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("node_id", nodeId);
        try {
            Attribute abbreviationAttribute = getNamedParameterJdbcTemplate()
                    .queryForObject(sql, params, BeanPropertyRowMapper.newInstance(Attribute.class));
            return abbreviationAttribute.getValue();
        } catch (EmptyResultDataAccessException notFoundException) {
            return null;
        }
    }

    public List<Attribute> getNameAttributesByNodeId(String nodeId) throws IOException {
        String sql = ReadSqlFiles.sqlString("nameAttributesByNodeId.sql");
        return getAttributeList(nodeId, sql, null);
    }

    public List<Attribute> getTypeAttributesByNodeId(String nodeId) throws IOException {
        String sql = ReadSqlFiles.sqlString("typeAttributesByNodeId.sql");
        return getAttributeList(nodeId, sql, null);
    }

    public List<Attribute> getSectionAttributesBySectionAndNodeId(String section, String nodeId) throws IOException {
        String sql = ReadSqlFiles.sqlString("sectionAttributesBySectionAndNodeId.sql");
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("nodeId", nodeId);
        params.addValue("section", section);
        List<Attribute> attributes = getNamedParameterJdbcTemplate()
                .query(sql, params, BeanPropertyRowMapper.newInstance(Attribute.class));
        return attributes;
    }

    public List<Attribute> getSectionAttributesByNodeId(String nodeId, List<SectionAttribute> sectionAttributes) throws IOException {
        List<String> attributeList = new ArrayList<>();
        sectionAttributes.stream().forEach(sectionAttribute -> attributeList.add(sectionAttribute.getAttr()));
        String sql = ReadSqlFiles.sqlString("sectionAttributesByNodeId.sql");
        return getAttributeList(nodeId, sql, attributeList);
    }

    public Attribute insertAttribute(Attribute attribute) throws IOException {

        String sql = ReadSqlFiles.sqlString("insertAttributes.sql");
        SimpleDateFormat yearMonthDay = new SimpleDateFormat("dd.MM.yyyy");

        MapSqlParameterSource params = new MapSqlParameterSource();
        Integer sequence = getJdbcTemplate().queryForObject("SELECT node_seq.nextval FROM dual", Integer.class);
        params.addValue("id", sequence);
        params.addValue("nodeId", attribute.getNodeId());
        params.addValue("key", attribute.getKey());
        params.addValue("value", attribute.getValue());
        String startDate = yearMonthDay.format(attribute.getStartDate());
        params.addValue("start_date", startDate);
        String endDate = yearMonthDay.format(attribute.getEndDate());
        params.addValue("end_date", endDate);
        getNamedParameterJdbcTemplate().update(sql, params);
        attribute.setId(sequence);
        return attribute;
    }


    private MapSqlParameterSource getMapSqlParameterSource(Attribute attribute, MapSqlParameterSource params) {
        params.addValue("node_id", attribute.getNodeId());
        params.addValue("key", attribute.getKey());
        params.addValue("value", attribute.getValue());
        params.addValue("start_date", attribute.getStartDate());
        params.addValue("end_date", attribute.getEndDate());
        params.addValue("id", attribute.getId());
        return params;
    }

    public int[] addAttributes(List<Attribute> attributes) throws IOException {
        String sql = ReadSqlFiles.sqlString("insertAttributes.sql");
        MapSqlParameterSource[] paramMaps = attributes.stream().map(attribute -> {
            Integer sequence = getJdbcTemplate().queryForObject("SELECT NODE_SEQ.nextval FROM dual", Integer.class);
            attribute.setId(sequence);
            MapSqlParameterSource params = new MapSqlParameterSource();
            return getMapSqlParameterSource(attribute, params);
        }).collect(Collectors.toList()).toArray(new MapSqlParameterSource[]{});
        
        return getNamedParameterJdbcTemplate().batchUpdate(sql, paramMaps);
    }


    public int[] updateAttributes(List<Attribute> attributes) throws IOException {
        String sql = ReadSqlFiles.sqlString("updateAttributes.sql");
        MapSqlParameterSource[] paramMaps = attributes.stream().map(attribute -> {
            MapSqlParameterSource params = new MapSqlParameterSource();
            return getMapSqlParameterSource(attribute, params);
        }).collect(Collectors.toList()).toArray(new MapSqlParameterSource[]{});
        return getNamedParameterJdbcTemplate().batchUpdate(sql, paramMaps);
    }


    public int[] deleteAttributes(List<Attribute> attributes) throws IOException {
        String sql = ReadSqlFiles.sqlString("deleteAttributes.sql");
        MapSqlParameterSource[] paramMaps = attributes.stream().map(attribute -> {
            MapSqlParameterSource params = new MapSqlParameterSource();
            return getMapSqlParameterSource(attribute, params);
        }).collect(Collectors.toList()).toArray(new MapSqlParameterSource[]{});
        return getNamedParameterJdbcTemplate().batchUpdate(sql, paramMaps);
    }

    public Attribute getExistingAttribute(Attribute attribute) throws IOException {
        String sql = ReadSqlFiles.sqlString("isSameAttributeAlreadyExisting.sql");
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("key", attribute.getKey());
        params.addValue("nodeId", attribute.getNodeId());
        params.addValue("value", attribute.getValue());
        fixDates(attribute, params);
        List<Attribute> attributes = getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Attribute.class));
        return (attributes.isEmpty() ? null :  attributes.get(0));

    }

    private Attribute checkIfEditingExistingAttribute(List<Attribute> attributeList, Attribute attribute) {
        boolean found_other_than_under_editing_attribute = false;

        for (Attribute attr: attributeList) {
            if (attr.getId().intValue() == attribute.getId().intValue()) {
                continue;
            }
            if (attr.getId().intValue() != attribute.getId().intValue()) {
                found_other_than_under_editing_attribute = true;
            }
        };

        if (found_other_than_under_editing_attribute) {
            return attribute;
        }
        return null;
    }

    public Attribute checkIfExists(Attribute attribute) throws IOException {
        String sql = ReadSqlFiles.sqlString("isUpdateOK.sql");
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("key", attribute.getKey());
        params.addValue("nodeId", attribute.getNodeId());
        params.addValue("value", attribute.getValue());
        fixDates(attribute, params);
        List<Attribute> attributes = getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Attribute.class));
        return (attributes.isEmpty() ? null : checkIfEditingExistingAttribute(attributes, attribute));
    }

    private static void fixDates(Attribute attribute, MapSqlParameterSource params) {
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, new Locale("fi"));
        if(attribute.getStartDate()==null) params.addValue("startDate", "1.1.0001");
        else params.addValue("startDate", df.format(attribute.getStartDate()));
        if(attribute.getEndDate()==null) params.addValue("endDate", "12.12.9999");
        else params.addValue("endDate", df.format(attribute.getEndDate()));
    }

    public List<NodeAttributeKeyValueDTO> getDistinctNodeAttrs() throws IOException {
        String sql = ReadSqlFiles.sqlString("distinctNodeAttrKeysAndValues.sql");

        List<NodeAttributeKeyValueDTO> query = getNamedParameterJdbcTemplate().query(sql, (rs, rowNum) -> {
            NodeAttributeKeyValueDTO nodeAttributeKeyValueDTO = new NodeAttributeKeyValueDTO();
            nodeAttributeKeyValueDTO.setKey(rs.getString("key"));
            nodeAttributeKeyValueDTO.setValue(rs.getString("value"));
            return nodeAttributeKeyValueDTO;
        });
        return query;
    }

    public Map<String, List<String>> sortableByValueAttributes() throws IOException {
      String sql = ReadSqlFiles.sqlString("sortableByValueAttributes.sql");
      Map<String, List<String>> results = new HashMap<>();
      getNamedParameterJdbcTemplate().query(sql, (rs) -> {
        results.putIfAbsent(rs.getString("key"), new ArrayList<>());
        results.get(rs.getString("key")).add(rs.getString("value"));
      });
      return results;
    }

    public List<Attribute> getAttributes(String nodeId) throws IOException {
        String sql = ReadSqlFiles.sqlString("nodeAttributes.sql");
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", nodeId);
        return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(Attribute.class));
    }
}
