package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain;

public class EducationUnit {

  private String uniqueId;

  private String code;

  private String abbreviation;

  private String educationQualifier;

  private String type;

  private String nameFi;

  private String nameEn;

  private String nameSv;

  private String parent;

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

  public String getAbbreviation() {
    return abbreviation;
  }

  public void setAbbreviation(String abbreviation) {
    this.abbreviation = abbreviation;
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

  public String getParent() {
    return parent;
  }

  public void setParent(String parent) {
    this.parent = parent;
  }

  public String getEducationQualifier() {
    return educationQualifier;
  }

  public void setEducationQualifier(String educationQualifier) {
    this.educationQualifier = educationQualifier;
  }
}
