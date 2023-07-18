package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.controller;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.EdgeDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.OrgUnitDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/node")
public class HierarchyController {

    @Autowired
    private OrgUnitDao orgUnitDao;

    @Autowired
    private EdgeDao edgeDao;

    @RequestMapping(method = GET, value = "/parents/types/{id}/{date}")
    public List<NodeWrapper> getParentNodeTypesByIdAndDate(@PathVariable("id") int uniqueId, @PathVariable("date") String date) throws IOException {
        Node node = orgUnitDao.getNodeByUniqueId(uniqueId);
        return orgUnitDao.getCurrentTypesByChildNodeId(node.getId(), date);
    }

    @RequestMapping(method = GET, value = "/parents/historyandcurrent/types/{id}/{date}")
    public List<NodeWrapper> getHistoryAndCurrentParentNodeTypesByIdAndDate(@PathVariable("id") int uniqueId, @PathVariable("date") String date) throws IOException {
        Node node = orgUnitDao.getNodeByUniqueId(uniqueId);
        return orgUnitDao.getHistoryAndCurrentTypesByChildNodeId(node.getId(), date);
    }

    @RequestMapping(method = GET, value = "/parents/futureandcurrent/types/{id}/{date}")
    public List<NodeWrapper> getFutureAndCurrentParentNodeTypesByIdAndDate(@PathVariable("id") int uniqueId, @PathVariable("date") String date) throws IOException {
        Node node = orgUnitDao.getNodeByUniqueId(uniqueId);
        return orgUnitDao.getFutureAndCurrentTypesByChildNodeId(node.getId(), date);
    }

    @RequestMapping(method = GET, value = "/children/types/{id}/{date}")
    public List<NodeWrapper> getChildNodeTypesByIdAndDate(@PathVariable("id") int uniqueId, @PathVariable("date") String date) throws IOException {
        Node node = orgUnitDao.getNodeByUniqueId(uniqueId);
        return orgUnitDao.getCurrentTypesByParentNodeId(node.getId(), date);
    }
    @RequestMapping(method = GET, value = "/children/historyandcurrent/types/{id}/{date}")
    public List<NodeWrapper> getHistoryAndCurrentChildNodeTypesByIdAndDate(@PathVariable("id") int uniqueId, @PathVariable("date") String date) throws IOException {
        Node node = orgUnitDao.getNodeByUniqueId(uniqueId);
        return orgUnitDao.getHistoryAndCurrentTypesByParentNodeId(node.getId(), date);
    }

    @RequestMapping(method = GET, value = "/children/futureandcurrent/types/{id}/{date}")
    public List<NodeWrapper> getFutureAndCurrentChildNodeTypesByIdAndDate(@PathVariable("id") int uniqueId, @PathVariable("date") String date) throws IOException {
        Node node = orgUnitDao.getNodeByUniqueId(uniqueId);
        return orgUnitDao.getFutureAndCurrentTypesByParentNodeId(node.getId(), date);
    }

    @RequestMapping(method = GET, value = "/predecessors/{id}/{date}")
    public List<Relative> getPredecessors(@PathVariable("id") int uniqueId, @PathVariable("date") String date) throws IOException {
        Node node = orgUnitDao.getNodeByUniqueId(uniqueId);
        return orgUnitDao.getPredecessors(node.getId(), date);
    }

    @RequestMapping(method = GET, value = "/successors/{id}/{date}")
    public List<Relative> getSuccessors(@PathVariable("id") int uniqueId, @PathVariable("date") String date) throws IOException {
        Node node = orgUnitDao.getNodeByUniqueId(uniqueId);
        return orgUnitDao.getSuccessors(node.getId());
    }

    @RequestMapping(method = GET, value = "/parents/{id}/{date}")
    public List<Relative> getParents(@PathVariable("id") int uniqueId, @PathVariable("date") String date) throws IOException {
        Node node = orgUnitDao.getNodeByUniqueId(uniqueId);
        return orgUnitDao.getParents(node.getId(), date);
    }

    @RequestMapping(method = GET, value = "/children/{id}/{date}")
    public List<Relative> getChildren(@PathVariable("id") int uniqueId, @PathVariable("date") String date) throws IOException {
        Node node = orgUnitDao.getNodeByUniqueId(uniqueId);
        return orgUnitDao.getChildren(node.getId(), date);
    }

    @RequestMapping(method = GET, value = "/children/historyandcurrent/{id}/{date}")
    public List<Relative> getCurrentAndPastChildren(@PathVariable("id") int uniqueId, @PathVariable("date") String date)  throws IOException {
        Node node = orgUnitDao.getNodeByUniqueId(uniqueId);
        return orgUnitDao.getCurrentAndPastChildren(node.getId(), date);
    }

    @RequestMapping(method = GET, value = "/parents/historyandcurrent/{id}/{date}")
    public List<Relative> getCurrentAndPastParents(@PathVariable("id") int uniqueId, @PathVariable("date") String date) throws IOException {
        Node node = orgUnitDao.getNodeByUniqueId(uniqueId);
        return orgUnitDao.getCurrentAndPastParents(node.getId(), date);
    }

    @RequestMapping(method = GET, value = "/children/futureandcurrent/{id}/{date}")
    public List<Relative> getCurrentAndFutureChildren(@PathVariable("id") int uniqueId, @PathVariable("date") String date) throws IOException {
        Node node = orgUnitDao.getNodeByUniqueId(uniqueId);
        return orgUnitDao.getCurrentAndFutureChildren(node.getId(), date);
    }

    @RequestMapping(method = GET, value = "/parents/futureandcurrent/{id}/{date}")
    public List<Relative> getCurrentAndFutureParents(@PathVariable("id") int uniqueId, @PathVariable("date") String date) throws IOException {
        Node node = orgUnitDao.getNodeByUniqueId(uniqueId);
        return orgUnitDao.getCurrentAndFutureParents(node.getId(), date);
    }

    @RequestMapping(method = GET, value = "/children/all/{id}/{date}")
    public List<Relative> getAllChildren(@PathVariable("id") int uniqueId, @PathVariable("date") String date) throws IOException {
        Node node = orgUnitDao.getNodeByUniqueId(uniqueId);
        return orgUnitDao.getAllChildren(node.getId(), date);
    }


    @RequestMapping(method = GET, value = "/parents/all/{id}/{date}")
    public List<Relative> getAllParents(@PathVariable("id") int uniqueId, @PathVariable("date") String date) throws IOException {
        Node node = orgUnitDao.getNodeByUniqueId(uniqueId);
        return orgUnitDao.getAllParents(node.getId(), date);
    }

    @PutMapping("/parentUnit/properties")
    public List<EdgeWrapper> updateParentUnitProperties(@RequestBody List<EdgeWrapper> parentUnitProperties) throws IOException {
        int[] i = edgeDao.updateParentUnitProperties(parentUnitProperties);
        return parentUnitProperties;
    }

    @PutMapping("/edge")
    public List<EdgeWrapper> updateEdges(@RequestBody List<EdgeWrapper> edges) throws IOException {
        int[] i = edgeDao.updateEdges(edges);
        return edges;
    }

    @PostMapping("/edge")
    public List<EdgeWrapper> insertEdges(@RequestBody List<EdgeWrapper> edges) throws IOException {
        int[] i = edgeDao.insertEdges(edges);
        return edges;
    }

    @DeleteMapping("/edge")
    public List<EdgeWrapper> deleteEdges(@RequestBody List<EdgeWrapper> edges) throws IOException {
        int i = edgeDao.deleteEdges(edges);
        return edges;
    }

}
