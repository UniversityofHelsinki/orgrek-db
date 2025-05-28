package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain;

public class KonserniUnitMap {
  private String subUnitCode;
  private String unitCode;
  private String unitNameFi;

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
  public String getUnitNameFi() {
    return unitNameFi;
  }
  public void setUnitNameFi(String unitNameFi) {
    this.unitNameFi = unitNameFi;
  }

}
