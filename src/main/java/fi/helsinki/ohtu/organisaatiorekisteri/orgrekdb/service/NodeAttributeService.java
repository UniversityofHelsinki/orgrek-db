package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.service;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.AttributeDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Attribute;
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

    @Transactional
    public void updateDeleteOrSaveNodeNameAttributes(Map<String, List<Attribute>> nameAttributesMap) throws IOException {
        for (Map.Entry<String, List<Attribute>> nameAttributesListEntry : nameAttributesMap.entrySet()) {
            if (nameAttributesListEntry.getKey().equals("newAttributes") && !nameAttributesListEntry.getValue().isEmpty()) {
                attributeDao.addAttributes(nameAttributesListEntry.getValue());
            }
            if (nameAttributesListEntry.getKey().equals("updatedAttributes") && !nameAttributesListEntry.getValue().isEmpty()) {
                attributeDao.updateAttributes(nameAttributesListEntry.getValue());
            }
            if (nameAttributesListEntry.getKey().equals("deletedAttributes") && !nameAttributesListEntry.getValue().isEmpty()) {
                attributeDao.deleteAttributes(nameAttributesListEntry.getValue());
            }
        }

    }
}
