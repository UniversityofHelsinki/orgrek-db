package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.EdgeWrapper;

public class ChildEdgeGroup implements EdgeGroup {

  private Map<String, List<EdgeWrapper>> byChildNodeId;

  public ChildEdgeGroup(List<EdgeWrapper> rawEdges) {
    byChildNodeId = new HashMap<>();
    for (EdgeWrapper edge : rawEdges) {
      if (!byChildNodeId.containsKey(edge.getChildNodeId())) {
        byChildNodeId.put(edge.getChildNodeId(), new ArrayList<>());
      }
      byChildNodeId.get(edge.getChildNodeId()).add(edge);
    }
  }

  public List<EdgeWrapper> get(String nodeId) {
    if (byChildNodeId.containsKey(nodeId)) {
      return byChildNodeId.get(nodeId);
    }
    return new ArrayList<>();
  }

}
