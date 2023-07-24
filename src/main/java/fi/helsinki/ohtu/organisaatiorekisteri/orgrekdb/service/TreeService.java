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

  public Map<String, List<OrgUnit>> getTree(Set<String> hierarchies, String date) throws IOException {
    List<TreeNode> treeNodes = orgUnitDao.getTreeNodes(hierarchies, date);
    Map<String, List<TreeNode>> byLanguage = groupByLanguage(treeNodes);
    Map<String, List<OrgUnit>> results = new HashMap<>();
    for (String language : byLanguage.keySet()) {
      List<OrgUnit> tree = getMergedTree(byLanguage.get(language));
      results.put(language, tree);
    }
    return results;
  }

  
  private Map<String, List<TreeNode>> groupByLanguage(List<TreeNode> nodes) {
    Map<String, List<TreeNode>> results = new HashMap<>();
    for (TreeNode node : nodes) {
      if (!results.containsKey(node.getLanguage())) {
        results.put(node.getLanguage(), new ArrayList<>());
      }
      results.get(node.getLanguage()).add(node);
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
    result.setName(node.getNodeName());
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
      return a.getName().compareTo(b.getName());
  }
 
  public static class OrgUnit {

    private String id;
    private String uniqueId;
    private String name;
    private Set<String> hierarchies;
    private List<OrgUnit> children;

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

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
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
      copy.setName(name);
      copy.setUniqueId(uniqueId);
      return copy;
    }

  } 

}
