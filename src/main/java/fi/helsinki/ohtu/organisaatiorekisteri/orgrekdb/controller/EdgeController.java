package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.EdgeDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.OrgUnitDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.EdgeWithChildUniqueId;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.EdgeWrapper;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.Node;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.service.EdgePropertiesService;

@RestController
@RequestMapping("/api/edge")
public class EdgeController {

    @Autowired
    private EdgeDao edgeDao;
    @Autowired
    private OrgUnitDao orgUnitDao;
    @Autowired
    private EdgePropertiesService edgePropertiesService;

    @RequestMapping("/types")
    public List<String> getHierarchyTypes() throws IOException {
        return edgeDao.getHierarchyTypes();
    }

    @RequestMapping("/edgehierarchies")
    public List<String> getEdgeHierarchies() throws IOException {
        return edgeDao.getEdgeHierarchies();
    }

    @RequestMapping("/paths/{hierarchy}/{uniqueId}")
    public List<EdgeWithChildUniqueId> getPathsFrom(@PathVariable("hierarchy") String hierarchy, @PathVariable("uniqueId") Integer uniqueId) throws IOException {
        Node n = orgUnitDao.getNodeByUniqueId(uniqueId);
        return edgeDao.getEdgesInHierarchy(n.getId(), hierarchy);
    }

    @PutMapping("/parents")
    public ResponseEntity<Map<String, List<EdgeWrapper>>> updateParents(@RequestBody Map<String, List<EdgeWithChildUniqueId>> edgeWithChildUniqueIdMap) {
        try {
            edgePropertiesService.updateDeleteOrSaveUpperUnit(edgeWithChildUniqueIdMap);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/children")
    public ResponseEntity<Map<String, List<EdgeWrapper>>> updateChildren(@RequestBody Map<String, List<EdgeWithChildUniqueId>> edgeWithChildUniqueIdMap) {
        try {
            edgePropertiesService.updateDeleteOrSaveChildUnit(edgeWithChildUniqueIdMap);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/")
    public ResponseEntity<Map<String, List<EdgeWithChildUniqueId>>> modifyEdges(@RequestBody Map<String, List<EdgeWithChildUniqueId>> edges) throws IOException {
        try {
            edgePropertiesService.addUpdateOrDeleteEdge(edges);
            return new ResponseEntity<>(edges, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(new HashMap<>(), HttpStatus.BAD_REQUEST);
        }
    }


}
