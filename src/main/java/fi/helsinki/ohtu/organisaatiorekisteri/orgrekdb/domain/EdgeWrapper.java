package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.util.Date;

public class EdgeWrapper {

    public static MapSqlParameterSource asSqlMapping(EdgeWrapper edge) {
        MapSqlParameterSource mapping = new MapSqlParameterSource();
        mapping.addValue("id", edge.getId());
        mapping.addValue("child_node_id", edge.getChildNodeId());
        mapping.addValue("parent_node_id", edge.getParentNodeId());
        mapping.addValue("start_date", edge.getStartDate());
        mapping.addValue("end_date", edge.getEndDate());
        mapping.addValue("hierarchy", edge.getHierarchy());
        return mapping;
    }
    private Integer id;
    private String parentNodeId;
    private String childNodeId;
    private Date startDate;
    private Date endDate;
    private String hierarchy;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(String hierarchy) {
        this.hierarchy = hierarchy;
    }
}
