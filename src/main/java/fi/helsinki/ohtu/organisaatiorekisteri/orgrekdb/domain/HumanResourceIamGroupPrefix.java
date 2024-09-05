package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain;

public class HumanResourceIamGroupPrefix {

  private String uniqueId;
  private String code;
  private String type;
  private String nameFi;
  private String nameEn;
  private String nameSv;
  private String iamGroupPrefix;

  public String getUniqueId() {
    return uniqueId;
  }

  public void setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getNameFi() {
    return nameFi;
  }

  public void setNameFi(String nameFi) {
    this.nameFi = nameFi;
  }

  public String getNameEn() {
    return nameEn;
  }

  public void setNameEn(String nameEn) {
    this.nameEn = nameEn;
  }

  public String getNameSv() {
    return nameSv;
  }

  public void setNameSv(String nameSv) {
    this.nameSv = nameSv;
  }

  public String getIamGroupPrefix() {
    return iamGroupPrefix;
  }

  public void setIamGroupPrefix(String iamGroupPrefix) {
    this.iamGroupPrefix = iamGroupPrefix;
  }

}
