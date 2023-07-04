package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.HierarchyPublicity;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.ReadSqlFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
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
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = ReadSqlFiles.sqlString("getHierarchiesWithPublicityInformation.sql");
        List<HierarchyPublicity> hierarchiesWithPublicityInformation = getNamedParameterJdbcTemplate().query(sql, BeanPropertyRowMapper.newInstance(HierarchyPublicity.class));
        return hierarchiesWithPublicityInformation;
    }
}
