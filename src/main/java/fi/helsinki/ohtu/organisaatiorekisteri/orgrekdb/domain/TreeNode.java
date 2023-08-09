package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain;

import java.util.Map;

public class TreeNode {
  private String parentNodeId;
  private String childNodeId;
  private String uniqueId;
  private String hierarchy;
  private String language;
  private String nodeName;
  private Map<String, String> names;

  public String getParentNodeId() {
    return parentNodeId;
  }
  public void setParentNodeId(String parentNodeId) {
    this.parentNodeId = parentNodeId;
  }
  public String getChildNodeId() {
    return childNodeId;
  }

  public String getUniqueId() {
    return uniqueId;
  }
  public void setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
  }

  public void setChildNodeId(String childNodeId) {
    this.childNodeId = childNodeId;
  }
  public String getHierarchy() {
    return hierarchy;
  }
  public void setHierarchy(String hierarchy) {
    this.hierarchy = hierarchy;
  }
  public String getLanguage() {
    return language;
  }
  public void setLanguage(String language) {
    this.language = language;
  }
  public String getNodeName() {
    return nodeName;
  }
  public void setNodeName(String nodeName) {
    this.nodeName = nodeName;
  }
  public Map<String, String> getNames() {
    return names;
  }
  public void setNames(Map<String, String> names) {
    this.names = names;
  }
  
}
