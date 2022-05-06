package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain;

public class DegreeProgrammeDTO {

    private String nodeId;
    private String iamGroup;
    private String programmeCode;
    private String programmeNameFi;
    private String programmeNameEn;
    private String programmeNameSv;
    private String type;
    private SteeringGroup steeringGroup;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getIamGroup() {
        return iamGroup;
    }

    public void setIamGroup(String iamGroup) {
        this.iamGroup = iamGroup;
    }

    public String getProgrammeCode() {
        return programmeCode;
    }

    public void setProgrammeCode(String programmeCode) {
        this.programmeCode = programmeCode;
    }

    public String getProgrammeNameFi() {
        return programmeNameFi;
    }

    public void setProgrammeNameFi(String programmeNameFi) {
        this.programmeNameFi = programmeNameFi;
    }

    public String getProgrammeNameEn() {
        return programmeNameEn;
    }

    public void setProgrammeNameEn(String programmeNameEn) {
        this.programmeNameEn = programmeNameEn;
    }

    public String getProgrammeNameSv() {
        return programmeNameSv;
    }

    public void setProgrammeNameSv(String programmeNameSv) {
        this.programmeNameSv = programmeNameSv;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public SteeringGroup getSteeringGroup() {
        return steeringGroup;
    }

    public void setSteeringGroup(SteeringGroup steeringGroup) {
        this.steeringGroup = steeringGroup;
    }

}
