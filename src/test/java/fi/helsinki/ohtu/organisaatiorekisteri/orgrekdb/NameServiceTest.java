package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.EdgeWrapper;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.FullName;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Node;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.service.NameService;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.ChildEdgeGroup;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.EdgeGroup;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.Names;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.ParentEdgeGroup;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.Names.Name;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application.properties")
public class NameServiceTest {

  @Autowired
  private NameService nameService;

  @Test
  public void testSettingFullNames() throws IOException {
    List<Node> nodes = new ArrayList<>();
    Node n1 = new Node();
    n1.setId("a1");
    n1.setName("default-a1");
    Node n2 = new Node();
    n2.setId("a2");
    n2.setName("default-a2");
    nodes.add(n1);
    nodes.add(n2);

    SimpleDateFormat sf = new SimpleDateFormat("dd.MM.YYYY");
    String date = sf.format(new Date());

    nameService.setFullNames(nodes, date);
    assertTrue(n1.getNames() != null);
    assertTrue(n2.getNames() != null);
    for (Node node : nodes) {
      assertTrue(node.getNames().getFi() != null);
      assertTrue(node.getNames().getEn() != null);
      assertTrue(node.getNames().getSv() != null);
    }

  }
  
}
