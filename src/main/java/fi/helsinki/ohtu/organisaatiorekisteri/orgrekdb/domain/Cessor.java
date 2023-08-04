package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain;

import java.util.List;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Node;

/**
 * Cessor = prede_cessor_ or suc_cessor_ :)
 */
public class Cessor {

  private Node node;
  private List<EdgeWrapper> edges;

  public Cessor() {
  }

  public Cessor(Node node, List<EdgeWrapper> edges) {
    this.node = node;
    this.edges = edges;
  }

  public Node getNode() {
    return node;
  }

  public void setNode(Node node) {
    this.node = node;
  }

  public List<EdgeWrapper> getEdges() {
    return edges;
  }

  public void setEdges(List<EdgeWrapper> edges) {
    this.edges = edges;
  }

}
