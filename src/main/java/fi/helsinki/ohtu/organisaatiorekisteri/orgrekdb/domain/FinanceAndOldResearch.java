package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain;

import java.util.Date;

public class FinanceAndOldResearch {

  private Date unitStartDate;

  private String nameFi;
  private String nameSv;
  private String nameEn;
  private String uniqueId;
  private String parent;

  private String publicity;
  private String type;
  private String code;
  private String uniqueCode;

  public Date getUnitStartDate() {
    return unitStartDate;
  }

  public void setUnitStartDate(Date unitStartDate) {
    this.unitStartDate = unitStartDate;
  }

  public String getNameFi() {
    return nameFi;
  }

  public void setNameFi(String nameFi) {
    this.nameFi = nameFi;
  }

  public String getNameSv() {
    return nameSv;
  }

  public void setNameSv(String nameSv) {
    this.nameSv = nameSv;
  }

  public String getNameEn() {
    return nameEn;
  }

  public void setNameEn(String nameEn) {
    this.nameEn = nameEn;
  }

  public String getUniqueId() {
    return uniqueId;
  }

  public void setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
  }

  public String getParent() {
    return parent;
  }

  public void setParent(String parent) {
    this.parent = parent;
  }

  public String getPublicity() {
    return publicity;
  }

  public void setPublicity(String publicity) {
    this.publicity = publicity;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getUniqueCode() {
    return uniqueCode;
  }

  public void setUniqueCode(String uniqueCode) {
    this.uniqueCode = uniqueCode;
  }

}
