package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain;

import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class EdgeWrapper {

    public static MapSqlParameterSource asSqlMapping(EdgeWrapper edge) {
        MapSqlParameterSource mapping = new MapSqlParameterSource();
        mapping.addValue("id", edge.getId());
        mapping.addValue("child_node_id", edge.getChildNodeId());
        mapping.addValue("parent_node_id", edge.getParentNodeId());
        mapping.addValue("start_date", toTimestamp(edge.getStartDate()), Types.TIMESTAMP);
        mapping.addValue("end_date", toTimestamp(edge.getEndDate()), Types.TIMESTAMP);
        mapping.addValue("hierarchy", edge.getHierarchy());
        return mapping;
    }
    private Integer id;
    private String parentNodeId;
    private String childNodeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String hierarchy;

    public EdgeWrapper() {
    }

    public EdgeWrapper(EdgeWithChildUniqueId input) {
        this.id = input.getId();
        this.startDate = input.getStartDate();
        this.endDate = input.getEndDate();
        this.hierarchy = input.getHierarchy();
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getParentNodeId() {
        return parentNodeId;
    }

    public void setParentNodeId(String parentNodeId) {
        this.parentNodeId = parentNodeId;
    }

    public String getChildNodeId() {
        return childNodeId;
    }

    public void setChildNodeId(String childNodeId) {
        this.childNodeId = childNodeId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(String hierarchy) {
        this.hierarchy = hierarchy;
    }

    private static LocalDateTime toTimestamp(LocalDate date) {
        return date == null ? null : date.atStartOfDay();
    }

}
