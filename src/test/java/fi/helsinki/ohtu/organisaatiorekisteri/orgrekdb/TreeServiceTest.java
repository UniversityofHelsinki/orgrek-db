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
    List<OrgUnit> tree = treeService.getTree(hierarchies, today);
    assertTrue(tree.size() > 0);
  }

  @Test
  public void hierarchiesMerged() throws IOException {
    List<OrgUnit> roots = treeService.getTree(hierarchies, today);
    Set<String> hierarchies = null;
    for (OrgUnit rootNode : roots) {
      if (rootNode.getId().equals("treeTestRoot1")) {
        hierarchies = rootNode.getHierarchies();
      }
    }
    
    assertTrue(hierarchies != null);
    assertTrue(hierarchies.contains("treeTest"));
    assertTrue(hierarchies.contains("treeTest1"));
  }

  @Test
  public void noDuplicatesIfMergedSamePaths() throws IOException {
    List<OrgUnit> tree = treeService.getTree(hierarchies, today);
    int timesFound = 0;
    for (OrgUnit unit : tree) {
      if (unit.getId().equals("treeTestRoot1")) {
        timesFound++;
      }
    }
    assertEquals(1, timesFound);
  };

  @Test
  public void generationsAreMade() throws IOException {
    List<OrgUnit> roots = treeService.getTree(hierarchies, today);
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
