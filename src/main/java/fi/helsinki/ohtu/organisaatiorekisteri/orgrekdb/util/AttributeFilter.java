package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Attribute;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.HierarchyFilter;

public class AttributeFilter {

  private String nodeId;
  private List<Attribute> attributes;
  private List<HierarchyFilter> filters;

  /**
   * This class assumes that the filters argument contains only hierarchy filters that 
   * are valid and are already filtered by the hierarchy column.
   * 
   * @param attributes 
   * @param filters
   */
  public AttributeFilter(String nodeId, List<Attribute> attributes, List<HierarchyFilter> filters) {
    this.nodeId = nodeId;
    this.attributes = attributes;
    this.filters = filters;
  }

  public List<Attribute> getFilteredAttributes() {
    return this.byKey().byValue().includeMetaInformation().includeMissingAttributes().attributes;
  }

  private AttributeFilter byKey() {
    List<Attribute> results = new ArrayList<>();
    Set<String> wantedKeys = getWantedKeySet();
    for (Attribute attribute : attributes) {
      if (wantedKeys.contains(attribute.getKey())) {
        results.add(attribute);
      }
    }
    return new AttributeFilter(nodeId, results, filters);
  }

  private AttributeFilter byValue() {
    List<Attribute> results = new ArrayList<>();
    Map<String, Value> valueFilters = getValueFilters();
    for (Attribute attribute : attributes) {
      if (valueFilters.containsKey(attribute.getKey())) {
        Value valueFilter = valueFilters.get(attribute.getKey());
        if (valueFilter.isAcceptedValue(attribute.getValue())) {
          results.add(attribute);
        }
      }
    }
    return new AttributeFilter(nodeId, results, filters);
  }

  private AttributeFilter includeMetaInformation() {
    List<Attribute> results = new ArrayList<>();
    Map<String, Value> valueFilters = getValueFilters();
    for (Attribute attribute : attributes) {
      attribute.setMeta(valueFilters.get(attribute.getKey()));
      results.add(attribute);
    }
    return new AttributeFilter(nodeId, results, filters);
  }

  private AttributeFilter includeMissingAttributes() {
    List<Attribute> results = new ArrayList<>();
    Map<String, Value> valueFilters = getValueFilters();
    Set<String> allAttributeKeys = valueFilters.keySet();
    Set<String> existingKeys = new HashSet<>();
    for (Attribute attribute : attributes) {
      existingKeys.add(attribute.getKey());
      results.add(attribute);
    }
    for (String key : allAttributeKeys) {
      if (!existingKeys.contains(key)) {
        Attribute attribute = new Attribute();
        attribute.setNew(true);
        attribute.setDeleted(false);
        attribute.setKey(key);
        attribute.setValue(null);
        attribute.setNodeId(nodeId);
        attribute.setMeta(valueFilters.get(key));
        results.add(attribute);
      }
    }
    return new AttributeFilter(nodeId, results, filters);
  }

  private Set<String> getWantedKeySet() {
    Set<String> keys = new HashSet<>();
    for (HierarchyFilter filter : filters) {
      keys.add(filter.getKey());
    }
    return keys;
  }

  private Map<String, Value> getValueFilters() {
    Map<String, Value> results = new HashMap<>();
    for (HierarchyFilter filter : filters) {
      if (!results.containsKey(filter.getKey())) {
        results.put(filter.getKey(), new Value());
      }
      if (filter.getValue() == null) {
        results.get(filter.getKey()).setAnythingIsAccepted(true);
      } else {
        results.get(filter.getKey()).addAcceptedValue(filter.getValue());
      }
    }
    return results;
  }


  public static class Value {
    private Boolean anythingIsAccepted;
    private Set<String> acceptedValues;

    public Value() {
      this.anythingIsAccepted = false;
      this.acceptedValues = new HashSet<>();
    }

    public Boolean getAnythingIsAccepted() {
      return anythingIsAccepted;
    }

    public void setAnythingIsAccepted(Boolean anythingIsAccepted) {
      this.anythingIsAccepted = anythingIsAccepted;
    }

    public Set<String> getAcceptedValues() {
      return acceptedValues;
    }

    public void addAcceptedValue(String value) {
      if (!this.acceptedValues.contains(value)) {
        this.acceptedValues.add(value);
      }
    }

    public boolean isAcceptedValue(String input) {
      return this.anythingIsAccepted || this.acceptedValues.contains(input);
    }

  }
}
