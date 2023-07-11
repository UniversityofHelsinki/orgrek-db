package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.service;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.EdgeDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.OrgUnitDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.EdgeWithChildUniqueId;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.EdgeWrapper;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Node;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository(value = "EdgePropertiesService")
public class EdgePropertiesService {
    @Autowired
    private EdgeDao edgeDao;
    @Autowired
    public OrgUnitDao orgUnitDao;

    private String nodeByUniqueId(int uniqueId) throws IOException{
        Node node = orgUnitDao.getNodeByUniqueId(uniqueId);
        return node.getId();
    }

    private void extracted(List<EdgeWrapper> edgeWrapperList, List<EdgeWithChildUniqueId> edgeWithChildUniqueIdList) throws IOException {
        for (EdgeWithChildUniqueId edgeChild : edgeWithChildUniqueIdList) {
            String childNodeId = nodeByUniqueId(edgeChild.getChildUniqueId());
            String parentNodeId = nodeByUniqueId(edgeChild.getParentUniqueId());
            EdgeWrapper edgeWrapper = new EdgeWrapper(edgeChild);
            edgeWrapper.setChildNodeId(childNodeId);
            edgeWrapper.setParentNodeId(parentNodeId);
            edgeWrapperList.add(edgeWrapper);
        }
    }

    private void addUpdateOrDeleteUnits(Map<String, List<EdgeWithChildUniqueId>> edgeWithChildUniqueIdMap) throws IOException {

        if (edgeWithChildUniqueIdMap.containsKey(Constants.NEW_EDGES) && !edgeWithChildUniqueIdMap.get(Constants.NEW_EDGES).isEmpty()) {
            List<EdgeWrapper> edgeWrapperList = new ArrayList<>();
            List<EdgeWithChildUniqueId> edgeWithChildUniqueIdList = edgeWithChildUniqueIdMap.get(Constants.NEW_EDGES);
            extracted(edgeWrapperList, edgeWithChildUniqueIdList);
            edgeDao.addNewUpperUnits(edgeWrapperList);
        }
        if (edgeWithChildUniqueIdMap.containsKey(Constants.UPDATED_EDGES) && !edgeWithChildUniqueIdMap.get(Constants.UPDATED_EDGES).isEmpty()) {
            List<EdgeWrapper> edgeWrapperList = new ArrayList<>();
            List<EdgeWithChildUniqueId> edgeWithChildUniqueIdList = edgeWithChildUniqueIdMap.get(Constants.UPDATED_EDGES);
            extracted(edgeWrapperList, edgeWithChildUniqueIdList);
            edgeDao.updateUpperUnits(edgeWrapperList);
        }
        if (edgeWithChildUniqueIdMap.containsKey(Constants.DELETED_EDGES) && !edgeWithChildUniqueIdMap.get(Constants.DELETED_EDGES).isEmpty()) {
            List<EdgeWrapper> edgeWrapperList = new ArrayList<>();
            List<EdgeWithChildUniqueId> edgeWithChildUniqueIdList = edgeWithChildUniqueIdMap.get(Constants.DELETED_EDGES);
            extracted(edgeWrapperList, edgeWithChildUniqueIdList);
            edgeDao.deleteUpperUnits(edgeWrapperList);
        }
    }
    @Transactional
    public void addUpdateOrDeleteEdge(Map<String, List<EdgeWithChildUniqueId>> edgeWithChildUniqueIdMap) throws IOException {

        if (edgeWithChildUniqueIdMap.containsKey(Constants.NEW_EDGES) && !edgeWithChildUniqueIdMap.get(Constants.NEW_EDGES).isEmpty()) {
            List<EdgeWrapper> edgeWrapperList = new ArrayList<>();
            List<EdgeWithChildUniqueId> edgeWithChildUniqueIdList = edgeWithChildUniqueIdMap.get(Constants.NEW_EDGES);
            extracted(edgeWrapperList, edgeWithChildUniqueIdList);
            edgeDao.insertEdges(edgeWrapperList);
        }
        if (edgeWithChildUniqueIdMap.containsKey(Constants.UPDATED_EDGES) && !edgeWithChildUniqueIdMap.get(Constants.UPDATED_EDGES).isEmpty()) {
            List<EdgeWrapper> edgeWrapperList = new ArrayList<>();
            List<EdgeWithChildUniqueId> edgeWithChildUniqueIdList = edgeWithChildUniqueIdMap.get(Constants.UPDATED_EDGES);
            extracted(edgeWrapperList, edgeWithChildUniqueIdList);
            edgeDao.updateEdges(edgeWrapperList);
        }
        if (edgeWithChildUniqueIdMap.containsKey(Constants.DELETED_EDGES) && !edgeWithChildUniqueIdMap.get(Constants.DELETED_EDGES).isEmpty()) {
            List<EdgeWrapper> edgeWrapperList = new ArrayList<>();
            List<EdgeWithChildUniqueId> edgeWithChildUniqueIdList = edgeWithChildUniqueIdMap.get(Constants.DELETED_EDGES);
            extracted(edgeWrapperList, edgeWithChildUniqueIdList);
            edgeDao.deleteEdges(edgeWrapperList);
        }
    }

    @Transactional
    public void updateDeleteOrSaveUpperUnit(Map<String, List<EdgeWithChildUniqueId>> edgeWithChildUniqueIdMap) throws IOException {
        addUpdateOrDeleteUnits(edgeWithChildUniqueIdMap);
    }

    @Transactional
    public void updateDeleteOrSaveChildUnit(Map<String, List<EdgeWithChildUniqueId>> edgeWithChildUniqueIdMap) throws IOException {
        addUpdateOrDeleteUnits(edgeWithChildUniqueIdMap);
    }

    public void updateMapToEdgeWrapper(List<EdgeWrapper> edgeWrappers, List<EdgeWithChildUniqueId> edges) throws IOException {
        extracted(edgeWrappers, edges);
    }
}
