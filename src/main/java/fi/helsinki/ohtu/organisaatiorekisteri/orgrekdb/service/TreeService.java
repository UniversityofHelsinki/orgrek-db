package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.OrgUnitDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.TreeNode;

@Service
public class TreeService {
  @Autowired
  private OrgUnitDao orgUnitDao;

  public List<OrgUnit> getTree(Set<String> hierarchies, String date) throws IOException {
    List<TreeNode> treeNodes = orgUnitDao.getTreeNodes(hierarchies, date);
    List<TreeNode> namedTreeNodes = gatherNames(treeNodes);
    List<OrgUnit> trees = getMergedTree(namedTreeNodes);
    return trees;
  }

  private List<TreeNode> gatherNames(List<TreeNode> treeNodes) {
    List<TreeNode> results = new ArrayList<>();
    Map<String, Map<String, String>> names = new HashMap<>();
    for (TreeNode node : treeNodes) {
      if (!names.containsKey(node.getChildNodeId())) {
        names.put(node.getChildNodeId(), new HashMap<>());
      }
      names.get(node.getChildNodeId()).put(node.getLanguage(), node.getNodeName());
      if (node.getLanguage().equals("fi")) {
        node.setNames(names.get(node.getChildNodeId()));
        results.add(node);
      }
    }
    return results;
  }
  
  private boolean isRootNode(TreeNode input) {
    return input.getParentNodeId() == null;
  }

  private Map<String, List<TreeNode>> categorizeByParent(List<TreeNode> treeNodes, List<TreeNode> rootNodes) {
    Map<String, List<TreeNode>> byParent = new HashMap<>();

    for (TreeNode node : treeNodes) {
      if (isRootNode(node)) {
        rootNodes.add(node);
        continue;
      }
      String key = node.getParentNodeId();
      if (!byParent.containsKey(key)) {
        byParent.put(key, new ArrayList<>());
      }
      byParent.get(key).add(node);
    }
  
    return byParent;
  }

  private OrgUnit convert(TreeNode node) {
    OrgUnit result = new OrgUnit();
    result.setId(node.getChildNodeId());
    result.setUniqueId(node.getUniqueId());
    result.setNames(node.getNames());
    result.setChildren(new ArrayList<>());
    result.setHierarchies(new HashSet<>());
    result.getHierarchies().add(node.getHierarchy());
    return result;
  }

  private List<OrgUnit> convert(List<TreeNode> nodes) {
    List<OrgUnit> results = new ArrayList<>();
    for (TreeNode node : nodes) {
      results.add(convert(node));
    }
    return results;
  }

  private List<OrgUnit> merge(List<OrgUnit>... lists) {
    List<OrgUnit> merged = new ArrayList<>();
    List<OrgUnit> all = new ArrayList<>();
    for (List<OrgUnit> orgUnitList : lists) {
      all.addAll(orgUnitList);
    }

    Map<String, OrgUnit> byId = new HashMap<>();
    for (OrgUnit o : all) {
      if (!byId.containsKey(o.getId())) {
        byId.put(o.getId(), o.clone());
        continue;
      }
      OrgUnit inProgress = byId.get(o.getId());
      inProgress.getChildren().addAll(o.getChildren());
      inProgress.getHierarchies().addAll(o.getHierarchies());
    }

    for (OrgUnit mergedUnit : byId.values()) {
      merged.add(mergedUnit);
    }
    return merged;
  }

  private List<OrgUnit> getMergedTree(List<TreeNode> treeNodes) throws IOException {
    List<TreeNode> rootNodes = new ArrayList<>();
    Map<String, List<TreeNode>> byParent 
      = categorizeByParent(treeNodes, rootNodes);
    
    OrgUnit pseudoRoot = new OrgUnit();
    List<OrgUnit> pseudoRootChildren = convert(rootNodes);
    pseudoRoot.setChildren(pseudoRootChildren);

    constructTree(pseudoRoot, byParent);

    return pseudoRoot.getChildren();

  }

  private void constructTree(OrgUnit root, Map<String, List<TreeNode>> byParent) {
    List<OrgUnit> children = root.getChildren();
    List<OrgUnit> mergedChildren = merge(children);
    root.setChildren(mergedChildren);
    for (OrgUnit child : root.getChildren()) {
      if (!byParent.containsKey(child.getId())) {
        continue;
      }
      List<TreeNode> grandChildren = byParent.get(child.getId());
      List<OrgUnit> convertedGrandChildren = convert(grandChildren);
      child.setChildren(convertedGrandChildren);
      constructTree(child, byParent);
    }
    mergedChildren.sort(this::compareOrgUnitsByChildCountAndName);
  }

  private Integer compareOrgUnitsByChildCountAndName(OrgUnit a, OrgUnit b) {
      Integer aCCount = a.getChildren().size();
      Integer bCCount = b.getChildren().size();
      if (aCCount > 0 && bCCount == 0) {
        return -1;
      } else if (bCCount > 0 && aCCount == 0) {
        return 1;
      }
      String defaultLanguage = "fi";
      return a.getNames().get(defaultLanguage).compareTo(b.getNames().get(defaultLanguage));
  }
 
  public static class OrgUnit {

    private String id;
    private String uniqueId;
    private Set<String> hierarchies;
    private List<OrgUnit> children;
    private Map<String, String> names;

    public Map<String, String> getNames() {
      return names;
    }

    public void setNames(Map<String, String> names) {
      this.names = names;
    }

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getUniqueId() {
      return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
      this.uniqueId = uniqueId;
    }

    public List<OrgUnit> getChildren() {
      return children;
    }

    public void setChildren(List<OrgUnit> rootNodes) {
      this.children = rootNodes;
    }

    public Set<String> getHierarchies() {
      return hierarchies;
    }

    public void setHierarchies(Set<String> hierarchies) {
      this.hierarchies = hierarchies;
    }

    public OrgUnit clone() {
      OrgUnit copy = new OrgUnit();
      List<OrgUnit> copyChildren = new ArrayList<>();
      for (OrgUnit child : children) {
        copyChildren.add(child.clone());
      }
      copy.setChildren(copyChildren);
      copy.setId(id);
      Set<String> copyHierarchies = new HashSet<>();
      copyHierarchies.addAll(getHierarchies());
      copy.setHierarchies(copyHierarchies);
      copy.setNames(new HashMap<>());
      for (String language : names.keySet()) {
        copy.getNames().put(language, names.get(language));
      }
      copy.setUniqueId(uniqueId);
      return copy;
    }

  } 

}
