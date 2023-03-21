package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.service;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.AttributeDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Attribute;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class NodeAttributeService {

    @Autowired
    private AttributeDao attributeDao;

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
}
