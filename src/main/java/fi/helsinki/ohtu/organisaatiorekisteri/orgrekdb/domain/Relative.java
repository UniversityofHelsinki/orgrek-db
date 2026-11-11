package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain;

import java.time.LocalDate;

public class Relative {
    private String id;
    private Integer uniqueId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String hierarchy;

    private Integer edgeId;
    private LocalDate edgeStartDate;
    private LocalDate edgeEndDate;
    private String fullName;
    private String language;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(Integer uniqueId) {
        this.uniqueId = uniqueId;
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

    public Integer getEdgeId() {
        return edgeId;
    }

    public void setEdgeId(Integer edgeId) {
        this.edgeId = edgeId;
    }

    public LocalDate getEdgeStartDate() {
        return edgeStartDate;
    }

    public void setEdgeStartDate(LocalDate edgeStartDate) {
        this.edgeStartDate = edgeStartDate;
    }

    public LocalDate getEdgeEndDate() {
        return edgeEndDate;
    }

    public void setEdgeEndDate(LocalDate edgeEndDate) {
        this.edgeEndDate = edgeEndDate;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}
