package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.EdgeWrapper;

public class ParentEdgeGroup implements EdgeGroup {

  private Map<String, List<EdgeWrapper>> byParentId;

  public ParentEdgeGroup(List<EdgeWrapper> edges) {
    byParentId = new HashMap<>();
    for (EdgeWrapper edge : edges) {
      if (!byParentId.containsKey(edge.getParentNodeId())) {
        byParentId.put(edge.getParentNodeId(), new ArrayList<>());
      }
      byParentId.get(edge.getParentNodeId()).add(edge);
    }
  }

  public List<EdgeWrapper> get(String nodeId) {
    if (byParentId.containsKey(nodeId)) {
      return byParentId.get(nodeId);
    }
    return new ArrayList<>();
  }
  
}
