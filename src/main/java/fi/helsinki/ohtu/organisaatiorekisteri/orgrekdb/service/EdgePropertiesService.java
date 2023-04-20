package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.service;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.EdgeDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.EdgeWrapper;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Repository(value = "EdgePropertiesService")
public class EdgePropertiesService {
    @Autowired
    private EdgeDao edgeDao;

    private void addUpdateOrDeleteUpperUnit(Map<String, List<EdgeWrapper>> parentUnitPropertiesMap) throws IOException {
        for (Map.Entry<String, List<EdgeWrapper>> parentUnitListEntry : parentUnitPropertiesMap.entrySet()) {
            if (parentUnitListEntry.getKey().equals(Constants.NEW_ATTRIBUTES) && !parentUnitListEntry.getValue().isEmpty()) {
                edgeDao.addNewUpperUnits(parentUnitListEntry.getValue());
            }
            if (parentUnitListEntry.getKey().equals(Constants.UPDATED_ATTRIBUTES) && !parentUnitListEntry.getValue().isEmpty()) {
                edgeDao.updateUpperUnits(parentUnitListEntry.getValue());
            }
            if (parentUnitListEntry.getKey().equals(Constants.DELETED_ATTRIBUTES) && !parentUnitListEntry.getValue().isEmpty()) {
                edgeDao.deleteUpperUnits(parentUnitListEntry.getValue());
            }
        }
    }

    @Transactional
    public void updateDeleteOrSaveUpperUnit(Map<String, List<EdgeWrapper>> parentUnitPropertiesMap) throws IOException {
        addUpdateOrDeleteUpperUnit(parentUnitPropertiesMap);
    }
}
