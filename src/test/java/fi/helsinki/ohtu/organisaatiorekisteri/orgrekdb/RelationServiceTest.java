package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Cessor;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.service.RelationService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application.properties")
public class RelationServiceTest {

  @Autowired
  private RelationService relationService;

  @Test
  public void testEveryRelativeHasNamesSet() throws IOException {
    List<String> hierarchies = Arrays.asList(new String[] { "virallinen", "talous" });
    SimpleDateFormat sf = new SimpleDateFormat("dd.MM.YYYY");
    String testDate = sf.format(new Date());
    List<Cessor> predecessors = relationService.getPredecessors("a1", testDate);
    List<Cessor> successors = relationService.getSuccessors("a1", testDate);
    List<Cessor> children = relationService.getChildren("a1", hierarchies, testDate);
    List<Cessor> parents = relationService.getParents("a1", hierarchies, testDate);
    for (Cessor cessor : predecessors) {
      assertTrue(cessor.getNode().getNames() != null);
    }
    for (Cessor cessor : successors) {
      assertTrue(cessor.getNode().getNames() != null);
    }
    for (Cessor cessor : children) {
      assertTrue(cessor.getNode().getNames() != null);
    }
    for (Cessor cessor : parents) {
      assertTrue(cessor.getNode().getNames() != null);
    }
  }
  
}
