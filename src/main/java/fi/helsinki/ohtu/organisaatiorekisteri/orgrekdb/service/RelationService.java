package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.EdgeDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Cessor;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.EdgeWrapper;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Node;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.ChildEdgeGroup;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.EdgeGroup;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.ParentEdgeGroup;

@Service
public class RelationService {

  @Autowired
  private EdgeDao edgeDao;

  @Autowired
  private NodeService nodeService;

  @Autowired NameService nameService;

  public List<Cessor> getPredecessors(String nodeId, String date) throws IOException {
    List<EdgeWrapper> edges = edgeDao.getPredecessors(nodeId);
    List<String> nodeIds = edges.stream().map(e -> e.getParentNodeId()).collect(Collectors.toList());
    EdgeGroup edgeGroup = new ParentEdgeGroup(edges);
    return getRelatives(edges, nodeIds, edgeGroup, date);
  }

  public List<Cessor> getSuccessors(String nodeId, String date) throws IOException {
    List<EdgeWrapper> edges = edgeDao.getSuccessors(nodeId);
    List<String> nodeIds = edges.stream().map(e -> e.getChildNodeId()).collect(Collectors.toList());
    EdgeGroup edgeGroup = new ChildEdgeGroup(edges);
    return getRelatives(edges, nodeIds, edgeGroup, date);
  }

  public List<Cessor> getChildren(String nodeId, List<String> hierarchies, String date) throws IOException {
    List<EdgeWrapper> edges = edgeDao.getChildren(nodeId, hierarchies);
    List<String> nodeIds = edges.stream().map(e -> e.getChildNodeId()).collect(Collectors.toList());
    EdgeGroup edgeGroup = new ChildEdgeGroup(edges);
    return getRelatives(edges, nodeIds, edgeGroup, date);
  }

  public List<Cessor> getParents(String nodeId, List<String> hierarchies, String date) throws IOException {
    List<EdgeWrapper> edges = edgeDao.getParents(nodeId, hierarchies);
    List<String> nodeIds = edges.stream().map(e -> e.getParentNodeId()).collect(Collectors.toList());
    EdgeGroup edgeGroup = new ParentEdgeGroup(edges);
    return getRelatives(edges, nodeIds, edgeGroup, date);
  }

  private List<Cessor> getRelatives(List<EdgeWrapper> edges, List<String> nodeIds, EdgeGroup edgeGroup, String date) throws IOException {
    if (edges.isEmpty()) {
      return new ArrayList<>();
    }
    List<Node> nodes = nodeService.getNodes(nodeIds);
    nameService.setFullNames(nodes, date);
    return asCessors(nodes, edgeGroup);
  }

  private List<Cessor> asCessors(List<Node> nodes, EdgeGroup edgeGroup) {
    List<Cessor> cessors = new ArrayList<>();
    for (Node node : nodes) {
      cessors.add(asCessor(node, edgeGroup.get(node.getId())));
    }
    return cessors;
  }

  private Cessor asCessor(Node node, List<EdgeWrapper> edges) {
    return new Cessor(node, edges);
  }

}
