package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain;

public class NodeEdgeHistoryWrapper {

    private String nodeId;
    private String startDate;
    private String endDate;
    private String edgeStartDate;
    private String edgeEndDate;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEdgeStartDate() {
        return edgeStartDate;
    }

    public void setEdgeStartDate(String edgeStartDate) {
        this.edgeStartDate = edgeStartDate;
    }

    public String getEdgeEndDate() {
        return edgeEndDate;
    }

    public void setEdgeEndDate(String edgeEndDate) {
        this.edgeEndDate = edgeEndDate;
    }

}
