package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.SectionAttribute;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.SectionDecoratedAttribute;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.ReadSqlFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository(value = "sectionDao")
public class SectionDao extends NamedParameterJdbcDaoSupport {
    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    public List<SectionAttribute> getAllSectionAttributes() throws IOException {
        String sql = ReadSqlFiles.sqlString("allSectionAttributes.sql");
        List<SectionAttribute> sectionAttributes = getNamedParameterJdbcTemplate()
                .query(sql, BeanPropertyRowMapper.newInstance(SectionAttribute.class));
        return sectionAttributes;
    }

    public List<String> getAllDistinctSectionAttributes() throws IOException {
        String sql = ReadSqlFiles.sqlString("allDistinctSectionAttributes.sql");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<String> sectionAttributes = jdbcTemplate.queryForList(sql, String.class);
        return sectionAttributes;
    }

    public List<SectionAttribute> getSectionAttributesBySection(String sectionType) throws IOException {
        String sql = ReadSqlFiles.sqlString("sectionAttributes.sql");
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("section", sectionType);
        List<SectionAttribute> sectionAttributes = getNamedParameterJdbcTemplate()
                .query(sql, params, BeanPropertyRowMapper.newInstance(SectionAttribute.class));
        return sectionAttributes;
    }

    public int updateSectionAttribute(SectionAttribute sectionAttribute) throws IOException{
        String sql = ReadSqlFiles.sqlString("updateSectionAttribute.sql");
        return getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(sectionAttribute));
    }

    public SectionAttribute insertSectionAttribute(SectionAttribute sectionAttribute) throws IOException {
        String sql = ReadSqlFiles.sqlString("insertSectionAttribute.sql");
        String sqlSequence = "SELECT NODE_SEQ.nextval FROM DUAL";
        int sequence = getJdbcTemplate().queryForObject(sqlSequence, Integer.class);
        sectionAttribute.setId(sequence);
        sectionAttribute.setStartDate(new Date());
        sectionAttribute.setEndDate(null);
        getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(sectionAttribute));
        return sectionAttribute;
    }

    public int deleteSectionAttribute(int sectionId) throws IOException {
        String sql = ReadSqlFiles.sqlString("deleteSectionAttribute.sql");
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("sectionId", sectionId);
        return getNamedParameterJdbcTemplate().update(sql, params);
    }

    public SectionAttribute getSectionAttributeById(int id) throws IOException {
        String sql = ReadSqlFiles.sqlString("getSectionAttributeById.sql");
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        SectionAttribute sectionAttribute = getNamedParameterJdbcTemplate()
                .queryForObject(sql, params,  BeanPropertyRowMapper.newInstance(SectionAttribute.class));
        return sectionAttribute;
    }

    public List<SectionDecoratedAttribute> getAttributesBySection(String id, List<String> hierarchies) throws IOException {
        String sql = ReadSqlFiles.sqlString("attributesBySection.sql");
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        params.addValue("hierarchies", hierarchies);
        List<SectionDecoratedAttribute> results = getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(SectionDecoratedAttribute.class));
        return results;
    }

    public List<SectionAttribute> getSections() throws IOException {
        String sql = ReadSqlFiles.sqlString("sections.sql");
        return getNamedParameterJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(SectionAttribute.class));
    }
}
