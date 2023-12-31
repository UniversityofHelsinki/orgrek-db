package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.EdgeDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.OrgUnitDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Cessor;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.EdgeWrapper;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Node;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.service.RelationService;

@RestController
@RequestMapping("/api/node")
public class HierarchyController {

    @Autowired
    private OrgUnitDao orgUnitDao;

    @Autowired
    private EdgeDao edgeDao;

    @Autowired
    private RelationService relationService;

    @RequestMapping(method = GET, value = "/{id}/{date}/predecessors")
    public List<Cessor> getPredecessors(@PathVariable("id") int uniqueId, @PathVariable("date") String date) throws IOException {
        Node node = orgUnitDao.getNodeByUniqueId(uniqueId);
        return relationService.getPredecessors(node.getId(), date);
    }

    @RequestMapping(method = GET, value = "/{id}/{date}/successors")
    public List<Cessor> getSuccessors(@PathVariable("id") int uniqueId, @PathVariable("date") String date) throws IOException {
        Node node = orgUnitDao.getNodeByUniqueId(uniqueId);
        return relationService.getSuccessors(node.getId(), date);
    }

    @RequestMapping(method = GET, value = "/{id}/{date}/children/{hierarchies}")
    public List<Cessor> getChildren(@PathVariable("id") int uniqueId, @PathVariable("hierarchies") String commaSeparatedHierarchies, @PathVariable("date") String date) throws IOException {
        List<String> hierarchies = Arrays.asList(commaSeparatedHierarchies.split(","));
        Node node = orgUnitDao.getNodeByUniqueId(uniqueId);
        return relationService.getChildren(node.getId(), hierarchies, date);
    }

    @RequestMapping(method = GET, value = "/{id}/{date}/parents/{hierarchies}")
    public List<Cessor> getParents(@PathVariable("id") int uniqueId, @PathVariable("hierarchies") String commaSeparatedHierarchies, @PathVariable("date") String date) throws IOException {
        List<String> hierarchies = Arrays.asList(commaSeparatedHierarchies.split(","));
        Node node = orgUnitDao.getNodeByUniqueId(uniqueId);
        return relationService.getParents(node.getId(), hierarchies, date);
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
