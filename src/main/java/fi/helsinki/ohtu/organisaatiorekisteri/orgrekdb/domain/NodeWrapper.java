package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain;

public class NodeWrapper {
    private String parentNodeId;
    private String childNodeId;
    private String type;

    public String getChildNodeId() { return childNodeId; }
    public String getParentNodeId() {
        return parentNodeId;
    }

    public void setParentNodeId(String parentNodeId) {
        this.parentNodeId = parentNodeId;
    }

    public void setChildNodeId(String childNodeId) { this.childNodeId = childNodeId; }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
