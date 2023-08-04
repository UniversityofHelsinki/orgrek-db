package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.NameDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.FullName;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Node;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.Names;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.Names.Name;

@Service
public class NameService {
  
  @Autowired
  private NameDao nameDao;

  public Names getFullNames(List<String> nodeIds, String date) throws IOException  {
    if (nodeIds.isEmpty()) {
      return new Names(new ArrayList<>());
    }
    List<FullName> rows = nameDao.getFullNames(nodeIds, date);
    Names names = new Names(rows);
    return names;
  }

  public void setFullNames(List<Node> targets, String date) throws IOException {
    List<String> nodeIds = targets.stream().map(Node::getId).toList();
    Names fullNames = getFullNames(nodeIds, date);
    for (Node node : targets) {
      Name nodesName = fullNames.get(node, node.getName());
      node.setNames(nodesName);
    }
  }
  
}
