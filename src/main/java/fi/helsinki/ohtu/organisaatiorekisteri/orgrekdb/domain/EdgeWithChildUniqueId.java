package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain;

import java.util.Date;

public class EdgeWithChildUniqueId {

    private Integer id;
    private int parentUniqueId;
    private int childUniqueId;
    private Date startDate;
    private Date endDate;
    private String hierarchy;

    public EdgeWithChildUniqueId() {

    }

    public EdgeWithChildUniqueId(Integer id, int parentUniqueId, int childUniqueId, Date startDate, Date endDate, String hierarchy) {
        this.id = id;
        this.parentUniqueId = parentUniqueId;
        this.childUniqueId = childUniqueId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.hierarchy = hierarchy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getParentUniqueId() {
        return parentUniqueId;
    }

    public void setParentUniqueId(int parentUniqueId) {
        this.parentUniqueId = parentUniqueId;
    }

    public int getChildUniqueId() {
        return childUniqueId;
    }

    public void setChildUniqueId(int childUniqueId) {
        this.childUniqueId = childUniqueId;
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
