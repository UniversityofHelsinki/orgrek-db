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

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.SectionDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Attribute;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.SectionAttribute;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.service.NodeAttributeService;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.service.TreeService;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.service.TreeService.OrgUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application.properties")
public class NodeAttributeServiceTest {

  @Autowired
  private NodeAttributeService nodeAttributeService;

  @Autowired
  private SectionDao sectionDao;

  @Test
  public void everySectionIsIncludedInAttributes() throws IOException {
    SimpleDateFormat sf = new SimpleDateFormat("dd.MM.yyyy");
    String date = sf.format(new Date());
    List<String> hierarchies = Arrays.asList(new String[] { "virallinen" });
    List<SectionAttribute> sections = sectionDao.getAllSectionAttributes();
    Set<String> uniqueSections = new HashSet<>();
    Map<String, List<Attribute>> attributes = nodeAttributeService.getAttributes("a1", hierarchies, date);
    for (SectionAttribute section : sections) {
      uniqueSections.add(section.getSection());
      assertTrue(attributes.keySet().contains(section.getSection()));
    }
    assertTrue(attributes.keySet().size() == uniqueSections.size());
  }

  
}
