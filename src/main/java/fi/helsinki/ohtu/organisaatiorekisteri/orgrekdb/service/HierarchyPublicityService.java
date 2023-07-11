package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.service;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.EdgeDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.HierarchyPublicityDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.OrgUnitDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.TextsDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.EdgeWrapper;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.NameLanguageWrapper;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.NewHierarchyPublicityDTO;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Node;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

@Service
public class HierarchyPublicityService {

    @Autowired
    private HierarchyPublicityDao hierarchyPublicityDao;

    @Autowired
    private EdgeDao edgeDao;

    @Autowired
    private TextsDao textsDao;

    @Autowired
    public OrgUnitDao orgUnitDao;

    @Transactional
    public NewHierarchyPublicityDTO insertHierarchyPublicity(NewHierarchyPublicityDTO newHierarchyPublicityDTO, String userName) throws IOException {
        hierarchyPublicityDao.insertHierarchyPublicity(newHierarchyPublicityDTO);
        List<EdgeWrapper> edgeWrapperList = getEdgeWrappers(newHierarchyPublicityDTO);
        edgeDao.insertEdges(edgeWrapperList);
        List<Map<String, String>> texts = getTexts(newHierarchyPublicityDTO, userName);
        textsDao.insertTexts(texts);
        return newHierarchyPublicityDTO;
    }

    private static List<Map<String, String>> getTexts(NewHierarchyPublicityDTO newHierarchyPublicityDTO, String userName) {
        List<Map<String, String>> texts = new ArrayList<>();
        for (NameLanguageWrapper nameLanguageWrapper : newHierarchyPublicityDTO.getNames()) {
            Map<String, String> textEntry = new HashMap<>();
            textEntry.put("key", newHierarchyPublicityDTO.getHierarchy());
            textEntry.put("value", nameLanguageWrapper.getName());
            textEntry.put("language", nameLanguageWrapper.getLanguage());
            textEntry.put("timestamp", String.valueOf(new Date()));
            textEntry.put("user_name", userName);
            texts.add(textEntry);
        }
        return texts;
    }

    private List<EdgeWrapper> getEdgeWrappers(NewHierarchyPublicityDTO newHierarchyPublicityDTO) throws IOException {
        List<EdgeWrapper> edgeWrapperList = new ArrayList<>();
        Node unit = orgUnitDao.getNodeByUniqueId(Integer.parseInt(newHierarchyPublicityDTO.getChildId()));
        EdgeWrapper edgeWrapper = new EdgeWrapper();
        edgeWrapper.setParentNodeId(null);
        edgeWrapper.setChildNodeId(unit.getId());
        edgeWrapper.setHierarchy(newHierarchyPublicityDTO.getHierarchy());
        edgeWrapper.setStartDate(null);
        edgeWrapper.setEndDate(null);
        edgeWrapperList.add(edgeWrapper);
        return edgeWrapperList;
    }
}
