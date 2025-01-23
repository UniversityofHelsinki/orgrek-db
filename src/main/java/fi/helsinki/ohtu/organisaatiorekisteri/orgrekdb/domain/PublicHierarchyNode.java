package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain;

public class PublicHierarchyNode {

  private String uniqueId;
  private String nameFi;
  private String nameEn;
  private String nameSv;

  public String getUniqueId() {
    return uniqueId;
  }

  public void setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
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

}
