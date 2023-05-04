package fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.controller;

import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.dao.EdgeDao;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.domain.EdgeWrapper;
import fi.helsinki.ohtu.organisaatiorekisteri.orgrekdb.service.EdgeService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/edge")
public class EdgeController {

    @Autowired
    private EdgeDao edgeDao;

    @Autowired
    private EdgeService edgeService;

    @RequestMapping("/types")
    public List<String> getHierarchyTypes() throws IOException {
        return edgeDao.getHierarchyTypes();
    }

    @PutMapping("/")
    public ResponseEntity<Map<String, List<EdgeWrapper>>> modifyEdges(@RequestBody Map<String, List<EdgeWrapper>> edges) throws IOException {
        try {
            edgeService.modifyEdges(edges);
            return new ResponseEntity<>(edges, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(new HashMap<>(), HttpStatus.BAD_REQUEST);
        }
    }


}
