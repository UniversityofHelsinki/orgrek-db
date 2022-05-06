package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.controller.SteeringGroupController;

public class SteeringGroup {

    private String nodeId;

    private String iamGroup;
    private String fi;
    private String sv;
    private String en;

    public SteeringGroup(String fi, String sv, String en) {
        this.fi=fi;
        this.sv=sv;
        this.en=en;
    }
    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }


    public SteeringGroup() {}

    public String getFi() {
        return fi;
    }

    public void setFi(String fi) {
        this.fi = fi;
    }

    public String getSv() {
        return sv;
    }

    public void setSv(String sv) {
        this.sv = sv;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }
    public String getIamGroup() {
        return iamGroup;
    }

    public void setIamGroup(String iamGroup) {
        this.iamGroup = iamGroup;
    }

}
