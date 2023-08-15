package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.EdgeWrapper;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.FullName;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Node;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.ChildEdgeGroup;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.EdgeGroup;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.Names;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.ParentEdgeGroup;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.Names.Name;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application.properties")
public class NamesTest {

  @Test
  public void testNamesSetsDefaultIfInputDoesNotContainFullNamesForEveryLanguage() {
    List<FullName> input = new ArrayList<>();
    FullName onlyOne = new FullName();
    onlyOne.setName("Helsingin yliopisto");
    onlyOne.setLanguage("fi");
    onlyOne.setNodeId("a1");
    input.add(onlyOne);

    Node n = new Node();
    n.setId("a1");

    Names testable = new Names(input);
    Name output = testable.get(n, "defaultName");
    assertEquals(output.getFi(), "Helsingin yliopisto");
    assertEquals(output.getEn(), "defaultName");
    assertEquals(output.getSv(), "defaultName");
  }
}
