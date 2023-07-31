package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.service;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.AttributeDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.HierarchyFilterDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.SectionDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Attribute;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.HierarchyFilter;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.SectionAttribute;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.AttributeFilter;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

@Service
public class NodeAttributeService {

    @Autowired
    private AttributeDao attributeDao;

    @Autowired
    private HierarchyFilterDao hierarchyFilterDao;

    @Autowired
    private SectionDao sectionDao;

    private void addUpdateOrDeleteAttributes(Map<String, List<Attribute>> nameAttributesMap) throws IOException {
        for (Map.Entry<String, List<Attribute>> nameAttributesListEntry : nameAttributesMap.entrySet()) {
            if (nameAttributesListEntry.getKey().equals(Constants.NEW_ATTRIBUTES) && !nameAttributesListEntry.getValue().isEmpty()) {
                attributeDao.addAttributes(nameAttributesListEntry.getValue());
            }
            if (nameAttributesListEntry.getKey().equals(Constants.UPDATED_ATTRIBUTES) && !nameAttributesListEntry.getValue().isEmpty()) {
                attributeDao.updateAttributes(nameAttributesListEntry.getValue());
            }
            if (nameAttributesListEntry.getKey().equals(Constants.DELETED_ATTRIBUTES) && !nameAttributesListEntry.getValue().isEmpty()) {
                attributeDao.deleteAttributes(nameAttributesListEntry.getValue());
            }
        }
    }

    @Transactional
    public void updateDeleteOrSaveNodeNameAttributes(Map<String, List<Attribute>> nameAttributesMap) throws IOException {
        addUpdateOrDeleteAttributes(nameAttributesMap);
    }

    @Transactional
    public void updateDeleteOrSaveNodeTypeAttributes(Map<String, List<Attribute>> typeAttributesMap) throws IOException {
        addUpdateOrDeleteAttributes(typeAttributesMap);
    }

    @Transactional
    public void updateDeleteOrSaveNodeCodeAttributes(Map<String, List<Attribute>> codeAttributesMap) throws IOException {
        addUpdateOrDeleteAttributes(codeAttributesMap);
    }

    @Transactional
    public void updateDeleteOrSaveNodeOtherAttributes(Map<String, List<Attribute>> codeAttributesMap) throws IOException {
        addUpdateOrDeleteAttributes(codeAttributesMap);
    }

    private Map<String, SectionAttribute> groupBySection(List<SectionAttribute> sections) {
        Map<String, SectionAttribute> sectionsByAttributeKey = new HashMap<>();
        for (SectionAttribute section : sections) {
            sectionsByAttributeKey.put(section.getAttr(), section);
        }
        return sectionsByAttributeKey;
    }

    private Map<String, Integer> getAttributeOrders(List<SectionAttribute> sections) {
        Map<String, Integer> attributeOrder = new HashMap<>();
        for (SectionAttribute section : sections) {
            attributeOrder.put(section.getAttr(), section.getOrderNro());
        }
        return attributeOrder;
    }

    private static class DateComparator implements Comparator<Date> {
        @Override
        public int compare(Date o1, Date o2) {
            if (o1 != null && o2 != null) {
                return o2.compareTo(o1);
            } else if (o1 != null) {
                return 1;
            }
            return -1;
        }
    }

    private static class OrderNoComparator implements Comparator<Attribute> {

        private DateComparator dateComparator;
        private Map<String, Integer> attributeOrder;
        public OrderNoComparator(Map<String, Integer> attributeOrder, DateComparator dateComparator) {
            super();
            this.attributeOrder = attributeOrder;
            this.dateComparator = dateComparator;
        }

        @Override
        public int compare(Attribute o1, Attribute o2) {
            Integer o1NO = attributeOrder.get(o1.getKey());
            Integer o2NO = attributeOrder.get(o2.getKey());
            Integer difference = o1NO - o2NO;
            if (difference == 0) {
                int startDateDifference = dateComparator.compare(o1.getStartDate(), o2.getStartDate());
                if (startDateDifference == 0) {
                    return dateComparator.compare(o1.getEndDate(), o2.getEndDate());
                }
                return startDateDifference;
            }
            return difference;
        }
    }

    private Map<String, List<Attribute>> groupBySection(List<Attribute> attributes, Map<String, SectionAttribute> sectionsByAttributeKey) {
        Map<String, List<Attribute>> attributesBySection = new HashMap<>();
        for (Attribute attribute : attributes) {
            SectionAttribute section = sectionsByAttributeKey.get(attribute.getKey());
            if (section != null && !attributesBySection.containsKey(section.getSection())) {
                attributesBySection.put(section.getSection(), new ArrayList<>());
            }
            if (section != null) {
                attributesBySection.get(section.getSection()).add(attribute);
            }
        }
        return attributesBySection;
    }

    public Map<String, List<Attribute>> getAttributes(String nodeId, List<String> hierarchies) throws IOException {
        List<Attribute> allAttributes = attributeDao.getAttributes(nodeId);
        List<HierarchyFilter> hierarchyFilters = hierarchyFilterDao.getHierarchyFiltersByHierarchies(hierarchies);
        AttributeFilter attributeFilter = new AttributeFilter(allAttributes, hierarchyFilters);
        List<Attribute> attributes = attributeFilter.getFilteredAttributes();

        List<SectionAttribute> sections = sectionDao.getSections();
        Map<String, SectionAttribute> sectionsByAttributeKey = groupBySection(sections);
        Map<String, Integer> attributeOrder = getAttributeOrders(sections);
        Map<String, List<Attribute>> attributesBySection = groupBySection(attributes, sectionsByAttributeKey);

        Comparator<Attribute> orderNoComparator = new OrderNoComparator(attributeOrder, new DateComparator());

        for (List<Attribute> attributeList : attributesBySection.values()) {
            attributeList.sort(orderNoComparator);
        }

        return attributesBySection;
    }
}
