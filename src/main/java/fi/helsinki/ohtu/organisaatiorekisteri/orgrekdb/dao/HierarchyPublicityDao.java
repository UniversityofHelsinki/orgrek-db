package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.HierarchyPublicity;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Node;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.ReadSqlFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@Repository(value = "hierarchyPublicityDao")
public class HierarchyPublicityDao  extends NamedParameterJdbcDaoSupport {
    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }


    public List<HierarchyPublicity> getHierarchiesWithPublicityInformation() throws IOException {
        String sql = ReadSqlFiles.sqlString("getHierarchiesWithPublicityInformation.sql");
        List<HierarchyPublicity> hierarchiesWithPublicityInformation = getNamedParameterJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(HierarchyPublicity.class));
        return hierarchiesWithPublicityInformation;
    }

    public HierarchyPublicity getHierarchyPublicityById(int id) throws IOException  {
        String sql = ReadSqlFiles.sqlString("getHierarchyPublicityById.sql");
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        return getNamedParameterJdbcTemplate().queryForObject(sql, params, BeanPropertyRowMapper.newInstance(HierarchyPublicity.class));
    }

    public HierarchyPublicity updateHierarchyPublicity(HierarchyPublicity hierarchyPublicity) throws IOException {
        String sql = ReadSqlFiles.sqlString("updateHierarchyPublicity.sql");
        getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(hierarchyPublicity));
        return hierarchyPublicity;
    }
}
