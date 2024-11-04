package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.FullName;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.ReadSqlFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository(value = "nameDao")
public class NameDao extends NamedParameterJdbcDaoSupport {
  
  @Autowired
  private DataSource dataSource;

  @PostConstruct
  private void initialize() {
    setDataSource(dataSource);
  }

  public List<FullName> getFullNames(List<String> nodeIds, String date) throws IOException {
    String sql = ReadSqlFiles.sqlString("fullNamesByNodeIds.sql");

    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("nodeIds", nodeIds);
    params.addValue("date", date);
    try {
      return getNamedParameterJdbcTemplate().query(sql, params, BeanPropertyRowMapper.newInstance(FullName.class));
    } catch (EmptyResultDataAccessException e) {
      return new ArrayList<>();
    }
  }


}
