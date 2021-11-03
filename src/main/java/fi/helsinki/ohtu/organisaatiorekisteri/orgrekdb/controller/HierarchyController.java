package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.controller;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.OrgUnitDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Node;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.NodeEdgeHistoryWrapper;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.NodeWrapper;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/node")
public class HierarchyController {

    @Autowired
    private OrgUnitDao orgUnitDao;

    @RequestMapping(method = GET, value = "/parents/{id}/{date}")
    public List<Node> getParentNodesByIdAndDate(@PathVariable("id") int uniqueId, @PathVariable("date") String date) {
        Node node = orgUnitDao.getNodeByUniqueId(uniqueId);
        return orgUnitDao.getCurrentParentsByChildNodeId(node.getId(), date);
    }

    @RequestMapping(method = GET, value = "/parents/types/{id}/{date}")
    public List<NodeWrapper> getParentNodeTypesByIdAndDate(@PathVariable("id") int uniqueId, @PathVariable("date") String date) {
        Node node = orgUnitDao.getNodeByUniqueId(uniqueId);
        return orgUnitDao.getCurrentTypesByChildNodeId(node.getId(), date);
    }

    @RequestMapping(method = GET, value = "/children/{id}/{date}")
    public List<Node> getChildNodesByIdAndDate(@PathVariable("id") int uniqueId, @PathVariable("date") String date) {
        Node node = orgUnitDao.getNodeByUniqueId(uniqueId);
        return orgUnitDao.getCurrentChildrenByParentNodeId(node.getId(), date);
    }

    @RequestMapping(method = GET, value = "/children/types/{id}/{date}")
    public List<NodeWrapper> getChildNodeTypesByIdAndDate(@PathVariable("id") int uniqueId, @PathVariable("date") String date) {
        Node node = orgUnitDao.getNodeByUniqueId(uniqueId);
        return orgUnitDao.getCurrentTypesByParentNodeId(node.getId(), date);
    }

    @RequestMapping(method = GET, value = "/predecessors/{id}")
    public List<NodeEdgeHistoryWrapper> getPredecessorsById(@PathVariable("id") int uniqueId) {
        Node node = orgUnitDao.getNodeByUniqueId(uniqueId);
        return orgUnitDao.getPredecessors(node.getId(), Constants.HISTORY_UNIT_TYPE);
    }

    @RequestMapping(method = GET, value = "/successors/{id}")
    public List<NodeEdgeHistoryWrapper> getSuccessors(@PathVariable("id") int uniqueId) {
        Node node = orgUnitDao.getNodeByUniqueId(uniqueId);
        return orgUnitDao.getSuccessors(node.getId(), Constants.HISTORY_UNIT_TYPE);
    }

}
