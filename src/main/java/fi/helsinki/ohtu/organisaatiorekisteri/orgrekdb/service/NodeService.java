package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.service;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.AttributeDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.EdgeDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.OrgUnitDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Attribute;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.EdgeWrapper;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.NewNodeDTO;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class NodeService {
    @Autowired
    private OrgUnitDao orgUnitDao;

    @Autowired
    private AttributeDao attributeDao;

    @Autowired
    private EdgeDao edgeDao;

    @Transactional
    public NewNodeDTO insertNode(NewNodeDTO newNodeDTO) throws IOException {
        newNodeDTO = orgUnitDao.insertNode(newNodeDTO);
        List<Attribute> attributeList = new ArrayList<>();
        attributeList.add(addAttribute(newNodeDTO, Constants.NAME_FI_FIELD, newNodeDTO.getNameFi()));
        attributeList.add(addAttribute(newNodeDTO, Constants.NAME_SV_FIELD, newNodeDTO.getNameSv()));
        attributeList.add(addAttribute(newNodeDTO, Constants.NAME_EN_FIELD, newNodeDTO.getNameEn()));
        attributeDao.addAttributes(attributeList);
        List<EdgeWrapper> edgeWrappers = getEdgeWrappers(newNodeDTO);
        edgeDao.insertEdges(edgeWrappers);
        return newNodeDTO;
    }

    private static List<EdgeWrapper> getEdgeWrappers(NewNodeDTO newNodeDTO) {
        List<EdgeWrapper> edgeWrappers = new ArrayList<>();
        EdgeWrapper edgeWrapper = new EdgeWrapper();
        edgeWrapper.setParentNodeId(newNodeDTO.getParentNodeId());
        edgeWrapper.setChildNodeId(newNodeDTO.getChildNodeId());
        edgeWrapper.setStartDate(newNodeDTO.getStartDate());
        edgeWrapper.setEndDate(newNodeDTO.getEndDate());
        edgeWrappers.add(edgeWrapper);
        return edgeWrappers;
    }

    private static Attribute addAttribute(NewNodeDTO newNodeDTO, String key, String value) {
        Attribute attribute = new Attribute();
        attribute.setNodeId(newNodeDTO.getChildNodeId());
        attribute.setValue(value);
        attribute.setKey(key);
        attribute.setStartDate(new Date());
        attribute.setEndDate(new Date());
        return attribute;
    }

}
