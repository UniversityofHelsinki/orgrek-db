package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain;

public class AttributeOrderUpdateRequest {

  private AttributeOrder old;
  private AttributeOrder updated;

  public AttributeOrder getOld() {
    return old;
  }

  public void setOld(AttributeOrder old) {
    this.old = old;
  }

  public AttributeOrder getUpdated() {
    return updated;
  }

  public void setUpdated(AttributeOrder updated) {
    this.updated = updated;
  }

}
