package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain;

public class AttributeOrder {

  private String key;
  private String value;
  private Integer order;

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public Integer getOrder() {
    return order;
  }

  public void setOrder(Integer order) {
    this.order = order;
  }

}
