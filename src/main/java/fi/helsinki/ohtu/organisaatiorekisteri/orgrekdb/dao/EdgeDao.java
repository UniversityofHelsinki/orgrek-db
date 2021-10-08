package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;
import java.util.stream.Collectors;

@Repository(value = "edgeDao")
public class EdgeDao extends NamedParameterJdbcDaoSupport {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    public List<String> getHierarchyTypes() {
        String sql = "SELECT DISTINCT TYPE FROM EDGE";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<String> hierarchies = jdbcTemplate.queryForList(sql, String.class);
        return hierarchies;
    }

}
