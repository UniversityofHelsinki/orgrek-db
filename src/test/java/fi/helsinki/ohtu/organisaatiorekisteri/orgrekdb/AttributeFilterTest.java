package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Attribute;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.HierarchyFilter;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.AttributeFilter;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application.properties")
public class AttributeFilterTest {


  @Test
  public void missingValuesAreIncluded() {
    List<Attribute> attributes = new ArrayList<>();

    List<HierarchyFilter> hierarchyFilters = new ArrayList<>();
    hierarchyFilters.add(getTestHierarchyFilter("emo_lyhenne", "lyhenne"));
    hierarchyFilters.add(getTestHierarchyFilter("lyhenne", "asdf"));
    hierarchyFilters.add(getTestHierarchyFilter("iam-ryhma", "lasdf"));

    AttributeFilter af = new AttributeFilter("1", attributes, hierarchyFilters);
    List<Attribute> results = af.getFilteredAttributes();

    for (Attribute a : results) {
      assertTrue(a.isNew());
      assertTrue(a.getNodeId().equals("1"));
    }
  }

  @Test
  public void onlyInvalidValueIsSeenAsMissing() {
    List<Attribute> attributes = new ArrayList<>();
    attributes.add(getTestAttribute("emo_lyhenne", "BAD"));

    List<HierarchyFilter> hierarchyFilters = new ArrayList<>();
    hierarchyFilters.add(getTestHierarchyFilter("emo_lyhenne", "HY"));

    AttributeFilter af = new AttributeFilter("1", attributes, hierarchyFilters);
    List<Attribute> results = af.getFilteredAttributes();

    assertEquals(1, results.size());
    assertTrue(results.get(0).isNew());
  }

  @Test
  public void valuesNotInHierarchyFiltersAreNotIncluded() {
    List<Attribute> attributes = new ArrayList<>();
    attributes.add(getTestAttribute("emo_lyhenne", "illegal"));
    attributes.add(getTestAttribute("emo_lyhenne", "HY"));

    List<HierarchyFilter> hierarchyFilters = new ArrayList<>();
    hierarchyFilters.add(getTestHierarchyFilter("emo_lyhenne", "HY"));
    hierarchyFilters.add(getTestHierarchyFilter("emo_lyhenne", "MUU"));

    AttributeFilter af = new AttributeFilter("1", attributes, hierarchyFilters);
    List<Attribute> results = af.getFilteredAttributes();
    assertEquals(1, results.size());
    assertTrue(results.get(0).getValue().equals("HY"));
  }
  
  @Test
  public void keysNotInHierarchyFiltersAreNotInResults() {
    List<Attribute> attributes = new ArrayList<>();
    attributes.add(getTestAttribute("crap", "value"));
    attributes.add(getTestAttribute("emo_lyhenne", "HY"));
    attributes.add(getTestAttribute("lyhenne", "MUU"));

    List<HierarchyFilter> hierarchyFilters = new ArrayList<>();
    hierarchyFilters.add(getTestHierarchyFilter("emo_lyhenne", null));
    hierarchyFilters.add(getTestHierarchyFilter("lyhenne", null));

    AttributeFilter af = new AttributeFilter("1", attributes, hierarchyFilters);
    List<Attribute> results = af.getFilteredAttributes();

    assertEquals(2, results.size());
    for (Attribute a : results) {
      assertTrue(a.getKey().equals("emo_lyhenne") || a.getKey().equals("lyhenne"));
    }

  }

  private Attribute getTestAttribute(String key, String value) {
    Attribute result = new Attribute();
    result.setKey(key);
    result.setValue(value);
    return result;
  }

  private HierarchyFilter getTestHierarchyFilter(String key, String value) {
    HierarchyFilter hierarchyFilter = new HierarchyFilter();
    hierarchyFilter.setKey(key);
    hierarchyFilter.setValue(value);
    return hierarchyFilter;
  }
}
