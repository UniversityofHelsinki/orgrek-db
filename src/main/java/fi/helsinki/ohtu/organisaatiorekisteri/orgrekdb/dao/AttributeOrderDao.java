package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.AttributeOrder;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.ReadSqlFiles;

@Repository
public class AttributeOrderDao extends NamedParameterJdbcDaoSupport {
    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    public List<AttributeOrder> getAll() throws IOException {
      String sql = ReadSqlFiles.sqlString("attributeOrders.sql");
      List<AttributeOrder> orders 
        = getNamedParameterJdbcTemplate().query(
            sql, 
            BeanPropertyRowMapper.newInstance(AttributeOrder.class)
        );
      return orders;
    }

    public Map<String, Map<String, Integer>> getAllByKeyAndValue() throws IOException {
      List<AttributeOrder> orders = getAll();
      Map<String, Map<String, Integer>> results = new HashMap<>();
      for (AttributeOrder order : orders) {
        results.putIfAbsent(order.getKey(), new HashMap<>());
        results.get(order.getKey()).putIfAbsent(order.getValue(), order.getOrder());
      }
      return results;
    }

    public AttributeOrder insert(AttributeOrder attributeOrder) throws IOException {
      String sql = ReadSqlFiles.sqlString("attributeOrders-insert.sql");
      getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(attributeOrder));
      return attributeOrder;
    }
    
    public AttributeOrder delete(AttributeOrder attributeOrder) throws IOException {
      String sql = ReadSqlFiles.sqlString("attributeOrders-delete.sql");
      getNamedParameterJdbcTemplate().update(
          sql, 
          new BeanPropertySqlParameterSource(attributeOrder)
      );
      return attributeOrder;
    }

    public void update(AttributeOrder old, AttributeOrder updated) throws IOException {
      String sql = ReadSqlFiles.sqlString("attributeOrders-update.sql");
      MapSqlParameterSource params = new MapSqlParameterSource()
        .addValue("key", updated.getKey())
        .addValue("value", updated.getValue())
        .addValue("order", updated.getOrder())
        .addValue("oldKey", old.getKey())
        .addValue("oldValue", old.getValue());
      getNamedParameterJdbcTemplate().update(
          sql,
          params
      );
    }

}
