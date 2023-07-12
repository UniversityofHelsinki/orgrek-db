package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.service.TreeService;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.service.TreeService.OrgUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application.properties")
public class TreeServiceTest {

  @Autowired
  private TreeService treeService;

  private static SimpleDateFormat formatter = new SimpleDateFormat("d.M.y");
  private static String today = formatter.format(new Date());

  private static Set<String> hierarchies = new HashSet<>(
    Arrays.asList(
      new String[] { "treeTest", "treeTest1" }
    )
  );

  @Test
  public void multipleRoots() throws IOException {
    Map<String, List<OrgUnit>> tree = treeService.getTree(hierarchies, today);
    assertTrue(tree.keySet().size() > 0);
    for (String language : tree.keySet()) {
      List<OrgUnit> roots = tree.get(language);
      assertTrue(roots.size() == 2);
    }
  }

  @Test
  public void hierarchiesMerged() throws IOException {
    Map<String, List<OrgUnit>> tree = treeService.getTree(hierarchies, today);
    Set<String> hierarchies = null;
    for (String language : tree.keySet()) {
      List<OrgUnit> roots = tree.get(language);
      for (OrgUnit rootNode : roots) {
        if (rootNode.getId().equals("treeTestRoot1")) {
          hierarchies = rootNode.getHierarchies();
        }
      }
    }
    assertTrue(hierarchies != null);
    assertTrue(hierarchies.contains("treeTest"));
    assertTrue(hierarchies.contains("treeTest1"));
  }

  @Test
  public void noDuplicatesIfMergedSamePaths() throws IOException {
    Map<String, List<OrgUnit>> tree = treeService.getTree(hierarchies, today);
    int timesFound = 0;
    for (String language : tree.keySet()) {
      for (OrgUnit unit : tree.get(language)) {
        if (unit.getId().equals("treeTestRoot1")) {
          timesFound++;
        }
      }
    }
    assertEquals(tree.keySet().size(), timesFound);
  };

  @Test
  public void generationsAreMade() throws IOException {
    Map<String, List<OrgUnit>> tree = treeService.getTree(hierarchies, today);
    List<OrgUnit> roots = tree.get("fi");
    OrgUnit testSubject = null;
    for (OrgUnit root : roots) {
      if (root.getId().equals("treeTestRoot")) {
        testSubject = root;
        break;
      }
    }
    assertTrue(testSubject != null);
    boolean found = false;
    for (OrgUnit children : testSubject.getChildren()) {
      for (OrgUnit grandChildren : children.getChildren()) {
        if (grandChildren.getId().equals("treeTestChild3")) {
          found = true;
          break;
        }
      }
    }
    assertTrue(found);
  }




  
}
