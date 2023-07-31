package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain;

import java.util.Date;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.AttributeFilter.Value;

public class Attribute {

    private String key;
    private String value;
    private Date startDate;
    private Date endDate;
    private String nodeId;
    private Integer id;
    private boolean isNew;
    private boolean deleted;

    private Value meta;

    public Attribute() {
        this(null, null);
    }

    public Attribute(String key, String value) {
        this(null, key, value, null, null);
    }

    public Attribute(Integer id, String key, String value, Date startDate, Date endDate) {
        this(null, id, key, value, startDate, endDate);
    }

    public Attribute(String nodeId, Integer id, String key, String value, Date startDate, Date endDate) {
        this.id = id;
        this.key = key;
        this.value = value;
        this.startDate = startDate;
        this.endDate = endDate;
        this.nodeId = nodeId;
    }

    public Attribute(SectionDecoratedAttribute input) {
        this.id = input.getId();
        this.key = input.getKey();
        this.value = input.getValue();
        this.startDate = input.getStartDate();
        this.endDate = input.getEndDate();
        this.nodeId = input.getNodeId();
        this.isNew = false;
        this.deleted = false;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Value getMeta() {
        return meta;
    }

    public void setMeta(Value meta) {
        this.meta = meta;
    }

    @Override
    public String toString() {
        return "Attribute{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", nodeId='" + nodeId + '\'' +
                ", id=" + id +
                ", isNew=" + isNew +
                ", deleted=" + deleted +
                '}';
    }
}
