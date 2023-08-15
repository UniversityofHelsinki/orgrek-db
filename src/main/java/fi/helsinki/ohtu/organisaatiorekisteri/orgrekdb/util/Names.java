package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.FullName;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Node;

public class Names {

  private Map<String, Name> names;

  public Names(List<FullName> rawNames) {
    names = new HashMap<>();
    for (FullName name : rawNames) {
      if (!names.containsKey(name.getNodeId())) {
        names.put(name.getNodeId(), new Name());
      }
      Name nodesName = names.get(name.getNodeId());
      nodesName.setName(name.getName(), name.getLanguage());
    }
  }

  public Name get(Node node, String defaultName) {
    Name nodesName = names.get(node.getId());
    if (nodesName == null) {
      names.put(node.getId(), new Name());
      nodesName = names.get(node.getId());
    }
    if (nodesName.getEn() == null) {
      nodesName.setEn(defaultName);
    }
    if (nodesName.getFi() == null) {
      nodesName.setFi(defaultName);
    }
    if (nodesName.getSv() == null) {
      nodesName.setSv(defaultName);
    }
    return nodesName;
  }

  public static class Name {
    private String fi;
    private String sv;
    private String en;

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

    public void setName(String name, String language) {
      switch (language) {
        case "fi":
          setFi(name);
          break;
        case "en":
          setEn(name);
          break;
        case "sv":
          setSv(name);
          break;
        default:
          break;
      }
    }

  }
  
}
