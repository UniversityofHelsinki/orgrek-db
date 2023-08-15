package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.EdgeWrapper;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.ChildEdgeGroup;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.EdgeGroup;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.ParentEdgeGroup;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application.properties")
public class EdgeGroupTest {


  @Test
  public void testChildEdgeGroupGroupsByChildNodeID() {
    List<EdgeWrapper> input = new ArrayList<>();
    input.add(getTestEdgeWrapper("parent", "child"));
    input.add(getTestEdgeWrapper("parent-1", "child"));
    input.add(getTestEdgeWrapper("parent-2", "child"));
    input.add(getTestEdgeWrapper("parent-2", "child-1"));
    input.add(getTestEdgeWrapper("parent-2", "child-2"));
    EdgeGroup edgeGroup = new ChildEdgeGroup(input);
    String[] childNodeIds = new String[] { "child", "child-1", "child-2" };
    for (String childNodeId : childNodeIds) {
      assertTrue(edgeGroup.get(childNodeId) != null);
      List<EdgeWrapper> grouped = edgeGroup.get(childNodeId);
      for (EdgeWrapper edge : grouped) {
        assertEquals(childNodeId, edge.getChildNodeId());
      }
    }
  }

  @Test
  public void testParentEdgeGroupGroupsByParentNodeID() {
    List<EdgeWrapper> input = new ArrayList<>();
    input.add(getTestEdgeWrapper("parent", "child"));
    input.add(getTestEdgeWrapper("parent-1", "child"));
    input.add(getTestEdgeWrapper("parent-2", "child"));
    input.add(getTestEdgeWrapper("parent-2", "child-1"));
    input.add(getTestEdgeWrapper("parent-2", "child-2"));
    EdgeGroup edgeGroup = new ParentEdgeGroup(input);
    String[] parentNodeIds = new String[] { "parent", "parent-1", "parent-2" };
    for (String parentNodeId : parentNodeIds) {
      assertTrue(edgeGroup.get(parentNodeId) != null);
      List<EdgeWrapper> grouped = edgeGroup.get(parentNodeId);
      for (EdgeWrapper edge : grouped) {
        assertEquals(parentNodeId, edge.getParentNodeId());
      }
    }
  }

  private EdgeWrapper getTestEdgeWrapper(String parentNodeId, String childNodeId) {
    EdgeWrapper output = new EdgeWrapper();
    output.setChildNodeId(childNodeId);
    output.setParentNodeId(parentNodeId);
    return output;
  }

}
