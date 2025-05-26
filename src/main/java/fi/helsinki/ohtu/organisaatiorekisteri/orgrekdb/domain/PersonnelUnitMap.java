package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain;

public class PersonnelUnitMap {
  private String subUnitCode;
  private String unitCode;
  private String unitName;

  public String getSubUnitCode() {
    return subUnitCode;
  }
  public void setSubUnitCode(String subUnitCode) {
    this.subUnitCode = subUnitCode;
  }
  public String getUnitCode() {
    return unitCode;
  }
  public void setUnitCode(String unitCode) {
    this.unitCode = unitCode;
  }
  public String getUnitName() {
    return unitName;
  }
  public void setUnitName(String unitName) {
    this.unitName = unitName;
  }
}
