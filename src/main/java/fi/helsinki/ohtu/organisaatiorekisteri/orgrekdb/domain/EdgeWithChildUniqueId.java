package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain;

import java.time.LocalDate;

public class EdgeWithChildUniqueId {

    private Integer id;
    private int parentUniqueId;
    private int childUniqueId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String hierarchy;

    public EdgeWithChildUniqueId() {

    }

    public EdgeWithChildUniqueId(Integer id, int parentUniqueId, int childUniqueId, LocalDate startDate, LocalDate endDate, String hierarchy) {
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

}
